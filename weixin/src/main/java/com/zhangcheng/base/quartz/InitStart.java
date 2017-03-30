package com.zhangcheng.base.quartz;  

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
  
public class InitStart implements HttpSessionListener {
	static {
		System.out.println("-----------------------------------------------------");
		System.out.println("init start");
		System.out.println("-----------------------------------------------------");
	}

	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub  
		
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub  
		
	}

}
