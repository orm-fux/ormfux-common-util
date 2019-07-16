package com.github.ormfux.common.utils.testlistutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.Test;

import com.github.ormfux.common.utils.ListUtils;

public class FilterAndMapTest {
    
    @Test
    public void testFilterWithMatch() {
        List<String> result = ListUtils.filterAndMap(Arrays.asList(1, 2, 3), value -> value == 1 || value == 2, value -> value.toString());
        
        assertEquals(2, result.size());
        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
    }
    
    @Test
    public void testWithoutMatch() {
        List<String> result = ListUtils.filterAndMap(Arrays.asList(1, 2, 3), value -> value == 4, value -> value.toString());
        
        assertEquals(0, result.size());
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullFilteredList() {
        ListUtils.filterAndMap(null, value -> Objects.equals("4", value), value -> value.toString());
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullPredicate() {
        ListUtils.filterAndMap(new ArrayList<>(), null, value -> value.toString());
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullMappingFunction() {
        ListUtils.filterAndMap(new ArrayList<>(), value -> Objects.equals("4", value), null);
    }
    
}
