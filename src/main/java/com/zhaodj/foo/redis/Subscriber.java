package com.zhaodj.foo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub{
	
	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		try {
			Subscriber subscriber = new Subscriber();
			jedis.subscribe(subscriber, "channel:1");
		}finally {
			jedis.close();
		}
	}

	@Override
	public void onMessage(String channel, String message) {
		System.out.println(message);
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub	
		
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		
	}

}
