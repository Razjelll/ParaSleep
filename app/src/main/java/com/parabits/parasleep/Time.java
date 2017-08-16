package com.parabits.parasleep;

import android.os.Parcel;
import android.os.Parcelable;

public class Time implements Parcelable{

    private int mHour;
    private int mMinute;

    public Time(int hours, int minutes)
    {
        mHour = hours;
        mMinute = minutes;
    }

    protected Time(Parcel in) {
        mHour = in.readInt();
        mMinute = in.readInt();
    }

    public static final Creator<Time> CREATOR = new Creator<Time>() {
        @Override
        public Time createFromParcel(Parcel in) {
            return new Time(in);
        }

        @Override
        public Time[] newArray(int size) {
            return new Time[size];
        }
    };

    public int getHour() {return mHour;}
    public void setHours(int hour) {
        if(hour >=0 && hour <=24)
        {
            mHour = hour;
        }
    }
    public int getMinute() {return mMinute;}
    public void setMinutes(int minute)
    {
        if(minute >= 0 && minute <= 60)
        {
            mMinute = minute;
        }
    }

    @Override
    public String toString()
    {
        String hour = String.valueOf(mHour);
        if(mHour < 10 )
        {
            hour = "0" + hour;
        }
        String minute = String.valueOf(mMinute);
        if(mMinute < 10)
        {
            minute = "0" + minute;
        }

        return hour + ":" + minute;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mHour);
        parcel.writeInt(mMinute);
    }
}
