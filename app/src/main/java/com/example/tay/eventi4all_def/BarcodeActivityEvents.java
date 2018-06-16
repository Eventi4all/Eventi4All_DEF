package com.example.tay.eventi4all_def;

import android.util.SparseArray;
import android.view.View;

import com.example.tay.eventi4all_def.BarcodeActivity;
import com.example.tay.eventi4all_def.Firebase.AbstractFirebaseAdminListener;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class BarcodeActivityEvents extends AbstractFirebaseAdminListener implements View.OnClickListener, BarcodeReader.BarcodeReaderListener{

    private BarcodeActivity barcodeActivity;



    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnCloseBarcodeActivity){
            this.barcodeActivity.finish();
        }

    }

    @Override
    public void onScanned(Barcode barcode) {
        System.out.println("el barcode leído es: " + barcode.displayValue);
        DataHolder.MyDataHolder.firebaseAdmin.checkIfUserisAssistant(barcode.displayValue.toString(),this.barcodeActivity);
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {

    }

    public BarcodeActivityEvents(BarcodeActivity barcodeActivity) {
        this.barcodeActivity = barcodeActivity;
    }


    public BarcodeActivity getBarcodeActivity() {
        return barcodeActivity;
    }

    public void setBarcodeActivity(BarcodeActivity barcodeActivity) {
        this.barcodeActivity = barcodeActivity;
    }

    @Override
    public void closeBarcode() {
        //this.barcodeActivity.onBackPressed();
    }
}
