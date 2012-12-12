package com.dreamail.mercury.petasos;

import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import com.dreamail.mercury.petasos.impl.AbstractFunctionHandle;

public class LoaderTest {
	private static ServiceLoader<AbstractFunctionHandle> serviceLoader = ServiceLoader.load(AbstractFunctionHandle.class);
    private static ConcurrentHashMap<String, AbstractFunctionHandle> executes = new ConcurrentHashMap<String, AbstractFunctionHandle>();

//    static {
//    	ServiceLoader<IEmailController> serviceLoader = ServiceLoader.load(IEmailController.class);
//    	ConcurrentHashMap<String, IEmailController> executes = new ConcurrentHashMap<String, IEmailController>();
//    	for (IEmailController execute : serviceLoader) {
//        	executes.put(execute.getMotheName(), execute);
//        }
//    }

    private void reloadServices() {
        serviceLoader = ServiceLoader.load(AbstractFunctionHandle.class);
        for (AbstractFunctionHandle handles : serviceLoader) {
        	executes.put(handles.getTypename(), handles);
        }
    }
	
    @Test
	public void loadServiceTest(){
    	reloadServices();
    	System.out.println(executes.size());
	}
    
    @SuppressWarnings("null")
	@Test
    public void test(){
    	final StringBuffer sb = new StringBuffer("1");
		sb.append("123");
		System.out.println(sb.toString());
    	String s = null;
    	try {
			s.substring(-1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String str = e.getClass().getSimpleName();
			System.out.println(str);
		}
		
    }
}
