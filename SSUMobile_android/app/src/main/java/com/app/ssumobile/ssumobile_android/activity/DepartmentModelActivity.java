package com.app.ssumobile.ssumobile_android.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.DepartmentModel;
import com.app.ssumobile.ssumobile_android.models.FacStaffModel;


/**
 * Created by WestFlow on 10/22/2015.
 */

public class DepartmentModelActivity extends AppCompatActivity {

    TextView DisplayName;
    Button PhoneButton;
    Button WebSite;
    Button BuildingButton;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the Department_model_view to the current view
        setContentView(R.layout.department_model_view);

        // Set instances of each Button/Text View
        DisplayName = (TextView) findViewById(R.id.DisplayName_button);
        PhoneButton = (Button) findViewById(R.id.Phone_button);
        WebSite = (Button) findViewById(R.id.webSite_button);
        BuildingButton = (Button) findViewById(R.id.building_button);

        // Get bundle to retrieve the Department Model
        Bundle data = getIntent().getExtras();
        final DepartmentModel Dmodel = (DepartmentModel)data.getSerializable("DepartmentModel");
        ContactProvider(Dmodel);
        adapter = new ArrayAdapter<>(this, R.layout.activity_listview, Dmodel.getFacStaffList());

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DepartmentModelActivity.this, FacStaffModelActivity.class);
                //based on item add info to intent
                FacStaffModel FSmodel = (FacStaffModel) adapter.getItem(position);
                FSmodel.department = Dmodel.displayName;
                Bundle FS = new Bundle();
                FS.putSerializable("FacStaffModel", FSmodel);
                intent.putExtras(FS);
                startActivity(intent);
            }
        });
        // Initiate Threads for onClickListeners
        PhoneButtonThread();
        setWebSiteButton();
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

    public void ContactProvider(DepartmentModel d) {
        if( d.buildingName != null && !d.buildingName.isEmpty() )
            BuildingButton.setText( d.buildingName );
        else{
            String noBuilding ="No building available";
            BuildingButton.setText(noBuilding);
        }
        if( !d.site.equals("null") && !d.site.isEmpty() )
            WebSite.setText(d.site);
        else{
            String noSite = "No website available";
            WebSite.setText(noSite);
        }
        if( !d.phone.equals("null") && !d.phone.isEmpty() )
            PhoneButton.setText(d.phone);
        else{
            String noPhone = "No phone available";
            PhoneButton.setText(noPhone);
        }
        if( d.displayName != null && !d.displayName.isEmpty() )
            DisplayName.setText(d.displayName);
        if( d.name != null && !d.name.isEmpty() )
            DisplayName.setText(d.name);
    }

    public void setWebSiteButton(){
        if( !WebSite.getText().toString().equals("No website available" )) {
            Thread runner = new Thread(new Runnable() {
                @Override
                public void run() {
                    WebSite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = WebSite.getText().toString();
                            Intent intent = new Intent(DepartmentModelActivity.this, WebViewActivity.class);
                            intent.putExtra("url", url);
                            startActivity(intent);
                        }
                    });
                }
            });
            runner.start();
        }
    }
    public void PhoneButtonThread() {
        Thread PhoneCallRunner = new Thread(new Runnable() {
            public void run() {
                if( PhoneButton.getText().toString().isEmpty() || PhoneButton.getText().toString().equals("No phone available"))
                    SetPhoneButton(0);
                else
                    SetPhoneButton(1);
            }
        });
        PhoneCallRunner.start();
    }

    public void SetPhoneButton(int flag) {
        if (flag == 1) {
            PhoneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivatePhoneCall();
                }
            });
        }
        if (flag == 0) {
            PhoneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = "There is no Phone number available.";
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void ActivatePhoneCall() {
        String message = "You need to activate Phone permissions for this app";
        try {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                final Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + PhoneButton.getText().toString()));
                DepartmentModelActivity.this.startActivity(callIntent);
            }else{
                String[] CallPermissions = {"android.permission.CALL_PHONE"};
                int requestID = 1;
                requestPermissions(CallPermissions, requestID);
            }
        }catch( Throwable t){
            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        String message = "Permission was not granted";
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ActivatePhoneCall();
            } else {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
