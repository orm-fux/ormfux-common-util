package com.github.ormfux.common.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Spliterator.OfInt;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;

/**
 * Extension of the default {@link java.util.stream.IntStream}. Adds a few methods that
 * make daily use a little more convenient.
 */
public class IntStream {
    
    /**
     * The original stream.
     */
    private java.util.stream.IntStream wrappedStream;
    
    /**
     * @param wrappedStream
     */
    IntStream(final java.util.stream.IntStream wrappedStream) {
        this.wrappedStream = wrappedStream;
    }
    
    /**
     * @see java.util.stream.IntStream#iterator()
     */
    public java.util.PrimitiveIterator.OfInt iterator() {
        return wrappedStream.iterator();
    }

    /**
     * @see java.util.stream.IntStream#spliterator()
     */
    public OfInt spliterator() {
        return wrappedStream.spliterator();
    }

    /**
     * @see java.util.stream.IntStream#isParallel()
     */
    public boolean isParallel() {
        return wrappedStream.isParallel();
    }

    /**
     * @see java.util.stream.IntStream#sequential()
     */
    public IntStream sequential() {
        return new IntStream(wrappedStream.sequential());
    }

    /**
     * @see java.util.stream.IntStream#parallel()
     */
    public IntStream parallel() {
        return new IntStream(wrappedStream.parallel());
    }

    /**
     * @see java.util.stream.IntStream#unordered()
     */
    public IntStream unordered() {
        return new IntStream(wrappedStream.unordered());
    }

    /**
     * @see java.util.stream.IntStream#onClose(Runnable)
     */
    public IntStream onClose(final Runnable closeHandler) {
        return new IntStream(wrappedStream.onClose(closeHandler));
    }

    /**
     * @see java.util.stream.IntStream#close()
     */
    public void close() {
        wrappedStream.close();
    }
    
    /**
     * @see java.util.stream.IntStream#filter(Predicate)
     */
    public IntStream filter(final IntPredicate predicate) {
        return new IntStream(wrappedStream.filter(predicate));
    }

    /**
     * Same as {@link java.util.stream.IntStream#filter(Predicate)}, but also
     * collects the result directly in a list.
     */
    public List<Integer> filterToList(final IntPredicate predicate) {
        return wrappedStream.filter(predicate).collect(ArrayList::new, List::add, List::addAll);
    }

    /**
     * Same as {@link java.util.stream.IntStream#filter(Predicate)}, but also
     * collects the result directly in a set.
     */
    public Set<Integer> filterToSet(final IntPredicate predicate) {
        return wrappedStream.filter(predicate).collect(HashSet::new, Set::add, Set::addAll);
    }

    /**
     * @see java.util.stream.IntStream#map(Function)
     */
    public IntStream map(final IntUnaryOperator mapper) {
        return new IntStream(wrappedStream.map(mapper));
    }

    /**
     * @see java.util.stream.IntStream#mapToLong(ToLongFunction)
     */
    public LongStream mapToLong(final IntToLongFunction mapper) {
        return new LongStream(wrappedStream.mapToLong(mapper));
    }

    /**
     * @see java.util.stream.IntStream#mapToDouble(ToDoubleFunction)
     */
    public DoubleStream mapToDouble(final IntToDoubleFunction mapper) {
        return new DoubleStream(wrappedStream.mapToDouble(mapper));
    }

    /**
     * @see java.util.stream.IntStream#flatMap(Function)
     */
    public IntStream flatMap(final IntFunction<? extends java.util.stream.IntStream> mapper) {
        return new IntStream(wrappedStream.flatMap(mapper));
    }

    /**
     * @see java.util.stream.IntStream#distinct()
     */
    public IntStream distinct() {
        return new IntStream(wrappedStream.distinct());
    }

    /**
     * @see java.util.stream.IntStream#sorted()
     */
    public IntStream sorted() {
        return new IntStream(wrappedStream.sorted());
    }

    /**
     * @see java.util.stream.IntStream#peek(Consumer)
     */
    public IntStream peek(final IntConsumer action) {
        return new IntStream(wrappedStream.peek(action));
    }

    /**
     * @see java.util.stream.IntStream#limit(long)
     */
    public IntStream limit(final long maxSize) {
        return new IntStream(wrappedStream.limit(maxSize));
    }

    /**
     * @see java.util.stream.IntStream#skip(long)
     */
    public IntStream skip(final long n) {
        return new IntStream(wrappedStream.skip(n));
    }

    /**
     * @see java.util.stream.IntStream#forEach(Consumer)
     */
    public void forEach(final IntConsumer action) {
        wrappedStream.forEach(action);
    }

