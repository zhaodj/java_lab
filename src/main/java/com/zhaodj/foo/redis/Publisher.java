package com.zhaodj.foo.redis;

import java.util.Scanner;

import redis.clients.jedis.Jedis;

public class Publisher {
	
	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		try {
			Scanner sc = new Scanner(System.in);
			String line = null;
			while(( line = sc.nextLine()) != null) {
				jedis.publish("channel:1", line);
			}
		}finally {
			jedis.close();
		}
	}

}
