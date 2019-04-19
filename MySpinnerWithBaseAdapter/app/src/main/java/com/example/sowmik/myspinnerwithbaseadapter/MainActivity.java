package com.example.sowmik.myspinnerwithbaseadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private String[] countryNames,population;
    int[] flags = {R.drawable.bangladesh, R.drawable.bangladesh,
            R.drawable.bangladesh, R.drawable.bangladesh, R.drawable.bangladesh,
            R.drawable.bangladesh, R.drawable.bangladesh, R.drawable.bangladesh,
            R.drawable.bangladesh, R.drawable.bangladesh, R.drawable.bangladesh,
            R.drawable.bangladesh, R.drawable.bangladesh, R.drawable.bangladesh,
            R.drawable.bangladesh, R.drawable.bangladesh, R.drawable.bangladesh,
            R.drawable.bangladesh, R.drawable.bangladesh, R.drawable.bangladesh,
            R.drawable.bangladesh, R.drawable.bangladesh, R.drawable.bangladesh,
            R.drawable.bangladesh, R.drawable.bangladesh, R.drawable.bangladesh,
            R.drawable.bangladesh, R.drawable.bangladesh, R.drawable.bangladesh,
            R.drawable.bangladesh, R.drawable.bangladesh, R.drawable.bangladesh,
            R.drawable.bangladesh, R.drawable.bangladesh, R.drawable.bangladesh,
    };


    private boolean isFirstSelected=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinnerid);

        countryNames = getResources().getStringArray(R.array.country_names);
        population = getResources().getStringArray(R.array.population);

        CustomAdapter adapter = new CustomAdapter(this,flags,countryNames,population);

        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(isFirstSelected==true)
                {
                    isFirstSelected = false;
                }
                else {
                    Toast.makeText(getApplicationContext(), countryNames[position] + " is selected.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        
        

    }
}
