package com.github.ormfux.common.utils.testmaputils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;

import com.github.ormfux.common.utils.MapUtils;

public class FillMapTest {
    
    @Test
    public void testFillWithContent() {
        Map<String, String> map = MapUtils.fillMap(MapUtils.entry("key1", "value1"), MapUtils.entry("key2", "value2"));
        assertNotNull(map);
        assertEquals(2,  map.size());
        assertEquals("value1", map.get("key1"));
        assertEquals("value2", map.get("key2"));
        
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testFillEmpty() {
        Map<?, ?> map = MapUtils.fillMap(new MapUtils.Entry[0]);
        assertNotNull(map);
        assertEquals(0,  map.size());
        
    }
    
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unchecked")
    public void testNoEntryArg() {
        MapUtils.fillMap((MapUtils.Entry[]) null);
    }
    
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unchecked")
    public void testNullEntry() {
        MapUtils.fillMap(new MapUtils.Entry[] {null});
    }
    
    @Test
    public void testMapChangeable() {
        Map<String, String> map = MapUtils.fillMap(MapUtils.entry("key1", "value1"), MapUtils.entry("key2", "value2"));
        assertNotNull(map);
        assertEquals(2,  map.size());
        assertEquals("value1", map.get("key1"));
        assertEquals("value2", map.get("key2"));
        
        map.remove("key1");
        map.put("key3", "value3");
        
        assertEquals(2,  map.size());
        assertEquals("value3", map.get("key3"));
        assertEquals("value2", map.get("key2"));
        
    }
    
}
