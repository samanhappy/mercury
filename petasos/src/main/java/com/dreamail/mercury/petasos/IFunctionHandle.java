package com.dreamail.mercury.petasos;

import com.dreamail.mercury.domain.qwert.QwertML;
import com.dreamail.mercury.domain.qwert.QwertTarget;

public interface IFunctionHandle {
	
	/**
	 * function handle 
	 * @return QwertTarget
	 * @throws HandleException
	 */
	public QwertTarget handle(QwertTarget target,QwertML qwertML) throws Exception;
	
	public String getTypename();
	
}
