package com.dreamail.mercury.petasos.servlet;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.petasos.IFunctionDispatcher;
import com.dreamail.mercury.petasos.impl.HandlerDispatcher;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.LzwCompression;
import com.dreamail.mercury.xml.factory.XMLDocumentFactory;
import com.dreamail.mercury.xml.interfaces.XMLDocumentDAO;

public class ProtocolProcess extends HttpServlet {

	private static final long serialVersionUID = -6037512784284755251L;

	public static final Logger logger = LoggerFactory
			.getLogger(ProtocolProcess.class);

	protected static String successXml = "<PushMail><Header><Version>PushMail1.0</Version></Header><Body><Target name=\"Email\" isAtom=\"true\" ><Cmd name=\"EmailSend\"><Status><Code>0</Code><Message>send mail success</Message></Status></Cmd></Target></Body></PushMail>";

	protected static String failureXml = "<PushMail><Header><Version>PushMail1.0</Version></Header><Body><Target name=\"Email\" isAtom=\"true\" ><Cmd name=\"EmailSend\"><Status><Code>"
			+ ErrorCode.CODE_SendEmail_FAIL
			+ "</Code><Message>send mail failure,Not in GZIP format</Message></Status></Cmd></Target></Body></PushMail>";

	/**
	 * 
	 * @param xmlString
	 * @param response
	 * @throws Exception
	 */
	protected void sendXml(String xmlString, HttpServletResponse response,
			String num) throws Exception {
		logger.info(xmlString);
		XMLDocumentDAO xml = new XMLDocumentFactory();
		PushMail sendXml = null;
		PushMail receiveXml = null;
		sendXml = xml.getObjectList(xmlString);
		IFunctionDispatcher fd = new HandlerDispatcher();
		receiveXml = fd.dispatch(sendXml);
		String content = xml.getResponseMessage(receiveXml);
		sendXmlProcess(content, response, null, null, num);
	}

	/**
	 * 
	 * @param content
	 * @param response
	 * @param uuid
	 * @param seq
	 * @throws IOException
	 */
	protected void sendXmlProcess(String content, HttpServletResponse response,
			String uuid, int seq, String num) throws IOException {
		sendXmlProcess(content, response, uuid, String.valueOf(seq), num);
	}

	/**
	 * 
	 * @param content
	 * @param response
	 * @param uuid
	 * @param seq
	 * @throws IOException
	 */
	protected void sendXmlProcess(String content, HttpServletResponse response,
			String uuid, String seq, String num) throws IOException {
		String contentEncoding = response.getHeader("Content-Encoding");
		boolean supportGzip = contentEncoding == null ? false : contentEncoding
				.toLowerCase().contains("gzip");
		boolean supportLzw = contentEncoding == null ? false : contentEncoding
				.toLowerCase().contains("lzw");
		try {
			response.setContentType("text/xml;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.setStatus(HttpServletResponse.SC_OK);
			if (uuid != null && seq != null) {
				response.setHeader("uuid", uuid);
				response.setHeader("seq", seq);
				// 返回时给客户端
				response.setHeader("num", num);
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			BufferedOutputStream bufos = null;
			if (supportGzip) {
				logger.info("response use gzip...");
				bufos = new BufferedOutputStream(new GZIPOutputStream(bos));
				bufos.write(content.getBytes());
				bufos.close();
			} else if (supportLzw) {
				logger.info("response use lzw...");
				new LzwCompression().compress(content, bos);
			} else {
				bufos = new BufferedOutputStream(bos);
				bufos.write(content.getBytes());
				bufos.close();
			}
			byte[] b = bos.toByteArray();
			bos.close();
			response.setContentLength(b.length);
			response.getOutputStream().write(b);
			response.getOutputStream().flush();
		} catch (IOException e) {
			logger.error("sendMessage error!", e);
		} finally {
			if (response.getOutputStream() != null) {
				response.getOutputStream().close();
			}
		}
	}
}
