package com.github.panhongan.util.db;

import java.util.List;

import com.github.panhongan.util.conf.Config;
import com.github.panhongan.util.db.JedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

public class TestJedisUtil {
	
	public static void main(String [] args) {
		Config config = new Config();
		if (config.parse("conf/redis.properties")) {
			System.out.println(config.toString());
		} else {
			System.err.println("parse conf file failed");
			return;
		}
		
		Pool<Jedis> pool = JedisUtil.createJedisPool(config);
		if (pool != null) {
			try {
				Jedis jedis = pool.getResource();
				jedis.select(0);
				List<String> list = jedis.lrange("account", 0, 10);
				if (list != null) {
					System.out.println(list.toString());
				}
				
				JedisUtil.returnSource(pool, jedis, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JedisUtil.closeJedisPool(pool);
		}
	}

}
