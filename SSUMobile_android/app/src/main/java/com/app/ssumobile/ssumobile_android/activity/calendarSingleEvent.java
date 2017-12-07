package com.app.ssumobile.ssumobile_android.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.ssumobile.ssumobile_android.R;

public class calendarSingleEvent extends AppCompatActivity {

    //public TextView summary;
    public TextView location;
    public TextView starttime;
    public TextView endtime;
    public TextView title;
    public TextView descrip;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_single_event);

        String msg = getIntent().getStringExtra("location");
        location = (TextView) findViewById(R.id.singleLocation);
        location.setText("Location: " + msg);


        msg = getIntent().getStringExtra("description");
        descrip = (TextView) findViewById(R.id.singleDescription);
        descrip.setText(msg);

        msg = getIntent().getStringExtra("ends_date").substring(11);
        endtime = (TextView) findViewById(R.id.singleEndsOn);
        endtime.setText("Ends: " +msg);

        msg = getIntent().getStringExtra("start_date").substring(11);
        starttime = (TextView) findViewById(R.id.singleStartsOn);
        starttime.setText("Starts: " + msg);

        msg = getIntent().getStringExtra("title");
        title = (TextView) findViewById(R.id.singleTitle);
        title.setText(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar_single_event, menu);
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
}
