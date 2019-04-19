package com.example.sowmik.mysearchview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    String[] countryNames;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.listviewid);
        searchView = findViewById(R.id.searchviewid);


        countryNames = getResources().getStringArray(R.array.country_names);


        adapter = new ArrayAdapter<String>(this,R.layout.sample_view,R.id.countrynameid,countryNames);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return false;
            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        String value = adapter.getItem(position);
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();

    }
}
