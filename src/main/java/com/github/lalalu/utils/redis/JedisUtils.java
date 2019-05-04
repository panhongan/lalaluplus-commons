package com.github.lalalu.utils.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.lalalu.utils.conf.Config;
import com.github.lalalu.utils.host.HostAndPortParser;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Pool;

/**
 * lalalu plus
 */

public class JedisUtils {

	private static final Logger logger = LoggerFactory.getLogger(JedisUtils.class);

	public void createShardedJedisPool(String redis_infos) {
		/* broken window (will be used)
		 * 
		// shard info
		List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>();
		
		String redis_list = conf.get("redis.list");
		if (redis_infos != null) {
			String [] arr = redis_list.split(",");
			if (arr != null && arr.length > 0) {
				for (int i = 0; i < arr.length; ++i) {
					String [] redis_info = arr[i].split(":");
					if (redis_info != null && redis_info.length == 3) {
						JedisShardInfo info = new JedisShardInfo(redis_info[0], Integer.parseInt(redis_info[1]));
						if (!StringUtil.isEmpty(redis_info[2])) {
							info.setPassword(redis_info[2]);
						}
						info.setConnectionTimeout(conf.get("redis.connection.timeout", 10));
						
						jdsInfoList.add(info);
					}
				}
			} else {
				logger.warn("redis.list is not valid : " + redis_list);
			}
		}
		*/
	}

	// one HostAndPort is ok
	public static JedisCluster createJedisCluster(Config conf) {
		try {
			Set<HostAndPort> nodes = new HashSet<>();
			for (HostAndPort hostAndPort : HostAndPortParser.parseRedisHost(conf.getString("redis.servers"))) {
				nodes.add(hostAndPort);
			}

			return new JedisCluster(nodes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static JedisCluster createJedisCluster(String redisConfFile) {
		Config conf = new Config();
		if (conf.parse(redisConfFile)) {
			return JedisUtils.createJedisCluster(conf);
		} else {
			throw new RuntimeException("parse redis config file failed : " + redisConfFile);
		}
	}

	public static Jedis createJedis(Config conf) {
		try {
			List<HostAndPort> nodes = HostAndPortParser.parseRedisHost(conf.getString("redis.servers"));
			Jedis jedis = new Jedis(nodes.get(0).getHost(), nodes.get(0).getPort());
			String passwd = conf.getString("redis.password");
			if (StringUtils.isNotEmpty(passwd)) {
				jedis.auth(passwd);
			}

			jedis.connect();
			return jedis;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Jedis createJedis(String redisConfFile) {
		Config conf = new Config();
		if (conf.parse(redisConfFile)) {
			return JedisUtils.createJedis(conf);
		} else {
			throw new RuntimeException("parse config file failed : " + redisConfFile);
		}
	}

	public static Pool<Jedis> createJedisPool(Config conf) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(conf.getInt("jedis.pool.max.resources", 100));
		config.setMaxIdle(conf.getInt("jedis.pool.max.idle", 40));
		config.setMaxWaitMillis(conf.getInt("jedis.pool.get_resource.timeout", 10 * 1000));
		config.setTestOnBorrow(true);

		Pool<Jedis> pool = null;

		try {
			List<HostAndPort> nodes = HostAndPortParser.parseRedisHost(conf.getString("redis.servers"));
			String redis_passwd = conf.getString("redis.password");
			int connection_timeout = conf.getInt("redis.connection.timeout", 10 * 1000);
			if (StringUtils.isEmpty(redis_passwd)) {
				pool = new JedisPool(config, nodes.get(0).getHost(), nodes.get(0).getPort(), connection_timeout);
			} else {
				pool = new JedisPool(config, nodes.get(0).getHost(), nodes.get(0).getPort(), connection_timeout, redis_passwd);
			}

			if (!JedisUtils.validatePool(pool)) {
				JedisUtils.closeJedisPool(pool);
				throw new RuntimeException("pool is invalid");
			}
			return pool;
		} catch (Exception e) {
			JedisUtils.closeJedisPool(pool);
			throw new RuntimeException(e);
		}
	}

	public static Pool<Jedis> createJedisPool(String redisConfFile) {
		Config conf = new Config();
		if (conf.parse(redisConfFile)) {
			return JedisUtils.createJedisPool(conf);
		} else {
			throw new RuntimeException("parse redis config file failed : " + redisConfFile);
		}
	}

	public static void returnSource(Pool<Jedis> pool, Jedis jedis, boolean isOk) {
		if (pool != null && jedis != null) {
			try {
				if (isOk) {
					pool.returnResource(jedis);
				} else {
					pool.returnBrokenResource(jedis);
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	public static void returnSource(Pool<ShardedJedis> pool, ShardedJedis jedis, boolean isOk) {
		if (pool != null && jedis != null) {
			try {
				if (isOk) {
					pool.returnResource(jedis);
				} else {
					pool.returnBrokenResource(jedis);
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	public static boolean validatePool(Pool<Jedis> pool) {
		try {
			Jedis jedis = pool.getResource();
			boolean ret = (jedis != null);
			returnSource(pool, jedis, ret);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}
	}

	public static void closeJedis(Jedis jedis) {
		if (jedis != null) {
			try {
				jedis.disconnect();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	public static void closeJedisCluster(JedisCluster jedisCluster) {
		if (jedisCluster != null) {
			try {
				jedisCluster.close();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	public static void closeJedisPool(Pool<Jedis> pool) {
		if (pool != null) {
			try {
				pool.close();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

}
