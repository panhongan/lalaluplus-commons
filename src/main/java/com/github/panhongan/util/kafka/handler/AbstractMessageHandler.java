package com.github.panhongan.util.kafka.handler;

import java.util.List;
import java.util.Map;

import com.github.panhongan.util.control.Lifecycleable;

public abstract class AbstractMessageHandler implements Lifecycleable {
	
	public abstract Map<String, List<String>> handle(String str);
	
	public abstract Object clone();
	
}
