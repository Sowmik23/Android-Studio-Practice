package com.example.sowmik.offline2;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager = findViewById(R.id.pagerid);

        FragmentManager fragmentManager = getSupportFragmentManager();

        CustomAdapter adapter = new CustomAdapter(fragmentManager);
        viewPager.setAdapter(adapter);


    }

    class CustomAdapter extends FragmentStatePagerAdapter {

        public CustomAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            Fragment fragment = null;
            if (i==0)
            {
                fragment = new FragmentOne();
            }
            else if (i==1)
            {
                fragment = new FragmentTwo();
            }
            else if (i==2)
            {
                fragment = new FragmentThree();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 3; //as amra 3 ta page dekhabo
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            if (position==0)
            {
                return "Tab1";
            }
            if (position==1)
            {
                return "Tab2";
            }
            if (position==2)
            {
                return "Tab3";
            }

            return super.getPageTitle(position);
        }
    }


}
