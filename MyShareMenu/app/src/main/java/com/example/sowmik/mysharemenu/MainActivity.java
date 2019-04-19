package com.example.sowmik.mysharemenu;

import android.content.Intent;
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

        if(item.getItemId()==R.id.shareid)
        {
            Toast.makeText(this, "Shared icon is clicked", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.setType("text/plain");
            String subject = "My application lab app";
            String body = "This app is very useful to buy train ticket in online\n you can also easily locate any train easily by\nthis app.";
            intent.putExtra(Intent.EXTRA_SUBJECT,subject);
            intent.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(intent.createChooser(intent,"share with"));


        }

        return super.onOptionsItemSelected(item);
    }
}
