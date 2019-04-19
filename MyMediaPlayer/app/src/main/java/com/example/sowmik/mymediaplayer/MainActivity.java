 package com.example.sowmik.mymediaplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

 public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton playbutton,pausebutton;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playbutton = findViewById(R.id.imagebuttonid);
        pausebutton = findViewById(R.id.pausebuttonid);

        mediaPlayer = MediaPlayer.create(this,R.raw.dilbar_dilbar);

        playbutton.setOnClickListener(this);
        pausebutton.setOnClickListener(this);


    }

     @Override
     public void onClick(View v) {

        if(v.getId()==R.id.imagebuttonid){

            if(mediaPlayer!=null)
            {
                mediaPlayer.start();
                int duration = mediaPlayer.getDuration();

                Toast.makeText(this, "Song played "+duration/1000, Toast.LENGTH_SHORT).show();
            }

        }
        if(v.getId()==R.id.pausebuttonid){


            if(mediaPlayer!=null)
            {
                mediaPlayer.pause();
                Toast.makeText(this, "Song paused", Toast.LENGTH_SHORT).show();
            }
        }


     }


     @Override
     protected void onDestroy() {

        if(mediaPlayer!=null && mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        super.onDestroy();
     }


 }
