package pha.java.util;


public class OSType {
	
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

}

