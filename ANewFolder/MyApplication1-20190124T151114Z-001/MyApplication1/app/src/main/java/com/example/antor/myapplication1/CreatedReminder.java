package com.example.antor.myapplication1;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CreatedReminder extends AppCompatActivity {

    TextView title, desc, from, to , via;
    ImageView del, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_reminder);
        Toast.makeText(getApplicationContext(), "Reminder Created", Toast.LENGTH_LONG);
        home = findViewById(R.id.homeimg);
        del = findViewById(R.id.delimg);
        title = findViewById(R.id.tv1);
        desc = findViewById(R.id.tv2);
        from = findViewById(R.id.tv3);
        to = findViewById(R.id.tv4);
        via = findViewById(R.id.tv5);

        Intent intent = getIntent();
        String Title = intent.getStringExtra("t1");
        String Desc = intent.getStringExtra("d");
        String From = intent.getStringExtra("f");
        String To = intent.getStringExtra("t2");
        String Via = intent.getStringExtra("v");
        System.out.println(Title+", "+Desc);
        title.setText(Title);
        desc.setText(Desc);
        from.setText("From: "+From);
        to.setText("To: "+To);
        via.setText("Transports: "+Via);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CreatedReminder.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(CreatedReminder.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(CreatedReminder.this);
                }
                builder.setTitle("Delete")
                        .setMessage("Sure to delete the Journey Reminder?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                AlarmManager a=(AlarmManager)getSystemService(ALARM_SERVICE);
                                Intent in=new Intent(getApplicationContext(), Alarm.class);
                                PendingIntent p1=PendingIntent.getBroadcast(getApplicationContext(),1, in,0);

                                a.cancel(p1);
                                System.out.println("Alarm cancelled");

                                Intent intent1 = new Intent(CreatedReminder.this,  MainActivity.class);
                                startActivity(intent1);
                                Toast.makeText(getApplicationContext(), "Reminder deleted", Toast.LENGTH_LONG);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
}
