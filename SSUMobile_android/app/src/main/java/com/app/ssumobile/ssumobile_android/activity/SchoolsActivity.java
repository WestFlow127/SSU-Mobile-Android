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
import com.app.ssumobile.ssumobile_android.models.SchoolModel;
import com.app.ssumobile.ssumobile_android.providers.DataProvider;

public class SchoolsActivity extends AppCompatActivity {
    ArrayAdapter adapter;
    EditText inputSearch;
    static DataProvider Dal;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_view);

        inputSearch = (EditText) findViewById(R.id.input_search);

        if( Dal.Sch != null)
            adapter = new ArrayAdapter<>(this, R.layout.activity_listview, Dal.Sch);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SchoolsActivity.this, SchoolModelActivity.class);
                //based on item add info to intent
                Bundle B = new Bundle();
                SchoolModel school = (SchoolModel) adapter.getItem(position);
                B.putSerializable("SchoolModel", school);
                intent.putExtras(B);
                startActivity(intent);
            }
        });

        // Enable Search Filter for search logic
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                SchoolsActivity.this.adapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SchoolsActivity.this.adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
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
            case R.id.Departments_Directory:
               Thread Drunner = new Thread(new Runnable() {
                   @Override
                   public void run() {
                       DepartmentsActivity.Dal = Dal;
                       Intent Dintent = new Intent(SchoolsActivity.this, DepartmentsActivity.class);
                       finish();
                       startActivity(Dintent);
                   }
               });
                Drunner.start();
                return true;
            case R.id.Faculty_Staff_Directory:
                Thread runner = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FacultyStaffActivity.Dal = Dal;
                        Intent FSintent = new Intent(SchoolsActivity.this, FacultyStaffActivity.class);
                        finish();
                        startActivity(FSintent);
                    }
                });
                runner.start();
                return true;
            case R.id.Buildings_Directory:
                Thread Brunner = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BuildingsActivity.Dal = Dal;
                        Intent Bintent = new Intent(SchoolsActivity.this, BuildingsActivity.class);
                        finish();
                        startActivity(Bintent);
                    }
                });
                Brunner.start();
                return true;
            case R.id.Schools_Directory:
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

