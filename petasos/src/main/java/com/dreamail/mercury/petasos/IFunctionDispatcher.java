package com.dreamail.mercury.petasos;

import com.dreamail.mercury.domain.qwert.PushMail;

public interface IFunctionDispatcher {
	/**
	 * dispatch related funcion by object qwertML;
	 * @param qwertML
	 * @return
	 * @throws DispatchException
	 * 
	 */
	public PushMail dispatch(PushMail pushMail) throws Exception;
}
