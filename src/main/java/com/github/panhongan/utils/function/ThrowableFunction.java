package com.github.panhongan.utils.function;

/**
 * @param <I> : input
 * @param <O> : output
 * @param <T> : throwable
 *
 * @author lalalu plus
 * @since 2019.5.6
 */

@FunctionalInterface
public interface ThrowableFunction<I, O, T extends Throwable> {

    O apply(I input) throws T;
}
