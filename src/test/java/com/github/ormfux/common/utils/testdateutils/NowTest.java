package com.github.ormfux.common.utils.testdateutils;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.github.ormfux.common.utils.DateUtils;

public class NowTest {
    
    @Test
    public void testNow() {
        final Date now = DateUtils.now();
        
        //can only check with some leniency
        final Date comparisonDate = new Date();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(comparisonDate);
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 2);
        
        //less than 2 second difference
        assertTrue(calendar.getTimeInMillis() - now.getTime() < 2000);
    }
    
}
