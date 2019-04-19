package com.example.sowmik.trainschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private EditText email1,password1;
    private Button login1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //logo dekhanor jonno ei 3 ta line likhte hobe.....

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        email1 = findViewById(R.id.emailId);
        password1 = findViewById(R.id.passwordId);
        login1 = findViewById(R.id.loginId);


        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String em = email1.getText().toString();
                String pas = password1.getText().toString();

                if (em.equals(pas))
                {
                    startActivity(new Intent(MainActivity.this,second_page_activity.class));

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Invalid Email or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
