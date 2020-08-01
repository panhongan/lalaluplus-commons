package com.github.panhongan.utils.collection;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * lalalu plus
 */

public class CollectionUtils {

	public static String join(Collection<?> co, String seperator) {
		return co.stream().map(x -> x.toString()).collect(Collectors.joining(seperator));
	}

}
