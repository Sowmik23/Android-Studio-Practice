package com.example.sowmik.myclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.TextClock;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextClock textClock;
    private AnalogClock analogClock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        analogClock = findViewById(R.id.analogid);
        textClock = findViewById(R.id.textclockid);

        analogClock.setOnClickListener(this);
        textClock.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.analogid)
        {
            Toast.makeText(MainActivity.this, "Analog Clock is Clicked", Toast.LENGTH_SHORT).show();
        }
        if(v.getId()==R.id.textclockid)
        {
            Toast.makeText(MainActivity.this, "Text Clock is Clicked", Toast.LENGTH_SHORT).show();
        }



    }
}
