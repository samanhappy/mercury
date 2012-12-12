package com.dreamail.mercury.mail.connection;

import javax.mail.Folder;
import javax.mail.Store;
import javax.mail.Transport;

public interface ICloseConnection {
	public void closeConnection(Transport trans);
	
	public void closeConnection(Store store,Folder folder);
}
