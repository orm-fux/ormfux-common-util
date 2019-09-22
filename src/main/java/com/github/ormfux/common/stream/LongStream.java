package com.github.ormfux.common.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.Set;
import java.util.Spliterator.OfLong;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

/**
 * Extension of the default {@link java.util.stream.LongStream}. Adds a few methods that
 * make daily use a little more convenient.
 */
public class LongStream {
    
    /**
     * The original stream.
     */
    private java.util.stream.LongStream wrappedStream;
    
    /**
     * @param wrappedStream
     */
    LongStream(final java.util.stream.LongStream wrappedStream) {
        this.wrappedStream = wrappedStream;
    }
    
    /**
     * @see java.util.stream.LongStream#iterator()
     */
    public java.util.PrimitiveIterator.OfLong iterator() {
        return wrappedStream.iterator();
    }

    /**
     * @see java.util.stream.LongStream#spliterator()
     */
    public OfLong spliterator() {
        return wrappedStream.spliterator();
    }

    /**
     * @see java.util.stream.LongStream#isParallel()
     */
    public boolean isParallel() {
        return wrappedStream.isParallel();
    }

    /**
     * @see java.util.stream.LongStream#sequential()
     */
    public LongStream sequential() {
        return new LongStream(wrappedStream.sequential());
    }

    /**
     * @see java.util.stream.LongStream#parallel()
     */
    public LongStream parallel() {
        return new LongStream(wrappedStream.parallel());
    }

    /**
     * @see java.util.stream.LongStream#unordered()
     */
    public LongStream unordered() {
        return new LongStream(wrappedStream.unordered());
    }

    /**
     * @see java.util.stream.LongStream#onClose(Runnable)
     */
    public LongStream onClose(final Runnable closeHandler) {
        return new LongStream(wrappedStream.onClose(closeHandler));
    }

    /**
     * @see java.util.stream.LongStream#close()
     */
    public void close() {
        wrappedStream.close();
    }
    
    /**
     * @see java.util.stream.LongStream#filter(Predicate)
     */
    public LongStream filter(final LongPredicate predicate) {
        return new LongStream(wrappedStream.filter(predicate));
    }

    /**
     * Same as {@link java.util.stream.LongStream#filter(Predicate)}, but also
     * collects the result directly in a list.
     */
    public List<Long> filterToList(final LongPredicate predicate) {
        return wrappedStream.filter(predicate).collect(ArrayList::new, List::add, List::addAll);
    }

    /**
     * Same as {@link java.util.stream.LongStream#filter(Predicate)}, but also
     * collects the result directly in a set.
     */
    public Set<Long> filterToSet(final LongPredicate predicate) {
        return wrappedStream.filter(predicate).collect(HashSet::new, Set::add, Set::addAll);
    }

    /**
     * @see java.util.stream.LongStream#map(Function)
     */
    public LongStream map(final LongUnaryOperator mapper) {
        return new LongStream(wrappedStream.map(mapper));
    }

    /**
     * @see java.util.stream.LongStream#mapToDouble(ToDoubleFunction)
     */
    public DoubleStream mapToDouble(final LongToDoubleFunction mapper) {
        return new DoubleStream(wrappedStream.mapToDouble(mapper));
    }

    /**
     * @see java.util.stream.LongStream#flatMap(Function)
     */
    public LongStream flatMap(final LongFunction<? extends java.util.stream.LongStream> mapper) {
        return new LongStream(wrappedStream.flatMap(mapper));
    }

    /**
     * @see java.util.stream.LongStream#distinct()
     */
    public LongStream distinct() {
        return new LongStream(wrappedStream.distinct());
    }

    /**
     * @see java.util.stream.LongStream#sorted()
     */
    public LongStream sorted() {
        return new LongStream(wrappedStream.sorted());
    }

    /**
     * @see java.util.stream.LongStream#peek(Consumer)
     */
    public LongStream peek(final LongConsumer action) {
        return new LongStream(wrappedStream.peek(action));
    }

    /**
     * @see java.util.stream.LongStream#limit(long)
     */
    public LongStream limit(final long maxSize) {
        return new LongStream(wrappedStream.limit(maxSize));
    }

    /**
     * @see java.util.stream.LongStream#skip(long)
     */
    public LongStream skip(final long n) {
        return new LongStream(wrappedStream.skip(n));
    }

    /**
     * @see java.util.stream.LongStream#forEach(Consumer)
     */
    public void forEach(final LongConsumer action) {
        wrappedStream.forEach(action);
    }

