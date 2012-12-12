package com.dreamail.mercury.proces;

import com.dreamail.mercury.domain.Context;


public interface IProces {
	/**
	 * 任务传递接口
	 * @param context
	 * @throws Exception
	 */
	public void doProces(Context context)throws Exception;
}
