package com.zhaodj.foo.redis;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by djzhao on 15-5-18.
 */
public class LuaDemo {

    private static final String script = "local ex = redis.call('exists',KEYS[1])\n" +
            "local res = -1\n" +
            "if ex == 1 then\n" +
            "	res = redis.call('hset',KEYS[1],ARGV[1],ARGV[2])\n" +
            "end\n" +
            "return res";

    public static void hset(Jedis jedis, String key, String field, String value){
        List<String> keys = new ArrayList<String>(1);
        keys.add(key);
        List<String> args = new ArrayList<>(2);
        args.add(field);
        args.add(value);
        Object obj = jedis.eval(script, keys, args);
        System.out.println(obj);
    }

    public static void main(String[] args){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        hset(jedis, "aaa", "fa", "va");
        hset(jedis, "bbb", "fb", "vb");
    }


}
