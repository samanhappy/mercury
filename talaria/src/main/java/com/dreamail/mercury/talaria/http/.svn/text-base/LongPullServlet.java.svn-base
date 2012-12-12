/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamail.mercury.talaria.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.talaria.http.handler.BADRequestHandler;
import com.dreamail.mercury.talaria.http.handler.IRequestHandler;
import com.dreamail.mercury.talaria.http.handler.UPERequestHandler;

/**
 * upe推送http长连接方式
 * 
 * @author kai.li
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/LongPullServlet" })
public class LongPullServlet extends HttpServlet {

	private static final long serialVersionUID = -749041179320022453L;

	public static final Logger logger = LoggerFactory
			.getLogger(LongPullServlet.class);

	/**
	 * 处理Post请求.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Accept-Encoding:" + request.getHeader("Accept-Encoding"));
		String acceptEncoding = request.getHeader("Accept-Encoding");
		boolean supportGzip = acceptEncoding == null ? false : acceptEncoding
				.toLowerCase().contains("gzip");
		boolean supportLzw = acceptEncoding == null ? false : acceptEncoding
				.toLowerCase().contains("lzw");
		if (response.isCommitted()) {
			logger.error("response has been commited, return...");
			return;
		}
		// 获取请求内容
		String requestStr = parseRequestStr(request).trim();
		logger.info("client request str is <--" + requestStr + "-->");

		IRequestHandler handler = null;

		// 根据请求的类型生成相应的请求处理器
		if (requestStr.endsWith("</UPE>")) {
			handler = new UPERequestHandler(request, response, requestStr);
		} else {
			handler = new BADRequestHandler(request, response, requestStr);
		}
		if (supportGzip) {
			response.setHeader("Content-Encoding", "gzip");
		} else if (supportLzw) {
			response.setHeader("Content-Encoding", "lzw");
		}
		// 处理请求
		handler.handle();
		// 响应请求
		handler.response();
	}

	/**
	 * 处理Get请求.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	/**
	 * 获取请求内容.
	 * 
	 * @param request
	 * @return
	 */
	private String parseRequestStr(HttpServletRequest request) {
		BufferedReader br = null;
		StringBuffer sb = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					(ServletInputStream) request.getInputStream()));
			String line = null;
			sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (sb != null) {
			return sb.toString().trim();
		}
		return null;
	}

}
