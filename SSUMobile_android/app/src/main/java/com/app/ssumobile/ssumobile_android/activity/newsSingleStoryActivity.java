package com.app.ssumobile.ssumobile_android.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.app.ssumobile.ssumobile_android.R;

public class newsSingleStoryActivity extends AppCompatActivity {

    private WebView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_single_story);

        content = (WebView) findViewById(R.id.singlenewscontent);

        String htmlData= "<font color='white'>" +
                "<h2>" +
                getIntent().getStringExtra("title") +
                "</h2> <br> <style>img{display: inline;height: auto;max-width: 100%;}</style>" +
                getIntent().getStringExtra("content") +
                "</font>";

        content.loadData(htmlData, "text/html; charset=UTF-8", null);
        content.setBackgroundColor(Color.parseColor("#001339"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_single_story, menu);
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
