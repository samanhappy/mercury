/**
 * 
 */
package com.dreamail.mercury.sender.auth;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author meng.sun
 *
 */
public class AuthUser extends Authenticator {

	/* mailbox name */
	private String mailboxName = null;

	/* mailbox password */
	private String password = null;

	public AuthUser(String mailboxName, String password) {
		this.mailboxName = mailboxName;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {

		return new PasswordAuthentication(mailboxName, password);

	}
}
