package com.dreamail.mercury.talaria.http;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.util.LzwCompression;

public class ResponseSender {
	public static final Logger logger = LoggerFactory
			.getLogger(ResponseSender.class);

	public static void response2Client(HttpServletResponse response,
			String content) {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		String contentEncoding = response.getHeader("Content-Encoding");
		boolean supportGzip = contentEncoding == null ? false : contentEncoding
				.toLowerCase().contains("gzip");
		boolean supportLzw = contentEncoding == null ? false : contentEncoding
				.toLowerCase().contains("lzw");
		try {
			logger.info("ResponseSender content:" + content);
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
			response.getOutputStream().close();
		} catch (IOException e1) {
			logger.error("response2Client err!", e1);
		} finally {
			try {
				if (response.getOutputStream() != null) {
					response.getOutputStream().close();
				}
			} catch (IOException e1) {
				logger.error("error to close stream ", e1);
			}

		}
	}
}
