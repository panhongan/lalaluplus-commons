package com.github.panhongan.utils.function;

@FunctionalInterface
public interface ThrowableConsumer<I, T extends Throwable> {

    void accept(I input) throws T;
}
