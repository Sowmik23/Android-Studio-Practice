package com.example.sowmik.myintent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.secondtextid);


        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            String value = bundle.getString("tag");
            textView.setText(value);
        }

    }
}
