package com.github.ormfux.common.utils.testnullableutils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

import org.junit.Test;

import com.github.ormfux.common.utils.NullableUtils;

public class IsNotEmptyTest {
    
    @Test
    public void testPredicateFunctionality() {
        Predicate<Collection<? extends Object>> isEmptyPredicate = NullableUtils.isNotEmpty();
        assertNotNull(isEmptyPredicate);
        
        assertFalse(isEmptyPredicate.test(new ArrayList<>()));
        assertTrue(isEmptyPredicate.test(Arrays.asList("1")));
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullCollectionParam() {
        Predicate<Collection<? extends Object>> isEmptyPredicate = NullableUtils.isNotEmpty();
        assertNotNull(isEmptyPredicate);
        
        isEmptyPredicate.test(null);
    }
    
}
