package com.dreamail.mercury.petasos.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailhead;
import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.QwertTarget;
import com.dreamail.mercury.petasos.IFunctionDispatcher;
import com.dreamail.mercury.petasos.impl.HandlerDispatcher;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.util.MethodName;
import com.dreamail.mercury.util.TypeName;
import com.dreamail.mercury.xml.factory.XMLDocumentFactory;
import com.dreamail.mercury.xml.interfaces.XMLDocumentDAO;

public class QwertmlServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String xmlString = request.getParameter("xml");
		System.out.println("sendXml:==============================\n"
				+ xmlString);
		XMLDocumentDAO xml = new XMLDocumentFactory();
		PushMail sendXml = null;
		PushMail receiveXml = null;
		try {
			sendXml = xml.getObjectList(xmlString);
			IFunctionDispatcher fd = new HandlerDispatcher();
			receiveXml = fd.dispatch(sendXml);
			System.out.println("receiveXml:==============================\n"
					+ xml.getResponseMessage(receiveXml));
			response.setContentType("text/plain;charset=utf-8");
			response.getWriter().print(xml.getResponseMessage(receiveXml));
			response.getWriter().flush();
			response.getWriter().close();
//			updataMessageStatus(receiveXml);
		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/plain;charset=utf-8");
			response.getWriter().print(e.toString());
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

	public void updataMessageStatus(PushMail receiveXml) {
		MessageService messageDao = new MessageService();
		Cred cred = receiveXml.getCred();
		QwertTarget[] targets = receiveXml.getTargets();
		for (int i = 0; i < targets.length; i++) {
			if (targets[i].getTypename().equals(TypeName.EMAIL)) {
				QwertCmd[] commands = targets[i].getCommands();
				for (int j = 0; j < commands.length; j++) {
					if (commands[j].getMethodName().equals(
							MethodName.EMAIL_LIST)) {
						Object[] objects = commands[j].getObjects();
						for (int k = 0; k < objects.length; k++){
							if (objects[k] instanceof WebEmail) {
								if (cred.getUuid() != null) {
									WebEmail email = (WebEmail) objects[k];
									if (email.getHead() != null) {
										WebEmailhead head = email.getHead();
										Clickoo_message message = new Clickoo_message();
										if (head.getMessageId() != null) {
											message.setId(Long.valueOf(head
													.getMessageId()));
											message.setStatus(1);
											messageDao
													.updateMessageState(message);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
