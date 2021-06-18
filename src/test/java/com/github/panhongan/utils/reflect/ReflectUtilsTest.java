package com.github.panhongan.utils.reflect;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import org.junit.Test;

/**
 * @author panhongan
 * @since 2019.7.14
 */

public class ReflectUtilsTest {

    @Test (expected = NullPointerException.class)
    public void testGetClassBeanFieldFast_Exception() {
        ReflectUtils.getClassBeanFieldFast(null);
    }

    @Test
    public void testGetClassBeanFieldFast_Ok() {
        assert (ReflectUtils.getClassBeanFieldFast(TestObj.class).size() == 4);
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
