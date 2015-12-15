package pha.java.util;

import org.apache.log4j.Logger;

public class OSUtil {
	
	private static Logger logger = Logger.getLogger(OSUtil.class);
	
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
					logger.warn("unknown os : " + os);
				}
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		
		return type;
	}

}
