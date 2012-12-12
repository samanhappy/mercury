package com.dreamail.mercury.imap;

import java.util.List;

public interface ImapProcessor {
	
	/**
	 * Determine the protocol type
	 * @param command
	 * @return boolean
	 */
	public boolean tag(String command);
	
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
	public List<String> proces(String command,ImapSession ctx) throws Exception;
}
