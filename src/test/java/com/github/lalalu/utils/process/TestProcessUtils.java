package com.github.lalalu.utils.process;

/**
 * lalalu plus
 */

public class TestProcessUtils {

    public static void main(String[] args) {
        for(int i = 0; i < 100; i++) {
            long begin = System.currentTimeMillis();
            System.out.println(ProcessUtils.getPid() + ", " + (System.currentTimeMillis() - begin));
        }

        for(int i = 0; i < 100; i++) {
            long begin = System.currentTimeMillis();
            System.out.println(ProcessUtils.getJavaCmd() + ", " + (System.currentTimeMillis() - begin));
        }

        System.out.println(ProcessUtils.getMainName());
        System.out.println(ProcessUtils.getCmdOptions());
    }

}