    /**
     * @see java.util.stream.IntStream#forEachOrdered(Consumer)
     */
    public void forEachOrdered(final IntConsumer action) {
        wrappedStream.forEachOrdered(action);
    }

    /**
     * @see java.util.stream.IntStream#reduce(Object, BinaryOperator)
     */
    public int reduce(final int identity, final IntBinaryOperator accumulator) {
        return wrappedStream.reduce(identity, accumulator);
    }

    /**
     * @see java.util.stream.IntStream#reduce(BinaryOperator)
     */
    public OptionalInt reduce(final IntBinaryOperator accumulator) {
        return wrappedStream.reduce(accumulator);
    }

    /**
     * @see java.util.stream.IntStream#collect(Supplier, BiConsumer, BiConsumer)
     */
    public <R> R collect(final Supplier<R> supplier, final ObjIntConsumer<R> accumulator, final BiConsumer<R, R> combiner) {
        return wrappedStream.collect(supplier, accumulator, combiner);
    }

    /**
     * Convenience methods to collect the stream content in a list.
     */
    public List<Integer> toList() {
        return wrappedStream.collect(ArrayList::new, List::add, List::addAll);
    }
    
    /**
     * Convenience method to collect the stream content in a set.
     */
    public Set<Integer> toSet() {
        return wrappedStream.collect(HashSet::new, Set::add, Set::addAll);
    }
    
    /**
     * @see java.util.stream.IntStream#toArray()
     */
    public int[] toArray() {
        return wrappedStream.toArray();
    }

    /**
     * @see java.util.stream.IntStream#min(Comparator)
     */
    public OptionalInt min() {
        return wrappedStream.min();
    }

    /**
     * @see java.util.stream.IntStream#max(Comparator)
     */
    public OptionalInt max() {
        return wrappedStream.max();
    }

    /**
     * @see java.util.stream.IntStream#count()
     */
    public long count() {
        return wrappedStream.count();
    }

    /**
     * @see java.util.stream.IntStream#anyMatch(Predicate)
     */
    public boolean anyMatch(final IntPredicate predicate) {
        return wrappedStream.anyMatch(predicate);
    }

    /**
     * @see java.util.stream.IntStream#allMatch(Predicate)
     */
    public boolean allMatch(final IntPredicate predicate) {
        return wrappedStream.allMatch(predicate);
    }

    /**
     * @see java.util.stream.IntStream#noneMatch(Predicate)
     */
    public boolean noneMatch(final IntPredicate predicate) {
        return wrappedStream.noneMatch(predicate);
    }

    /**
     * @see java.util.stream.IntStream#findFirst()
     */
    public OptionalInt findFirst() {
        return wrappedStream.findFirst();
    }

    /**
     * Like {@link java.util.stream.IntStream#findFirst()}, but the stream entry
     * has to conform to the predicate.
     */
    public OptionalInt findFirst(final IntPredicate predicate) {
        return wrappedStream.filter(predicate).findFirst();
    }

    /**
     * @see java.util.stream.IntStream#findAny()
     */
    public OptionalInt findAny() {
        return wrappedStream.findAny();
    }
    
    /**
     * Like {@link java.util.stream.IntStream#findAny()}, but the stream entry has
     * to confirm to the predicate.
     */
    public OptionalInt findAny(final IntPredicate predicate) {
        return wrappedStream.filter(predicate).findAny();
    }

    /**
     * @see java.util.stream.IntStream#mapToObj(IntFunction)
     */
    public <U> Stream<U> mapToObj(final IntFunction<? extends U> mapper) {
        return new Stream<U>(wrappedStream.mapToObj(mapper));
    }

    /**
     * @see java.util.stream.IntStream#sum()
     */
    public int sum() {
        return wrappedStream.sum();
    }

    /**
     * @see java.util.stream.IntStream#average()
     */
    public OptionalDouble average() {
        return wrappedStream.average();
    }

    /**
     * @see java.util.stream.IntStream#summaryStatistics()
     */
    public IntSummaryStatistics summaryStatistics() {
        return wrappedStream.summaryStatistics();
    }

    /**
     * @see java.util.stream.IntStream#asLongStream()
     */
    public LongStream asLongStream() {
        return new LongStream(wrappedStream.asLongStream());
    }

    /**
     * @see java.util.stream.IntStream#asDoubleStream()
     */
    public DoubleStream asDoubleStream() {
        return new DoubleStream(wrappedStream.asDoubleStream());
    }

    /**
     * @see java.util.stream.IntStream#boxed()
     */
    public Stream<Integer> boxed() {
        return new Stream<>(wrappedStream.mapToObj(val -> val));
    }
    
}
