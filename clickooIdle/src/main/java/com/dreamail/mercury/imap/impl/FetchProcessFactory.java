package com.dreamail.mercury.imap.impl;

import com.dreamail.mercury.constant.ImapConstant;
import com.dreamail.mercury.imap.domain.FetchData;

public class FetchProcessFactory extends AbstractProcess {
	FetchData fetchData;
	public FetchProcessFactory(FetchData fetchData) {
		this.fetchData = fetchData;
	}
	public AbstractFetchEmail getFactory(){
		AbstractFetchEmail fetchEmail = null;
		if (fetchData.getContent().startsWith(ImapConstant.Fetch.FETCH_BODY_PEEK)||fetchData.getContent().startsWith("(BODY.PEEK")) {
			fetchEmail = new FetchEmailDetail();
		} else {
			fetchEmail = new FetchEmailStructure();
		}
		return fetchEmail;
	}

}
