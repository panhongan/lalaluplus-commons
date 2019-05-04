package com.github.lalalu.utils.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

import java.util.Arrays;

/**
 * lalalu plus
 */
public class TestPersistentWatcher {

	public static void main(String[] args) {
		ZooKeeper zk = ZkUtils.connectZK("localhost:2181,localhost:2182,localhost:2183", 30000, null);
		if (zk != null) {
			String path = "/pha";

			// add watcher to path node
			PersistentWatcher watcher = new PersistentWatcher(zk, path, Arrays.asList(new TestWatcher(path)));
			System.out.println("persistent watcher init ok");

			try {
				if (zk.exists(path, false) != null) {
					zk.delete(path, -1);
				}

				Thread.sleep(1000);
				zk.create(path, "test_pha".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

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

		ZkUtils.closeZK(zk);
	}


}
