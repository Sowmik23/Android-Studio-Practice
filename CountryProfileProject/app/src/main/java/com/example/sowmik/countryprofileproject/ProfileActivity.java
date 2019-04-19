package com.example.sowmik.countryprofileproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageView = findViewById(R.id.imageid);
        textView = findViewById(R.id.textid);


        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            String countryName = bundle.getString("Name");

            showDetails(countryName);
        }
    }



    void showDetails(String countryName)
    {
        if(countryName.equals("Bangladesh"))
        {
            imageView.setImageResource(R.drawable.sansad_bhaban);
            textView.setText(R.string.bangladesh_text);
        }

        if(countryName.equals("India"))
        {
            imageView.setImageResource(R.drawable.taj_mahal);
            textView.setText(R.string.india_text);
        }

        if(countryName.equals("Pakistan"))
        {
            imageView.setImageResource(R.drawable.islamabad_religious_landmarks);
            textView.setText(R.string.pakistan_text);
        }
    }






}
