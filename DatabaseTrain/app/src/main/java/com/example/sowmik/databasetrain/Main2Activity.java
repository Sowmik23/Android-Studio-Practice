package com.example.sowmik.databasetrain;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    DbHelper dbHelper;

    private Button searchButton;
    private EditText departure,arrival;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        departure = findViewById(R.id.departureid);
        arrival = findViewById(R.id.arrivalid);

        searchButton = findViewById(R.id.searchbuttonid);


        dbHelper = new DbHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String dep = departure.getText().toString();
                final String arri = arrival.getText().toString();

                //System.out.println(dep +" sowmik sarker"+arri);

                Cursor cursor = dbHelper.displaySearchData(dep,arri);

                if (cursor.getCount()==0){

                    showSearchData("Error","No data found");
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

                showSearchData("Resultset",stringBuffer.toString());

            }
        });

    }

    public void showSearchData(String title,String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }

}
