package com.github.panhongan.utils.function;

/**
 * @author lalalu plus
 * @since 2019.8.7
 *
 * @param <I> Input
 * @param <T> Throwable
 */

@FunctionalInterface
public interface ThrowableConsumer<I, T extends Throwable> {

    void accept(I input) throws T;
}
