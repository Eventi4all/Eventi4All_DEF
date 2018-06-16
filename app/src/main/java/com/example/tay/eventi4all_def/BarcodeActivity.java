package com.example.tay.eventi4all_def;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import info.androidhive.barcode.BarcodeReader;

public class BarcodeActivity extends AppCompatActivity {
    private BarcodeActivityEvents barcodeActivityEvents;
    private BarcodeActivityListener barcodeActivityListener;
    private BarcodeReader barcodeReader;
    private ImageView btnCloseBarcodeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
       // DataHolder.MyDataHolder.firebaseAdmin.setAbstractFirebaseAdminListener(this.barcodeActivityEvents);
        this.barcodeActivityEvents = new BarcodeActivityEvents(this);
        this.barcodeReader = (BarcodeReader) this.getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
        this.btnCloseBarcodeActivity = this.findViewById(R.id.btnCloseBarcodeActivity);
        this.barcodeReader.setListener(this.barcodeActivityEvents);
        this.btnCloseBarcodeActivity.setOnClickListener(this.barcodeActivityEvents);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color

        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorGreen));

        getSupportActionBar().hide();
    }

    public BarcodeActivityEvents getBarcodeActivityEvents() {
        return barcodeActivityEvents;
    }

    public void setBarcodeActivityEvents(BarcodeActivityEvents barcodeActivityEvents) {
        this.barcodeActivityEvents = barcodeActivityEvents;
    }

    public BarcodeActivityListener getBarcodeActivityListener() {
        return barcodeActivityListener;
    }

    public void setBarcodeActivityListener(BarcodeActivityListener barcodeActivityListener) {
        this.barcodeActivityListener = barcodeActivityListener;
    }

    public BarcodeReader getBarcodeReader() {
        return barcodeReader;
    }

    public void setBarcodeReader(BarcodeReader barcodeReader) {
        this.barcodeReader = barcodeReader;
    }

    public ImageView getBtnCloseBarcodeActivity() {
        return btnCloseBarcodeActivity;
    }

    public void setBtnCloseBarcodeActivity(ImageView btnCloseBarcodeActivity) {
        this.btnCloseBarcodeActivity = btnCloseBarcodeActivity;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
