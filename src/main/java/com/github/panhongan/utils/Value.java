package com.github.panhongan.utils;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lalalu plus
 * @since 2017.1.7
 * @version 1.0
 */

@Getter
public class Value {

	private String value;

	private ValueType valueType;

	public Value(String str, ValueType type) {
		this.setValue(str, type);
	}

	public void setValue(String str, ValueType type) {
		this.value = str;
		this.valueType = type;
		Preconditions.checkArgument(StringUtils.isNotEmpty(str));
		Preconditions.checkArgument(type != null && type != ValueType.UNKNOWN);
	}

	@Override
	public String toString() {
		return "(" + this.value + ", " + valueType.toString() + ")";
	}

	public boolean toBoolean() {
		Preconditions.checkArgument(ValueType.BOOLEAN == valueType, "not boolean type");
		return Boolean.valueOf(value);
	}

	public byte toByte() throws RuntimeException {
		Preconditions.checkArgument(ValueType.BYTE == valueType, "not byte type");
		return Byte.valueOf(value);
	}

	public char toChar() throws RuntimeException {
		Preconditions.checkArgument(ValueType.CHAR == valueType, "not char type");
		return value.charAt(0);
	}

	public short toShort() throws RuntimeException {
		Preconditions.checkArgument(ValueType.SHORT == valueType, "not short type");
		return Short.valueOf(value);
	}

	public int toInt() throws RuntimeException {
		Preconditions.checkArgument(ValueType.INT == valueType, "not int type");
		return Integer.valueOf(value);
	}

	public long toLong() throws RuntimeException {
		Preconditions.checkArgument(ValueType.LONG == valueType, "not long type");
		return Long.valueOf(value);
	}

	public float toFloat() throws RuntimeException {
		Preconditions.checkArgument(ValueType.FLOAT == valueType, "not float type");
		return Float.valueOf(value);
	}

	public double toDouble() throws RuntimeException {
		Preconditions.checkArgument(ValueType.DOUBLE == valueType, "not double type");
		return Double.valueOf(value);
	}

	@Getter
	public enum ValueType {
		UNKNOWN(-1),
		STRING(0),
		BOOLEAN(1),
		CHAR(2),
		BYTE(3),
		SHORT(4),
		INT(5),
		LONG(6),
		FLOAT(7),
		DOUBLE(8);

		private final int type;

		ValueType(int type) {
			this.type = type;
		}
	} // end class ValueType
}
