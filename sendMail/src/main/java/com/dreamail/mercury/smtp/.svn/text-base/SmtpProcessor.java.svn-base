package com.dreamail.mercury.smtp;

import java.util.List;

public interface SmtpProcessor {
	
	/**
	 * Determine the protocol type
	 * @param command
	 * @return boolean
	 */
	public boolean tag(String command,SmtpSession ctx);
	
	/**
	 * paeser protocol,save object in youSelf Impl
	 * @param command
	 * @return
	 * @throws Exception 
	 */
	public void parser(String command) throws Exception;
	
	/**
	 * proces interface
	 * @param command
	 * @return
	 */
	public List<String> proces(String command,SmtpSession ctx) throws Exception;
}
