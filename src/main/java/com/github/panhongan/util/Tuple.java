package com.github.panhongan.util;

public class Tuple {
	
	public static class Tuple2<T1, T2> {

		public T1 _1;
		
		public T2 _2;
		
		public Tuple2(T1 t1, T2 t2) {
			this._1 = t1;
			this._2 = t2;
		}
		
		@Override
		public String toString() {
			return "Tuple2(" + StringUtil.toString(_1) + "," + StringUtil.toString(_2) + ")";
		}
	} // end class Tuple2
	
	public static class Tuple3<T1, T2, T3> {

		public T1 _1;
		
		public T2 _2;
		
		public T3 _3;
		
		public Tuple3(T1 t1, T2 t2, T3 t3) {
			this._1 = t1;
			this._2 = t2;
			this._3 = t3;
		}
		
		@Override
		public String toString() {
			return "Tuple3(" + StringUtil.toString(_1) + "," + StringUtil.toString(_2) +
				"," + StringUtil.toString(_3) + ")";
		}
	} // end class Tuple3
	
	public static class Tuple4<T1, T2, T3, T4> {

		public T1 _1;
		
		public T2 _2;
		
		public T3 _3;
		
		public T4 _4;
		
		public Tuple4(T1 t1, T2 t2, T3 t3, T4 t4) {
			this._1 = t1;
			this._2 = t2;
			this._3 = t3;
			this._4 = t4;
		}
		
		@Override
		public String toString() {
			return "Tuple4(" + StringUtil.toString(_1) + "," + StringUtil.toString(_2) +
				"," + StringUtil.toString(_3) + "," + StringUtil.toString(_4) + ")";
		}
	} // end class Tuple4
	
	public static class Tuple5<T1, T2, T3, T4, T5> {

		public T1 _1;
		
		public T2 _2;
		
		public T3 _3;
		
		public T4 _4;
		
		public T5 _5;
		
		public Tuple5(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
			this._1 = t1;
			this._2 = t2;
			this._3 = t3;
			this._4 = t4;
			this._5 = t5;
		}
		
		@Override
		public String toString() {
			return "Tuple5(" + StringUtil.toString(_1) + "," + StringUtil.toString(_2) +
				"," + StringUtil.toString(_3) + "," + StringUtil.toString(_4) + 
				"," + StringUtil.toString(_5) +  ")";
		}
	} // end class Tuple5

}
