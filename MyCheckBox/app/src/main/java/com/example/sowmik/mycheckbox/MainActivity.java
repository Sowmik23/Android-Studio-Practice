package com.example.sowmik.mycheckbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CheckBox milk,sugar,water,juice;
    private Button button;
    private TextView resulttext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        milk = findViewById(R.id.milkid);
        sugar = findViewById(R.id.sugarid);
        water = findViewById(R.id.waterid);
        juice = findViewById(R.id.juiceid);


        button = findViewById(R.id.buttonid);
        resulttext = findViewById(R.id.textid);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder sb = new StringBuilder();
                if(milk.isChecked())
                {
                    String value = milk.getText().toString();
                    sb.append(value+" is ordered\n");
                }
                if(sugar.isChecked())
                {
                    String value = sugar.getText().toString();
                    sb.append(value+" is ordered\n");
                }
                if(water.isChecked())
                {
                    String value = water.getText().toString();
                    sb.append(value+" is ordered\n");
                }
                if(juice.isChecked())
                {
                    String value = juice.getText().toString();
                    sb.append(value+" is ordered\n");
                }

                resulttext.setText(sb);
            }
        });
    }
}
