/**
 * 
 */
package com.dreamail.mercury.dal.service;

import com.dreamail.mercury.domain.EmailCacheObject;
import com.dreamail.mercury.pojo.Clickoo_message_data;
import com.dreamail.mercury.util.ZipUtil;

/**
 * @author meng.sun
 * 
 */
public class DataService {

	/**
	 * 根据id查询邮件正文.
	 * 
	 * @param id
	 *            data id
	 * @return Clickoo_message_data
	 */
	public Clickoo_message_data selectDataById(Long id,EmailCacheObject emailCache) {
		Clickoo_message_data data = null;
		if(emailCache!=null && emailCache.getData()!=null){
				data = new Clickoo_message_data();
				data.setData(new String(ZipUtil.decompress(emailCache.getData())));
				data.setSize(emailCache.getData_size());
				data.setId(id);
		}
		return data;
	}

}
