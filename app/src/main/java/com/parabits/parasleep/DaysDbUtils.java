package com.parabits.parasleep;

/**
 * Created by Razjelll on 16.08.2017.
 */

public class DaysDbUtils {
    private static final int MONDAY = 1;
    private static final int TUESDAY = 2;
    private static final int WEDNESDAY = 4;
    private static final int THURSDAY= 8;
    private static final int FRIDAY = 16;
    private static final int SATURDAY = 32;
    private static final int SUNDAY = 64;

    private static final int[] flagsValueArray = {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};

    public static int  getValue(final boolean[] days)
    {
        int result = 0;
        for(int i=0; i<days.length; i++)
        {
            if(days[i])
            {
                result += flagsValueArray[i];
            }
        }
        return result;
    }

    public static boolean[] getDays(int value)
    {
        int tempValue = value;
        boolean[] resultArray = new boolean[flagsValueArray.length];
        for(int i = flagsValueArray.length - 1; i >= 0; i--)
        {
            if(tempValue >= flagsValueArray[i])
            {
                resultArray[i] = true;
                tempValue -= flagsValueArray[i];
            }
        }
        return resultArray;
    }
}
