package pha.java.util.zookeeper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import pha.java.util.thread.ControllableThread;

public class ZookeeperSessionMonitor extends ControllableThread {
	
	private static Logger logger = Logger.getLogger(ZookeeperSessionMonitor.class);
	
	private ZooKeeper zk = null;
	
	private String zk_host = null;
	
	private int timeout = 0;
	
	private Watcher watcher = null;
	
	private Set<SessionUpdatable> session_subscribers = Collections.synchronizedSet(new HashSet<SessionUpdatable>());
	
	public ZookeeperSessionMonitor(String zk_host, int timeout, Watcher global_watcher) {
		this.zk_host = zk_host;
		this.timeout = timeout;
		this.watcher = global_watcher;
	}
	
	public ZooKeeper getZooKeeperSession() {
		return zk;
	}
	
	public void addSubscriber(SessionUpdatable su) {
		if (su != null) {
			session_subscribers.add(su);
		}
	}
	
	public void removeSubscriber(SessionUpdatable su) {
		if (su != null) {
			session_subscribers.remove(su);
		}
	}
	
	public void clearSubscriber() {
		session_subscribers.clear();
	}
	
	@Override
	public boolean init() {
		boolean ret = false;
		
		zk = ZKUtil.connectZK(zk_host, timeout, watcher);
		if (zk != null) {
			this.start();
			ret = true;
		}

		return ret;
	}
	
	@Override
	public void uninit() {
		super.uninit();
		ZKUtil.closeZK(zk);
	}
	
	@Override
	protected void work() {
		if (zk == null) {
			zk = ZKUtil.connectZK(zk_host, timeout, watcher);
			if (zk != null) {
				this.notifySubscribers();
			}
		}
		
		try {
			if (zk != null) {
				zk.exists("/abc", false);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
			logger.warn("Exception class : " + e.getClass().getName());
			
			ZKUtil.closeZK(zk);
			zk = null;
		}
	}
	
	private void notifySubscribers() {
		if (zk != null) {
			for (SessionUpdatable ss : session_subscribers) {
				ss.set(zk);
			}
		}
	}

}
