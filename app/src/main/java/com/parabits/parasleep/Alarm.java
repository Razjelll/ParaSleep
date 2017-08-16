package com.parabits.parasleep;

import android.os.Parcel;
import android.os.Parcelable;

public class Alarm implements Parcelable{

    private long mId;
    private Time mTime;
    private boolean mEnabled;
    private String mName;
    private boolean[] mDaysOfTheWeek; //tablica 7 elementowa dla każdego dnia tygodnia. Określa czy budzik ma zadzwonić w dany dzień czy nie

    public Alarm(int hour, int minute)
    {
        init(hour, minute, true);
    }

    public Alarm(int hours, int minutes, boolean enabled)
    {
        init(hours, minutes, enabled);
    }

    private void init(int hours, int minutes, boolean enabled)
    {
        mTime = new Time(hours, minutes);
        mEnabled = enabled;
        mName = "";
        mDaysOfTheWeek = new boolean[Days.DAYS_COUNT];
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

    public String getName() { return mName;}
    public void setName(String name){mName = name;}

    public boolean isEnableDay(int dayOfTheWeek)
    {
        assert (dayOfTheWeek > 0 && dayOfTheWeek < Days.DAYS_COUNT);
        return mDaysOfTheWeek[dayOfTheWeek];
    }

    public void setEnableDay(int dayOfTheWeek, boolean enabled)
    {
        assert (dayOfTheWeek > 0 && dayOfTheWeek < Days.DAYS_COUNT);
        mDaysOfTheWeek[dayOfTheWeek] = enabled;
    }

    public boolean[] getEnabledDays()
    {
        return mDaysOfTheWeek;
    }

    public void setEnabledDays(boolean[] days)
    {
        assert (days.length == Days.DAYS_COUNT);
        mDaysOfTheWeek = days;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Alarm(Parcel in) {
        mId = in.readLong();
        mEnabled = in.readByte() != 0;
        mName = in.readString();
        mTime = in.readParcelable(Time.class.getClassLoader());
        mDaysOfTheWeek = new boolean[Days.DAYS_COUNT];
        in.readBooleanArray(mDaysOfTheWeek);
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeByte((byte) (mEnabled ? 1 : 0));
        parcel.writeString(mName);
        parcel.writeParcelable(mTime, i);
        parcel.writeBooleanArray(mDaysOfTheWeek);
    }
}
