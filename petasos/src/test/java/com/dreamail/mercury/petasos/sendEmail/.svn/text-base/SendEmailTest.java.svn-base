package com.dreamail.mercury.petasos.sendEmail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Test;

import com.dreamail.mercury.cache.VolumeCacheManager;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.petasos.IFunctionDispatcher;
import com.dreamail.mercury.petasos.impl.HandlerDispatcher;

public class SendEmailTest {
	/**
     * Rigourous Test :-)
     */
    @Test
    public void testSendEmail() throws JiBXException, FileNotFoundException {
//        java.util.logging.Logger.getLogger(SendEmailTest.class.getName()).log(Level.INFO, (this.getClass().getResource("/").getPath()));
//        Starting st = new Starting();
//        st.addTimer();
    	new VolumeCacheManager().init();
        IBindingFactory bfact = BindingDirectory.getFactory(PushMail.class);
        // unmarshal customer information from file
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
        FileInputStream in = new FileInputStream(this.getClass().getResource("/").getPath() + File.separator + "sendEmail.xml");
        PushMail customer = (PushMail) uctx.unmarshalDocument(in, null);
        IFunctionDispatcher fd = new HandlerDispatcher();
        try {
			customer  = fd.dispatch(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        IMarshallingContext mctx = bfact.createMarshallingContext();
        mctx.setIndent(2);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();        
        mctx.marshalDocument(customer, "UTF-8", null, baos);
        String s = baos.toString();
        System.out.println("===="+s);
    }
}
