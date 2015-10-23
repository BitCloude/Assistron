package com.appers.ayvaz.assistron;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class CalenderAct extends AppCompatActivity {
    Button butRem;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    String message = "";
    int hour, min;
    int timeMillis = 0;
    int currMillis= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        butRem = (Button) findViewById(R.id.butReminder);
        butRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(CalenderAct.this);
                dialog.setContentView(R.layout.reminder_dialog);
                dialog.setTitle("Set Time And Message");
                final EditText editText = (EditText) dialog.findViewById(R.id.reminder_message);
                final TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.reminder_timePicker);
                Button button = (Button) dialog.findViewById(R.id.reminder_save_button);

                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        min = minute;
                    }
                });

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        message = editText.getText().toString();
                        setAlarm();
                        dialog.dismiss();

                    }
                });


                dialog.show();
            }
        });


    }

    public void setAlarm() {
        long millis=System.currentTimeMillis();
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(millis);
        int hours=c.get(Calendar.HOUR);
        int minutes=c.get(Calendar.MINUTE);
        currMillis+=TimeUnit.MINUTES.toMillis(minutes);
        currMillis+=TimeUnit.HOURS.toMillis(hours);

    timeMillis+=TimeUnit.MINUTES.toMillis(min);
    timeMillis+=TimeUnit.HOURS.toMillis(hour);
    Intent intent = new Intent(this, AlarmRec.class);
    intent.putExtra("message",message);
    pendingIntent=pendingIntent.getBroadcast(this,1,intent,0);
    alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
    alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(timeMillis-currMillis),pendingIntent);

}
}




