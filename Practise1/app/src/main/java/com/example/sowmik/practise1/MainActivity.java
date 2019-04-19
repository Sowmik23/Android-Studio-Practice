package com.example.sowmik.practise1;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.*;
import android.widget.Toast;

public class MainActivity extends Activity {


    private TextView text;
    private Button loginButton,imageButton,logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.about_me);
//      text.setText("Sowmik Sarker");

        loginButton =  (Button) findViewById(R.id.loginbttonid);
        logoutButton = (Button) findViewById(R.id.logoutid);

    /*    loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.loginbttonid)
                {
                    text.setText("Login button is click once");
                }
            }
        });

    */


    }
    public void showMessage(View v)
    {
        if(v.getId()==R.id.loginbttonid)
        {
           // text.setText("Login button is clicked");
            Toast.makeText(MainActivity.this,"Login button is clicked",Toast.LENGTH_SHORT).show();

            Log.d("tag","Login button is clicked");
        }
        else if(v.getId()==R.id.logoutid)
        {
            //text.setText("Logout button is clicked");
            Toast.makeText(MainActivity.this, "Logout button is clicked", Toast.LENGTH_SHORT).show();

            Log.d("tag","Logout button is clicked");
        }
    }
}
