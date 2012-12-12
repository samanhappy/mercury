package com.dreamail.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.SendMessageDao;
import com.dreamail.mercury.mail.delete.CleanDataTask;
import com.dreamail.mercury.pojo.Clickoo_send_message;

public class SendMessageDataCleaner extends Thread{

	private static Logger logger = LoggerFactory.getLogger(SendMessageDataCleaner.class);
	
	@Override
	public void run() {
		logger.info("start to clean send message data...");
		List<Clickoo_send_message> sendList = new SendMessageDao()
		.getAllSendDoneMessage();
		CleanDataTask.removeSendEmlFromNFS(sendList);
		logger.info("clean send message data is done...");
	}
}
