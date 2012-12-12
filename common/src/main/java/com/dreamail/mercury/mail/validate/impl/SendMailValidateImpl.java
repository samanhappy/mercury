/**
 * 
 */
package com.dreamail.mercury.mail.validate.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailbody;
import com.dreamail.mercury.domain.WebEmailhead;
import com.dreamail.mercury.mail.validate.IMailValidate;
import com.dreamail.mercury.sender.operator.SendOperator;

/**
 * @author meng.sun
 * 
 */
public class SendMailValidateImpl implements IMailValidate {

	Logger logger = LoggerFactory.getLogger(SendMailValidateImpl.class);
	
	@Override
	public boolean validate(WebAccount account) {

		boolean validate = false;
		
		SendOperator operator = new SendOperator();
		WebEmail email = getWebEmail(account);

		try {
			validate = operator.sendMailWithoutSave(email);
		} catch (Exception e) {
			logger.warn("failed to validate account:" + account.getName() + " when send email use itself server", e);
		} 
		
		return validate;

	}
	
	private WebEmail getWebEmail(WebAccount account) {
		
		WebEmailhead head = new WebEmailhead();
		head.setFrom(account.getName());
		head.setTo("meng.sun@archermind.com");
		head.setSubject("smtp test");
		WebEmailbody body = new WebEmailbody();
		body.setData("hello this is a smtp test mail");
		body.setDatatype("");
		WebEmail email = new WebEmail();
		email.setBody(body);
		email.setHead(head);
		email.setAccount(account);
		
		return email;
	}

}
