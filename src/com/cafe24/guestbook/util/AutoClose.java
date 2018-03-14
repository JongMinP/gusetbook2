package com.cafe24.guestbook.util;

public class AutoClose {
	
	public static void closeResource(AutoCloseable...autoCloseables){
		
		for(AutoCloseable auto : autoCloseables){
			if(auto == null){
				continue;
			}
			
			try {
				auto.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
