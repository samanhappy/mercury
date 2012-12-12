package com.dreamail.mercury.mail.connection;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.Transport;

public class CloseConnectionImpl implements ICloseConnection{

	@Override
	public void closeConnection(Transport trans) {
		if(trans!=null && trans.isConnected()) {
			try {
				trans.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void closeConnection(Store store, Folder folder) {
		if(folder!=null && folder.isOpen()){
			try {
				folder.close(false);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		if(store!=null && store.isConnected()){
			try {
				store.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
	
}
