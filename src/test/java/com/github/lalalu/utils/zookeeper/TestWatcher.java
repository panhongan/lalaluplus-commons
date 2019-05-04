package com.github.lalalu.utils.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * lalalu plus
 */
public class TestWatcher implements Watcher {

	private String path;

	public TestWatcher(String path) {
		this.path = path;
	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getPath() != null) {
			System.out.println("znode = " + event.getPath());

			if (event.getPath().contentEquals(path)) {
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

