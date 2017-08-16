package com.parabits.parasleep;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    // CONSTANTS -----------------------------------------------------------------------------------
    private final int ALARM_ITEM_RESOURCE = R.layout.item_alarm;

    private final int CLOCK_RESOURCE = R.id.clock_text_view;
    private final int LIST_RESOURCE = R.id.list;

    private final int EDIT_MENU_RESOURCE = R.string.edit;
    private final int DELETE_MENU_RESOURCE = R.string.delete;
    private final int TURN_ON_RESOURCE = R.string.turn_on;
    private final int TURN_OFF_RESOURCE = R.string.turn_off;
    // ---------------------------------------------------------------------------------------------

    // CONTROLS ------------------------------------------------------------------------------------
    private TextView mClock;
    private ListView mAlarmsList;

    //----------------------------------------------------------------------------------------------
    private AlarmAdapter mAlarmsAdapter;
    private AlarmDao mAlarmDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupControls();

        AlarmDbHelper dbHelper = new AlarmDbHelper(getApplicationContext());
        mAlarmDao = new AlarmDao(dbHelper.getReadableDatabase());

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setAdapter();

    }

    private void setupToolbar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(R.menu.menu_main_toolbar);
    }

    private void setupControls()
    {
        mClock = (TextView)findViewById(CLOCK_RESOURCE);
        mAlarmsList = (ListView)findViewById(LIST_RESOURCE);

        //rejestracja menu kontrkstowego dla elementów listy. Menu będzie dostępne po długim przytrzymaniu elementu.
        //menu tworzone jest za pomocą onCreateContextMenu. Obsługa zdarzeń menu odbywa się
        //za pomocą funkcji onContextItemSelected
        registerForContextMenu(mAlarmsList);
    }

    private void setAdapter() {
        List<Alarm> alarmsList = mAlarmDao.getAll();
        mAlarmsAdapter = new AlarmAdapter(this, ALARM_ITEM_RESOURCE, alarmsList);
        mAlarmsList.setAdapter(mAlarmsAdapter);
    }

    private void setListeners()
    {
        mAlarmsList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,View view, ContextMenu.ContextMenuInfo info)
    {

        //sprawdzamy, czy menu kontekstowe zostało uruchomione z poziomu mAlarmsList
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)info;
        int position = menuInfo.position;
        if(view.getId() == mAlarmsList.getId())
        {
            String alarmTime = mAlarmsAdapter.getTime(position);
            menu.setHeaderTitle(alarmTime);
            if(mAlarmsAdapter.isEnable(position))
            {
                menu.add(Menu.NONE, TURN_OFF_RESOURCE, Menu.NONE, getString(TURN_OFF_RESOURCE));
            } else {
                menu.add(Menu.NONE, TURN_ON_RESOURCE, Menu.NONE, getString(TURN_ON_RESOURCE));
            }
            menu.add(Menu.NONE, EDIT_MENU_RESOURCE, Menu.NONE, getString(EDIT_MENU_RESOURCE));
            menu.add(Menu.NONE, DELETE_MENU_RESOURCE, Menu.NONE, getString(DELETE_MENU_RESOURCE));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        switch (item.getItemId())
        {
            case EDIT_MENU_RESOURCE:
                startEditAlarm(position); break;
            case DELETE_MENU_RESOURCE:
                deleteAlarm(position); break;
            case TURN_OFF_RESOURCE:
                turnOffAlarm(position); break;
            case TURN_ON_RESOURCE:
                turnOnAlarm(position); break;

        }
        return true;
    }

    private void startEditAlarm(int position)
    {
        Intent intent = new Intent(this, EditAlarmActivity.class);
        intent.putExtra("alarm", mAlarmsAdapter.getAlarm(position));
        startActivity(intent);
    }

    private void deleteAlarm(final int position)
    {
        //wyświetlenie dialogu z pytaniem czy na pewno usunąć ten budzik
        AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert); //TODO zobaczyć, czy nie zmienić tego na inną wartość
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(getString(R.string.deleting_alarm_title));
        builder.setMessage(getString(R.string.sure_deleting_alerm));
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean success = mAlarmDao.delete(mAlarmsAdapter.getAlarmId(position));
                if(success) {
                    final int DELETING_SUCCESS_RESOURCE = R.string.deleting_alarm_success;
                    mAlarmsAdapter.deleteItem(position);
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss(); //zamykamy dialog
            }
        });
        builder.show();

    }

    private void turnOnAlarm(int position)
    {
        mAlarmsAdapter.enableAlarm(position, true);
    }

    public void turnOffAlarm(int position)
    {
        mAlarmsAdapter.enableAlarm(position, false);
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
                startAddAlarmActivity();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startAddAlarmActivity()
    {
        Intent intent = new Intent(this, EditAlarmActivity.class);
        startActivity(intent); //TODO najprawdopodobniej należy użyć startActivityForResult
    }
}
