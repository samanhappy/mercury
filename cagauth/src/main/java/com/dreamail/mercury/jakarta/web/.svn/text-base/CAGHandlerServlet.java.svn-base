package com.dreamail.mercury.jakarta.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.jakarta.handle.HandlerFactory;
import com.dreamail.mercury.jakarta.handle.INotificationHandler;
import com.dreamail.mercury.jakarta.xstream.XStreamParser;
import com.dreamail.mercury.util.CAGConstants;
import com.dreamail.mercury.util.UPEConstants;

@WebServlet(asyncSupported = true, urlPatterns = { "/cagapi" })
public class CAGHandlerServlet extends HttpServlet {

	private static final long serialVersionUID = 5791825854722557813L;

	private static Logger logger = LoggerFactory
			.getLogger(CAGHandlerServlet.class);

	private CAGParserObject CAGObject;

	private String notification;

	private static final String properties = "config.properties";

	protected static long suspendTime;

	/**
	 * 加载配置信息.
	 */
	static {
		InputStream inputStream = CAGHandlerServlet.class.getClassLoader()
				.getResourceAsStream(properties);
		Properties p = new Properties();
		try {
			p.load(inputStream);
			suspendTime = Long.parseLong((String) p.get("suspendTime"));
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (response.isCommitted()) {
			logger.info("response has been commited, return...");
			return;
		}

		// 获取请求内容
		String requestStr = parseRequestStr(request);

		if (requestStr != null) {

			requestStr = requestStr.trim();
			logger.info("client request str is <--" + requestStr + "-->");

			// 判断请求内容是否合法
			if (!validateRequestContent(requestStr)) {
				ResponseSender.response2Client(request, response,
						XStreamParser.CAGObject2Xml(CAGObject));
				return;
			}

			// 判断请求类型是否合法
			if (!validateNoticification(CAGObject.getNotification())) {
				ResponseSender.response2Client(request, response,
						XStreamParser.CAGObject2Xml(CAGObject));
				return;
			}

			INotificationHandler handler = HandlerFactory.getHandler(CAGObject);
			if (handler.handle()) {
				ResponseSender.response2Client(request, response,
						XStreamParser.CAGObject2Xml(CAGObject));
				return;
			}

			// 处理之前未响应的请求
			AsyncContext acOld = AsyncContextManager.getInstance()
					.getCAGContext(CAGObject.getUuid());
			if (acOld != null && acOld.getRequest().isAsyncStarted()) {
				logger.info("complete old request......");
				competeOldRequest(acOld);
				acOld.complete();
			}

			// 挂起请求
			logger.info("start suspend.....");
			AsyncContext ac = request.startAsync(request, response);
			ac.getRequest().setAttribute(CAGConstants.KEY_OF_NOTIFICATION,
					notification);
			ac.getRequest().setAttribute(CAGConstants.KEY_OF_UID,
					CAGObject.getUuid());
			ac.setTimeout(suspendTime);
			ac.addListener(new PMAsyncListener());
			AsyncContextManager.getInstance().putCAGContext(
					CAGObject.getUuid(), ac);
		} else {
			logger.info("client request str is null.");
		}

	}

	/**
	 * 响应上一次存在的请求.
	 * 
	 * @param context
	 */
	private void competeOldRequest(AsyncContext context) {
		CAGParserObject CAGObject = new CAGParserObject();
		CAGObject.setUuid(CAGObject.getUuid());
		CAGObject.setNotification((String) context.getRequest().getAttribute(
				UPEConstants.NOTIFICATION_KEY));
		CAGObject.setCode(CAGConstants.CAG_CLIENT_UNREACHABLE_CODE);
		CAGObject.setStatus("Client Unreachable");
		ResponseSender.response2Client(null,
				(HttpServletResponse) context.getResponse(),
				XStreamParser.CAGObject2Xml(CAGObject));
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
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

	/**
	 * 判断请求内容是否合法.
	 * 
	 * @return
	 */
	private boolean validateRequestContent(String requestStr) {
		// 解析请求协议，如果接触出错，直接返回Bad Request响应
		try {
			CAGObject = XStreamParser.xml2CAGObject(requestStr);
		} catch (Exception e) {
			logger.error("request content is error.", e);
			CAGObject = new CAGParserObject();
			CAGObject.setCode(CAGConstants.CAG_BAD_REQUEST_CODE);
			CAGObject.setStatus("Bad Request");
			return false;
		}
		return true;
	}

	/**
	 * 判断通知类型是否合法.
	 * 
	 * @param notification
	 * @return
	 */
	private boolean validateNoticification(String notification) {
		if (CAGConstants.CAG_GETCLIENTCONFIG_NOTIF
				.equalsIgnoreCase(notification)
				|| CAGConstants.CAG_UPDATECONFIG_NOTIF
						.equalsIgnoreCase(notification)
				|| CAGConstants.CAG_GETSERVERCONFIG_NOTIF
						.equalsIgnoreCase(notification)
				|| CAGConstants.CAG_SETSERVERCONFIG_NOTIF
						.equalsIgnoreCase(notification)
				|| CAGConstants.CAG_SERVICEAUTHENTICATION_NOTIF
						.equalsIgnoreCase(notification)) {
			this.notification = notification;
			return true;
		} else {
			logger.error("notification type is error.");
			CAGObject.setCode(CAGConstants.CAG_BAD_REQUEST_CODE);
			CAGObject.setStatus("Bad Request");
			return false;
		}
	}

}
