package com.example.sowmik.myapplication1.feature;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    EditText emailInput;
    EditText passwordInput;
    TextView errorLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void login(View view)
    {
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        errorLabel = findViewById(R.id.error_label);

        if(emailInput.getText().toString().equals(passwordInput.getText().toString()))
        {
            errorLabel.setVisibility(View.GONE);
            startActivity(new Intent(this,SuccessActivity.class ));
        }
        else {
            errorLabel.setVisibility(View.VISIBLE);
        }

    }
}