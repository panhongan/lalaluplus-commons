package com.github.lalalu.utils.bytes;

import java.nio.charset.StandardCharsets;

/**
 * lalalu plus
 */

public class ByteUtils {

    public static byte[] toBytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(double val) {
        return String.valueOf(val).getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(float val) {
        return String.valueOf(val).getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(long val) {
        return String.valueOf(val).getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(int val) {
        return String.valueOf(val).getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(short val) {
        return String.valueOf(val).getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(char val) {
        return String.valueOf(val).getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(boolean val) {
        return String.valueOf(val).getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(Double val) {
        return val.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(Float val) {
        return val.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(Long val) {
        return val.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(Integer val) {
        return val.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(Short val) {
        return val.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(Character val) {
        return val.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(Boolean val) {
        return val.toString().getBytes(StandardCharsets.UTF_8);
    }

}
