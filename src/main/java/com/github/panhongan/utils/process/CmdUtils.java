package com.github.panhongan.utils.process;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * execute command
 *
 * @author lalalu plus
 * @version 1.0
 * @since 2017.7.10
 */

public class CmdUtils {

    private static final Logger logger = LoggerFactory.getLogger(CmdUtils.class);

    /**
     * @param cmd Command lines
     * @param output Output for executing
     * @param err Error Information for executing
     * @return True if succeed. Else False
     */
    public static boolean exec(String[] cmd, Collection<String> output, Collection<String> err) {
        boolean ret = false;

        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd);
            ret = readData(process, output, err);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            err.add(e.getMessage());
        }

        return ret;
    }

    /**
     * @param process Process object
     * @param output Output for executing
     * @param err Error Information for executing
     * @return True if succeed. Else False
     */
    private static boolean readData(Process process,
                                    Collection<String> output,
                                    Collection<String> err) {
        boolean ret = false;

        try {
            ProcessDataReader outputReader = new ProcessDataReader(process.getInputStream(),
                    output);
            outputReader.start();

            ProcessDataReader errReader = new ProcessDataReader(process.getErrorStream(), err);
            errReader.start();

            outputReader.waitForCompletion();
            errReader.waitForCompletion();

            ret = (process.waitFor() == 0);
        } catch (Exception e) {
            logger.warn("", e);
        }

        return ret;
    }
}
