package com.example.sowmik.myratingbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private TextView restexview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ratingBar = findViewById(R.id.ratingbarid);
        restexview = findViewById(R.id.textid);

        restexview.setText("Value: "+ratingBar.getProgress());


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                restexview.setText("Value: "+rating);

            }
        });

    }
}
