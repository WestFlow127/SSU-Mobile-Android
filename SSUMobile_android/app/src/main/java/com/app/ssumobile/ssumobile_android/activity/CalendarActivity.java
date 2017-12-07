package com.app.ssumobile.ssumobile_android.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.service.RestClient;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;


public class CalendarActivity extends AppCompatActivity {

    CaldroidFragment caldroidFragment;
    android.support.v4.app.FragmentTransaction t;


    RestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set layout for activity
        setContentView(R.layout.activity_calendar);

        initializeCalendar();

        restClient = new RestClient();

        initializeListener();

        boolean isEvent = getIntent().getBooleanExtra("noEvent", false);
        if (isEvent == true){
            Toast.makeText(getBaseContext(), "There are no events on this date.", Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void initializeCalendar(){
        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);
        caldroidFragment.setArguments(args);
        t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar, caldroidFragment);
        t.commit();
    }
        
    public void initializeListener() {
        CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                final Date dateCopy = date;

                Thread runner = new Thread(new Runnable() {
                    public void run() {
                        String message = "Error: Failed to start single date .";
                        try {
                            Intent singleDateIntent = new Intent(CalendarActivity.this, CalendarSingleDate.class);
                            singleDateIntent.putExtra("dateString", dateCopy.toString());

                            startActivity(singleDateIntent); // put intent with event map in activity
                        } catch (Throwable t) {
                            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                runner.start();
            }

            @Override
            public void onChangeMonth(int month, int year) {
            }

            @Override
            public void onLongClickDate(Date date, View view) {

                final Date dateCopy = date;

                Thread runner = new Thread(new Runnable() {
                    public void run() {
                        String message = "Error: Failed to start single date .";
                        try {
                            Intent singleDateIntent = new Intent(CalendarActivity.this, CalendarSingleDate.class);
                            singleDateIntent.putExtra("dateString", dateCopy.toString());

                            startActivity(singleDateIntent); // put intent with event map in activity
                        } catch (Throwable t) {
                            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                runner.start();

            }

            @Override
            public void onCaldroidViewCreated() {
            }
        };
        caldroidFragment.setCaldroidListener(listener);
    }

}
