package edu.univdhaka.cse.cse2216.mycards.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.univdhaka.cse.cse2216.mycards.R;


public class StartPage extends Activity {
    private EditText email,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        email=findViewById(R.id.emailId);
        password=findViewById(R.id.passwordId);
        login=findViewById(R.id.loginId);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em=email.getText().toString();
                String pas=password.getText().toString();
                if (em.equals(pas))
                {
                    startActivity(new Intent(StartPage.this,CardListActivity.class));
                }
                else
                {
                    Toast.makeText(StartPage.this,"Wrong entry",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}