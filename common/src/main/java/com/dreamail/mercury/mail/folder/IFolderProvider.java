package com.dreamail.mercury.mail.folder;

import javax.mail.Folder;

import com.dreamail.mercury.domain.WebAccount;

public interface IFolderProvider {
	/**
	 * 添加一个folder.
	 * @return true/false
	 */
	boolean addFolder(WebAccount account,String folder);
	
	/**
	 * 修改指定folder.
	 * @return true/false
	 */
	boolean modifyFolder(WebAccount account,String folder1,String folder2);
	
	/**
	 * 删除指定folder.
	 * @return true/false
	 */
	boolean deleteFolder(WebAccount account,String folder);
	
	/**
	 * 从一个folder中拷贝邮件到另一个folder.
	 * @return true/false
	 */
	boolean copyMessage(WebAccount account,String folder1,String folder2,String[] uuids);
	
	/**
	 * 列出账号的文件夹.
	 * @param account
	 * @return
	 */
	Folder[] listFolder(WebAccount account);
}
