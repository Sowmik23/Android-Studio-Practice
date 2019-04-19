package com.example.sowmik.myexittextdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button addButton ,subButton;
    private TextView result;
    private EditText var1,var2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        var1 = findViewById(R.id.text1);
        var2 = findViewById(R.id.text2);

        addButton = findViewById(R.id.addid);
        subButton = findViewById(R.id.subid);

        result = findViewById(R.id.resid);

        addButton.setOnClickListener(this);
        subButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        try{
            String number1 = var1.getText().toString();
            String number2 = var2.getText().toString();

            //converting to double

            double n1 = Double.parseDouble(number1);
            double n2 = Double.parseDouble(number2);

            if(v.getId()==R.id.addid)
            {
                double sum = n1 + n2;
                result.setText("Result: "+sum);

            }
            if(v.getId()==R.id.subid)
            {
                double sub = n1 - n2;
                result.setText("Result: "+sub);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this,"Please Enter number",Toast.LENGTH_SHORT).show();
        }




    }
}
