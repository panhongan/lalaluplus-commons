package com.github.panhongan.util;

public class Value {
	
	private String value;
	
	private ValueType value_type = ValueType.UNKNOWN;
	
	public Value(String str, ValueType type) {
		this.value = str;
		this.value_type = type;
		if (type == null) {
			value_type = ValueType.UNKNOWN;
		}
	}
	
	public void setValue(String str, ValueType type) {
		this.value = str;
		this.value_type = type;
		if (type == null) {
			value_type = ValueType.UNKNOWN;
		}
	}
	
	public String getInternal() {
		return value;
	}
	
	public ValueType getValueType() {
		return value_type;
	}
	
	@Override
	public String toString() {
		return "(" + StringUtil.toString(this.value) + ", " + value_type.toString() + ")";
	}
	
	public boolean toBoolean() throws ValueTypeException {
		if (ValueType.BOOLEAN.equals(value_type)) {
			try {
				return Boolean.valueOf(value);
			} catch (Exception e) {
				throw new ValueTypeException(e.getMessage());
			}
		} else {
			throw new ValueTypeException("Invalid type");
		}
	}
	
	public byte toByte() throws ValueTypeException {
		if (ValueType.BYTE.equals(value_type)) {
			try {
				return Byte.valueOf(value);
			} catch (Exception e) {
				throw new ValueTypeException(e.getMessage());
			}
		} else {
			throw new ValueTypeException("Invalid type");
		}
	}
	
	public short toShort() throws ValueTypeException {
		if (value_type.isBetween(ValueType.BYTE, ValueType.SHORT)) {
			try {
				return Short.valueOf(value);
			} catch (Exception e) {
				throw new ValueTypeException(e.getMessage());
			}
		} else {
			throw new ValueTypeException("Invalid type");
		}
	}
	
	public int toInt() throws ValueTypeException {
		if (value_type.isBetween(ValueType.BYTE, ValueType.INT)) {
			try {
				return Integer.valueOf(value);
			} catch (Exception e) {
				throw new ValueTypeException(e.getMessage());
			}
		} else {
			throw new ValueTypeException("Invalid type");
		}
	}
	
	public long toLong() throws ValueTypeException {
		if (value_type.isBetween(ValueType.BYTE, ValueType.LONG)) {
			try {
				return Long.valueOf(value);
			} catch (Exception e) {
				throw new ValueTypeException(e.getMessage());
			}
		} else {
			throw new ValueTypeException("Invalid type");
		}
	}
	
	public float toFloat() throws ValueTypeException {
		if (value_type.isBetween(ValueType.BYTE, ValueType.FLOAT)) {
			try {
				return Float.valueOf(value);
			} catch (Exception e) {
				throw new ValueTypeException(e.getMessage());
			}
		} else {
			throw new ValueTypeException("Invalid type");
		}
	}
	
	public double toDouble() throws ValueTypeException {
		if (value_type.isBetween(ValueType.BYTE, ValueType.DOUBLE)) {
			try {
				return Double.valueOf(value);
			} catch (Exception e) {
				throw new ValueTypeException(e.getMessage());
			}
		} else {
			throw new ValueTypeException("Invalid type");
		}
	}

	public static class ValueType {
		
		public static final ValueType UNKNOWN = new ValueType(-1);
		
		public static final ValueType STRING = new ValueType(0);
		
		public static final ValueType BOOLEAN = new ValueType(1);
		
		// unsigned 16bits
		// public static final ValueType CHAR = new ValueType(2);
		
		// signed
		public static final ValueType BYTE = new ValueType(3);
		
		public static final ValueType SHORT = new ValueType(4);
		
		public static final ValueType INT = new ValueType(5);
		
		public static final ValueType LONG = new ValueType(6);
		
		public static final ValueType FLOAT = new ValueType(7);
		
		public static final ValueType DOUBLE = new ValueType(8);
		
		private int type = -1;
		
		private ValueType(int type) {
			this.type = type;
		}
		
		public int getType() {
			return type;
		}
		
		public boolean isGreaterThan(ValueType value_type) {
			boolean is_gt = false;
			
			if (value_type != null) {
				is_gt = (type > value_type.getType());
			}
			
			return is_gt;
		}
		
		public boolean isGreaterOrEqual(ValueType value_type) {
			boolean is_gt = false;
			
			if (value_type != null) {
				is_gt = (type >= value_type.getType());
			}
			
			return is_gt;
		}
		
		public boolean isLessThan(ValueType value_type) {
			boolean is_gt = false;
			
			if (value_type != null) {
				is_gt = (type < value_type.getType());
			}
			
			return is_gt;
		}
		
		public boolean isLessOrEqual(ValueType value_type) {
			boolean is_gt = false;
			
			if (value_type != null) {
				is_gt = (type <= value_type.getType());
			}
			
			return is_gt;
		}
		
		public boolean isEqual(ValueType value_type) {
			return this.equals(value_type);
		}
		
		public boolean isBetween(ValueType value_type1, ValueType value_type2) {
			return (this.isGreaterOrEqual(value_type1) && 
					this.isLessOrEqual(value_type2)); 
		}
		
		@Override
		public boolean equals(Object obj) {
			boolean is_equal = false;
			
			if (obj != null) {
				if (obj instanceof ValueType) {
					is_equal = (type == ((ValueType)obj).getType());
				} else if (obj instanceof Integer) {
					is_equal = (type == ((Integer)obj).intValue());
				}
			}
			
			return is_equal;
		}
		
		@Override
		public String toString() {
			return StringUtil.toString(type);
		}
		
	} // end class ValueType
	
	public static class ValueTypeException extends Exception {
		
		private static final long serialVersionUID = -5892087616645686388L;

		public ValueTypeException(String err) {
			super(err);
		}
		
	} // end class ValueTypeException
	
}
