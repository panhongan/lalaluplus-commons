package com.github.lalalu.utils.zookeeper;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

/**
 * lalalu plus
 */

public class ZkUtils {

    private static final int DEFAULT_TIMEOUT_MILL = 5 * 1000;   // million seconds

	public static void waitForConnectionFinished(ZooKeeper zk) {
		if (zk != null) {
			while (true) {
				if (zk.getState() != States.CONNECTING) {
					break;
				}
				
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					// do nothing
				}
			}
		}
	}
	
	public static void waitForConnectionFinished(ZooKeeper zk, int timeoutMillSeconds) {
	    if (timeoutMillSeconds <= 0) {
	        throw new IllegalArgumentException("timeoutMillSeconds : " + timeoutMillSeconds);
        }

		if (zk != null) {
			int passedTime = 0;
			while (passedTime < timeoutMillSeconds) {
				if (zk.getState() != States.CONNECTING) {
					return;
				}
				
				try {
					Thread.sleep(100);
					passedTime += 100;
				} catch (Exception e) {
					// do nothing
				}
			}

			throw new RuntimeException("connect zk timeout : " + timeoutMillSeconds);
		}
	}

	public static ZooKeeper connectZK(String zkHost, int timeoutMillSeconds, Watcher watcher) {
        ZooKeeper zk = null;

		try {
            zk = new ZooKeeper(zkHost, timeoutMillSeconds, watcher);
			ZkUtils.waitForConnectionFinished(zk);
			if (zk.getState().isConnected()) {
                if (watcher != null) {
                    zk.register(watcher);
                }
                return zk;
            } else {
			    closeZK(zk);
			    throw new RuntimeException("zk not connected");
            }
		} catch (Exception e) {
		    closeZK(zk);
			throw new RuntimeException(e);
		}
	}
	
	public static void closeZK(ZooKeeper zk) {
		if (zk != null) {
			try {
				zk.close();
			} catch (Exception e) {
				// do nothing
			}
		}
	}
	
	public static boolean createNodeRecursively(ZooKeeper zk, String path, boolean persistent) {
		boolean ret = false;

		String[] arr = path.split("/");
		if (ArrayUtils.isNotEmpty(arr)) {
		    String str = "";
			for (int i = 1; i < arr.length - 1; ++i) {
				try {
				    str += "/";
				    str += arr[i];
					zk.create(str, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					ret = true;
				} catch (Exception e) {
					if (e instanceof KeeperException.NodeExistsException) {
						ret = true;
					} else {
						ret = false;
						break;
					}
				}
			}
			
			if (ret) {
			    str += "/";
			    str += arr[arr.length - 1];

				try {
					zk.create(str, null, Ids.OPEN_ACL_UNSAFE,
							(persistent ? CreateMode.PERSISTENT : CreateMode.EPHEMERAL));
					ret = true;
				} catch (Exception e) {
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
			    // do nothing
			}
		}
	}
	
	public static void deleteNode(String zk_host, String path) {
		ZooKeeper zk = ZkUtils.connectZK(zk_host, DEFAULT_TIMEOUT_MILL, null);
		if (zk != null) {
			ZkUtils.deleteNode(zk, path);
			ZkUtils.closeZK(zk);
		}
	}
	
	public static boolean isValidZk(ZooKeeper zk) {
		if (zk != null) {
			try {
				zk.exists("/155d392f35b0005", false);
				return true;
			} catch (Exception e) {
			    return false;
			}
		} else {
		    return false;
        }
	}

}
