package com.github.ormfux.common.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public final class ArrayUtils {
    
    private ArrayUtils() {
        throw new UnsupportedOperationException("This class is not supposed to be instantiated.");
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T[] filter(final T[] array, final Predicate<T> filter) {
        return Arrays.stream(array)
                     .filter(filter)
                     .toArray(length -> (T[]) Array.newInstance(array.getClass().getComponentType(), length));
    }
    
    @SuppressWarnings("unchecked")
    public static <T, R> R[] map(final T[] array, final Function<T, R> mappingFunction, Class<R> targetType) {
        return Arrays.stream(array)
                     .map(mappingFunction)
                     .toArray(length -> (R[]) Array.newInstance(targetType, length));
    }
    
}
