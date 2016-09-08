package com.github.panhongan.util.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

import com.github.panhongan.util.zookeeper.ZKUtil;

import org.apache.zookeeper.CreateMode;

class MyWatcher implements Watcher {

	@Override
	public void process(WatchedEvent event) {
		if (event.getPath() != null) {
			System.out.println("1 znode = " + event.getPath());

			if (event.getType().equals(Watcher.Event.EventType.NodeDeleted)) {
				System.out.println("1 node deleted");
			} else if (event.getType().equals(Watcher.Event.EventType.NodeCreated)) {
				System.out.println("1 node created");
			} else if (event.getType().equals(Watcher.Event.EventType.NodeDataChanged)){
				System.out.println("1 node data changed");
			} else {
				System.out.println("1 node other event");
			}
		}
	}
	
}

public class TestZookeeper {
	public static void main(String [] args) {
		ZooKeeper zk = null;
		
		try {
			zk = ZKUtil.connectZK("localhost:2181,localhost:2182,localhost:2183", 30 * 1000, new MyWatcher());
			if (zk != null) {
				System.out.println("zk connected");
				
				// pha1
				String path = "/pha3";
				if (zk.exists(path, true) != null) {
					zk.delete(path, -1);
				}
				
				Thread.sleep(2*1000);
				
				zk.exists(path, true);
				zk.exists(path, new MyWatcher());
				zk.exists(path, new MyWatcher());
			
	
				path = zk.create(path, "test_pha".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				System.out.println("***"+new String(zk.getData(path, true, null)));
				
				zk.setData(path, "22".getBytes(), -1);
				System.out.println(new String(zk.getData(path, true, null)));
			} else {
				System.out.println("zk not connected");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ZKUtil.closeZK(zk);
		}
	}
	
}
