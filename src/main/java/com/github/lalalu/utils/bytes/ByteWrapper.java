package com.github.lalalu.utils.bytes;

import com.github.lalalu.utils.function.ThrowableFunction;
import com.google.common.base.Preconditions;

/**
 * lalalu plus
 */

public class ByteWrapper {

    private byte[] bytes;

    public ByteWrapper(byte[] bytes) {
        Preconditions.checkNotNull(bytes);
        this.bytes = bytes;
    }

    public String getAsString() {
        return new String(this.bytes);
    }

    public int getAsInt() {
        return Integer.valueOf(this.getAsString());
    }

    public long getAsLong() {
        return Long.valueOf(this.getAsString());
    }

    public float getAsFloat() {
        return Float.valueOf(this.getAsString());
    }

    public double getAsDouble() {
        return Double.valueOf(this.getAsString());
    }

    public boolean getAsBoolean() {
        return Boolean.valueOf(this.getAsString());
    }

    public <T> T getAsProto(ThrowableFunction<byte[], T, Throwable> func) throws Throwable {
        return (T) func.apply(this.bytes);
    }

}
