package com.github.panhongan.util.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class TestWatcher implements Watcher {
	
	private String target_path = null;
	
	public TestWatcher(String path) {
		this.target_path = path;
	}
	
	public String getTargetPath() {
		return this.target_path;
	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getPath() != null) {
			System.out.println("znode = " + event.getPath());
			
			if (event.getPath().contentEquals(target_path)) {
				if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
					System.out.println("node deleted");
				} else if (event.getType() == Watcher.Event.EventType.NodeCreated) {
					System.out.println("node created");
				} else {
					System.out.println("other event");
				}
			}			
		}
	}
	
}

