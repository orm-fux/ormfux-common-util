package com.github.ormfux.common.utils.testnullableutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.function.Function;

import org.junit.Test;

import com.github.ormfux.common.utils.NullableUtils;

public class CheckAndRetrieveTest {
    
    @Test
    public void testNonNullSourceWithoutFallback() {
        assertNull(NullableUtils.checkAndRetrieve(Integer.valueOf(3), val -> val.intValue() == 3, val -> null));
        assertNull(NullableUtils.checkAndRetrieve(Integer.valueOf(3), val -> val.intValue() != 3, val -> val * 2));
        
        assertEquals(Integer.valueOf(6), NullableUtils.checkAndRetrieve(Integer.valueOf(3), val -> val.intValue() == 3, val -> val * 2));
        
    }
    
    @Test
    public void testNonNullSourceWithFallback() {
        assertNull(NullableUtils.checkAndRetrieve(Integer.valueOf(3), val -> val.intValue() == 3, val -> null, null));
        assertNull(NullableUtils.checkAndRetrieve(Integer.valueOf(3), val -> val.intValue() != 3, val -> val * 2, null));
        
        assertEquals(Integer.valueOf(42), NullableUtils.checkAndRetrieve(Integer.valueOf(3), val -> val.intValue() != 3, val -> null, Integer.valueOf(42)));
        assertEquals(Integer.valueOf(42), NullableUtils.checkAndRetrieve(Integer.valueOf(3), val -> val.intValue() != 3, val -> val * 2, Integer.valueOf(42)));
        
        assertEquals(Integer.valueOf(6), NullableUtils.checkAndRetrieve(Integer.valueOf(3), val -> val.intValue() == 3, val -> val * 2, null));
        assertEquals(Integer.valueOf(42), NullableUtils.checkAndRetrieve(Integer.valueOf(3), val -> val.intValue() == 3, val -> null, Integer.valueOf(42)));
        assertEquals(Integer.valueOf(6), NullableUtils.checkAndRetrieve(Integer.valueOf(3), val -> val.intValue() == 3, val -> val * 2, Integer.valueOf(42)));
    }
    
    @Test
    public void testNullSource() {
        assertNull(NullableUtils.checkAndRetrieve(null, val -> true, Function.identity()));
        assertNull(NullableUtils.checkAndRetrieve(null, val -> true, Function.identity()), null);
        assertEquals("fallback", NullableUtils.checkAndRetrieve(null, val -> true, Function.identity(), "fallback"));
        
        assertNull(NullableUtils.checkAndRetrieve(null, val -> false, Function.identity()));
        assertNull(NullableUtils.checkAndRetrieve(null, val -> false, Function.identity()), null);
        assertEquals("fallback", NullableUtils.checkAndRetrieve(null, val -> false, Function.identity(), "fallback"));
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullFunctionFallback() {
        NullableUtils.checkAndRetrieve(null, val -> true, null, "fallback");
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullFunctionNullFallback() {
        NullableUtils.checkAndRetrieve(null, val -> true, null, null);
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullFunctionNoFallback() {
        NullableUtils.checkAndRetrieve(null, val -> true, null);
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullPredicateFallback() {
        NullableUtils.checkAndRetrieve(null, null, val -> true, "fallback");
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullPredicateNullFallback() {
        NullableUtils.checkAndRetrieve(null, null, val -> true, null);
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullPredicateNoFallback() {
        NullableUtils.checkAndRetrieve(null, null, val -> true);
    }
    
}
