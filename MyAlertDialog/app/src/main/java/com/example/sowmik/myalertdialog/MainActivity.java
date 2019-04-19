package com.example.sowmik.myalertdialog;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.buttonid);

        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        alertDialogBuilder = new AlertDialog.Builder(this);
        //for setting title
        alertDialogBuilder.setTitle(R.string.title_name);
        //for setting message
        alertDialogBuilder.setMessage(R.string.message_name);
        //for bhaire click korle jeno na chole jay
        alertDialogBuilder.setCancelable(false);
        //for setting icon
        alertDialogBuilder.setIcon(R.drawable.alert);


        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //exit
                finish();

            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
