package com.github.lalalu.utils.bytes;

/**
 * lalalu plus
 */

public class TestByteWrapper {

    public static void main(String[] args) {
        byte[] bytes = ByteUtils.toBytes("true");
        System.out.println(new ByteWrapper(bytes).getAsBoolean());

        bytes = ByteUtils.toBytes("123");
        System.out.println(new ByteWrapper(bytes).getAsInt());

        bytes = ByteUtils.toBytes("123");
        System.out.println(new ByteWrapper(bytes).getAsLong());

        bytes = ByteUtils.toBytes("123.0f");
        System.out.println(new ByteWrapper(bytes).getAsFloat());

        bytes = ByteUtils.toBytes("123.01d");
        System.out.println(new ByteWrapper(bytes).getAsDouble());
    }

}
