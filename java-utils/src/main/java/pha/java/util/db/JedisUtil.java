package pha.java.util.db;

import java.util.List;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Pool;

public class JedisUtil {
	
	private static Logger logger = Logger.getLogger(JedisUtil.class);
	
	public void createShardedJedisPool() {
		/* broken window (will be used)
		 * 
		// shard info
		List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>();
		
		String redis_list = conf.get("redis.list");
		if (redis_list != null) {
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
	
	public static void returnSource(Pool<Jedis> pool, Jedis jedis, boolean is_ok) {
		if (pool != null) {
			if (is_ok) {
				try {
					pool.returnResource(jedis);
				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
			} else {
				try {
					pool.returnBrokenResource(jedis);
				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
			}
		}
	}
	
	public static void returnSource(Pool<ShardedJedis> pool, ShardedJedis jedis, boolean is_ok) {
		if (pool != null) {
			if (is_ok) {
				try {
					pool.returnResource(jedis);
				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
			} else {
				try {
					pool.returnBrokenResource(jedis);
				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
			}
		}
	}
	
	public static boolean validatePool(Pool<Jedis> pool) {
		boolean ret = false;
		Jedis jedis = null;
		
		try {
			jedis = pool.getResource();
			ret = (jedis != null);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
		} 
		
		if (ret) {
			returnSource(pool, jedis, ret);
		}
		
		return ret;
	}
	
	public synchronized static List<String> consumeListByRangeSync(Jedis jedis, String redis_key, 
			int start, int end) {
		List<String> result = jedis.lrange(redis_key, start, end);
		if (result != null) {
			jedis.ltrim(redis_key, result.size(), -1);
		}
		return result;
	}
	
	

}
