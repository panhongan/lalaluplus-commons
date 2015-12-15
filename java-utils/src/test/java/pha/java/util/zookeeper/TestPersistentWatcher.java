package pha.java.util.zookeeper;

import pha.java.util.zookeeper.ZKUtil;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class TestPersistentWatcher {
	
	private static Logger logger = Logger.getLogger(TestPersistentWatcher.class);
	
	public static void main(String [] args) {
		ZooKeeper zk = ZKUtil.connectZK("localhost:2181,localhost:2182,localhost:2183", 30000, null);
		if (zk != null) {
			String path = "/pha";
			
			// add watcher to path node
			PersistentWatcher watcher = new PersistentWatcher(zk, "/pha", new TestWatcher(path));
			if (watcher.init()) {
				logger.info("persistent watcher init ok");
				
				watcher.setRewatchInterval(10);
			
				try {
					if (zk.exists(path, false) != null) {
						zk.delete(path, -1);
					}
					
					Thread.sleep(1000);
					zk.create(path, "test_pha".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
			} else {
				logger.warn("persistent watcher init failed");
			}
			
			watcher.uninit();
			
			// no watcher
			try {
				if (zk.exists(path, false) != null) {
					zk.delete(path, -1);
				}
				
				Thread.sleep(1000);
				zk.create(path, "test_pha".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}
		
		ZKUtil.closeZK(zk);
	}


}
