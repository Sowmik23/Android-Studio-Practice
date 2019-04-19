package com.example.sowmik.mygridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private String[] countryNames;
    int[] flags = {R.drawable.bangladesh, R.drawable.banglades2,
            R.drawable.bangladesh, R.drawable.banglades2,R.drawable.bangladesh,
            R.drawable.banglades2,R.drawable.bangladesh, R.drawable.banglades2,
            R.drawable.bangladesh, R.drawable.banglades2,R.drawable.bangladesh,
            R.drawable.banglades2,R.drawable.bangladesh, R.drawable.banglades2,
            R.drawable.bangladesh, R.drawable.banglades2,R.drawable.bangladesh,
            R.drawable.banglades2,R.drawable.bangladesh, R.drawable.banglades2,
            R.drawable.bangladesh, R.drawable.banglades2,R.drawable.bangladesh,
            R.drawable.banglades2,R.drawable.bangladesh, R.drawable.banglades2,
            R.drawable.bangladesh, R.drawable.banglades2,R.drawable.bangladesh,
            R.drawable.banglades2, R.drawable.bangladesh, R.drawable.banglades2,
            R.drawable.bangladesh, R.drawable.banglades2,R.drawable.bangladesh,
            R.drawable.banglades2,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gridView = findViewById(R.id.gridviewid);

        countryNames = getResources().getStringArray(R.array.country_names);

        CustomAdapter adapter = new CustomAdapter(this,countryNames,flags);

        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = countryNames[position];
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
