/**
 * 
 */
package com.dreamail.mercury.petasos.impl;

import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.QwertTarget;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.IUserController;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.TypeName;

/**
 * @author 000843
 * 
 */
public class UserController extends AbstractFunctionHandle {

	private static ServiceLoader<IUserController> serviceLoader = ServiceLoader
			.load(IUserController.class);
	private static ConcurrentHashMap<String, IUserController> executes = new ConcurrentHashMap<String, IUserController>();

	static {
		for (IUserController execute : serviceLoader) {
			executes.put(execute.getMethodName(), execute);
		}
	}

	private void reloadServices() {
		serviceLoader = ServiceLoader.load(IUserController.class);
		for (IUserController execute : serviceLoader) {
			executes.put(execute.getMethodName(), execute);
		}
	}

	@Override
	public QwertTarget handle(QwertTarget target, PushMail pushMail) throws Exception {
		QwertCmd[] commands = target.getCommands();
		QwertCmd[] resultCommands = new QwertCmd[commands.length];
		IUserController execute = null;
		for (int i = 0; i < commands.length; i++) {
			execute = getController(commands[i].getMethodName());
			if (execute == null) {
				resultCommands[i] = getQwertCmdbyNull();
			} else {
				resultCommands[i] = execute.execute(commands[i], pushMail);
			}
		}
		
		target.setCommands(resultCommands);
		return target;
	}

	public QwertCmd getQwertCmdbyNull() {

		QwertCmd[] commands = new QwertCmd[1];
		Object[] objects = new Object[1];
		Status status = new Status();
		status.setCode(ErrorCode.CODE_BusinessProcess_Handle);
		status.setMessage("Method for less than");
		objects[0] = status;
		commands[0] = new QwertCmd();
		commands[0].setObjects(objects);
		return commands[0];
	}

	public IUserController getController(String methodName){
		IUserController execute = executes.get(methodName);
		if(execute!=null){
			return execute;
		}
		reloadServices();
		return executes.get(methodName);
	}
	
	@Override
	public String getTypename() {
		return TypeName.USER;
	}

	public UserController() {
	}

}
