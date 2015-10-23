package com.appers.ayvaz.assistron;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LocationDest extends AppCompatActivity {
Button butLoc, butDest;
    String address_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_dest);
        butLoc = (Button) findViewById(R.id.butMyLoc);
        butDest = (Button) findViewById(R.id.butDestination);

        butLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MapsLocation.class);
                startActivity(intent);
            }
        });

        butDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LocationDest.this);
                dialog.setContentView(R.layout.address_input);
                dialog.setTitle("Destination Address");
                final EditText address = (EditText) dialog.findViewById(R.id.edit_address);
                Button butAdr = (Button) dialog.findViewById(R.id.but_address);
                butAdr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        address_name = address.getText().toString();
                        Toast.makeText(getApplicationContext(),address_name,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent intent = new Intent(LocationDest.this, MapsDest.class);
                        //intent.putExtra("address", address_name);
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Mydata", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("GOT", address_name);
                        editor.commit();
                        startActivity(intent);

                    }
                });


                dialog.show();


            }
        });

        }
}
