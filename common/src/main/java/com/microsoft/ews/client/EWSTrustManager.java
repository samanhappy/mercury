package com.microsoft.ews.client;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class EWSTrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate ax509certificate[], String s)
			throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate ax509certificate[], String s)
			throws CertificateException {
	}

	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}
