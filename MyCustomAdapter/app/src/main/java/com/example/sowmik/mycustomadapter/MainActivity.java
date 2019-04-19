package com.example.sowmik.mycustomadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private String[] countryNames;
    int[] images = {R.drawable.bangladesh,
            R.drawable.bangladesh1,R.drawable.bangladesh2,R.drawable.bangladesh3,
            R.drawable.bangladesh4,R.drawable.bangladesh5,R.drawable.bangladesh6,
            R.drawable.bangladesh7,R.drawable.bangladesh8,R.drawable.bangladesh9
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listviewid);

        countryNames = getResources().getStringArray(R.array.country_names);

        CustomAdapter adapter = new CustomAdapter(this,countryNames,images);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = countryNames[position];

                Toast.makeText(MainActivity.this,value +" Position: "+position,Toast.LENGTH_SHORT).show();

            }
        });


    }
}
