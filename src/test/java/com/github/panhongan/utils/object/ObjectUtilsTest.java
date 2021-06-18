package com.github.panhongan.utils.object;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import org.junit.Test;

/**
 * @author panhongan
 * @since 2019.7.14
 */

public class ObjectUtilsTest {

    @Test (expected = RuntimeException.class)
    public void testValidateObject_Exception() {
        ObjectUtils.validateObject(new TestObj());
    }

    @Test
    public void testValidateObject_Ok() {
        TestObj obj = new TestObj();
        obj.name = "hello";
        ObjectUtils.validateObject(obj);
    }

    private class TestObj {
        @NotNull
        @NotBlank
        public String name;

        public Integer age;

        public Float height;

        public Double weight;
    }
}
