package com.example.sowmik.myimageview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView image1,image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image1 = findViewById(R.id.Image1id);
        image2 = findViewById(R.id.Image2id);

        image1.setOnClickListener(this);
        image2.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.Image1id) {
//            Toast.makeText(MainActivity.this,"Sowmik Sarker",Toast.LENGTH_SHORT).show();
//        }
//        else if(v.getId()==R.id.Image2id)
//        {
//            Toast.makeText(MainActivity.this,"Happy Diwali",Toast.LENGTH_SHORT).show();
//
//        }

            LayoutInflater inflater = getLayoutInflater();

            View custom_view = inflater.inflate(R.layout.custom_toast_view, (ViewGroup) findViewById(R.id.customToastid));

            Toast toast = new Toast(MainActivity.this);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(custom_view);
            toast.show();
        }
    }
}
