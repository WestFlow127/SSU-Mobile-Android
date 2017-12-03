package com.app.ssumobile.ssumobile_android.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.adapters.newsCardAdapter;
import com.app.ssumobile.ssumobile_android.models.newsStoryModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;



public class NewsActivity extends AppCompatActivity {

    ArrayList<newsStoryModel> events = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mRecyclerView = (RecyclerView) findViewById(R.id.news_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        Context c = getApplicationContext();

        mLayoutManager = new LinearLayoutManager(c);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new newsCardAdapter(events); // specify an adapter
        mRecyclerView.setAdapter(mAdapter);

        Thread runner = new Thread(new Runnable(){
            public void run()  {
                try {
                    sendGet("https://moonlight.cs.sonoma.edu/api/v1/news/article/?format=json");
                } catch (Throwable t) {
                    System.out.println(t.getCause());
                }
            }
        });
        runner.start();
        try {
            runner.join();
            mAdapter.notifyDataSetChanged(); // update cards
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
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

    // HTTP GET request
    private void sendGet(String url) throws Exception {

        final String USER_AGENT = "Mozilla/5.0";

        URL obj = new URL(url);
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
    private void parseOutEvents() throws org.json.JSONException {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<newsStoryModel>>(){}.getType();
        List<newsStoryModel> array = gson.fromJson(body, listType);
        for (newsStoryModel current : array){
            events.add(current);
        }
        Collections.sort(events, newsStoryModel.COMPARE_BY_PUBLISHED);
        mAdapter.notifyDataSetChanged();
    }

}
