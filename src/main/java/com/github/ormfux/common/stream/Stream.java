package com.github.ormfux.common.stream;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Extension of the default {@link java.util.stream.Stream}. Adds a few methods that
 * make daily use a little more convenient.
 *
 * @param <T>
 */
public class Stream<T> {
    
    /**
     * The original stream.
     */
    private java.util.stream.Stream<T> wrappedStream;
    
    /**
     * Stream for a collection.
     */
    public static <S> Stream<S> of(final Collection<S> collection) {
        return new Stream<>(collection.stream());
    }
    
    /**
     * Stream for an array.
     */
    public static <S> Stream<S> of(final S[] array) {
        return new Stream<>(Arrays.stream(array));
    }
    
    /**
     * Stream for a map.
     */
    public static <K, V> Stream<Entry<K, V>> of(final Map<K, V> map) {
        return of(map.entrySet());
    }
    
    /**
     * Stream for a standard Stream.
     */
    public static <S> Stream<S> of(final java.util.stream.Stream<S> stream) {
        return new Stream<>(stream);
    }
    
    /**
     * A stream of `Optional` from an `Optional` that contains a collection.
     * 
     * @param optional The Optional with the Collection.
     * @return The Stream of Optional elements in the Collection.
     */
    public static <S> Stream<Optional<S>> of(final Optional<Collection<S>> optional) {
        if (optional.isPresent()) {
            return of(optional.get()).map(Optional::ofNullable);
        } else {
            return of(Collections.emptyList());
        }
    }
    
    /**
     * Parallel stream for a collection.
     */
    public static <S> Stream<S> parallelOf(final Collection<S> collection) {
        return new Stream<>(collection.parallelStream());
    }
    
    /**
     * Parallel Stream for a map.
     */
    public static <K, V> Stream<Entry<K, V>> parallelOf(final Map<K, V> map) {
        return of(map.entrySet()).parallel();
    }
    
    /**
     * Parallel Stream for a standard Stream.
     */
    public static <S> Stream<S> parallelOf(final java.util.stream.Stream<S> stream) {
        return new Stream<>(stream.parallel());
    }
    
    /**
     * @param wrappedStream
     */
    Stream(final java.util.stream.Stream<T> wrappedStream) {
        this.wrappedStream = wrappedStream;
    }
    
    /**
     * @see java.util.stream.Stream#iterator()
     */
    public Iterator<T> iterator() {
        return wrappedStream.iterator();
    }

    /**
     * @see java.util.stream.Stream#spliterator()
     */
    public Spliterator<T> spliterator() {
        return wrappedStream.spliterator();
    }

    /**
     * @see java.util.stream.Stream#isParallel()
     */
    public boolean isParallel() {
        return wrappedStream.isParallel();
    }

    /**
     * @see java.util.stream.Stream#sequential()
     */
    public Stream<T> sequential() {
        return new Stream<>(wrappedStream.sequential());
    }

    /**
     * @see java.util.stream.Stream#parallel()
     */
    public Stream<T> parallel() {
        return new Stream<>(wrappedStream.parallel());
    }

    /**
     * @see java.util.stream.Stream#unordered()
     */
    public Stream<T> unordered() {
        return new Stream<>(wrappedStream.unordered());
    }

    /**
     * @see java.util.stream.Stream#onClose(Runnable)
     */
    public Stream<T> onClose(final Runnable closeHandler) {
        return new Stream<>(wrappedStream.onClose(closeHandler));
    }

    /**
     * @see java.util.stream.Stream#close()
     */
    public void close() {
        wrappedStream.close();
    }
    
    /**
     * @see java.util.stream.Stream#filter(Predicate)
     */
    public Stream<T> filter(final Predicate<? super T> predicate) {
        return new Stream<>(wrappedStream.filter(predicate));
    }

    /**
     * Same as {@link java.util.stream.Stream#filter(Predicate)}, but also
     * collects the result directly in a list.
     */
    public List<T> filterToList(final Predicate<? super T> predicate) {
        return wrappedStream.filter(predicate).collect(Collectors.toList());
    }
    
    /**
     * Same as {@link java.util.stream.Stream#filter(Predicate)}, but also
     * collects the result directly in a set.
     */
    public Set<T> filterToSet(final Predicate<? super T> predicate) {
        return wrappedStream.filter(predicate).collect(Collectors.toSet());
    }

