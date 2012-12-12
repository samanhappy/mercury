package com.dreamail.mercury.petasos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.junit.Test;

import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.QwertTarget;
import com.dreamail.mercury.petasos.impl.user.AccountRemove;

public class AccountRemoveTest {

	@Test
	public void testExecute() throws Exception {
        java.util.logging.Logger.getLogger(AccountRemoveTest.class.getName()).log(Level.INFO, (this.getClass().getResource("/").getPath()));

        IBindingFactory bfact = BindingDirectory.getFactory(PushMail.class);
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
        FileInputStream in = new FileInputStream(this.getClass().getResource("/").getPath() + File.separator + "deleteUser.xml");
        PushMail customer = (PushMail) uctx.unmarshalDocument(in, null);
        QwertTarget[] targets = customer.getTargets();
        for(int i = 0; i< targets.length;i ++)
        {
        	QwertCmd[] commands = targets[i].getCommands();
        	for(int j =0 ;j < commands.length; j++)
        	{
        		AccountRemove r = new AccountRemove();
        		Cred c = new Cred();
        		c.setUuid(String.valueOf("77"));
        		r.execute(commands[j], customer);
        	}
        }
        
        IMarshallingContext mctx = bfact.createMarshallingContext();
        mctx.setIndent(2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();        
        mctx.marshalDocument(customer, "UTF-8", null, baos);
        String s = baos.toString();
        System.out.println("===="+s);
	}

}
