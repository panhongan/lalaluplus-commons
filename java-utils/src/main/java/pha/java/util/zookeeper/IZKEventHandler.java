package pha.java.util.zookeeper;

import org.apache.zookeeper.WatchedEvent;

public interface IZKEventHandler {
	
	public void handleCreateEvent(WatchedEvent event);
	
	public void handleDeleteEvent(WatchedEvent event);
	
	public void handleUpdateEvent(WatchedEvent event);

}
