package com.adgvit.appathon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.adgvit.appathon.R;
import com.adgvit.appathon.fragments.Tracks;
import com.adgvit.appathon.fragments.faq;
import com.adgvit.appathon.fragments.partners;
import com.adgvit.appathon.fragments.timeline;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {
    private SmoothBottomBar smoothBottomBar;

    private ImageView Start;
    public static CardView aboutUsCardView;
    private ConstraintLayout eventChoose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences.Editor editor = getSharedPreferences("com.adgvit.appathon", MODE_PRIVATE).edit();
        Start = findViewById(R.id.startbtn);
        eventChoose = findViewById(R.id.eventChoose);
        aboutUsCardView = findViewById(R.id.aboutUsCardView);
        SharedPreferences sp= getSharedPreferences("com.adgvit.appathon", MODE_PRIVATE);
        String token = sp.getString("token", "");
        if (token.equals("")){
            editor.putString("token", "token");
            Intent i = new Intent(MainActivity.this,WelcomeActivity.class);
            startActivity(i);

            //BottomSheetBehavior.from(bottomSheetDialog.findViewById(R.id.)).setDraggable(false);

        }
//        Start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                welcome.setVisibility(View.INVISIBLE);
//            }
//        });
        smoothBottomBar = (SmoothBottomBar) findViewById(R.id.bottomBar);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new timeline()).commit();
        smoothBottomBar.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            Fragment selectedFragment = null;
            switch (i){
                case 0:
                    selectedFragment = new timeline();
                    break;
                case 1:
                    selectedFragment = new partners();
                    break;
                case 2:
                    selectedFragment = new Tracks();
                    break;
                case 3:
                    selectedFragment = new faq();
                    break;
            }

            //Frag Transaction
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selectedFragment).commit();
            return true;
        });
        eventChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutUsCardView.setVisibility(View.GONE);
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new timeline()).commit();

    }
    public static void aboutUs(){
        aboutUsCardView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (aboutUsCardView.getVisibility()==View.VISIBLE){
            aboutUsCardView.setVisibility(View.GONE);
        }else{
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }
}