    /**
     * @see java.util.stream.LongStream#forEachOrdered(Consumer)
     */
    public void forEachOrdered(final LongConsumer action) {
        wrappedStream.forEachOrdered(action);
    }

    /**
     * @see java.util.stream.LongStream#reduce(Object, BinaryOperator)
     */
    public Long reduce(final Long identity, final LongBinaryOperator accumulator) {
        return wrappedStream.reduce(identity, accumulator);
    }

    /**
     * @see java.util.stream.LongStream#reduce(BinaryOperator)
     */
    public OptionalLong reduce(final LongBinaryOperator accumulator) {
        return wrappedStream.reduce(accumulator);
    }

    /**
     * @see java.util.stream.LongStream#collect(Supplier, BiConsumer, BiConsumer)
     */
    public <R> R collect(final Supplier<R> supplier, final ObjLongConsumer<R> accumulator, final BiConsumer<R, R> combiner) {
        return wrappedStream.collect(supplier, accumulator, combiner);
    }

    /**
     * Convenience methods to collect the stream content in a list.
     */
    public List<Long> toList() {
        return wrappedStream.collect(ArrayList::new, List::add, List::addAll);
    }
    
    /**
     * Convenience method to collect the stream content in a set.
     */
    public Set<Long> toSet() {
        return wrappedStream.collect(HashSet::new, Set::add, Set::addAll);
    }
    
    /**
     * @see java.util.stream.LongStream#toArray()
     */
    public long[] toArray() {
        return wrappedStream.toArray();
    }

    /**
     * @see java.util.stream.LongStream#min(Comparator)
     */
    public OptionalLong min() {
        return wrappedStream.min();
    }

    /**
     * @see java.util.stream.LongStream#max(Comparator)
     */
    public OptionalLong max() {
        return wrappedStream.max();
    }

    /**
     * @see java.util.stream.LongStream#count()
     */
    public long count() {
        return wrappedStream.count();
    }

    /**
     * @see java.util.stream.LongStream#anyMatch(Predicate)
     */
    public boolean anyMatch(final LongPredicate predicate) {
        return wrappedStream.anyMatch(predicate);
    }

    /**
     * @see java.util.stream.LongStream#allMatch(Predicate)
     */
    public boolean allMatch(final LongPredicate predicate) {
        return wrappedStream.allMatch(predicate);
    }

    /**
     * @see java.util.stream.LongStream#noneMatch(Predicate)
     */
    public boolean noneMatch(final LongPredicate predicate) {
        return wrappedStream.noneMatch(predicate);
    }

    /**
     * @see java.util.stream.LongStream#findFirst()
     */
    public OptionalLong findFirst() {
        return wrappedStream.findFirst();
    }

    /**
     * Like {@link java.util.stream.LongStream#findFirst()}, but the stream entry
     * has to conform to the predicate.
     */
    public OptionalLong findFirst(final LongPredicate predicate) {
        return wrappedStream.filter(predicate).findFirst();
    }

    /**
     * @see java.util.stream.LongStream#findAny()
     */
    public OptionalLong findAny() {
        return wrappedStream.findAny();
    }
    
    /**
     * Like {@link java.util.stream.LongStream#findAny()}, but the stream entry has
     * to confirm to the predicate.
     */
    public OptionalLong findAny(final LongPredicate predicate) {
        return wrappedStream.filter(predicate).findAny();
    }

    /**
     * @see java.util.stream.LongStream#mapToObj(LongFunction)
     */
    public <U> Stream<U> mapToObj(final LongFunction<? extends U> mapper) {
        return new Stream<U>(wrappedStream.mapToObj(mapper));
    }

    /**
     * @see java.util.stream.LongStream#sum()
     */
    public Long sum() {
        return wrappedStream.sum();
    }

    /**
     * @see java.util.stream.LongStream#average()
     */
    public OptionalDouble average() {
        return wrappedStream.average();
    }

    /**
     * @see java.util.stream.LongStream#summaryStatistics()
     */
    public LongSummaryStatistics summaryStatistics() {
        return wrappedStream.summaryStatistics();
    }

    /**
     * @see java.util.stream.LongStream#asDoubleStream()
     */
    public DoubleStream asDoubleStream() {
        return new DoubleStream(wrappedStream.asDoubleStream());
    }

    /**
     * @see java.util.stream.LongStream#boxed()
     */
    public Stream<Long> boxed() {
        return new Stream<>(wrappedStream.mapToObj(val -> val));
    }
    
}
