package com.example.sowmik.myfragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] names = {"Sowmik Sarker","Jahid Hasan","Papon Banik","Mridul Papon","Rony Robiul","Safwan","Dewan Imon","Ayman Rashed"};

        listView = findViewById(R.id.listviewid);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Fragment fragment;
        if(position==0)
        {

            fragment = new SowmikFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentid,fragment);
            fragmentTransaction.commit();
            Toast.makeText(this, "Sowmik", Toast.LENGTH_SHORT).show();
        }
        else if(position==1)
        {

//            fragment = new JahidFragment();
//            getFragmentManager().beginTransaction().replace(R.id.fragmentid,fragment).commit();

            fragment = new JahidFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentid,fragment);
            fragmentTransaction.commit();
            Toast.makeText(this, "Jahid", Toast.LENGTH_SHORT).show();
        }
        else if(position==2)
        {
            Toast.makeText(this, "Papon", Toast.LENGTH_SHORT).show();
        }
        else if(position==3)
        {
            Toast.makeText(this, "Mridul", Toast.LENGTH_SHORT).show();
        }
        else if(position==4)
        {
            Toast.makeText(this, "Rony", Toast.LENGTH_SHORT).show();
        }
        else if(position==5)
        {
            Toast.makeText(this, "Safwan", Toast.LENGTH_SHORT).show();
        }
        else if(position==6)
        {
            Toast.makeText(this, "Dewan", Toast.LENGTH_SHORT).show();
        }
        else if(position==7)
        {
            Toast.makeText(this, "Ayman Rashed", Toast.LENGTH_SHORT).show();
        }
    }


}
