package com.dreamail.mercury.petasos.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamail.mercury.dal.service.SendXmlService;
import com.dreamail.mercury.util.ExceSupport;
import com.dreamail.mercury.util.ServletUtil;

public class ProtocolExchangeServlet extends ProtocolProcess {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String content = null;
		String acceptEncoding = request.getHeader("Accept-Encoding");
		boolean supportGzip = acceptEncoding == null ? false : acceptEncoding
				.toLowerCase().contains("gzip");
		boolean supportLzw = acceptEncoding == null ? false : acceptEncoding
				.toLowerCase().contains("lzw");
		if (supportGzip) {
			response.setHeader("Content-Encoding", "gzip");
		} else if (supportLzw) {
			response.setHeader("Content-Encoding", "lzw");
		}

		try {
			// 将流转换成字符串.
			content = ServletUtil.parse(request.getInputStream());
		} catch (IOException e1) {
			logger.error("protocol is null !", e1);
		}
		String num = request.getHeader("num");
		if (content == null || "".equals(content)) {
			logger.warn("protocol is null !");
			try {
				sendXmlProcess(failureXml, response, null, null, num);
			} catch (IOException e) {
				logger.error("send message err!", e);
			}
			return;
		}
		String uuid = request.getHeader("uuid");
		String seq = request.getHeader("seq");
		if (uuid != null && seq != null && num != null) {
			PageProcess(uuid, seq, response, content, num);
		} else {
			try {
				sendXml(content, response, num);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void PageProcess(String uuid, String seqNo,
			HttpServletResponse response, String content, String num) {
		try {
			int seq = Integer.parseInt(seqNo);
			if (content != null && !"".equals(content)) {
				String xmlstr = new String(
						content.trim().getBytes("iso8859-1"), "utf-8");
				boolean state = addXML(uuid, xmlstr, seq, num);
				if (seq != 0 && state == true) {
					sendXmlProcess(successXml, response, uuid, seqNo, num);
					return;
				} else if (seq == 0 && state == true) {
					String str = SendXmlService.getXml(uuid + num);
					SendXmlService.emptyXml(uuid + num);
					sendXml(str, response, num);
					return;
				}
				sendXmlProcess(failureXml, response, uuid, seqNo, num);
			}
		} catch (IOException e) {
			if (e.getMessage() != null && e.getMessage().contains("GZIP")) {
				try {
					sendXmlProcess(failureXml, response, uuid, seqNo, num);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (Exception e) {
			String exceptXml = ExceSupport.excHandle(e);
			try {
				sendXmlProcess(exceptXml, response, null, null, num);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	// public boolean getString(HttpServletResponse response, String uuid,int
	// seqNo,String content,String num)
	// throws Exception {
	// boolean state = false;
	// if(content!=null &&!"".equals(content)){
	// String xmlstr = new String(content.trim().getBytes("iso8859-1"),"utf-8");
	// state = addXML(uuid,xmlstr,seqNo,num);
	// if(seqNo!=0){
	// sendXmlProcess(successXml,response,uuid,seqNo,num);
	// logger.error("err!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	// }
	// }
	// return state;
	// }

	private boolean addXML(String uuid, String xmlstr, int seqNo, String num) {
		if (seqNo == 1) {
			SendXmlService.emptyXml(uuid + num);
		}
		return SendXmlService.addXml(uuid + num, xmlstr);
	}

}
