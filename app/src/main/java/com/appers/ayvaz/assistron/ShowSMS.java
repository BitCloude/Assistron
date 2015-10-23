package com.appers.ayvaz.assistron;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShowSMS extends AppCompatActivity implements TextToSpeech.OnInitListener {
ListView showMessage;
Button butSpeechSMS, butDeleteSMS;
    List<String> lstSms = new ArrayList<String>();
    List<String> id_Sms = new ArrayList<String>();
    int position_selected;
    private int MY_DATA_CHECK_CODE = 0;
    private TextToSpeech myTTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sms);
        showMessage = (ListView) findViewById(R.id.listMessage);
        butSpeechSMS = (Button) findViewById(R.id.butReadSMS);
        butDeleteSMS = (Button) findViewById(R.id.butDeleteSMS);
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
        // public static final String INBOX = "content://sms/inbox";
        // public static final String SENT = "content://sms/sent";
        // public static final String DRAFT = "content://sms/draft";

        getAllSmsFromProvider();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,lstSms);
        showMessage.setAdapter(adapter);

        butSpeechSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"To Speech" + showMessage.getItemAtPosition(position_selected).toString(), Toast.LENGTH_LONG).show();
                String words = showMessage.getItemAtPosition(position_selected).toString();
                speakWords(words);
            }
        });
        butDeleteSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean del = deleteSMS(position_selected);
                Toast.makeText(getApplicationContext(),"Deleted: id " + id_Sms.get(position_selected), Toast.LENGTH_LONG).show();
            }

        });
        showMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                position_selected = position;
            }
        });


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                myTTS = new TextToSpeech(this, this);
            }
            else {
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    public void getAllSmsFromProvider() {

        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(Telephony.Sms.Inbox.CONTENT_URI, // Official CONTENT_URI from docs
                new String[] { Telephony.Sms.Inbox.BODY, Telephony.Sms.Inbox.ADDRESS, Telephony.Sms.Inbox._ID}, // Select body text
                null,
                null,
                Telephony.Sms.Inbox.DEFAULT_SORT_ORDER); // Default sort order

        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                lstSms.add(c.getString(1)+"\n" + c.getString(0));
                id_Sms.add(c.getString(2));
                c.moveToNext();
            }
        } else {
            throw new RuntimeException("You have no SMS in Inbox");
        }
        c.close();

    }
    public boolean deleteSMS(int position) {
        boolean isSmsDeleted = false;
        try {
            getContentResolver().delete(Uri.parse("content://sms/conversations/" + id_Sms.get(position)), null, null);
            isSmsDeleted = true;

        } catch (Exception ex) {
            isSmsDeleted = false;
        }
        return isSmsDeleted;
    }
    private void speakWords(String speech) {

        //speak straight away
        myTTS.speak(speech,TextToSpeech.QUEUE_FLUSH,null,null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            myTTS.setLanguage(Locale.US);
        }
        else if (status == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }
}

/*
 contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(Telephony.Sms.CONTENT_URI,null,null,null,null);
        namesforMessages = new String[cursor.getCount()];
        if(cursor.getCount() >0){
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                namesforMessages[i] = name;
                i++;
            }
            StringBuilder sb = new StringBuilder("names :");
            for(String value : namesforMessages){
                sb.append(value);
                sb.append("\n");
            }

        }
        else {
            Toast.makeText(getApplicationContext(), "No Rows Returned", Toast.LENGTH_SHORT).show();
        }



                Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        namesforMessages = new String[cursor.getCount()];
        namesforNumber = new String[cursor.getCount()];
        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                String msgData = "";
                //for(int idx=0;idx<cursor.getColumnCount();idx++)
                //{
                    namesforMessages[i] = cursor.getColumnName(0);
                    namesforNumber[i] = cursor.getString(0);
                   // msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                    i++;
                //}
                // use msgData

            } while (cursor.moveToNext());
        } else {
            // empty box, no SMS
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,namesforNumber);
        showMessage.setAdapter(adapter);
 */