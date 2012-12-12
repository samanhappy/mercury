package com.dreamail.talaria.memcached;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.pojo.Clickoo_send_message;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.UPEConstants;

public class AbstractCacheManager {

	public static Logger logger = LoggerFactory
			.getLogger(AbstractCacheManager.class);

	/**
	 * 增加新邮件标记.
	 * 
	 * @param aid
	 * @return
	 */
	public static void addNewMessageFlag4String(UPECacheObject cache, String aid) {
		String new_msg_flag_map = cache.getNew_msg_flag_map();
		if (new_msg_flag_map == null) {
			new_msg_flag_map = "{}";
		}
		JSONObject json = JSONObject.fromObject(new_msg_flag_map);
		json.put(aid, true);
		cache.setNew_msg_flag_map(json.toString());
	}

	/**
	 * 增加邮件发送状态标记.
	 * 
	 * @param aid
	 * @return
	 */
	public static void addMailSentFlag(UPECacheObject cache,
			List<Clickoo_send_message> list) {
		if (list != null && list.size() > 0) {
			String mail_sent_state_map = cache.getMail_sent_state_map();
			if (mail_sent_state_map == null) {
				mail_sent_state_map = "{}";
			}
			JSONObject json = JSONObject.fromObject(mail_sent_state_map);
			String upeState = null;
			for (Clickoo_send_message message : list) {
				if (Constant.MAIL_SENT_SUCCEESS_STATUS == message.getStatus()) {
					upeState = UPEConstants.UPE_SENDMAIL_SUCCESS_STATE;
				} else if (Constant.MAIL_SENT_FAIL_STATUS == message
						.getStatus()) {
					upeState = UPEConstants.UPE_SENDMAIL_FAIL_STATE;
				}

				if (upeState != null && message.getId() != 0
						&& message.getHid() != 0) {
					json.put(String.valueOf(message.getId()), new StringBuffer(
							upeState).append(",").append(message.getHid())
							.toString());

				} else {
					logger.error("wrong mail sent info");
				}
			}

			cache.setMail_sent_state_map(json.toString());
		}
	}

	/**
	 * 增加邮件发送状态标记.
	 * 
	 * @param aid
	 * @return
	 */
	public static void addMailSentFlag(UPECacheObject cache, String mid,
			String state, String hid) {
		String mail_sent_state_map = cache.getMail_sent_state_map();
		if (mail_sent_state_map == null) {
			mail_sent_state_map = "{}";
		}
		JSONObject json = JSONObject.fromObject(mail_sent_state_map);
		json.put(mid, new StringBuffer(state).append(",").append(hid)
				.toString());
		cache.setMail_sent_state_map(json.toString());
	}

	/**
	 * 得到第一个邮件发送状态通知并从缓存中删除该通知.
	 * 
	 * @param state_map
	 */
	@SuppressWarnings("unchecked")
	public static Map.Entry<String, String> getFirstMailSentId(
			UPECacheObject cache) {

		String state_map = cache.getMail_sent_state_map();

		if (state_map != null) {

			Map.Entry<String, String> entry = null;
			String key = null;
			String value = null;

			JSONObject json = JSONObject.fromObject(state_map);
			for (Object obj : json.entrySet()) {
				entry = (Map.Entry<String, String>) obj;
				key = entry.getKey();
				value = entry.getValue();
				break;
			}
			if (key != null) {
				json.remove(key);
				cache.setMail_sent_state_map(json.toString());
				entry.setValue(value);
			}
			return entry;
		}
		return null;
	}

	/**
	 * 删除新邮件标记.
	 * 
	 * @param aid
	 * @return
	 */
	public static void removeNewMessageFlag(UPECacheObject cache, String aid) {
		String new_msg_flag_map = cache.getNew_msg_flag_map();
		if (new_msg_flag_map != null) {
			JSONObject json = JSONObject.fromObject(new_msg_flag_map);
			json.put(aid, false);
			cache.setNew_msg_flag_map(json.toString());
		}
	}

	/**
	 * 查询新邮件标记.
	 * 
	 * @param aid
	 * @return
	 */
	public static boolean getNewMessageFlag(String new_msg_flag_map, String aid) {
		if (new_msg_flag_map != null) {
			JSONObject json = JSONObject.fromObject(new_msg_flag_map);
			return json.containsKey(aid);
		}
		return false;
	}

	/**
	 * 查询新邮件标记.
	 * 
	 * @param aid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean hasNewMessageFlag(UPECacheObject cache) {
		String new_msg_flag_map = cache.getNew_msg_flag_map();
		if (new_msg_flag_map != null) {
			JSONObject json = JSONObject.fromObject(new_msg_flag_map);
			for (Object obj : json.entrySet()) {
				Map.Entry<String, Boolean> entry = (Map.Entry<String, Boolean>) obj;
				if (entry.getValue()) {
					return true;
				}
			}
		}
		return false;
	}

}
