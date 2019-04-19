package com.example.sowmik.myexpandablelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private CustomAdapter adapter;
    List<String>listDataHeader;
    HashMap<String,List<String>> listDataChild;

    private int lastExpandedPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareListData();

        expandableListView = findViewById(R.id.expandableid);

        adapter = new CustomAdapter(this,listDataHeader,listDataChild);

        expandableListView.setAdapter(adapter);


        //adding listener in header



        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                String groupName = listDataHeader.get(groupPosition);
                Toast.makeText(MainActivity.this, "group name: "+groupName, Toast.LENGTH_SHORT).show();

                return false;
            }
        });


        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                String groupName = listDataHeader.get(groupPosition);
                Toast.makeText(MainActivity.this, groupName+" is collapsed", Toast.LENGTH_SHORT).show();

            }
        });



        //adding listener in child


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String childString = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                Toast.makeText(MainActivity.this, " "+childString, Toast.LENGTH_SHORT).show();

                return false;
            }
        });


        //last expandable header ta close korar jonno


        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                if(lastExpandedPosition !=-1 && lastExpandedPosition != groupPosition)
                {
                    expandableListView.collapseGroup(lastExpandedPosition);

                }
                lastExpandedPosition = groupPosition;
            }
        });




    }





    public void prepareListData()
    {

        String[] header = getResources().getStringArray(R.array.expandable_list_header);
        String[] child = getResources().getStringArray(R.array.expandable_list_child);


        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        for(int i=0;i<header.length;i++)
        {
            //adding header data
            listDataHeader.add(header[i]);

            List<String> answer = new ArrayList<>();
            answer.add(child[i]);

            listDataChild.put(listDataHeader.get(i),answer);

        }

    }

}
