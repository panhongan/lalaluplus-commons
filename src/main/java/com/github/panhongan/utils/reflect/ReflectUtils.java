package com.github.panhongan.utils.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author panhongan
 * @version 1.0
 * @since 2019.7.13
 */

public class ReflectUtils {

    private static Map<Class, Collection<Field>> classFieldsMap = new ConcurrentHashMap<>();

    private static Map<String, Class> classMap = new ConcurrentHashMap<>();


    /**
     * @param c Class
     * @return Fields for @param c
     */
    public static Collection<Field> getClassBeanFieldFast(Class c) {
        Collection<Field> fields = classFieldsMap.get(c);
        if (fields != null) {
            return fields;
        }

        return classFieldsMap.computeIfAbsent(c, ReflectUtils::getAllFields);
    }

    /**
     * @param className Class name
     * @return Class object for className
     * @throws Exception Any Exception
     */
    public static Class getClassFast(String className) throws Exception {
        Class c = classMap.get(className);
        if (c != null) {
            return c;
        }

        c = Class.forName(className);
        classMap.put(className, c);
        return c;
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> c) throws Exception {
        return (T) getClassFast(c.getName()).newInstance();
    }

    static Collection<Field> getAllFields(Class c) {
        Collection<Field> fields = new ArrayList<>();
        Class tmp = c;

        while (tmp != null) {
            Field[] arr = tmp.getDeclaredFields();
            if (ArrayUtils.isNotEmpty(arr)) {
                for (Field field : arr) {
                    int modifier = field.getModifiers();
                    if (modifier == Modifier.PRIVATE
                            || modifier == Modifier.PROTECTED
                            || modifier == Modifier.PUBLIC) {
                        field.setAccessible(true);
                        fields.add(field);
                    }
                }
            }

            tmp = tmp.getSuperclass();
        }

        return fields;
    }
}
