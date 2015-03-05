package com.zhaodj.foo.redis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class PoolValidater implements Runnable{

	private static String keyPre = "user_token#";
	private static int expireTime = 3600*24;
	private static ShardedJedisPool jedisPool;
	
	static {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("127.0.0.1", 6379));
		shards.add(new JedisShardInfo("127.0.0.1", 6380));
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(2000);
		config.setMaxWaitMillis(2000);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		jedisPool = new ShardedJedisPool(config,shards);
	}
	
	private void set() throws InterruptedException {
		ShardedJedis jedis = jedisPool.getResource();
		try {
			long timestamp = System.nanoTime(); 
			jedis.setex(keyPre + timestamp + "#", expireTime, genValue(timestamp));
		}catch(Exception e){
			e.printStackTrace();
			Thread.sleep(60000);
		}finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	public void run() {
		for(int i = 0;i<100;i++) {
			try {
				set();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String genValue(long timestamp) {
		return DigestUtils.md5Hex(String.valueOf(timestamp));
	}
	
	public static void main(String[] args) {
		Thread[] threads = new Thread[10];
		for(int i=0;i<10;i++) {
			threads[i]= new Thread(new PoolValidater());
			threads[i].start();
		}
	}

}
