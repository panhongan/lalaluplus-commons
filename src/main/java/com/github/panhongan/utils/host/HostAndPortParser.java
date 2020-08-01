package com.github.panhongan.utils.host;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.net.HostAndPort;

/**
 * lalalu plus
 */

public class HostAndPortParser {

	/**
	 * @param uri ip:port,ip:port,ip:port
	 * @return host and port list
	 */
	public static List<HostAndPort> parse(String uri) {
		try {
			String[] arr = uri.split("[,]");
			if (ArrayUtils.isNotEmpty(arr)) {
				List<HostAndPort> list = new ArrayList<>();
				for (int i = 0; i < arr.length; ++i) {
					list.add(HostAndPort.fromString(arr[i]));
				}
				return list;
			} else {
				throw new RuntimeException("Invalid URI : " + uri);
			}
		} catch (Exception e) {
			throw new RuntimeException("Invalid URI : " + uri);
		}
	}
}
