package com.dreamail.mercury.mail.truepush.impl;

public interface IHotmailPing {
	boolean start();

	void shutdown();

	void updateAccount(String password);

	boolean reconnect();

	void notifyReceiveMail();
}
