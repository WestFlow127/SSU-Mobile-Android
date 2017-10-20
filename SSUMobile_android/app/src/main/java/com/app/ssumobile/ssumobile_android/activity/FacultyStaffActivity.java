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
import com.app.ssumobile.ssumobile_android.models.DepartmentModel;
import com.app.ssumobile.ssumobile_android.models.FacStaffModel;
import com.app.ssumobile.ssumobile_android.providers.DataProvider;

import java.util.ArrayList;

public class FacultyStaffActivity extends AppCompatActivity {
    DataProvider Dal;
    ArrayAdapter adapter;
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_view);

        inputSearch = (EditText) findViewById(R.id.input_search);

        Bundle data = getIntent().getExtras();
        Dal.Fac = (ArrayList<FacStaffModel>) data.getSerializable("dal.f");
        Dal.Dep = (ArrayList<DepartmentModel>) data.getSerializable("dal.d");

        if(Dal.Fac != null)
            adapter = new ArrayAdapter<>(this, R.layout.activity_listview, Dal.Fac);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FacultyStaffActivity.this, FacStaffModelActivity.class);
                //based on item add info to intent
                FacStaffModel FSmodel = (FacStaffModel) adapter.getItem(position);
                Bundle FS = new Bundle();
                FS.putSerializable("FacStaffModel", FSmodel);
                intent.putExtras(FS);
                startActivity(intent);
            }

        });

        // Enable Search Filter for search logic
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                FacultyStaffActivity.this.adapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FacultyStaffActivity.this.adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        // Set the FacStaff Department to it's respective name
        Thread runner = new Thread(new Runnable() {
            @Override
            public void run() {
                for( int i = 0; i < Dal.Fac.size(); i++ ){
                    for( int j = 0; j < Dal.Dep.size(); j++){
                        if( Dal.Fac.get(i).department.equals( Dal.Dep.get(j).id ) ) {
                            if (Dal.Dep.get(j).displayName != null)
                                Dal.Fac.get(i).department = Dal.Dep.get(j).displayName;
                            else
                                Dal.Fac.get(i).department = Dal.Dep.get(j).name;
                        }
                    }
                }
            }
        });
        runner.start();
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
                        Intent Dintent = new Intent(FacultyStaffActivity.this, DepartmentsActivity.class);
                        Bundle B = new Bundle();
                        B.putSerializable("dal.d", Dal.Dep);
                        Dintent.putExtras(B);
                        finish();
                        startActivity(Dintent);
                    }
                });
                Drunner.start();
                return true;

            case R.id.Buildings_Directory:
                Thread Brunner = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent Bintent = new Intent(FacultyStaffActivity.this, BuildingsActivity.class);
                        Bundle B = new Bundle();
                        B.putSerializable("dal.b", Dal.Bui);
                        Bintent.putExtras(B);
                        finish();
                        startActivity(Bintent);
                    }
                });
                Brunner.start();
                return true;

            case R.id.Schools_Directory:
                Thread Srunner = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent Sintent = new Intent(FacultyStaffActivity.this, SchoolsActivity.class);
                        Bundle B = new Bundle();
                        B.putSerializable("dal.s", Dal.Sch);
                        Sintent.putExtras(B);
                        finish();
                        startActivity(Sintent);
                    }
                });
                Srunner.start();
                return true;

            case R.id.Faculty_Staff_Directory:
                return true;

            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
