package com.example.tay.eventi4all_def;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SplashActivity extends AppCompatActivity {
    private GifImageView gifImageView;
    private ProgressBar progressBar;
    private TextView txtTtileApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        this.gifImageView = this.findViewById(R.id.gifImageView);
        this.progressBar = this.findViewById(R.id.progressBar);
        this.txtTtileApp = this.findViewById(R.id.titleApp);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/billabong.ttf");
        this.txtTtileApp.setTypeface(type);
        progressBar.setVisibility(progressBar.VISIBLE);
        try{
            InputStream inputStream = this.getAssets().open("gifcamera.gif");
            byte [] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        }catch(IOException ex){

        }


        //Delay while MainActivity is loading
        new Handler().postDelayed(new Runnable() {
            //packageContext es el nombre del activity.this, this es el propio activity y .class la clase del activity
            @Override
            public void run() {
                //Instancia de la clase SignIn
                SplashActivity.this.startActivity(new Intent(SplashActivity.this,SignIn.class));
                SplashActivity.this.finish();
            }
        },4000);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color

        window.setStatusBarColor(ContextCompat.getColor(this,  R.color.splashColor));
    }



    public GifImageView getGifImageView() {
        return gifImageView;
    }

    public void setGifImageView(GifImageView gifImageView) {
        this.gifImageView = gifImageView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public TextView getTxtTtileApp() {
        return txtTtileApp;
    }

    public void setTxtTtileApp(TextView txtTtileApp) {
        this.txtTtileApp = txtTtileApp;
    }
}
