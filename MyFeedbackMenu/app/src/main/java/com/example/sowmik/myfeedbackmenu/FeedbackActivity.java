package com.example.sowmik.myfeedbackmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    //to format lines press (alt + ctrl + l)


    private Button sendButton, clearButton;
    private EditText nameEdittext, messagerEdittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        sendButton = findViewById(R.id.sendid);
        clearButton = findViewById(R.id.clearid);

        nameEdittext = findViewById(R.id.nameid);
        messagerEdittext = findViewById(R.id.feedid);

        sendButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        try {
            String name = nameEdittext.getText().toString();
            String message = messagerEdittext.getText().toString();

            if (v.getId() == R.id.sendid) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/email");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sowmiksarker.2355.csedu@gmail.com", "safwan@gmail.com", "dewan@gmail.com"});

                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback from android app");
                intent.putExtra(Intent.EXTRA_TEXT, "Name: " + name + "\nMessage: " + message);
                startActivity(Intent.createChooser(intent, "Feedback With"));

            }
            else if (v.getId() == R.id.clearid) {
                nameEdittext.setText("");
                messagerEdittext.setText("");
            }

        } catch (Exception e) {

            Toast.makeText(this, "Exception: "+e, Toast.LENGTH_SHORT).show();
        }

    }
}
