package com.app.ssumobile.ssumobile_android.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;


/**
 * Created by WestFlow on 10/22/2015.
 */

public class ContactActivity extends AppCompatActivity {

    TextView Fname;
    TextView Lname;
    TextView Title;
    Button PhoneButton;
    Button EmailButton;
    Button DepartmentButton;
    Button OfficeButton;
    Button AddToContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the contact_view to the current view
        setContentView(R.layout.contact_view);
        // Set instances of each Button/Text View
        Fname = (TextView) findViewById(R.id.Fname_button);
        Lname = (TextView) findViewById(R.id.Lname_button);
        Title = (TextView) findViewById(R.id.Title_button);
        PhoneButton = (Button) findViewById(R.id.Phone_button);
        EmailButton = (Button) findViewById(R.id.Email_button);
        DepartmentButton = (Button) findViewById(R.id.department_button);
        OfficeButton = (Button) findViewById(R.id.office_button);
        AddToContacts = (Button) findViewById(R.id.AddContact_button);

        // Create Mock Data
        MockContactProvider();
        // Initiate Threads for onClickListeners
        PhoneButtonThread();
        EmailButtonThread();

        // DepartmentButtonThread();
        // OfficeButtonThread();
        AddToContactsThread();
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

    public void MockContactProvider() {
        Fname.setText("John");
        Lname.setText("Doe");
        Title.setText("Student");
        PhoneButton.setText("310-999-9999");
        EmailButton.setText("JohnDoe@gmail.com");
        DepartmentButton.setText("Computer Science");
        OfficeButton.setText("Room 1337");
    }

    public void PhoneButtonThread() {
        Thread PhoneCallRunner = new Thread(new Runnable() {
            public void run() {
                String message = "Error: Failed to SetPhoneButton.";
                try {
                    SetPhoneButton();
                } catch (Throwable t) {
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        PhoneCallRunner.start();
    }

    public void SetPhoneButton() {
        PhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivatePhoneCall();
            }
        });
    }

    public void EmailButtonThread() {
        Thread EmailRunner = new Thread(new Runnable() {
            @Override
            public void run() {
                String message = "Error: Failed to Set Email Button";
                try {
                    SetEmailButton();
                } catch (Throwable t) {
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        EmailRunner.start();
    }

    public void SetEmailButton() {
        EmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType(getResources().getString(R.string.PLAIN_TEXT_TYPE));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{EmailButton.getText().toString()});
                startActivity(intent);
            }
        });
    }

    public void AddToContactsThread() {
        Thread AddContact = new Thread(new Runnable() {
            @Override
            public void run() {
                String message = "Error: Failed to Set Add-To-Contacts Button";
                try {
                    SetAddToContactsButton();
                } catch (Throwable t) {
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        AddContact.start();
    }

    public void SetAddToContactsButton(){
        AddToContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, EmailButton.getText());
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, PhoneButton.getText());
                intent.putExtra(ContactsContract.Intents.Insert.NAME, Fname.getText() + " " + Lname.getText());
                intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, Title.getText());
                startActivity(intent);
            }
        });
    }
    public void ActivatePhoneCall() {
        String message = "You need to activate Phone permissions for this app";
        try {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                String[] CallPermissions = {"android.permission.CALL_PHONE"};
                int requestID = 1;
                requestPermissions(CallPermissions, requestID);
            }else {
                final Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + PhoneButton.getText().toString()));
                ContactActivity.this.startActivity(callIntent);
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
