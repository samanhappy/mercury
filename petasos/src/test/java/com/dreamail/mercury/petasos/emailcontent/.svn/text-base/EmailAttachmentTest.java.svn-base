package com.dreamail.mercury.petasos.emailcontent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Test;

import com.dreamail.mercury.cache.MemCachedManager;
import com.dreamail.mercury.cache.ReceiveServerCacheManager;
import com.dreamail.mercury.cache.RoleCacheManager;
import com.dreamail.mercury.cache.SendServerCacheManager;
import com.dreamail.mercury.cache.VolumeCacheManager;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.petasos.IFunctionDispatcher;
import com.dreamail.mercury.petasos.EmailList.EmailListTest;
import com.dreamail.mercury.petasos.impl.HandlerDispatcher;


public class EmailAttachmentTest {
	/**
     * Rigourous Test :-)
     */
    @Test
    public void testEmailAttachment() throws JiBXException, FileNotFoundException {
        java.util.logging.Logger.getLogger(EmailListTest.class.getName()).log(Level.INFO, (this.getClass().getResource("/").getPath()));
    	MemCachedManager.getInstance().init();
        new RoleCacheManager().init();
        new ReceiveServerCacheManager().init();
        new SendServerCacheManager().init();
        new PropertiesDeploy().init();
    	MemCachedManager.getInstance().init();
    	new VolumeCacheManager().init();
//    	System.out.println(EmailCacheManager.removeEmail("246"));
        IBindingFactory bfact = BindingDirectory.getFactory(PushMail.class);
        // unmarshal customer information from file
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
        FileInputStream in = new FileInputStream(this.getClass().getResource("/").getPath() + File.separator + "EmailAttachment.xml");
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
    
    public static void main(String[] args) {
		String str = "message,4,192.168.20.12";
		String[] a = str.split(",");
		System.out.println(a.length);
		System.out.println(a.length<3);
		
		System.out.println("192.168.20.12".trim());
	}
}
