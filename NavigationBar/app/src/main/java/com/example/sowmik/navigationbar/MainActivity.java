package com.example.sowmik.navigationbar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerlayoutid);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        //to display the navigation bar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView = findViewById(R.id.nav_viewid);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()==R.id.homeid)
                {

                    Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);

                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                }
                else if (menuItem.getItemId()==R.id.myprofileid)
                {

                    Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                }
                else if (menuItem.getItemId()==R.id.settingid)
                {

                    Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                }
                else if (menuItem.getItemId()==R.id.helpid)
                {

                    Toast.makeText(MainActivity.this, "Help", Toast.LENGTH_SHORT).show();
                }
                else if (menuItem.getItemId()==R.id.feedbackid)
                {

                    Toast.makeText(MainActivity.this, "Feedback", Toast.LENGTH_SHORT).show();
                }
                else if (menuItem.getItemId()==R.id.signoutid)
                {

                    Toast.makeText(MainActivity.this, "Sign Out", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
