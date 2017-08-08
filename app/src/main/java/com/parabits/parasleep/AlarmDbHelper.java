package com.parabits.parasleep;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AlarmDbHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AlarmsClock.db";

    //TODO będzie można zrobić to jakoś ciekawiej
    private final String CREATE_STATEMENT = "CREATE TABLE alarms ( id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "hour INTEGER NOT NULL, " +
            "minutes INTEGER NOT NULL, "+
            "enabled INTEGER NOT NULL DEFAULT 1)";

    public AlarmDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public AlarmDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //TODO dorobić aktualizację bazy danych
    }

    public SQLiteDatabase getDatabase()
    {
        return getWritableDatabase();
    }
}
