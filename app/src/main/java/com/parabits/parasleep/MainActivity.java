package com.parabits.parasleep;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int ALARM_ITEM_RESOURCE = R.layout.item_alarm;

    private TextView mClock;
    private ListView mAlarmsList;

    private AlarmAdapter mAlarmsAdapter;
    private AlarmDao mAlarmDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupControls();
        
        AlarmDbHelper dbHelper = new AlarmDbHelper(getApplicationContext());
        mAlarmDao = new AlarmDao(dbHelper.getDatabase());

        setAdapter();
    }

    private void setupToolbar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(R.menu.menu_main_toolbar);
    }

    private void setupControls()
    {
        mClock = (TextView)findViewById(R.id.clock_text_view);
        mAlarmsList = (ListView)findViewById(R.id.list);
    }

    private void setAdapter() {
       /* ArrayList<Alarm> alarmsList = new ArrayList<>();
        Alarm one = new Alarm(0, 0);
        Alarm two = new Alarm(10, 5);
        alarmsList.add(one);
        alarmsList.add(two);

        mAlarmsAdapter = new AlarmAdapter(getApplicationContext(), ALARM_ITEM_RESOURCE, alarmsList);

        mAlarmsList.setAdapter(mAlarmsAdapter);*/
        List<Alarm> alarmsList = mAlarmDao.getAll();
        mAlarmsAdapter = new AlarmAdapter(this, ALARM_ITEM_RESOURCE, alarmsList);
        mAlarmsList.setAdapter(mAlarmsAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.add_alarm:
                //TODO zrobienie dodawanie alarmu

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
