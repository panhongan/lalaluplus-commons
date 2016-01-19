package com.github.panhongan.util.zookeeper;

import org.apache.zookeeper.ZooKeeper;

public interface SessionUpdatable {
	
	public void set(ZooKeeper zk);

}
