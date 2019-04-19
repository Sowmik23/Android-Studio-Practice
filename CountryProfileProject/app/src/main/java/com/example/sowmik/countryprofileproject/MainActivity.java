package com.example.sowmik.countryprofileproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bangladesh,india,pakistan;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bangladesh = findViewById(R.id.bangladeid);
        india = findViewById(R.id.indiaid);
        pakistan = findViewById(R.id.pakistanid);

        bangladesh.setOnClickListener(this);
        india.setOnClickListener(this);
        pakistan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.bangladeid)
        {
            intent = new Intent(MainActivity.this,ProfileActivity.class);

            intent.putExtra("Name","Bangladesh");
            startActivity(intent);

        }
        else if(v.getId()==R.id.indiaid)
        {
            intent = new Intent(MainActivity.this,ProfileActivity.class);

            intent.putExtra("Name","India");
            startActivity(intent);

        }
        else if(v.getId()==R.id.indiaid)
        {
            intent = new Intent(MainActivity.this,ProfileActivity.class);

            intent.putExtra("Name","Pakistan");
            startActivity(intent);

        }

    }
}
