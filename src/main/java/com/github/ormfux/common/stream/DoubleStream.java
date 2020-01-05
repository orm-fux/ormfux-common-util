package com.github.ormfux.common.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.Spliterator.OfDouble;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Extension of the default {@link java.util.stream.DoubleStream}. Adds a few methods that
 * make daily use a little more convenient.
 */
public class DoubleStream {
    
    /**
     * The original stream.
     */
    private java.util.stream.DoubleStream wrappedStream;
    
    /**
     * @param wrappedStream
     */
    DoubleStream(final java.util.stream.DoubleStream wrappedStream) {
        this.wrappedStream = wrappedStream;
    }
    
    /**
     * @see java.util.stream.DoubleStream#iterator()
     */
    public java.util.PrimitiveIterator.OfDouble iterator() {
        return wrappedStream.iterator();
    }

    /**
     * @see java.util.stream.DoubleStream#spliterator()
     */
    public OfDouble spliterator() {
        return wrappedStream.spliterator();
    }

    /**
     * @see java.util.stream.DoubleStream#isParallel()
     */
    public boolean isParallel() {
        return wrappedStream.isParallel();
    }

    /**
     * @see java.util.stream.DoubleStream#sequential()
     */
    public DoubleStream sequential() {
        return new DoubleStream(wrappedStream.sequential());
    }

    /**
     * @see java.util.stream.DoubleStream#parallel()
     */
    public DoubleStream parallel() {
        return new DoubleStream(wrappedStream.parallel());
    }

    /**
     * @see java.util.stream.DoubleStream#unordered()
     */
    public DoubleStream unordered() {
        return new DoubleStream(wrappedStream.unordered());
    }

    /**
     * @see java.util.stream.DoubleStream#onClose(Runnable)
     */
    public DoubleStream onClose(final Runnable closeHandler) {
        return new DoubleStream(wrappedStream.onClose(closeHandler));
    }

    /**
     * @see java.util.stream.DoubleStream#close()
     */
    public void close() {
        wrappedStream.close();
    }
    
    /**
     * @see java.util.stream.DoubleStream#filter(Predicate)
     */
    public DoubleStream filter(final DoublePredicate predicate) {
        return new DoubleStream(wrappedStream.filter(predicate));
    }

    /**
     * Same as {@link java.util.stream.DoubleStream#filter(Predicate)}, but also
     * collects the result directly in a list.
     */
    public List<Double> filterToList(final DoublePredicate predicate) {
        return wrappedStream.filter(predicate).collect(ArrayList::new, List::add, List::addAll);
    }

    /**
     * Same as {@link java.util.stream.DoubleStream#filter(Predicate)}, but also
     * collects the result directly in a set.
     */
    public Set<Double> filterToSet(final DoublePredicate predicate) {
        return wrappedStream.filter(predicate).collect(HashSet::new, Set::add, Set::addAll);
    }

    /**
     * @see java.util.stream.DoubleStream#map(Function)
     */
    public DoubleStream map(final DoubleUnaryOperator mapper) {
        return new DoubleStream(wrappedStream.map(mapper));
    }

    /**
     * @see java.util.stream.DoubleStream#flatMap(Function)
     */
    public DoubleStream flatMap(final DoubleFunction<? extends java.util.stream.DoubleStream> mapper) {
        return new DoubleStream(wrappedStream.flatMap(mapper));
    }

    /**
     * @see java.util.stream.DoubleStream#distinct()
     */
    public DoubleStream distinct() {
        return new DoubleStream(wrappedStream.distinct());
    }

    /**
     * @see java.util.stream.DoubleStream#sorted()
     */
    public DoubleStream sorted() {
        return new DoubleStream(wrappedStream.sorted());
    }

    /**
     * @see java.util.stream.DoubleStream#peek(Consumer)
     */
    public DoubleStream peek(final DoubleConsumer action) {
        return new DoubleStream(wrappedStream.peek(action));
    }

    /**
     * @see java.util.stream.DoubleStream#limit(long)
     */
    public DoubleStream limit(final long maxSize) {
        return new DoubleStream(wrappedStream.limit(maxSize));
    }

    /**
     * @see java.util.stream.DoubleStream#skip(int)
     */
    public DoubleStream skip(final int n) {
        return new DoubleStream(wrappedStream.skip(n));
    }

    /**
     * @see java.util.stream.DoubleStream#forEach(Consumer)
     */
    public DoubleStream forEach(final DoubleConsumer action) {
        return map(element -> { action.accept(element); return element; });
    }

