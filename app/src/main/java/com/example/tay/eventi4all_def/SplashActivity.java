package com.example.tay.eventi4all_def;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        EasySplashScreen config = new EasySplashScreen(this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(4000)
                .withBackgroundResource(R.color.splashColor)
                .withLogo(R.drawable.gifcamera)
                .withAfterLogoText("Eventy4All");
        //add custom font
        Typeface pacificoFont = Typeface.createFromAsset(getAssets(), "fonts/billabong.ttf");
        config.getAfterLogoTextView().setTypeface(pacificoFont);

        //change text color
        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        //finally create the view
        View easySplashScreenView = config.create();
        setContentView(easySplashScreenView);

    }
}
