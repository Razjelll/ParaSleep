package com.parabits.parasleep;

/**
 * Created by Razjelll on 08.08.2017.
 */

public class SQLiteUtils {

    public static int getInt(boolean value)
    {
        return value ? 1 : 0;
    }

    public static boolean getBoolean(int value)
    {
        return value == 1;
    }
}
