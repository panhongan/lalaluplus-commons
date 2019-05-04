package com.github.lalalu.utils.redis;

import java.util.List;

import com.github.lalalu.utils.conf.Config;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

/**
 * lalalu plus
 */

public class TestJedisUtils {

	public static void main(String[] args) {
		Config config = new Config();
		if (config.parse("conf/redis.properties")) {
			System.out.println(config.toString());
		} else {
			System.err.println("parse conf file failed");
			return;
		}

		Pool<Jedis> pool = JedisUtils.createJedisPool(config);
		if (pool != null) {
			try {
				Jedis jedis = pool.getResource();
				jedis.select(0);
				List<String> list = jedis.lrange("account", 0, 10);
				if (list != null) {
					System.out.println(list.toString());
				}

				JedisUtils.returnSource(pool, jedis, true);
			} catch (Exception e) {
				e.printStackTrace();
			}

			JedisUtils.closeJedisPool(pool);
		}
	}

}
