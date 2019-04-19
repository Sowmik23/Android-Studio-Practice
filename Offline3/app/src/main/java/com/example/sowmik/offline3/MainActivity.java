package com.example.sowmik.offline3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText userName,password;
    private Button loginButton;
    private TextView textView;
    private int cnt=3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.usernameid);
        password = findViewById(R.id.passwordid);

        loginButton = findViewById(R.id.loginbuttonid);
        textView = findViewById(R.id.textViewid);

        textView.setText("Number of attempts remaining: 03");


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = userName.getText().toString();
                String pass = password.getText().toString();

                if(username.equals("admin") && pass.endsWith("1234"))
                {

                    Intent intent = new Intent(MainActivity.this,MyCardViewActivity.class);
                    startActivity(intent);

                }
                else
                {
                    cnt--;
                    textView.setText("Number of attempts remaining: 0"+cnt);
                    if (cnt==0)
                    {
                        loginButton.setEnabled(false);
                    }
                }

            }
        });




    }
}
