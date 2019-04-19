package com.example.antor.myapplication1;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

//import static com.example.antor.myapplication1.MainActivity.alertBox;

public class Alarm extends BroadcastReceiver {

     MediaPlayer mp;
     String title, desc, from, to , via;
     //Context ctx;

    @Override
    public void onReceive(final Context context, Intent intent) {

        Toast.makeText(context,"Journey",Toast.LENGTH_LONG).show();

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        mp = MediaPlayer.create(context.getApplicationContext(), notification);
        mp.start();
        title = intent.getStringExtra("t1");
        desc = intent.getStringExtra("d");
        System.out.println("Fired alarm on " + System.currentTimeMillis());
        Intent i = new Intent();
        i.setClassName("com.example.antor.myapplication1",     "com.example.antor.myapplication1.AlertAcitivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        //Toast.makeText(context,"Turned off",Toast.LENGTH_LONG).show();
                        mp.stop();
                    }
                },
                8000
        );
    }

    /*public void alertBox(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Alarm.this);
        builder.setMessage("You've a journey")
                .setCancelable(false)
                .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Alert");
        System.out.println("Upto here");
        alertDialog.show();
    }*/

}
