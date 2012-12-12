/**
 * 
 */
package com.dreamail.mercury.xml.factory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.domain.qwert.Version;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.xml.interfaces.XMLDocumentDAO;

/**
 * @author 000843
 * 
 */
public class XMLDocumentFactory implements XMLDocumentDAO {
	private static Logger logger = (Logger) LoggerFactory
	.getLogger(XMLDocumentFactory.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dreamail.mercury.xml.interfaces.XMLDocumentDAO#getObjectList(java.
	 * lang.String)
	 */
	@Override
	public PushMail getObjectList(String xml) {
		PushMail pushMail = null;
		InputStream in = null;
		try {
			IBindingFactory bfact = BindingDirectory.getFactory(PushMail.class);
			IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
			in = new ByteArrayInputStream(xml.getBytes("utf-8"));
			pushMail = (PushMail) uctx.unmarshalDocument(in, null);
		} catch (JiBXException e) {
			logger.error("XMLtoPushMail parser error",e);
			pushMail = getErrorObject();
		} catch (UnsupportedEncodingException e) {
			logger.error("XMLtoPushMail parser error",e);
			pushMail = getErrorObject();
		}
		finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close in err!",e);
					e.printStackTrace();
				}
			}
		}
		return pushMail;
	}

	private PushMail getErrorObject() {
		PushMail pushMail = new PushMail();
		Version version = new Version();
		Status status = new Status();
		version.setVersion("PushMail1.0");
		status.setCode(ErrorCode.CODE_PTL_TOXML_ERR);
		status.setMessage("XMLtoPushMail parser error");
		pushMail.setVersion(version);
		pushMail.setStatus(status);
		return pushMail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dreamail.mercury.xml.interfaces.XMLDocumentDAO#getResponseMessage(
	 * com.dreamail.mercury.domain.qwert.PushMail)
	 */
	@Override
	public String getResponseMessage(PushMail pushMail){
		String s = null;
		ByteArrayOutputStream baos = null;
		try {
			IBindingFactory bfact = BindingDirectory.getFactory(PushMail.class);
			IMarshallingContext mctx = bfact.createMarshallingContext();
//			mctx.setIndent(2);
			baos = new ByteArrayOutputStream();
			mctx.marshalDocument(pushMail, "UTF-8", null, baos);
			s = baos.toString("UTF-8");
			logger.info(s);
		} catch (JiBXException e) {
			// TODO Auto-generated catch block
			logger.error("PushMailtoXML parser error",e);
			s = getErrorXml();
		} catch (UnsupportedEncodingException e) {
			logger.error("PushMailtoXML parser error",e);
			s = getErrorXml();
		}
		finally{
			if(baos!=null){
				try {
					baos.close();
				} catch (IOException e) {
					logger.error("",e);
					e.printStackTrace();
				}
			}
		}
		return s;
	}

	private String getErrorXml() {
		// TODO Auto-generated method stub
		StringBuffer s = new StringBuffer();
		s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(
		"<PushMail>").append("<Header>").append(
		"<Version>PushMail1.0</Version>").append("<Status>").append("<Code>")
		.append(ErrorCode.CODE_PTL_TOXML_ERR).append("</Code>").append("<Message>")
		.append("PushMailtoXML parser error").append("</Message>").append("</Status>")
		.append("</Header>").append("</PushMail>");
		return s.toString();
	}

}
