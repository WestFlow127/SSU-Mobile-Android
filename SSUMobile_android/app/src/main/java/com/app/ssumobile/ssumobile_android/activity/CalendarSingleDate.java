package com.app.ssumobile.ssumobile_android.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.adapters.calendarCardAdapter;
import com.app.ssumobile.ssumobile_android.models.calendarEventModel;
import com.app.ssumobile.ssumobile_android.service.CalendarService;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RestAdapter;


public class CalendarSingleDate extends AppCompatActivity {
    TextView t;
    ArrayList<calendarEventModel> events = new ArrayList<>();

    RestAdapter restAdapter;
    CalendarService calendarService;

    final String url = "https://moonlight.cs.sonoma.edu/api/v1/events/event/?format=json";

    String body;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    String Year, Month, Day, DateString = null;
    String currentdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_single_date);

        String dateString = getIntent().getStringExtra("dateString");
        setDateFields(dateString);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        Context c = getApplicationContext();
        mLayoutManager = new LinearLayoutManager(c);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void setDateFields(String dateStr) {
        Date date = null;
        String format = "EEE MMM dd hh:mm:ss zzz yyyy";


        try {
            date = new SimpleDateFormat(format, Locale.US).parse(dateStr);
            Integer month = date.getMonth() + 1;

            Year = dateStr.substring(24);
            Month = month.toString();
            Day = dateStr.substring(8, 10);

            DateString = Year + Month + Day;
            currentdate = Year + "-" + Month + "-" + Day;

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar_single_date, menu);
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

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter = new calendarCardAdapter(events); // specify an adapter
        mRecyclerView.setAdapter(mAdapter);

        Thread runner = new Thread(new Runnable(){
            public void run()  {
                try {
                    sendGet(url); // get selected date's info
                } catch (Throwable t) {
                    System.out.println(t.getCause());
                }
            }
        });
        runner.start();

        try {
            runner.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("in onstart()");
    }

    // HTTP GET request
    private void sendGet(String url) throws Exception {

        final String USER_AGENT = "Mozilla/5.0";
        URL obj = new URL(url);
       // URL obj = new URL("http://www.cs.sonoma.edu/~levinsky/mini_events.json");
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");  // optional default is GET
        con.setRequestProperty("User-Agent", USER_AGENT); //add request header
        con.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {response.append(inputLine);}
        in.close();

        body = response.toString();
        parseOutEvents();
    }

    // parse out events from body
    private void parseOutEvents() throws InterruptedException {
        Boolean hasEvents = Boolean.FALSE;

        String[] parsedBody = body.split("\\}\\,\\{");
        for (int i = 0; i < parsedBody.length; i++){

            if (parsedBody[i].contains(currentdate)){
                events.add(stringToEvent(parsedBody[i]));
                hasEvents = Boolean.TRUE;
            }

        }

        if (hasEvents == Boolean.FALSE){
            RelativeLayout r = (RelativeLayout)findViewById(R.id.poss);

            TextView err = new TextView(this);
            err.setText("There are no events on this date.");
            err.setTextColor(Color.BLACK);
            r.addView(err);

        }
        Collections.sort(events, calendarEventModel.COMPARE_BY_START);

        mAdapter.notifyDataSetChanged(); // update cards for user
    }

    static final String REstart_date = "start_date\\\"\\:\\\"(.*?)\\\"";
    static final String REdescription = "description\\\"\\:\\\"(.*?)\\\"";
    static final String REtitle = "title\\\"\\:\\\"(.*?)\\\"";
    static final String REendson = "ends_date\\\"\\:\\\"(.*?)\\\"";
    static final String RElocation = "location\\\"\\:\\\"(.*?)\\\"";

    private calendarEventModel stringToEvent(String text){
        calendarEventModel c = new calendarEventModel();

        Matcher m;
        String insertion;

        m = Pattern.compile(REstart_date).matcher(text); // startson
        if (m.find()){
            insertion = m.group(1);
            c.setStartsOn(insertion.substring(0,10));
        }
        m = Pattern.compile(REdescription).matcher(text); // description
        if (m.find()) {
            insertion = m.group(1);
            c.setDescription(insertion);
        }
        m = Pattern.compile(REtitle).matcher(text); // title
        if (m.find()) {
            insertion = m.group(1);
            c.setTitle(insertion);
        }
        m = Pattern.compile(RElocation).matcher(text); // location
        if (m.find()) {
            insertion = m.group(1);
            c.setLocation(insertion);
        }
        m = Pattern.compile(REendson).matcher(text); // endson
        if (m.find()) {
            insertion = m.group(1);
            c.setEndsOn(insertion);
        }
        return c;
    }
}
