/*
 *  你看，你看，我的程序
 *  http://www.@!#!&.com
 *  jackjhy@gmail.com
 * 
 *  听说牛粪离钻石只有一步之遥，听说稻草离金条只有一步之遥，
 *  听说色情离艺术只有一步之遥，听说裸体离衣服只有一步之遥，
 *  听说龙芯离AMD只有�?��之遥，听说神舟离月球只有�?��之遥�? *  听说现实离乌邦只有一步之遥，听说社会离共产只有一步之遥，
 *  听说台湾离独立只有一步之遥，听说日本离玩完只有一步之遥，
 */
package com.dreamail.mercury.petasos.impl;

import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.QwertTarget;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.IFunctionDispatcher;
import com.dreamail.mercury.util.ErrorCode;

/**
 * 
 * @author tiger Created on 2010-7-14 9:19:08
 */
public class HandlerDispatcher implements IFunctionDispatcher {

	private static ServiceLoader<AbstractFunctionHandle> serviceLoader = ServiceLoader
			.load(AbstractFunctionHandle.class);
	private static ConcurrentHashMap<String, AbstractFunctionHandle> handles = new ConcurrentHashMap<String, AbstractFunctionHandle>();

	static {
		for (AbstractFunctionHandle handle : serviceLoader) {
			handles.put(handle.getClass().toString(), handle);
		}
	}

	private void reloadServices() {
		serviceLoader = ServiceLoader.load(AbstractFunctionHandle.class);
		for (AbstractFunctionHandle handle : serviceLoader) {
			handles.put(handle.getTypename(), handle);
		}
	}

	@Override
	public PushMail dispatch(PushMail pushMail) throws Exception {
		if(pushMail.getTargets() == null ){
			return pushMail;
		}
		QwertTarget[] targets = pushMail.getTargets();
		QwertTarget[] resultTargets = new QwertTarget[targets.length];
		AbstractFunctionHandle handle = null;
		for (int i = 0; i < targets.length; i++) {
			handle = getController(targets[i].getTypename());
			if (handle == null) {
				resultTargets[i] = getQwertTargetbyNull();// TODO 需要实现当不存在此类型操作的提示返回数据包，待实现
			} else {
				resultTargets[i] = handle.handle(targets[i], pushMail);
			}
		}
		return pushMail;
	}
	
	public AbstractFunctionHandle getController(String typeName){
		AbstractFunctionHandle handle = handles.get(typeName);
		if(handle!=null){
			return handle;
		}
		reloadServices();
		return handles.get(typeName);
	}
	
	public QwertTarget getQwertTargetbyNull() {

		QwertCmd[] commands = new QwertCmd[1];
		Object[] objects = new Object[1];
		Status status = new Status();
		status.setCode(ErrorCode.CODE_BusinessProcess_Handle);
		status.setMessage("Method for less than");
		objects[0] = status;
		commands[0].setObjects(objects);
		QwertTarget[] targets = new QwertTarget[1];
		targets[0].setCommands(commands);
		return targets[0];
	}
}
