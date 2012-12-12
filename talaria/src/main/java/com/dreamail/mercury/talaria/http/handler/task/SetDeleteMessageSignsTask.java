package com.dreamail.mercury.talaria.http.handler.task;

import java.util.List;

import com.dreamail.mercury.dal.dao.MessageDao;
/**
 * 执行根据批量message id更新其对应的status.
 * @author Administrator
 *
 */
public class SetDeleteMessageSignsTask implements Runnable {
	private List<String> mids;
	
	public SetDeleteMessageSignsTask(List<String> mids) {
		super();
		this.mids = mids;
	}

	@Override
	public void run() {
		new MessageDao().setDeleteMessageSigns(mids);
	}

}
