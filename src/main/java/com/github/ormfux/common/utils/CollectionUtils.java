package com.github.ormfux.common.utils;

import static com.github.ormfux.common.utils.NullableUtils.isNull;
import static com.github.ormfux.common.utils.NullableUtils.nonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public final class CollectionUtils {
    
    private CollectionUtils() {
        throw new IllegalAccessError(CollectionUtils.class.getSimpleName() + " class is not intended to be instantiated");
    }
    
    /**
     * Maps from source collection to a target collection.
     * 
     * @param <S> source type
     * @param <T> target type
     * @param source source collection
     * @param result target collection
     * @param functor mapping functor
     * @return target collection
     */
    public static <S, T> Collection<T> map(final S[] source, final Collection<T> result, final Function<S, T> functor) {
        Objects.requireNonNull(result);
        Objects.requireNonNull(functor);
        
        for (final S sourceElement : source) {
            result.add(functor.apply(sourceElement));
        }
        
        return result;
    }
    
    /**
     * Maps from source collection to a target collection. ONly elements from the source collection that satisfy the filter
     * condition will be mapped and included in the result.
     * 
     * @param <S> source type
     * @param <T> target type
     * @param source source collection
     * @param result target collection
     * @param filterCondition the filter condition
     * @param mapFunction mapping functor
     * @return target collection
     */
    public static <S, T> Collection<T> filterAndMap(final Collection<? extends S> source, final Collection<T> result, final Predicate<S> filterCondition, final Function<S, T> mapFunction) {
        Objects.requireNonNull(result);
        Objects.requireNonNull(filterCondition);
        Objects.requireNonNull(mapFunction);
        
        for (final S sourceElement : source) {
            if (filterCondition.test(sourceElement)) {
                result.add(mapFunction.apply(sourceElement));
            }
        }
        
        return result;
    }
    
    
    /**
     * Maps from source collection to a target collection.
     * 
     * @param <S> source type
     * @param <T> target type
     * @param source source collection
     * @param result target collection
     * @param functor mapping functor
     * @return target collection
     */
    public static <S, T> Collection<T> map(final Collection<? extends S> source, final Collection<T> result, final Function<S, T> functor) {
        Objects.requireNonNull(result);
        Objects.requireNonNull(functor);
        
        for (final S sourceElement : source) {
            result.add(functor.apply(sourceElement));
        }
        
        return result;
    }
    
    /**
     * Maps from source collection to a target array.
     * 
     * @param source source collection
     * @param functor mapping functor
     * @return target array
     * 
     * @param <S> source type
     * @param <T> target type
     * 
     */
    @SuppressWarnings("unchecked")
    public static <S, T> T[] mapToArray(final Collection<S> source, final Function<S, T> functor) {
        Objects.requireNonNull(functor);
        
        final List<T> resultList = new ArrayList<>(source.size());
        
        for (final S sourceElement : source) {
            final T executeResult = functor.apply(sourceElement);
            
            if (nonNull(executeResult)) {
                // add the non-null results to the list only
                resultList.add(executeResult);
            }
        }
        
        return (T[]) resultList.toArray();
    }
    
    /**
     * Filters a source collection and add result to a target collection.
     * 
     * @param <T> source/target type
     * @param source source collection
     * @param result target collection
     * @param predicate filtering predicate
     * @return target array
     */
    public static <T> Collection<T> filter(final Collection<T> source, final Collection<T> result, final Predicate<T> predicate) {
        Objects.requireNonNull(result);
        Objects.requireNonNull(predicate);
        
        for (final T sourceElement : source) {
            if (predicate.test(sourceElement)) {
                result.add(sourceElement);
            }
        }
        return result;
    }
    
    /**
     * Checks whether a collection is {@code null} or empty.
     * 
     * @param <T> type
     * @param collection collection
     * @return true, if {@code null} or empty
     */
    public static <T> boolean isEmpty(final Collection<T> collection) {
        return NullableUtils.check(collection, NullableUtils.isEmpty(), true);
    }
    
    /**
     * Returns the first element from the given collection that evaluates the given predicate to
     * {@code true}.
     * 
     * @param <T> Parameterized type.
     * 
     * @param collection a collection.
     * @param predicate The predicate.
     * @return the first element from the given collection that evaluates the given predicate to
     *         {@code true}; {@code null} when there is none.
     */
    public static <T> T selectFirst(final Collection<T> collection, final Predicate<T> predicate) {
        Objects.requireNonNull(predicate);
        
        for (final T sourceElement : collection) {
            if (predicate.test(sourceElement)) {
                return sourceElement;
            }
        }
        
        return null;
    }
    
    /**
     * Determines whether an element in the given collection evaluates the given predicate to
     * {@code true}.
     * 
     * @param <T> Parameterized type.
     * 
     * @param collection a collection.
     * @param predicate a predicate.
     * 
     * @return {@code true} when there is an element in the collection fulfilling the predicate.
     */
    public static <T> boolean exists(final Collection<T> collection, final Predicate<T> predicate) {
        Objects.requireNonNull(predicate);
        
        for (final T sourceElement : collection) {
            if (predicate.test(sourceElement)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Compares two collections for equality. The elements in both collections should be unique,
     * otherwise the result might not be very accurate.
     * 
     * @param <T> Parameterized type.
     * 
     * @param col1 source collection.
     * @param col2 target collection.
     * 
     * @return {@code true} both collections have the same size and contain the same elements.
     */
    public static <T> boolean isEqual(final Collection<T> col1, final Collection<T> col2) {
        if (col1 == col2) {
            return true;
        } else if ((isNull(col1) && nonNull(col2)) || (nonNull(col1) && isNull(col2))) {
            return false;
        } else if (col1.size() != col2.size()) {
            return false;
        } else {
            final List<T> remainingContents = new ArrayList<T>(col1);
            
            for (final T col2Element : col2) {
                if (!remainingContents.remove(col2Element)) {
                    return false;
                }
            }
            
            return remainingContents.isEmpty();
            
        }
    }
    
    /**
     * Converts a collection to a map. The function is used to produce the map keys.
     * 
     * @param <K> key type
     * @param <E> entry type
     * 
     * @param source source collection
     * @param keyFunction Function to produce the map keys.
     * @return The map.
     */
    public static <K, E> Map<K, E> asMap(final Collection<E> source, final Function<E, K> keyFunction) {
        return asMap(source, keyFunction, Function.identity());
    }
    
    /**
     * Converts a collection to a map.
     * 
     * @param <K> key type
     * @param <V> map value type
     * @param <E> collection element type
     * 
     * @param source source collection
     * @param keyFunction Function to produce the map keys.
     * @param valueFunction Function to produce the map values.
     * @return The collection as map.
     */
    public static <K, V, E> Map<K, V> asMap(final Collection<E> source, 
                                            final Function<E, K> keyFunction,
                                            final Function<E, V> valueFunction) {
        final Map<K, V> map = new HashMap<>();
        
        for (final E sourceElement : source) {
            map.put(keyFunction.apply(sourceElement), valueFunction.apply(sourceElement));
        }
        
        return map;
    }
    
}
