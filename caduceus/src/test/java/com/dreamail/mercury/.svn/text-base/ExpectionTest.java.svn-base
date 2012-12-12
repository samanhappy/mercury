package com.dreamail.mercury;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ExpectionTest {
	@Test
	public void expTest(){
		try {
			for(int i=0;i<2;i++){
				bizTest();
				System.out.println("in...");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String bizTest() throws Exception{
//		int i = 1/0;
		return null;
	}
	
	public void t(){
		for(int i=0;i<2;i++){
			try{
				bizTest();
				System.out.println("....................");
			}catch (Exception e) {
				System.out.println("err!");
			}
		}
	}
	
	@Test
	public void t2(){
		for(int i=0;i<2;i++){
			t();
		}
	}
	
	@Test
	public void arrayListTest(){
		List<String> a = new ArrayList<String>();
		a.add("3");
		a.add("2");
		a.add("1");
		for(String tmp:a){
			System.out.println(tmp);
		}
	}
	
	@Test
	public void t3(){
		long i = 1000/2;
		System.out.println(i);
	}
	
	
	
	public void t6(){
		try {
			t4();
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void t4()throws Exception{
		try{
			t5();
		}catch (Exception e) {
			System.out.println("in the catch...");
		}
	}
	
	
	public void t5()throws Exception{
//		int i = 0;
//		int b = i/0;
	}
	
	@Test
	public void t7(){
//		List<String> uuidList = new ArrayList<String>();
//		uuidList.add("1");
//		long i = 1;
//		System.out.println(uuidList.contains(Long.toString(i)));
		String a = "123";
		System.out.println(a.indexOf("1"));
		
	}
	
//	public class thread1 implements Runnable{
//		@Override
//		public void run() {
//			System.out.println("run...");
//			
//		}
//	}
//	
//	@Test
//	public void TestThread(){ 
//		Runnable run = new thread1();
//		run.run();
//	}
	
//	public class call implements Callable{
//
//		@Override
//		public Object call() throws Exception {
//			System.out.println("call run....");
//			return null;
//		}
//	}
//	
//	@Test
//	public void testThread(){
//		call taskCallable = new call();
//		FutureResult futureResult = new FutureResult();
//		Thread thread = new Thread(futureResult.setter(taskCallable));
//		thread.setName("a1");
//		thread.start();
//		System.out.println(Thread.activeCount());
//		Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
//		
//		
//	}
	@Test
	public void t9(){
//		System.out.println("".getBytes());
	}
	
	
}
