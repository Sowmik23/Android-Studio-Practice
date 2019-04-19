package com.example.sowmik.myintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        button = findViewById(R.id.buttionid);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.getId()==R.id.buttionid)
                {
                    Intent intent = new Intent(FirstActivity.this,SecondActivity.class);

                    //er maddhome kono kisu pathate pari second window te

                    intent.putExtra("tag","I am Sowmik Sarker");

                    startActivity(intent);
                }

            }
        });

    }
}
