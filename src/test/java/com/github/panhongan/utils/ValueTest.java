package com.github.panhongan.utils;

import org.junit.Test;

/**
 * lalalu plus
 */
public class ValueTest {

    @Test (expected = IllegalArgumentException.class)
	public void testToBoolean_TypeException() {
        Value value = new Value("true", Value.ValueType.STRING);
        value.toBoolean();
    }

    @Test
    public void testToBoolean_InvalidValue() {
        Value value = new Value("abc", Value.ValueType.BOOLEAN);
        assert (!value.toBoolean());
    }

    @Test
    public void testToBoolean_Ok() {
        Value value = new Value("false", Value.ValueType.BOOLEAN);
        assert (!value.toBoolean());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testToByte_TypeException() {
        Value value = new Value("a", Value.ValueType.STRING);
        value.toByte();
    }

    @Test (expected = NumberFormatException.class)
    public void testToByte_ValueException() {
        Value value = new Value("abc", Value.ValueType.BYTE);
        value.toByte();
    }

    @Test
    public void testToByte_Ok() {
        Value value = new Value("97", Value.ValueType.BYTE);
        System.out.println(value.toByte());
        assert (value.toByte() == (byte) 97);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testToChar_TypeException() {
        Value value = new Value("a", Value.ValueType.STRING);
        value.toChar();
    }

    @Test
    public void testToChar_InvalidValue() {
        Value value = new Value("abc", Value.ValueType.CHAR);
        assert (value.toChar() == 'a');
    }

    @Test
    public void testToChar_Ok() {
        Value value = new Value("a", Value.ValueType.CHAR);
        assert (value.toChar() == 'a');
    }

    @Test (expected = IllegalArgumentException.class)
    public void testToInt_TypeException() {
        Value value = new Value("123", Value.ValueType.STRING);
        value.toInt();
    }

    @Test (expected = NumberFormatException.class)
    public void testToInt_ValueException() {
        Value value = new Value("abc", Value.ValueType.INT);
        value.toInt();
    }

    @Test
    public void testToInt_Ok() {
        Value value = new Value("123", Value.ValueType.INT);
        assert (value.toInt() == 123);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testToShort_TypeException() {
        Value value = new Value("123", Value.ValueType.STRING);
        value.toShort();
    }

    @Test (expected = NumberFormatException.class)
    public void testToShort_ValueException() {
        Value value = new Value("abc", Value.ValueType.SHORT);
        value.toShort();
    }

    @Test
    public void testToShort_Ok() {
        Value value = new Value("123", Value.ValueType.SHORT);
        assert (value.toShort() == 123);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testToLong_TypeException() {
        Value value = new Value("123", Value.ValueType.STRING);
        value.toLong();
    }

    @Test (expected = NumberFormatException.class)
    public void testToLong_ValueException() {
        Value value = new Value("abc", Value.ValueType.LONG);
        value.toLong();
    }

    @Test
    public void testToLong_Ok() {
        Value value = new Value("123", Value.ValueType.LONG);
        assert (value.toLong() == 123L);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testToFloat_TypeException() {
        Value value = new Value("123", Value.ValueType.STRING);
        value.toFloat();
    }

    @Test (expected = NumberFormatException.class)
    public void testToFloat_ValueException() {
        Value value = new Value("abc", Value.ValueType.FLOAT);
        value.toFloat();
    }

    @Test
    public void testToFloat_Ok() {
        Value value = new Value("123", Value.ValueType.FLOAT);
        assert ((int) value.toFloat() == 123);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testToDouble_TypeException() {
        Value value = new Value("123", Value.ValueType.STRING);
        value.toDouble();
    }

    @Test (expected = NumberFormatException.class)
    public void testToDoube_ValueException() {
        Value value = new Value("abc", Value.ValueType.DOUBLE);
        value.toDouble();
    }

    @Test
    public void testToDoube_Ok() {
        Value value = new Value("123", Value.ValueType.DOUBLE);
        assert ((int) value.toDouble() == 123);
    }
}
