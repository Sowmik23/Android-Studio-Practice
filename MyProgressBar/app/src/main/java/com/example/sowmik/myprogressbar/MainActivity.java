  package com.example.sowmik.myprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

  public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    int progres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Remove the title bar

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove the notification bar


         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
         WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);




        progressBar = findViewById(R.id.progressid);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                doWork();

            }
        });

        thread.start();

    }


    public void doWork(){

        for(progres=20;progres<=100;progres+=20)
        {
            try {
                Thread.sleep(1000);
                progressBar.setProgress(progres);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
