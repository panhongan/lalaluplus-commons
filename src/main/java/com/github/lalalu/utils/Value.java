package com.github.lalalu.utils;

import com.google.common.base.Preconditions;

/**
 * lalalu plus
 */

public class Value {
	
	private String value;
	
	private ValueType valueType;
	
	public Value(String str, ValueType type) {
		this.setValue(str, type);
	}
	
	public void setValue(String str, ValueType type) {
		this.value = str;
		this.valueType = type;
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(type);
	}
	
	public String getValue() {
		return value;
	}
	
	public ValueType getValueType() {
		return valueType;
	}
	
	@Override
	public String toString() {
		return "(" + this.value + ", " + valueType.toString() + ")";
	}
	
	public boolean toBoolean() throws RuntimeException {
		if (ValueType.BOOLEAN == valueType) {
			try {
				return Boolean.valueOf(value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("Invalid type");
		}
	}
	
	public byte toByte() throws RuntimeException {
        if (ValueType.BYTE == valueType) {
            try {
                return Byte.valueOf(value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Invalid type");
        }
    }

    public char toChar() throws RuntimeException {
        if (ValueType.CHAR == valueType) {
            try {
                return value.charAt(0);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Invalid type");
        }
    }
	
	public short toShort() throws RuntimeException {
		if (ValueType.SHORT == valueType) {
			try {
				return Short.valueOf(value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("Invalid type");
		}
	}
	
	public int toInt() throws RuntimeException {
		if (ValueType.INT == valueType) {
			try {
				return Integer.valueOf(value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("Invalid type");
		}
	}
	
	public long toLong() throws RuntimeException {
		if (ValueType.LONG == valueType) {
			try {
				return Long.valueOf(value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("Invalid type");
		}
	}
	
	public float toFloat() throws RuntimeException {
		if (ValueType.FLOAT == valueType) {
			try {
				return Float.valueOf(value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("Invalid type");
		}
	}
	
	public double toDouble() throws RuntimeException {
		if (ValueType.DOUBLE == valueType) {
			try {
				return Double.valueOf(value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("Invalid type");
		}
	}

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
		
		public int getType() {
			return type;
		}
		
	} // end class ValueType
	
}
