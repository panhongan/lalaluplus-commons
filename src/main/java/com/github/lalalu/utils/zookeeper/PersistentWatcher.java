package com.github.lalalu.utils.zookeeper;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * lalalu plus
 */

public class PersistentWatcher {

	private static Logger logger = LoggerFactory.getLogger(PersistentWatcher.class);

	private static final int DEFAULT_WATCH_INTERVAL_MILLSECONDS = 1000;

	private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	private ZooKeeper zk;

	private String path;

	private Collection<Watcher> watchers;

	public PersistentWatcher(ZooKeeper zk, String path, Collection<Watcher> watchers) {
		this.zk = zk;
		this.path = path;
		this.watchers = watchers;

		Preconditions.checkNotNull(zk);
		Preconditions.checkNotNull(path);
		Preconditions.checkNotNull(watchers);

		executorService.scheduleWithFixedDelay(this::doWatch,
				DEFAULT_WATCH_INTERVAL_MILLSECONDS,
				DEFAULT_WATCH_INTERVAL_MILLSECONDS,
				TimeUnit.MILLISECONDS);
	}

	private void doWatch() {
		try {
			for (Watcher watcher : watchers) {
				zk.exists(path, watcher);
			}
		} catch (Exception e) {
			logger.warn("", e);
		}
	}

}
