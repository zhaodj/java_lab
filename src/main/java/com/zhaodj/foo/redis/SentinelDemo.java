package com.zhaodj.foo.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedis;

public class SentinelDemo {
	
	private static void testSentinel(String master, Set<String> sentinels){
		JedisSentinelPool pool = new JedisSentinelPool(master, sentinels);
		Jedis jedis = pool.getResource();
		try{
			String key = "test";
			jedis.set(key, String.valueOf(System.currentTimeMillis()));
			System.out.println(jedis.get(key));
		}catch(Exception e){
			e.printStackTrace();
			pool.returnResource(jedis);
		}
		pool.close();
	}
	
	private static void testShardedSentinel(List<String> masters, Set<String> sentinels){
	    ShardedJedisSentinelPool pool = new ShardedJedisSentinelPool(masters, sentinels);
	    ShardedJedis jedis = pool.getResource();
	    try{
	        for(int i = 0; i < 10; i++){
	            String key = "test#" + i + "#";
	            jedis.set(key, String.valueOf(System.currentTimeMillis()));
	            System.out.println(jedis.get(key));
	        }
        }catch(Exception e){
            e.printStackTrace();
            pool.returnResource(jedis);
        }
        pool.close();
	}
	
	public static void main(String[] args){
		Set<String> sentinels = new HashSet<String>();
		sentinels.add("127.0.0.1:9001");
		sentinels.add("127.0.0.1:9002");
		sentinels.add("127.0.0.1:9003");
		testSentinel("mymaster", sentinels);
		List<String> masters = new ArrayList<String>();
		masters.add("mymaster");
		masters.add("myshard");
		testShardedSentinel(masters, sentinels);
	}

}
