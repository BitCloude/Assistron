package com.appers.ayvaz.assistron;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button butCall, butCalender, butBrowse,butAddr, butLocation ,butSMS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butCall = (Button) findViewById(R.id.butCall);
        butCalender = (Button) findViewById(R.id.butCalender);
        butBrowse = (Button) findViewById(R.id.butBrowse);
        butAddr = (Button) findViewById(R.id.butAdr);
        butLocation = (Button) findViewById(R.id.butLocation);
        butSMS = (Button) findViewById(R.id.butSMS);

        butCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
               // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        butCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalenderAct.class);
                startActivity(intent);
            }
        });
        butBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yahoo.com"));
                startActivity(intent);
            }
        });
        butAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddressMan.class);
                startActivity(intent);
            }
        });
        butLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LocationDest.class);
                startActivity(intent);
            }
        });
        butSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SMSAct.class);
                startActivity(intent);
            }
        });



    }
}