    /**
     * @see java.util.stream.DoubleStream#forEach(Consumer)
     */
    public void consume(final DoubleConsumer action) {
        wrappedStream.forEach(action);
    }

    /**
     * @see java.util.stream.DoubleStream#reduce(Object, BinaryOperator)
     */
    public Double reduce(final Double identity, final DoubleBinaryOperator accumulator) {
        return wrappedStream.reduce(identity, accumulator);
    }

    /**
     * @see java.util.stream.DoubleStream#reduce(BinaryOperator)
     */
    public OptionalDouble reduce(final DoubleBinaryOperator accumulator) {
        return wrappedStream.reduce(accumulator);
    }

    /**
     * @see java.util.stream.DoubleStream#collect(Supplier, BiConsumer, BiConsumer)
     */
    public <R> R collect(final Supplier<R> supplier, final ObjDoubleConsumer<R> accumulator, final BiConsumer<R, R> combiner) {
        return wrappedStream.collect(supplier, accumulator, combiner);
    }

    /**
     * Convenience methods to collect the stream content in a list.
     */
    public List<Double> toList() {
        return wrappedStream.collect(ArrayList::new, List::add, List::addAll);
    }
    
    /**
     * Convenience method to collect the stream content in a set.
     */
    public Set<Double> toSet() {
        return wrappedStream.collect(HashSet::new, Set::add, Set::addAll);
    }
    
    /**
     * @see java.util.stream.DoubleStream#toArray()
     */
    public double[] toArray() {
        return wrappedStream.toArray();
    }

    /**
     * @see java.util.stream.DoubleStream#min(Comparator)
     */
    public OptionalDouble min() {
        return wrappedStream.min();
    }

    /**
     * @see java.util.stream.DoubleStream#max(Comparator)
     */
    public OptionalDouble max() {
        return wrappedStream.max();
    }

    /**
     * @see java.util.stream.DoubleStream#count()
     */
    public long count() {
        return wrappedStream.count();
    }

    /**
     * @see java.util.stream.DoubleStream#anyMatch(Predicate)
     */
    public boolean anyMatch(final DoublePredicate predicate) {
        return wrappedStream.anyMatch(predicate);
    }

    /**
     * @see java.util.stream.DoubleStream#allMatch(Predicate)
     */
    public boolean allMatch(final DoublePredicate predicate) {
        return wrappedStream.allMatch(predicate);
    }

    /**
     * @see java.util.stream.DoubleStream#noneMatch(Predicate)
     */
    public boolean noneMatch(final DoublePredicate predicate) {
        return wrappedStream.noneMatch(predicate);
    }

    /**
     * @see java.util.stream.DoubleStream#findFirst()
     */
    public OptionalDouble findFirst() {
        return wrappedStream.findFirst();
    }

    /**
     * Like {@link java.util.stream.DoubleStream#findFirst()}, but the stream entry
     * has to conform to the predicate.
     */
    public OptionalDouble findFirst(final DoublePredicate predicate) {
        return wrappedStream.filter(predicate).findFirst();
    }

    /**
     * @see java.util.stream.DoubleStream#findAny()
     */
    public OptionalDouble findAny() {
        return wrappedStream.findAny();
    }
    
    /**
     * Like {@link java.util.stream.DoubleStream#findAny()}, but the stream entry has
     * to confirm to the predicate.
     */
    public OptionalDouble findAny(final DoublePredicate predicate) {
        return wrappedStream.filter(predicate).findAny();
    }

    /**
     * @see java.util.stream.DoubleStream#mapToObj(DoubleFunction)
     */
    public <U> Stream<U> mapToObj(final DoubleFunction<? extends U> mapper) {
        return new Stream<U>(wrappedStream.mapToObj(mapper));
    }

    /**
     * @see java.util.stream.DoubleStream#sum()
     */
    public Double sum() {
        return wrappedStream.sum();
    }

    /**
     * @see java.util.stream.DoubleStream#average()
     */
    public OptionalDouble average() {
        return wrappedStream.average();
    }

    /**
     * @see java.util.stream.DoubleStream#summaryStatistics()
     */
    public DoubleSummaryStatistics summaryStatistics() {
        return wrappedStream.summaryStatistics();
    }

    /**
     * @see java.util.stream.DoubleStream#boxed()
     */
    public Stream<Double> boxed() {
        return new Stream<>(wrappedStream.mapToObj(val -> val));
    }
    
}
