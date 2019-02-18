package com.jt.pachong;

import java.io.IOException;

import javax.persistence.Table;

import org.junit.Test;

public class WaitNotifyAllDemo {

	@Test
	public static void main(String[] args) {

		
		

		new Thread(new Ask2()).start();
		

	}

}

class Ask2 implements Runnable {

	public ZLUtils zl;

	

	@Override
	public void run() {
		while (true) {
					try {
						
						zl.testHZInternet();
						zl.wait(500);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
			
				
			
		}
	}

}

