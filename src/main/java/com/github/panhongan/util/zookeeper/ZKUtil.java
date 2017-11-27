package com.github.panhongan.util.zookeeper;

import java.util.List;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

import com.github.panhongan.util.path.PathUtil;
import com.github.panhongan.util.collection.CollectionUtil;

public class ZKUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ZKUtil.class);
	
	public static void waitForConnectionFinished(ZooKeeper zk) {
		if (zk != null) {
			while (true) {
				if (zk.getState() != States.CONNECTING) {
					break;
				}
				
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static boolean waitForConnectionFinished(ZooKeeper zk, int timeout) {
		boolean ret = false;
		
		if (zk != null) {
			float passed_time = 0;
			
			for (int i = 0; i < timeout; ) {
				if (zk.getState() != States.CONNECTING) {
					ret = true;
					break;
				}
				
				try {
					Thread.sleep(100);
					passed_time += 0.1;
					i = (int)passed_time;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return ret;
	}

	public static ZooKeeper connectZK(String zk_host, int timeout, Watcher watcher) {
		ZooKeeper zk = null;
		boolean is_ok = false;
		
		try {
			zk = new ZooKeeper(zk_host, timeout, 
					(watcher != null ? watcher : new EmptyWatcher()));
			ZKUtil.waitForConnectionFinished(zk);
			if (zk.getState().isConnected()) {
				is_ok = true;
				
				if (watcher != null) {
					zk.register(watcher);
				}
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		
		if (!is_ok) {
			ZKUtil.closeZK(zk);
			zk = null;
		}
		
		return zk;
	}
	
	public static void closeZK(ZooKeeper zk) {
		if (zk != null) {
			try {
				zk.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean createNodeRecursively(ZooKeeper zk, String path, boolean is_persistent) {
		boolean ret = false;

		List<String> path_list = PathUtil.recursivePathList(path);
		if (!CollectionUtil.isEmpty(path_list)) {
			for (int i = 0; i < path_list.size() - 1; ++i) {
				try {
					zk.create(path_list.get(i), null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					ret = true;
				} catch (Exception e) {
					if (e instanceof KeeperException.NodeExistsException) {
						ret = true;
					} else {
						logger.warn(e.getMessage());
						ret = false;
						break;
					}
				}
			}
			
			if (ret) {
				try {
					zk.create(path_list.get(path_list.size() - 1), null, Ids.OPEN_ACL_UNSAFE, 
							(is_persistent ? CreateMode.PERSISTENT : CreateMode.EPHEMERAL));
					ret = true;
				} catch (Exception e) {
					logger.warn(e.getMessage());
					ret = false;
				}
			}
		}

		return ret;
	}
	
	public static void deleteNode(ZooKeeper zk, String path) {
		if (zk != null && path != null) {
			try {
				zk.delete(path, -1);
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}
	}
	
	public static void deleteNode(String zk_host, String path) {
		ZooKeeper zk = ZKUtil.connectZK(zk_host, 30 * 1000, null);
		if (zk != null) {
			ZKUtil.deleteNode(zk, path);
			ZKUtil.closeZK(zk);
		}
	}
	
	public static void deleteNodeRecursively(ZooKeeper zk, String path) {
		List<String> path_list = PathUtil.recursivePathList(path);
		if (zk != null && !CollectionUtil.isEmpty(path_list)) {
			for (int i = path_list.size() - 1; i >= 0; --i) {
				deleteNode(zk, path_list.get(i));
			}
		}
	}
	
	public static boolean isValidSession(ZooKeeper zk) {
		boolean ret = false;
		
		if (zk != null) {
			try {
				zk.exists("/155d392f35b0005", false);
				ret = true;
			} catch (Exception e) {
			}
		}
		
		return ret;
	}
	
	private static class EmptyWatcher implements Watcher {
		@Override
		public void process(WatchedEvent event) {
		}
	}

}
