package com.github.lalalu.utils.throttling;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.lalalu.utils.function.ThrowableFunction;

/**
 * lalalu plus
 *
 * <p>
 * I : input
 * O : output
 * T : throwable
 */

public class Throttling {

	private static final Logger logger = LoggerFactory.getLogger(Throttling.class);

	public static <I, O> O throttling(ThrottlingConfig throttlingConfig,
	                                  Function<I, O> function,
	                                  I input,
	                                  O throttlingResult) {
		try {
			if (throttlingConfig.tryEnter()) {
				return function.apply(input);
			} else {
				logger.warn("trigger throttling : {}", throttlingConfig.toString());
				return throttlingResult;
			}
		} finally {
			throttlingConfig.leave();
		}
	}

	public static <I, O, T extends Throwable> O throttling(ThrottlingConfig throttlingConfig,
	                                                       ThrowableFunction<I, O, T> function,
	                                                       I input,
	                                                       O throttlingResult) throws T {
		try {
			if (throttlingConfig.tryEnter()) {
				return function.apply(input);
			} else {
				logger.warn("trigger throttling : {}", throttlingConfig.toString());
				return throttlingResult;
			}
		} catch (Throwable t) {
			logger.warn("", t);
			throw t;
		} finally {
			throttlingConfig.leave();
		}
	}

}
