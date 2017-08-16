package com.parabits.parasleep;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class AlarmDao {

    SQLiteDatabase mDb;

    public AlarmDao(SQLiteDatabase database)
    {
        mDb = database;
    }

    public void add(Alarm alarm)
    {
        ContentValues values = new ContentValues();
        values.put(AlarmTable.HOUR_COLUMN, alarm.getTime().getHour());
        values.put(AlarmTable.MINUTES_COLUMN, alarm.getTime().getMinute());
        values.put(AlarmTable.ENABLED_COLUMN, SQLiteUtils.getInt(alarm.isEnabled()));
        int daysValue = DaysDbUtils.getValue(alarm.getEnabledDays());
        values.put(AlarmTable.DAYS_COLUMN, daysValue);
        mDb.insert(AlarmTable.TABLE_NAME, null, values);
    }

    public boolean delete(long id)
    {
        if(id > 0)
        {
            String where = AlarmTable.ID_COLUMN + "=?";
            String[] whereArguments = {String.valueOf(id)};
            int numberDeleting =  mDb.delete(AlarmTable.TABLE_NAME, where, whereArguments);
            return numberDeleting > 0;
        }
        return false;
    }

    public Alarm get(long id)
    {
        if(id > 0)
        {
            String[] columns = {"*"};
            String selection = AlarmTable.ID_COLUMN + "=?";
            String[] selectionArguments = {String.valueOf(id)};
            Cursor cursor = mDb.query(AlarmTable.TABLE_NAME, columns, selection, selectionArguments, null, null, null);
            if(cursor.moveToFirst())
            {
                return AlarmCreator.createFromCursor(cursor);
            }
        }
        return null;
    }

    public List<Alarm> getAll()
    {
        List<Alarm> alarmsList = new ArrayList<>();
        String[] columns = {"*"};
        Cursor cursor = mDb.query(AlarmTable.TABLE_NAME, columns, null, null, null, null, null);
        if(cursor.moveToFirst())
        {
            Alarm alarm = null;
            do{
                alarm = AlarmCreator.createFromCursor(cursor);
                if(alarm != null)
                {
                    alarmsList.add(alarm);
                }
            }while(cursor.moveToNext());
        }
        return alarmsList;
    }
}
