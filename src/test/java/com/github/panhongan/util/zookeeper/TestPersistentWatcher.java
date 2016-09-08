package com.github.panhongan.util.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

import com.github.panhongan.util.zookeeper.PersistentWatcher;
import com.github.panhongan.util.zookeeper.ZKUtil;

public class TestPersistentWatcher {
	
	public static void main(String [] args) {
		ZooKeeper zk = ZKUtil.connectZK("localhost:2181,localhost:2182,localhost:2183", 30000, null);
		if (zk != null) {
			String path = "/pha";
			
			// add watcher to path node
			PersistentWatcher watcher = new PersistentWatcher(zk, "/pha", new TestWatcher(path));
			if (watcher.init()) {
				System.out.println("persistent watcher init ok");
				
				watcher.setRewatchInterval(10);
			
				try {
					if (zk.exists(path, false) != null) {
						zk.delete(path, -1);
					}
					
					Thread.sleep(1000);
					zk.create(path, "test_pha".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			} else {
				System.err.println("persistent watcher init failed");
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
				System.err.println(e.getMessage());
			}
		}
		
		ZKUtil.closeZK(zk);
	}


}
