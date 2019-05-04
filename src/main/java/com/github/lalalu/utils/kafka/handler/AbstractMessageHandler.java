package com.github.lalalu.utils.kafka.handler;

import java.util.List;
import java.util.Map;

import com.github.lalalu.utils.control.Lifecycleable;

/**
 * lalalu plus
 */
public abstract class AbstractMessageHandler implements Lifecycleable {

	public abstract Map<String, List<String>> handle(String str);

	public abstract Object clone();

}
