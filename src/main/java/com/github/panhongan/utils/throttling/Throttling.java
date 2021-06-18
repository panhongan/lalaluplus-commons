package com.github.panhongan.utils.throttling;

import com.github.panhongan.utils.function.ThrowableFunction;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Throttling
 *
 * @author lalalu plus
 * @since 2019.6.5
 */

public class Throttling {

    private static final Logger logger = LoggerFactory.getLogger(Throttling.class);

    /**
     * @param throttlingCounter ThrottlingCounter
     * @param function Function
     * @param input Input
     * @param throttlingResult Output
     * @param <I> Input type
     * @param <O> Output type
     * @return O Output
     */
    public static <I, O> O throttling(ThrottlingCounter throttlingCounter,
                                      Function<I, O> function,
                                      I input,
                                      O throttlingResult) {
        try {
            if (throttlingCounter.tryEnter()) {
                return function.apply(input);
            } else {
                logger.warn("trigger throttling : {}", throttlingCounter.toString());
                return throttlingResult;
            }
        } finally {
            throttlingCounter.leave();
        }
    }

    /**
     * @param throttlingCounter ThrottlingCounter
     * @param function Function
     * @param input Input
     * @param throttlingResult Output
     * @param <I> Input type
     * @param <O> Output type
     * @param <T> Throwable type
     * @return O Output
     * @throws T Throwable object
     */
    public static <I, O, T extends Throwable> O throttling(ThrottlingCounter throttlingCounter,
                                                           ThrowableFunction<I, O, T> function,
                                                           I input,
                                                           O throttlingResult) throws T {
        try {
            if (throttlingCounter.tryEnter()) {
                return function.apply(input);
            } else {
                logger.warn("trigger throttling : {}", throttlingCounter.toString());
                return throttlingResult;
            }
        } catch (Throwable t) {
            logger.warn("", t);
            throw t;
        } finally {
            throttlingCounter.leave();
        }
    }
}
