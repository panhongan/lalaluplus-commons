package com.github.lalalu.utils.process;

import javax.annotation.Nonnull;

import java.lang.management.ManagementFactory;
import java.util.function.Supplier;

/**
 * lalalu plus
 */
public class ProcessUtils {

    private static Supplier<Integer> pidSupplier = () -> getPidExec();

    private static Supplier<String> javaCmdSupplier = () -> getJavaCmdExec();

    @Nonnull
    public static Integer getPid() {
        return pidSupplier.get();
    }

    @Nonnull
    public static String getJavaCmd() {
        return javaCmdSupplier.get();
    }

    @Nonnull
    public static String getMainName() {
        return getJavaCmd().split(" ")[0];
    }

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
