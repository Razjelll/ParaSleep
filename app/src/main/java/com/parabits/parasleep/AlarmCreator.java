package com.parabits.parasleep;

import android.database.Cursor;

/**
 * Created by Razjelll on 08.08.2017.
 */

public class AlarmCreator {

    public static Alarm createFromCursor(Cursor cursor)
    {
        long id = cursor.getLong(AlarmTable.ID_POSITION);
        int hours = cursor.getInt(AlarmTable.HOUR_POSITION);
        int minutes = cursor.getInt(AlarmTable.MINUTES_POSITION);
        boolean enabled = SQLiteUtils.getBoolean(cursor.getInt(AlarmTable.ENABLED_POSITION));

        Alarm alarm = new Alarm(hours, minutes, enabled);
        alarm.setId(id);
        return alarm;
    }
}
