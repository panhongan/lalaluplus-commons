package com.github.panhongan.utils.object;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */

public class ObjectUtils {

    private static Validator validator = new Validator();

    public static void validateObject(Object object) {
        List<ConstraintViolation> violations = validator.validate(object);
        if (CollectionUtils.isNotEmpty(violations)) {
            throw new RuntimeException(violations.get(0).getMessage());
        }
    }
}
