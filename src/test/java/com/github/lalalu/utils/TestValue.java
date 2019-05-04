package com.github.lalalu.utils;

import com.github.lalalu.utils.Value.ValueType;

/**
 * lalalu plus
 */
public class TestValue {

	public static void testValue(Value value) {
		System.out.println(value.toString());

		try {
			System.out.println(value.toBoolean());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println(value.toByte());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println(value.toShort());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println(value.toInt());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println(value.toLong());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println(value.toFloat());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println(value.toDouble());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {

		try {
			System.out.println("\n**** test String ****");
			Value value = new Value("abc", ValueType.STRING);
			testValue(value);

			System.out.println("\n**** test Boolean ****");
			value = new Value("false", ValueType.BOOLEAN);
			testValue(value);

			System.out.println("\n**** test Byte ****");
			value = new Value("-123", ValueType.BYTE);
			testValue(value);

			System.out.println("\n**** test Short ****");
			value = new Value("-123", ValueType.SHORT);
			testValue(value);

			System.out.println("\n**** test Int ****");
			value = new Value("123", ValueType.INT);
			testValue(value);

			System.out.println("\n**** test Long ****");
			value = new Value("123", ValueType.LONG);
			testValue(value);

			System.out.println("\n**** test Float ****");
			value.setValue("123.4f", null);
			testValue(value);

			System.out.println("\n**** test Double ****");
			value = new Value("123", ValueType.DOUBLE);
			testValue(value);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
