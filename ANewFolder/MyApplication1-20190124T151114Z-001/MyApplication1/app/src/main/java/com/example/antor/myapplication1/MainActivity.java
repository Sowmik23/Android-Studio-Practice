package com.example.antor.myapplication1;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import static android.content.Context.ALARM_SERVICE;

public class MainActivity  extends AppCompatActivity {

    Button btnSet, btn, but, btnSetAlarm;
    EditText  et1, et2, et3, et5, et6, et7;
    TextView etVal;
    DatePicker date;
    TimePicker time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSet = findViewById(R.id.btnSetAlarm);
        etVal =  findViewById(R.id.etVal);
        et1 =  findViewById(R.id.et1);
        et2 =  findViewById(R.id.et2);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);
        et7 = findViewById(R.id.et7);

        //but = (Button) findViewById(R.id.button);


        date = (DatePicker) findViewById(R.id.datepicker);
        time = (TimePicker) findViewById(R.id.timepicker);
        btnSetAlarm = (Button) findViewById(R.id.btnSetAlarm);
        // perform click event on submit button
        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // get the values for day of month , month and year from a date picker

                //get texts
                String title = et1.getText().toString();
                String desc = et2.getText().toString();
                String from = et5.getText().toString();
                String to = et6.getText().toString();
                String via = et7.getText().toString();
                //Mine mine = new Mine();
                //mine.get(title, desc);

                String day = "" + date.getDayOfMonth();
                String month = "" + (date.getMonth() + 1);
                String year = "" + date.getYear();

                String hour = "" + time.getHour();
                String minute = "" + time.getMinute();
                String second = "00";
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(from) || TextUtils.isEmpty(to) || TextUtils.isEmpty(via)) {
                    System.out.println("Found a null!");
                    Toast.makeText(getApplicationContext(), "Can't leave any field empty", Toast.LENGTH_LONG).show();
                }

                // display the values by using a toast
                //Toast.makeText(getApplicationContext(), "Day: " + day + "/" + month + "/" + year + "\n" + "Time: " + hour + ":" + minute, Toast.LENGTH_LONG).show();
                else{//Calculation
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                long time = 0, timeInMinute = 0;
                Date d1 = null;
                Date d2 = Calendar.getInstance().getTime();
                String dt1 = day + "/" + month + "/" + year + " " + hour + ":" + minute + ":00";
                try {
                    d1 = format.parse(dt1);
                    time = d1.getTime() - d2.getTime();
                    System.out.println(d2.getTime() + ", " + d1.getTime() + ", " + "Time: " + time + " (ms)/ Current time: " + System.currentTimeMillis());
                    timeInMinute = time / (1000 * 60);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                AlarmManager a = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), Alarm.class);
                PendingIntent p1 = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);

                //Send data to CreatedReminder
                Intent intent_alert = new Intent(MainActivity.this, AlertAcitivity.class);
                intent_alert.putExtra("t", title);
                intent_alert.putExtra("d", desc);
                Intent intent_cr = new Intent(MainActivity.this, CreatedReminder.class);
                intent_cr.putExtra("t1", title);
                intent_cr.putExtra("d", desc);
                intent_cr.putExtra("f", from);
                intent_cr.putExtra("t2", to);
                intent_cr.putExtra("v", via);

                startActivity(intent_cr);

                System.out.println("From main: " + title + ", " + desc);
                a.setExact(AlarmManager.RTC, System.currentTimeMillis() + time, p1);
                //Toast.makeText(getApplicationContext(),"Alarm set in "+time+"seconds",Toast.LENGTH_LONG).show();
                //System.out.println(mine.getTitle()+": "+mine.getDesc());
                //Toast.makeText(getApplicationContext(), "Alarm Set at " + dt1 + "\nafter " + time/1000 + " seconds", Toast.LENGTH_LONG).show();

                }
            }
        });




        /*but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlarmManager a=(AlarmManager)getSystemService(ALARM_SERVICE);
                Intent intent=new Intent(getApplicationContext(), Alarm.class);
                PendingIntent p1=PendingIntent.getBroadcast(getApplicationContext(),1, intent,0);

                a.cancel(p1);
                Toast.makeText(getApplicationContext(), "Alarm Off", Toast.LENGTH_LONG).show();
            }
        });*/


    }
}