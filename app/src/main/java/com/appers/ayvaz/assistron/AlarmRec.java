package com.appers.ayvaz.assistron;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmRec extends BroadcastReceiver {
    public AlarmRec() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context, "ALERT: " + intent.getStringExtra("message"), Toast.LENGTH_LONG).show();

    }
}