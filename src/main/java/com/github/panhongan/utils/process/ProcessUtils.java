package com.github.panhongan.utils.process;

import java.lang.management.ManagementFactory;
import java.util.function.Supplier;
import javax.annotation.Nonnull;

/**
 * @author lalalu plus
 * @since 2017.5.6
 */
public class ProcessUtils {

    private static final Supplier<Integer> PID_SUPPLIER = ProcessUtils::getPidExec;

    private static final Supplier<String> JAVA_CMD_SUPPLIER = ProcessUtils::getJavaCmdExec;

    @Nonnull
    public static Integer getPid() {
        return PID_SUPPLIER.get();
    }

    @Nonnull
    public static String getJavaCmd() {
        return JAVA_CMD_SUPPLIER.get();
    }

    @Nonnull
    public static String getMainName() {
        return getJavaCmd().split(" ")[0];
    }

    /**
     * @return Java command options
     */
    public static String getCmdOptions() {
        String cmd = getJavaCmd();
        int pos = cmd.indexOf(' ');
        if (pos > 0) {
            return cmd.substring(cmd.indexOf(' ') + 1);
        } else {
            return null;
        }
    }

    private static Integer getPidExec() {
        try {
            String name = ManagementFactory.getRuntimeMXBean().getName();
            return Integer.parseInt(name.substring(0, name.indexOf('@')));
        } catch (Throwable t) {
            return null;
        }
    }

    private static String getJavaCmdExec() {
        return System.getProperty("sun.java.command");
    }

}
