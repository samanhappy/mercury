package com.dreamail.mercury.talaria.xstream;

public class UPEParserObject {
	
	private String IMEI;

	private PushMailObject pushMail;
	
	private IMObject im;
	
	private MSNObject msn;
	
	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public PushMailObject getPushMail() {
		return pushMail;
	}

	public void setPushMail(PushMailObject pushMail) {
		this.pushMail = pushMail;
	}

	public IMObject getIM() {
		return im;
	}

	public void setIM(IMObject iM) {
		im = iM;
	}

	public MSNObject getMSN() {
		return msn;
	}

	public void setMSN(MSNObject mSN) {
		msn = mSN;
	}
	
	
}
