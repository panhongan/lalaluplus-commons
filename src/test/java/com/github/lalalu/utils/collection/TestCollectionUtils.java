package com.github.lalalu.utils.collection;

import java.util.Arrays;

/**
 * lalalu plus
 */
public class TestCollectionUtils {

    public static void main(String[] args) {
        System.out.println(CollectionUtils.join(Arrays.asList(1, 2, 3, "a"), "."));
        System.out.println(CollectionUtils.join(Arrays.asList(1, 2, 3, 4), ";"));
    }

}
