package com.parabits.parasleep;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by Razjelll on 16.08.2017.
 */

public class DaysDbUtilsTest {
    @Test
    public void getValueTest() throws Exception {
        boolean[] days = {true, false, false, false, false, false, false}; //wartość 1
        int result = DaysDbUtils.getValue(days);
        assertEquals(1, result);
        days =new boolean[] {false, true, false, true, false, true, false}; //42
        result = DaysDbUtils.getValue(days);
        assertEquals(42, result);
    }

    @Test
    public void getDaysTest() throws Exception
    {
        int value = 1;
        boolean[] correctArray = {true, false, false, false, false, false, false};
        boolean[] resultArray = DaysDbUtils.getDays(value);
        assertArrayEquals(correctArray, resultArray);
        value = 42;
        correctArray = new boolean[]{false, true, false, true, false, true, false};
        resultArray = DaysDbUtils.getDays(value);
        assertArrayEquals(correctArray, resultArray);
    }
}
