package com.dreamail.mercury.mail.folder.impl;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.TimeoutException;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.connection.CloseConnectionImpl;
import com.dreamail.mercury.mail.connection.ICloseConnection;
import com.dreamail.mercury.mail.folder.IFolderProvider;
import com.dreamail.mercury.mail.receiver.AbstractImapProvide;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
/**
 * 增加、删除、重命名文件夹等操作.
 * @author pengkai.wang
 *
 */
public class FolderProviderImpl extends AbstractImapProvide implements
		IFolderProvider {

	/**
	 * 添加一个folder.
	 * 
	 * @param account
	 * @param folder
	 * @return true/false
	 */
	@Override
	public boolean addFolder(WebAccount account, String folder) {
		try {
			IMAPStore store = getStore(account);
			IMAPFolder inbox = (IMAPFolder) store.getFolder(folder);
			if (!inbox.exists()) {
				inbox.create(Folder.HOLDS_MESSAGES);
			}
			closeConection(store,new Folder[]{inbox});
		} catch (MessagingException e) {
			logger.error("Create folder " + folder + "for account:"
					+ account.getName() + "fail.", e);
			return false;
		}
		return true;
	}

	/**
	 * 从一个folder中拷贝邮件到另一个folder.
	 * 
	 * @param account
	 * @param folder1
	 * @param folder2
	 * @param uuids
	 * @return true/false
	 */
	@Override
	public boolean copyMessage(WebAccount account, String folder1,
			String folder2, String[] uuids) {
		try {
			IMAPStore store = getStore(account);
			IMAPFolder inbox = (IMAPFolder) store.getFolder(folder1);
			IMAPFolder inbox1 = (IMAPFolder) store.getFolder(folder2);
			if (uuids != null) {
				Message[] messages = new Message[uuids.length];
				if(!inbox.isOpen()) inbox.open(Folder.READ_WRITE);
				for (int i = 0; i < uuids.length; i++) {
					messages[i] = inbox.getMessageByUID(Long
							.parseLong(uuids[i]));
				}
				inbox.copyMessages(messages, inbox1);
			}
			closeConection(store,new Folder[]{inbox,inbox1});
		} catch (MessagingException e) {
			logger
					.error("CopyMessage from folder " + folder1 + " to "
							+ folder2 + "for account:" + account.getName()
							+ "fail.", e);
			return false;
		}
		return true;
	}

	/**
	 * 删除指定folder.
	 * 
	 * @param account
	 * @param folder
	 * @return true/false
	 */
	@Override
	public boolean deleteFolder(WebAccount account, String folder) {
		try {
			IMAPStore store = getStore(account);
			IMAPFolder inbox = (IMAPFolder) store.getFolder(folder);
			if(inbox.isOpen()) inbox.close(false);
			inbox.delete(true);
			closeConection(store,new Folder[]{inbox});
		} catch (MessagingException e) {
			logger.error("Delete folder " + folder + "for account:"
					+ account.getName() + "fail.", e);
			return false;
		}
		return true;
	}

	/**
	 * 修改指定folder.
	 * 
	 * @param account
	 * @param folder1
	 * @param folder2
	 * @return true/false
	 */
	@Override
	public boolean modifyFolder(WebAccount account, String folder1,
			String folder2) {
		try {
			IMAPStore store = getStore(account);
			IMAPFolder inbox = (IMAPFolder) store.getFolder(folder1);
			IMAPFolder inbox1 = (IMAPFolder) store.getFolder(folder2);
//			inbox.close(false); // gmail 需要关闭 yahoo不需要
			inbox.renameTo(inbox1);
			closeConection(store,new Folder[]{inbox,inbox1});
		} catch (MessagingException e) {
			logger.error("Modify  folder " + folder1 + " to " + folder2
					+ " for account:" + account.getName() + "fail.", e);
			return false;
		}
		return true;
	}
	
	/**
	 * 列出账号的文件夹.
	 * @param account
	 * @return
	 */
	@Override
	public Folder[] listFolder(WebAccount account){
		Folder[] folders = null;
		try {
			IMAPStore store = getStore(account);
			IMAPFolder inbox = (IMAPFolder) store.getDefaultFolder();
			folders = inbox.list();
		} catch (MessagingException e) {
			logger.error("listFolder" +  " for account:" +
					account.getName() + "fail.", e);
		}
		return folders;
	}

	/**
	 * 关闭连接.
	 * 
	 * @param store
	 * @param folders
	 */
	private void closeConection(Store store, Folder[] folders) {
		ICloseConnection closeConnection = new CloseConnectionImpl();
		if(folders!=null){
			for(Folder folder:folders){
				closeConnection.closeConnection(store, folder);
			}
		}
	}

	@Override
	public void receiveLargeMail(Context context) throws MessagingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveMail(Context context) throws MessagingException,
			TimeoutException {
		// TODO Auto-generated method stub

	}

}
