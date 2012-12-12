package com.dreamail.mercury.petasos.impl;

import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.QwertTarget;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.IEmailController;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.TypeName;

public class EmailController extends AbstractFunctionHandle {
	
	private static ServiceLoader<IEmailController> serviceLoader = ServiceLoader
			.load(IEmailController.class);
	
	private static ConcurrentHashMap<String, IEmailController> executes = new ConcurrentHashMap<String, IEmailController>();

	static {
		for (IEmailController execute : serviceLoader) {
			executes.put(execute.getMethodName(), execute);
		}
	}

	private void reloadServices() {
		serviceLoader = ServiceLoader.load(IEmailController.class);
		for (IEmailController execute : serviceLoader) {
			executes.put(execute.getMethodName(), execute);
		}
	}
	

	/**
	 * @since 1.0
	 * @author 
	 * @exception HandleException
	 * @param no
	 * @return QwertTarget
	 * @throws Exception 
	 * 
	 */
	@Override
	public QwertTarget handle(QwertTarget target, PushMail pushMail) throws Exception {
		QwertCmd[] commands = target.getCommands();
		QwertCmd[] resultCommands = new QwertCmd[commands.length];
		IEmailController execute = null;
		for(int i = 0; i < commands.length; i++){
			execute = getController(commands[i].getMethodName());
            if(execute == null){
            	resultCommands[i] = getQwertCmdbyNull();
            }
            else{
            	resultCommands[i] = execute.execute(commands[i],pushMail);
            }
		}
		target.setCommands(resultCommands);
		return target;
	
	}

	public QwertCmd getQwertCmdbyNull(){
		
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
	
	public IEmailController getController(String methodName){
		IEmailController execute = executes.get(methodName);
		if(execute!=null){
			return execute;
		}
		reloadServices();
		return executes.get(methodName);
	}
	


	@Override
	public String getTypename() {
		// TODO Auto-generated method stub
		return TypeName.EMAIL;
	}
	
	public EmailController() {
		// TODO Auto-generated constructor stub
	}
	
}
