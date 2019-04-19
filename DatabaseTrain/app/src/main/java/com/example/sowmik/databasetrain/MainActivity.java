package com.example.sowmik.databasetrain;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper;

    private EditText trainName,trainNo,offday,departurStation,departureTime,arrivalStation,arrivalTime;
    private Button insertButton,displayAlldata,nextPageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper = new DbHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();


        trainName = findViewById(R.id.train_nameid);
        trainNo = findViewById(R.id.train_noid);
        offday = findViewById(R.id.offdayid);
        departurStation = findViewById(R.id.departurestationid);
        departureTime = findViewById(R.id.departuretimeid);
        arrivalStation = findViewById(R.id.arrivalstationid);
        arrivalTime = findViewById(R.id.arrivaltimeid);

        insertButton = findViewById(R.id.insertbuttonid);
        displayAlldata = findViewById(R.id.displaydataid);
        nextPageButton = findViewById(R.id.nextpageid);


        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = trainName.getText().toString();
                String no = trainNo.getText().toString();
                String holiday = offday.getText().toString();
                String departure = departurStation.getText().toString();
                String d_time = departureTime.getText().toString();
                String arrival = arrivalStation.getText().toString();
                String a_time = arrivalTime.getText().toString();


                long rowId = dbHelper.insertData(name,no,holiday,departure,d_time,arrival,a_time);

                if (rowId==-1){

                    Toast.makeText(MainActivity.this, "Unsuccessful to insert", Toast.LENGTH_SHORT).show();

                }else {

                    Toast.makeText(MainActivity.this, "Inserted data in row "+rowId, Toast.LENGTH_SHORT).show();

                }

            }
        });


        displayAlldata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = dbHelper.displayAllData();

                if (cursor.getCount()==0){

                    showData("Error","No data found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();

                while (cursor.moveToNext()){


                    stringBuffer.append("TRAIN NAME: "+cursor.getString(1)+"\n");
                    stringBuffer.append("TRAIN NO: "+cursor.getString(2)+"\n");
                    stringBuffer.append("OFFDAY: "+cursor.getString(3)+"\n");
                    stringBuffer.append("DEPARTURE: "+cursor.getString(4)+"\n");
                    stringBuffer.append("DEPARTURE TIME: "+cursor.getString(5)+"\n");
                    stringBuffer.append("ARRIVAL: "+cursor.getString(6)+"\n");
                    stringBuffer.append("ARRIVAL TIME: "+cursor.getString(7)+"\n\n\n");
                }

                showData("Resultset",stringBuffer.toString());

            }
        });


        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });



    }

    public void showData(String title,String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }


}
