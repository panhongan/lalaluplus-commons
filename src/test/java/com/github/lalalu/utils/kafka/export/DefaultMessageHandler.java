package com.github.lalalu.utils.kafka.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.lalalu.utils.kafka.handler.AbstractMessageHandler;

/**
 * lalalu plus
 */
public class DefaultMessageHandler extends AbstractMessageHandler {

	@Override
	public boolean init() {
		return true;
	}

	@Override
	public void uninit() {
	}

	@Override
	public Map<String, List<String>> handle(String str) {
		Map<String, List<String>> ret = new HashMap<String, List<String>>();
		ret.put("abc", new ArrayList<String>());
		return ret;
	}

	@Override
	public Object clone() {
		return new DefaultMessageHandler();
	}

}
