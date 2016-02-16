package com.github.panhongan.util.zookeeper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.thread.ControllableThread;

public class ZookeeperSessionMonitor extends ControllableThread {
	
	private static Logger logger = LoggerFactory.getLogger(ZookeeperSessionMonitor.class);
	
	private ZooKeeper zk = null;
	
	private String zk_host = null;
	
	private int timeout = 0;
	
	private Watcher watcher = null;
	
	private Set<SessionHoldable> session_holders = 
			Collections.synchronizedSet(new HashSet<SessionHoldable>());
	
	private Set<SessionExceptionProcessable> session_exception_processors = 
			Collections.synchronizedSet(new HashSet<SessionExceptionProcessable>());
	
	public ZookeeperSessionMonitor(String zk_host, int timeout, Watcher global_watcher) {
		this.zk_host = zk_host;
		this.timeout = timeout;
		this.watcher = global_watcher;
	}
	
	public ZooKeeper getZooKeeperSession() {
		return zk;
	}
	
	public void addSessionHolder(SessionHoldable su) {
		if (su != null) {
			session_holders.add(su);
		}
	}
	
	public void removeSessionHolder(SessionHoldable su) {
		if (su != null) {
			session_holders.remove(su);
		}
	}
	
	public void clearSessionHolder() {
		session_holders.clear();
	}
	
	public void addSessionExceptionPrcoessor(SessionExceptionProcessable processor) {
		if (processor != null) {
			session_exception_processors.add(processor);
		}
	}
	
	public void removeSessionExceptionProcessor(SessionExceptionProcessable processor) {
		session_exception_processors.remove(processor);
	}
	
	public void clearSessionExceptionProcessor() {
		session_exception_processors.clear();
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
		zk = null;
		
		this.clearSessionExceptionProcessor();
		this.clearSessionHolder();
	}
	
	@Override
	protected void work() {
		if (zk == null) {
			zk = ZKUtil.connectZK(zk_host, timeout, watcher);
			if (zk != null) {
				this.notifySessionUpdaters();
			}
		}
		
		try {
			if (zk != null) {
				zk.exists("/abc", false);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
			logger.warn("Exception class : %s", e.getClass().getName());
			
			this.processConnectionException();
			
			ZKUtil.closeZK(zk);
			zk = null;
		}
	}
	
	private void notifySessionUpdaters() {
		if (zk != null) {
			for (SessionHoldable sh : session_holders) {
				sh.set(zk);
			}
		}
	}
	
	private void processConnectionException() {
		for (SessionExceptionProcessable sep : session_exception_processors) {
			sep.process();
		}
	}

}
