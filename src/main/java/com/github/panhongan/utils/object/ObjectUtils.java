package com.github.panhongan.utils.object;

import java.util.List;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.apache.commons.collections4.CollectionUtils;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */

public class ObjectUtils {

    private static final Validator VALIDATOR = new Validator();

    /**
     * @param object Any object
     */
    public static void validateObject(Object object) {
        List<ConstraintViolation> violations = VALIDATOR.validate(object);
        if (CollectionUtils.isNotEmpty(violations)) {
            throw new RuntimeException(violations.get(0).getMessage());
        }
    }
}
