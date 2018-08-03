package com.example.hlarbi.app3.MainClasses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.hlarbi.app3.ViewClasses.FragmentOne;
import com.example.hlarbi.app3.ViewClasses.FragmentThree;
import com.example.hlarbi.app3.ViewClasses.FragmentTwo;
import com.example.hlarbi.app3.R;


public class MainActivity extends AppCompatActivity {


    //<Fragments
    private BottomNavigationView mMainnav;


    ///>Fragments
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mMainnav = (BottomNavigationView) findViewById(R.id.navigation);
            switch (item.getItemId()) {
                case R.id.navigation_run:
                    FragmentOne fragmentOne = new FragmentOne();
                    android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.fram, fragmentOne, "FragmentName");
                    fragmentTransaction1.commit();
                    mMainnav.setItemBackgroundResource(R.color.june4);

                   return true;

                case R.id.navigation_pollu:
                    FragmentTwo fragmentTwo = new FragmentTwo();
                    android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.fram, fragmentTwo, "FragmentName");
                    fragmentTransaction2.commit();
                    mMainnav.setItemBackgroundResource(R.color.bleu);

                    return true;


                case R.id.navigation_profil:
                    FragmentThree fragmentThree = new FragmentThree();
                    android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.fram, fragmentThree, "FragmentName");
                    fragmentTransaction3.commit();
                    mMainnav.setItemBackgroundResource(R.color.bleu);
                    return true;
            }
            return false;
        }
    };

     @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_main);
         BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
         navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        }
     }


