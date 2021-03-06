package com.app.ssumobile.ssumobile_android.activity;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;
import com.koushikdutta.ion.Ion;

public class newsSingleStoryActivity extends AppCompatActivity {

    private WebView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_single_story);


        content = (WebView) findViewById(R.id.singlenewscontent);

        String htmlData= "<font color='white'>" +
                "<h2>" +
                getIntent().getStringExtra("Title") +
                "</h2> <br>" +
                getIntent().getStringExtra("Content") +
                "</font>";


        content.loadData(htmlData, "text/html", null);
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
