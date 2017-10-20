package com.app.ssumobile.ssumobile_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.BuildingModel;
import com.app.ssumobile.ssumobile_android.providers.DataProvider;

import java.util.ArrayList;

public class BuildingsActivity extends AppCompatActivity {
    DataProvider Dal;
    ArrayAdapter adapter;
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_view);

        inputSearch = (EditText) findViewById(R.id.input_search);

        Bundle data = getIntent().getExtras();
        Dal.Bui = (ArrayList<BuildingModel>) data.getSerializable("dal.b");

        if( Dal.Bui != null)
            adapter = new ArrayAdapter<>(this, R.layout.activity_listview, Dal.Bui);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BuildingsActivity.this, BuildingModelActivity.class);
                //based on item add info to intent
                BuildingModel building = (BuildingModel) adapter.getItem(position);
                Bundle B = new Bundle();
                B.putSerializable("BuildingModel", building);
                intent.putExtras(B);
                startActivity(intent);
            }
        });
        // Enable Search Filter for search logic
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                BuildingsActivity.this.adapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                BuildingsActivity.this.adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dir, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()){
            case R.id.Faculty_Staff_Directory:
                Thread FSrunner = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent FSintent = new Intent(BuildingsActivity.this, FacultyStaffActivity.class);
                        Bundle B = new Bundle();
                        B.putSerializable("dal.f", Dal.Fac);
                        FSintent.putExtras(B);
                        finish();
                        startActivity(FSintent);
                    }
                });
                FSrunner.start();
                return true;
            case R.id.Departments_Directory:
                Thread Drunner = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent Dintent = new Intent(BuildingsActivity.this, DepartmentsActivity.class);
                        Bundle B = new Bundle();
                        B.putSerializable("dal.d", Dal.Dep);
                        Dintent.putExtras(B);
                        finish();
                        startActivity(Dintent);
                    }
                });
                Drunner.start();
                return true;
            case R.id.Schools_Directory:
                Thread Srunner = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent Sintent = new Intent(BuildingsActivity.this, SchoolsActivity.class);
                        Bundle B = new Bundle();
                        B.putSerializable("dal.s", Dal.Sch);
                        Sintent.putExtras(B);
                        finish();
                        startActivity(Sintent);
                    }
                });
                Srunner.start();
                return true;
            case R.id.Buildings_Directory:
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}