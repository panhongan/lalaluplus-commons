package com.github.panhongan.util.web.url;

public class HTTPConstants {
	
	public static class Method {
		
		public static final Method GET = new Method("get");
		
		public static final Method PUT = new Method("put");
		
		public static final Method POST = new Method("post");
		
		public static final Method DELETE = new Method("delete");
		
		public static final Method HEAD = new Method("head");
		
		public static final Method TRACE = new Method("trace");
		
		public static final Method OPTIONS = new Method("options");
		
		public static final Method CONNECT = new Method("connect");
		
		private String method = null;
		
		private Method(String method) {
			this.method = method;
		}
		
		@Override
		public String toString() {
			return method;
		}
		
		@Override
		public boolean equals(Object obj) {
			boolean is_equal = false;
			
			if (method != null && obj != null) {
				if (obj instanceof Method) {
					is_equal = method.contentEquals(((Method)obj).method);
				} else if (obj instanceof String) {
					is_equal = method.contentEquals((String)obj);
				}
			}
			
			return is_equal;
		}
	}

}
