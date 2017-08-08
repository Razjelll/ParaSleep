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

    public AlarmAdapter(Context context, int resource, List<Alarm> data)
    {
        super(context, resource, data);
        mAlarmItems = data;
        mContext = context;
        mResource = resource;
        mInflater = LayoutInflater.from(context);
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

        return rowView;
    }

    private class ViewHolder
    {
        private TextView mTimeTextView;
        private ToggleButton mAlarmEnableButton;

        public ViewHolder(View view)
        {
            mTimeTextView = view.findViewById(R.id.time_text_view);
            mAlarmEnableButton =  view.findViewById(R.id.alarm_enable_button);
        }

        public void setTimeText(String text) {
            mTimeTextView.setText(text);
        }

        public void setEnableAlarmButton(boolean enable)
        {
            mAlarmEnableButton.setEnabled(enable);
        }
    }
}
