package com.example.sowmik.firebasetest1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;


public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;

    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        firebase = new Firebase("https://fir-test1-1e476.firebaseio.com/");


        editText = findViewById(R.id.edittextid);
        button = findViewById(R.id.buttonid);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editText.getText().toString();

                Firebase firebaseChild = firebase.child("Name");

                firebaseChild.setValue(name);
            }
        });

    }
}
