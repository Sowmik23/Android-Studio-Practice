package com.example.sowmik.myradiobutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton gender;
    private Button button;
    private TextView resulttext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radiogroupid);
        button = findViewById(R.id.buttonid);
        resulttext = findViewById(R.id.textviewid);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedid = radioGroup.getCheckedRadioButtonId();

                gender = findViewById(selectedid);
                String value = gender.getText().toString();


                resulttext.setText("You have selected gender "+value);
            }
        });

    }
}
