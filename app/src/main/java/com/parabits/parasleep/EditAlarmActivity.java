package com.parabits.parasleep;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class EditAlarmActivity extends AppCompatActivity {

    private final int ALARM_NAME_RESOURCE = R.id.alarm_name_edit_text;
    private final int TIME_PICKER_RESOURCE = R.id.alarm_time_picker;
    private final int SAVE_BUTTON_RESOURCE = R.id.save_button;

    private final int MONDAY_BUTTON_RESOURCE = R.id.monday_button;
    private final int TUESDAY_BUTTON_RESOURCE = R.id.tuesday_button;
    private final int WEDNESDAY_BUTTON_RESOURCE = R.id.wednesdey_button;
    private final int THURSDAY_BUTTON_RESOURCE = R.id.thursday_button;
    private final int FRIDAY_BUTTON_RESOURCE = R.id.friday_button;
    private final int SATURDAY_BUTTON_RESOURCE = R.id.saturday_button;
    private final int SUNDAY_BUTTON_RESOURCE = R.id.sunday_button;

    private EditText mAlarmNameEditText;
    private TimePicker mTimePicker;
    private Button mSaveButton;
    private Button[] mDaysOfTheWeekButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
        setupToolbar();
        setupControls();
        setListeners();
        setData();
    }

    private void setData()
    {
        Intent intent = getIntent();
        Alarm alarm = intent.getParcelableExtra("alarm");
        if(alarm != null)
        {
            mAlarmNameEditText.setText(alarm.getName());
            mTimePicker.setCurrentHour(alarm.getTime().getHour());
            mTimePicker.setCurrentMinute(alarm.getTime().getMinute());

            for(int day=0; day<alarm.getEnabledDays().length; day++)
            {
                mDaysOfTheWeekButtons[day].setSelected(alarm.isEnableDay(day));
            }
        }


    }

    private void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//TODO zobaczy czy o to mi chodzi
    }

    private void setupControls()
    {
        mDaysOfTheWeekButtons = new Button[Days.DAYS_COUNT];
        mDaysOfTheWeekButtons[Days.MONDAY] = (Button)findViewById(MONDAY_BUTTON_RESOURCE);
        mDaysOfTheWeekButtons[Days.TUESDAY] = (Button)findViewById(TUESDAY_BUTTON_RESOURCE);
        mDaysOfTheWeekButtons[Days.WEDNESDAY] = (Button)findViewById(WEDNESDAY_BUTTON_RESOURCE);
        mDaysOfTheWeekButtons[Days.THURSDAY] = (Button)findViewById(THURSDAY_BUTTON_RESOURCE);
        mDaysOfTheWeekButtons[Days.FRIDAY] =(Button)findViewById(FRIDAY_BUTTON_RESOURCE);
        mDaysOfTheWeekButtons[Days.SATURDAY] = (Button)findViewById(SATURDAY_BUTTON_RESOURCE);
        mDaysOfTheWeekButtons[Days.SUNDAY] = (Button)findViewById(SUNDAY_BUTTON_RESOURCE);

        mAlarmNameEditText = (EditText)findViewById(ALARM_NAME_RESOURCE);
        mTimePicker = (TimePicker)findViewById(TIME_PICKER_RESOURCE);
        mSaveButton = (Button) findViewById(SAVE_BUTTON_RESOURCE);
    }

    private void setListeners()
    {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAlarm();
            }
        });
        for(int i = 0; i< mDaysOfTheWeekButtons.length; i++)
        {
            final int position = i;
            mDaysOfTheWeekButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mDaysOfTheWeekButtons[position].isSelected())
                    {
                        mDaysOfTheWeekButtons[position].setSelected(false);
                    } else {
                        mDaysOfTheWeekButtons[position].setSelected(true);
                    }
                }
            });
        }
    }

    private void saveAlarm()
    {
        AlarmDbHelper dbHelper = new AlarmDbHelper(this);
        AlarmDao dao = new AlarmDao(dbHelper.getWritableDatabase());

        String name = mAlarmNameEditText.getText().toString();
        int hour = 0;
        int minute = 0;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            hour = mTimePicker.getHour();
            minute = mTimePicker.getMinute();
        } else {
            hour = mTimePicker.getCurrentHour();
            minute = mTimePicker.getCurrentMinute();
        }

        Alarm alarm = new Alarm(hour, minute);
        alarm.setName(name);
        for(int i=0; i<mDaysOfTheWeekButtons.length; i++)
        {
            alarm.setEnableDay(i, mDaysOfTheWeekButtons[i].isSelected());
        }
        dao.add(alarm);

        finish();
    }

}
