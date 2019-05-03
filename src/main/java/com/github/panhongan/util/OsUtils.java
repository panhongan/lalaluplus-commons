package com.github.panhongan.util;

/**
 * lalalu plus
 */

public class OsUtils {

    public static OSType getOsType() {
        OSType type = OSType.UNKNOWN;

        String os = System.getProperty("os.name");
        if (os != null) {
            String os_ = os.toLowerCase();
            if (os_.contains("windows")) {
                type = OSType.WINDOWS;
            } else if (os_.contains("mac")) {
                type = OSType.MAC;
            } else if (os_.contains("linux") ||
                    os_.contains("centos") ||
                    os_.contains("solaris")) {
                type = OSType.LINUX;
            } else {
                type = OSType.UNKNOWN;
            }
        }

        return type;
    }

    public enum OSType {
        UNKNOWN("unknown"),
        WINDOWS("windows"),
        MAC("mac"),
        LINUX("linux");

        private final String type;

        OSType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    } // end class OSType

}
