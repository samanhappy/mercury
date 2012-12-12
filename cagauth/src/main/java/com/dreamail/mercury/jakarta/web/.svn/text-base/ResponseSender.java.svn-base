package com.dreamail.mercury.jakarta.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.jakarta.xstream.XStreamParser;

public class ResponseSender {
	public static final Logger logger = LoggerFactory
			.getLogger(ResponseSender.class);

	/**
	 * 返回没有gzip压缩的响应.
	 * 
	 * @param response
	 * @param content
	 */
	public static void response2Client(HttpServletRequest request,
			HttpServletResponse response, String content) {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");

		try {
			logger.info("ResponseSender content:" + content);
			byte[] b = content.getBytes("utf-8");
			response.setContentLength(b.length);
			response.getOutputStream().write(b);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			logger.error("response2Client err!", e);
		} finally {
			try {
				if (response.getOutputStream() != null) {
					response.getOutputStream().close();
				}
			} catch (IOException e) {
				logger.error("error to close stream ", e);

			}

		}
	}

	/**
	 * 返回没有gzip压缩的响应.
	 * 
	 * @param response
	 * @param content
	 */
	public static void response2Client(HttpServletRequest request,
			HttpServletResponse response, CAGParserObject CAGObject) {
		String content = XStreamParser.CAGObject2Xml(CAGObject);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");

		try {
			logger.info("ResponseSender content:" + content);
			byte[] b = content.getBytes("utf-8");
			response.setContentLength(b.length);
			response.getOutputStream().write(b);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			logger.error("response2Client err", e);
		} finally {
			try {
				if (response.getOutputStream() != null) {
					response.getOutputStream().close();
				}
			} catch (IOException e) {
				logger.error("error to close stream ", e);
			}

		}
	}

}