    /**
     * Convenience method to filter and collect the stream content in a map.
     * 
     * @param keyMapper Function to create the key from a stream entry.
     * @param valueMapper Function to create the value from a stream entry.
     */
    public <K, V> Map<K, V> fiterToMap(final Predicate<? super T> predicate,
                                  final Function<? super T, ? extends K> keyMapper,
                                  final Function<? super T, ? extends V> valueMapper) {
        return wrappedStream.filter(predicate).collect(Collectors.toMap(keyMapper, valueMapper));
    }

    /**
     * Convenience method to filter and collect the stream content in a map. The values are
     * simply the stream entries.
     * 
     * @param keyMapper Function to create the key from a stream entry.
     */
    public <K> Map<K, T> filterToMap(final Predicate<? super T> predicate, 
                                     final Function<? super T, ? extends K> keyMapper) {
        return wrappedStream.filter(predicate).collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    /**
     * @see java.util.stream.Stream#map(Function)
     */
    public <R> Stream<R> map(final Function<? super T, ? extends R> mapper) {
        return new Stream<>(wrappedStream.map(mapper));
    }

    /**
     * @see java.util.stream.Stream#mapToInt(ToIntFunction)
     */
    public IntStream mapToInt(final ToIntFunction<? super T> mapper) {
        return new IntStream(wrappedStream.mapToInt(mapper));
    }

    /**
     * @see java.util.stream.Stream#mapToLong(ToLongFunction)
     */
    public LongStream mapToLong(final ToLongFunction<? super T> mapper) {
        return new LongStream(wrappedStream.mapToLong(mapper));
    }

    /**
     * @see java.util.stream.Stream#mapToDouble(ToDoubleFunction)
     */
    public DoubleStream mapToDouble(final ToDoubleFunction<? super T> mapper) {
        return new DoubleStream(wrappedStream.mapToDouble(mapper));
    }

    /**
     * @see java.util.stream.Stream#flatMap(Function)
     */
    public <R> Stream<R> flatMap(final Function<? super T, ? extends java.util.stream.Stream<? extends R>> mapper) {
        return new Stream<>(wrappedStream.flatMap(mapper));
    }

    /**
     * @see java.util.stream.Stream#flatMapToInt(Function)
     */
    public IntStream flatMapToInt(final Function<? super T, ? extends java.util.stream.IntStream> mapper) {
        return new IntStream(wrappedStream.flatMapToInt(mapper));
    }

    /**
     * @see java.util.stream.Stream#flatMapToLong(Function)
     */
    public LongStream flatMapToLong(final Function<? super T, ? extends java.util.stream.LongStream> mapper) {
        return new LongStream(wrappedStream.flatMapToLong(mapper));
    }

    /**
     * @see java.util.stream.Stream#flatMapToDouble(Function)
     */
    public DoubleStream flatMapToDouble(final Function<? super T, ? extends java.util.stream.DoubleStream> mapper) {
        return new DoubleStream(wrappedStream.flatMapToDouble(mapper));
    }

    /**
     * @see java.util.stream.Stream#distinct()
     */
    public Stream<T> distinct() {
        return new Stream<>(wrappedStream.distinct());
    }

    /**
     * @see java.util.stream.Stream#sorted()
     */
    public Stream<T> sorted() {
        return new Stream<>(wrappedStream.sorted());
    }

    /**
     * @see java.util.stream.Stream#sorted(Comparator)
     */
    public Stream<T> sorted(final Comparator<? super T> comparator) {
        return new Stream<>(wrappedStream.sorted(comparator));
    }

    /**
     * @see java.util.stream.Stream#peek(Consumer)
     */
    public Stream<T> peek(final Consumer<? super T> action) {
        return new Stream<>(wrappedStream.peek(action));
    }

    /**
     * @see java.util.stream.Stream#limit(long)
     */
    public Stream<T> limit(final long maxSize) {
        return new Stream<>(wrappedStream.limit(maxSize));
    }

    /**
     * @see java.util.stream.Stream#skip(long)
     */
    public Stream<T> skip(final long n) {
        return new Stream<>(wrappedStream.skip(n));
    }

    /**
     * In contrast to the original Stream implementation this is not a terminal operation.
     * 
     * @see java.util.stream.Stream#forEach(Consumer)
     */
    public Stream<T> forEach(final Consumer<? super T> action) {
        return map(elem -> {action.accept(elem); return elem;});
    }

    /**
     * Replacement of the standard Stream's forEach implementation.
     * 
     * @see java.util.stream.Stream#forEach(Consumer)
     */
    public void consume(final Consumer<? super T> action) {
        wrappedStream.forEach(action);
    }

    /**
     * @see java.util.stream.Stream#reduce(Object, BinaryOperator)
     */
    public T reduce(final T identity, final BinaryOperator<T> accumulator) {
        return wrappedStream.reduce(identity, accumulator);
    }

    /**
     * @see java.util.stream.Stream#reduce(BinaryOperator)
     */
    public Optional<T> reduce(final BinaryOperator<T> accumulator) {
        return wrappedStream.reduce(accumulator);
    }

    /**
     * @see java.util.stream.Stream#reduce(Object, BiFunction, BinaryOperator)
     */
    public <U> U reduce(final U identity, final BiFunction<U, ? super T, U> accumulator, final BinaryOperator<U> combiner) {
        return wrappedStream.reduce(identity, accumulator, combiner);
    }

    /**
     * @see java.util.stream.Stream#collect(Supplier, BiConsumer, BiConsumer)
     */
    public <R> R collect(final Supplier<R> supplier, final BiConsumer<R, ? super T> accumulator, final BiConsumer<R, R> combiner) {
        return wrappedStream.collect(supplier, accumulator, combiner);
    }

    /**
     * @see java.util.stream.Stream#collect(Collector)
     */
    public <R, A> R collect(final Collector<? super T, A, R> collector) {
        return wrappedStream.collect(collector);
    }
    
    /**
     * Convenience methods to collect the stream content in a list.
     */
    public List<T> toList() {
        return wrappedStream.collect(Collectors.toList());
    }
    
    /**
     * Convenience method to collect the stream content in a set.
     */
    public Set<T> toSet() {
        return wrappedStream.collect(Collectors.toSet());
    }
    
    /**
     * Convenience method to collect the stream content in a map.
     * 
     * @param keyMapper Function to create the key from a stream entry.
     * @param valueMapper Function to create the value from a stream entry.
     */
    public <K, V> Map<K, V> toMap(final Function<? super T, ? extends K> keyMapper,
                                  final Function<? super T, ? extends V> valueMapper) {
        return wrappedStream.collect(Collectors.toMap(keyMapper, valueMapper));
    }

    /**
     * Convenience method to collect the stream content in a map. The values are
     * simply the stream entries.
     * 
     * @param keyMapper Function to create the key from a stream entry.
     */
    public <K> Map<K, T> toMap(final Function<? super T, ? extends K> keyMapper) {
        return wrappedStream.collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    /**
     * Convenience method to collect the stream contents in an array.
     */
    @SuppressWarnings("unchecked")
    public T[] toArray(final Class<T> componentType) {
        return wrappedStream.toArray(length -> (T[]) Array.newInstance(componentType, length));
    }

    /**
     * @see java.util.stream.Stream#min(Comparator)
     */
    public Optional<T> min(final Comparator<? super T> comparator) {
        return wrappedStream.min(comparator);
    }

    /**
     * @see java.util.stream.Stream#max(Comparator)
     */
    public Optional<T> max(final Comparator<? super T> comparator) {
        return wrappedStream.max(comparator);
    }

    /**
     * @see java.util.stream.Stream#count()
     */
    public long count() {
        return wrappedStream.count();
    }

    /**
     * @see java.util.stream.Stream#anyMatch(Predicate)
     */
    public boolean anyMatch(final Predicate<? super T> predicate) {
        return wrappedStream.anyMatch(predicate);
    }

    /**
     * @see java.util.stream.Stream#allMatch(Predicate)
     */
    public boolean allMatch(final Predicate<? super T> predicate) {
        return wrappedStream.allMatch(predicate);
    }

    /**
     * @see java.util.stream.Stream#noneMatch(Predicate)
     */
    public boolean noneMatch(final Predicate<? super T> predicate) {
        return wrappedStream.noneMatch(predicate);
    }

    /**
     * @see java.util.stream.Stream#findFirst()
     */
    public Optional<T> findFirst() {
        return wrappedStream.findFirst();
    }

    /**
     * Like {@link java.util.stream.Stream#findFirst()}, but the stream entry
     * has to conform to the predicate.
     */
    public Optional<T> findFirst(final Predicate<T> predicate) {
        return wrappedStream.filter(predicate).findFirst();
    }

    /**
     * @see java.util.stream.Stream#findAny()
     */
    public Optional<T> findAny() {
        return wrappedStream.findAny();
    }
    
    /**
     * Like {@link java.util.stream.Stream#findAny()}, but the stream entry has
     * to confirm to the predicate.
     */
    public Optional<T> findAny(final Predicate<T> predicate) {
        return wrappedStream.filter(predicate).findAny();
    }
    
}
