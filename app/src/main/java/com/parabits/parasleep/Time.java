package com.parabits.parasleep;

public class Time {

    private int mHours;
    private int mMinutes;

    public Time(int hours, int minutes)
    {
        mHours = hours;
        mMinutes = minutes;
    }

    public int getHours() {return mHours;}
    public void setHours(int hours) {
        if(hours >=0 && hours <=24)
        {
            mHours = hours;
        }
    }
    public int getMinutes() {return mMinutes;}
    public void setMinutes(int minutes)
    {
        if(minutes >= 0 && minutes <= 60)
        {
            mMinutes = minutes;
        }
    }

    @Override
    public String toString()
    {
        String hours = String.valueOf(mHours);
        if(mHours < 10 )
        {
            hours = "0" + hours;
        }
        String minutes = String.valueOf(mMinutes);
        if(mMinutes < 10)
        {
            minutes = "0" + minutes;
        }

        return hours + ":" + minutes;
    }
}
