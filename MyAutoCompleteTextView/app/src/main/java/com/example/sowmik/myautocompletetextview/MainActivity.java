package com.example.sowmik.myautocompletetextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;
    private String[] countryNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        autoCompleteTextView = findViewById(R.id.autoid);
        countryNames = getResources().getStringArray(R.array.country_names);

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countryNames);


        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setThreshold(1);

        //1 ta character er sathe match sob string e dekhabe ei 1 dile r 2 dile 2 ta match ala string dekhabe

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = autoCompleteTextView.getText().toString();
                Toast.makeText(MainActivity.this, ""+value, Toast.LENGTH_SHORT).show();

            }
        });

    }
}
