package com.revolut.tonsaito.util;

import java.util.Date;
import java.util.Map;

public class StaticCache {
	private static long defaultExpirationMs = 500L;
	private static Map<Integer, Date> cache;

	public static void put(Integer... ids) {
		for (Integer id : ids) {
			cache.put(id, new Date(System.currentTimeMillis() + defaultExpirationMs));
		}
	}

	public static void release(Integer... ids) {
		for (Integer id : ids) {

		}
	}

	public static void main(String ... args ) {
		Thread thread = new Thread(){
			public void run(){
				System.out.println("Thread Running");
	    	}
		};
		thread.start();
	}
}
