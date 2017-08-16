package com.parabits.parasleep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;



import java.util.List;

/**
 * Created by Razjelll on 08.08.2017.
 */

public class AlarmAdapter extends ArrayAdapter {

    private int mResource;
    private Context mContext;
    private List<Alarm> mAlarmItems;
    private LayoutInflater mInflater;

    /// tablica z dniami tygodnia, ułatwiająca wypisywanie dni w których aktywny jest budzik
    /// tablica pobierana jest w kontruktorze z zasobów
    private String[] mDaysHelpArray;

    public AlarmAdapter(Context context, int resource, List<Alarm> data)
    {
        super(context, resource, data);
        mAlarmItems = data;
        mContext = context;
        mResource = resource;
        mInflater = LayoutInflater.from(context);

        mDaysHelpArray = context.getResources().getStringArray(R.array.days_of_the_week_abbr);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View rowView = view;
        if(rowView == null)
        {
            rowView = mInflater.inflate(mResource, null);
            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);
        } else {
            viewHolder  = (ViewHolder) rowView.getTag();
        }
        Alarm alarm = mAlarmItems.get(position);
        viewHolder.setTimeText(alarm.getTimeString());
        viewHolder.setEnableAlarmButton(alarm.isEnabled());
        viewHolder.setDaysTextView(getDaysTextView(position));

        return rowView;
    }

    private String getDaysTextView(int position)
    {
        StringBuilder stringBuilder = new StringBuilder();
        Alarm alarm = mAlarmItems.get(position);
        for(int day=0; day<Days.DAYS_COUNT; day++)
        {
            if(alarm.isEnableDay(day))
            {
                stringBuilder.append(mDaysHelpArray[day]);
            }
        }
        return stringBuilder.toString();
    }

    public Alarm getAlarm(int position)
    {
        return mAlarmItems.get(position);
    }

    public long getAlarmId(int position)
    {
        return mAlarmItems.get(position).getId();
    }

    public String getTime(int position)
    {
        return mAlarmItems.get(position).getTime().toString();
    }

    public boolean isEnable(int position)
    {
        return mAlarmItems.get(position).isEnabled();
    }

    public void enableAlarm(int position, boolean enabled)
    {
        mAlarmItems.get(position).setEnabled(enabled);
        notifyDataSetChanged();
    }

    public void deleteItem(int position)
    {
        mAlarmItems.remove(position);
        notifyDataSetChanged();
    }

    private class ViewHolder
    {
        private TextView mTimeTextView;
        private ToggleButton mAlarmEnableButton;
        private TextView mDaysTextView;

        public ViewHolder(View view)
        {
            mTimeTextView = view.findViewById(R.id.time_text_view);
            mAlarmEnableButton =  view.findViewById(R.id.alarm_enable_button);
            mDaysTextView = view.findViewById(R.id.days_text_view);
        }

        public void setTimeText(String text) {
            mTimeTextView.setText(text);
        }

        public void setEnableAlarmButton(boolean enable)
        {
            mAlarmEnableButton.setChecked(enable);
        }

        public void setDaysTextView(String daysText) {mDaysTextView.setText(daysText);}
    }
}
