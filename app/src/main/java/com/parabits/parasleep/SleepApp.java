package com.parabits.parasleep;

import android.app.Application;
import android.content.res.Configuration;

public class SleepApp extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();
        //TODO inicjalizacja aplikacji
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
    }
}
