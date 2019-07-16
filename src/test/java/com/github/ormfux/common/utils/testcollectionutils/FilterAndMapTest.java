package com.github.ormfux.common.utils.testcollectionutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.Test;

import com.github.ormfux.common.utils.CollectionUtils;

public class FilterAndMapTest {
    
    @Test
    public void testFilterWithMatch() {
        List<String> result = new ArrayList<>();
        CollectionUtils.filterAndMap(Arrays.asList(1, 2, 3), result, value -> value == 1 || value == 2, value -> value.toString());
        
        assertEquals(2, result.size());
        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
        
        CollectionUtils.filterAndMap(Arrays.asList(1, 2, 3), result, value -> value == 1 || value == 2, value -> value.toString());
        
        assertEquals(4, result.size());
        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
        result.remove("1");
        result.remove("2");
        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
    }
    
    @Test
    public void testWithoutMatch() {
        List<String> result = new ArrayList<>();
        CollectionUtils.filterAndMap(Arrays.asList(1, 2, 3), result, value -> value == 4, value -> value.toString());
        
        assertEquals(0, result.size());
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullFilteredCollection() {
        CollectionUtils.filterAndMap(null, new ArrayList<>(), value -> Objects.equals("4", value), value -> value.toString());
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullResultCollection() {
        CollectionUtils.filterAndMap(new ArrayList<>(), null, value -> Objects.equals("4", value), value -> value.toString());
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullPredicate() {
        CollectionUtils.filterAndMap(new ArrayList<>(), new ArrayList<>(), null, value -> value.toString());
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullMappingFunction() {
        CollectionUtils.filterAndMap(new ArrayList<>(), new ArrayList<>(), value -> Objects.equals("4", value), null);
    }
}
