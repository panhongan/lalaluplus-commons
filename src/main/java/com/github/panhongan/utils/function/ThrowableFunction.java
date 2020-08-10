package com.github.panhongan.utils.function;

/**
 * lalalu plus
 *
 * @param <I> : input
 * @param <O> : output
 * @param <T> : throwable
 */

@FunctionalInterface
public interface ThrowableFunction<I, O, T extends Throwable> {

	O apply(I input) throws T;
}
