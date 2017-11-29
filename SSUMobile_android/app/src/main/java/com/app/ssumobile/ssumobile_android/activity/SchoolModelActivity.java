package com.app.ssumobile.ssumobile_android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.SchoolModel;

/**
 * Created by Tephros on 11/20/2015.
 */
public class SchoolModelActivity extends AppCompatActivity {

    TextView SchoolName;
    TextView AdminName;
    TextView DeanName;
    TextView BuildingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the Department_model_view to the current view
        setContentView(R.layout.school_model_view);
        // Set instances of each Button/Text View
        SchoolName = (TextView) findViewById(R.id.School_Name_textview);
        AdminName = (TextView) findViewById(R.id.admin_name);
        DeanName = (TextView) findViewById(R.id.dean_name);
        BuildingName = (TextView) findViewById(R.id.building_name);

        Bundle data = getIntent().getExtras();
        SchoolModel school;
        school = (SchoolModel) data.getSerializable("SchoolModel");
        if( !school.name.equals("null") )
            SchoolName.setText(school.name);
        
        if( !school.admin_name.equals("null") )
            AdminName.setText(school.admin_name);
        else
            AdminName.setText(R.string.No_Admin);

        if( !school.building.equals("null"))
            BuildingName.setText(school.building);

        if(!school.dean_name.equals("null"))
            DeanName.setText(school.dean_name);
        else
            DeanName.setText(R.string.No_Dean);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
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