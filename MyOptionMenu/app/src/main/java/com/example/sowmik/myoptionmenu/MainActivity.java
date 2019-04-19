package com.example.sowmik.myoptionmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.settingid)
        {
            Toast.makeText(this, "Setting is selected", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(item.getItemId()==R.id.shareid)
        {
            Toast.makeText(this, "Share is selected", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(item.getItemId()==R.id.feedbackid)
        {
            Toast.makeText(this, "Feedback is selected", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(item.getItemId()==R.id.aboutusid)
        {
            Toast.makeText(this, "About us is selected", Toast.LENGTH_SHORT).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
