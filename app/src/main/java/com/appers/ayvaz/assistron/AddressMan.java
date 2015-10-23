package com.appers.ayvaz.assistron;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AddressMan extends AppCompatActivity {
    ListView listView;
    String[] namesforContacts;
    int i =0;
    ContentResolver contentResolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_man);

        listView = (ListView) findViewById(R.id.listView);
        contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        namesforContacts = new String[cursor.getCount()];

        if(cursor.getCount() >0){
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                namesforContacts[i] = name;
                i++;
            }
            StringBuilder sb = new StringBuilder("names :");
            for(String value : namesforContacts){
                sb.append(value);
                sb.append("\n");
            }

        }
        else {
            Toast.makeText(getApplicationContext(), "No Rows Returned", Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,namesforContacts);
        listView.setAdapter(adapter);
    }
}
