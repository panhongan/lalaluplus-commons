package com.github.panhongan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSUtil {
	
	private static Logger logger = LoggerFactory.getLogger(OSUtil.class);
	
	public static OSType getOSType() {
		OSType type = OSType.UNKNOWN;
		
		try {
			String os = System.getProperty("os.name");
			if (os != null) {
				String os_ = os.toLowerCase();
				if (os_.contains("windows")) {
					type = OSType.WIN;
				} else if (os_.contains("mac")) {
					type = OSType.MAC;
				} else if (os_.contains("linux") || 
						os_.contains("centos") || 
						os_.contains("solaris")){
					type = OSType.LINUX;
				} else {
					logger.warn("unknown os : {}", os);
				}
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		
		return type;
	}

	public static class OSType {

		public static final OSType UNKNOWN = new OSType("unknown");

		public static final OSType WIN = new OSType("windows");

		public static final OSType MAC = new OSType("mac");

		public static final OSType LINUX = new OSType("linux");

		private String type = null;

		private OSType(String type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return type;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (obj instanceof OSType) {
				return type.contentEquals(((OSType) obj).type);
			} else if (obj instanceof String) {
				return type.contentEquals((String) obj);
			}

			return false;
		}

	} // end class OSType

}
