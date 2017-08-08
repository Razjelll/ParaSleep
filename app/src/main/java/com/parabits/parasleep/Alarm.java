package com.parabits.parasleep;

/**
 * Created by Razjelll on 08.08.2017.
 */

public class Alarm {

    private long mId;
    private Time mTime;
    private boolean mEnabled;

    public Alarm(int hours, int minutes)
    {
        mTime = new Time(hours, minutes);
        mEnabled = true;
    }

    public Alarm(int hours, int minutes, boolean enabled)
    {
        mTime = new Time(hours, minutes);
        mEnabled = enabled;
    }

    public long getId() { return mId;}
    public void setId(long id) {mId = id;}
    public Time getTime() { return mTime;}
    public String getTimeString() {return mTime.toString();}
    public void setTime(Time time) { mTime = time;}
    public void setTime(int hours, int minutes) { mTime = new Time(hours, minutes);}

    public boolean isEnabled() { return mEnabled;}
    public void setEnabled(boolean enable) {
        mEnabled = enable;
    }
}
