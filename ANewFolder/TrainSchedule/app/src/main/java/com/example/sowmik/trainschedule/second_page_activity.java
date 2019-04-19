package com.example.sowmik.trainschedule;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class second_page_activity extends AppCompatActivity {

    private Button profile,search_train,locate_train,travel_history,train_info;


    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.second_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        profile = findViewById(R.id.profileid);
        search_train = findViewById(R.id.search_trainid);
        locate_train = findViewById(R.id.locate_trainid);
        travel_history = findViewById(R.id.travel_historyid);
        train_info = findViewById(R.id.train_infoid);

    }



}
