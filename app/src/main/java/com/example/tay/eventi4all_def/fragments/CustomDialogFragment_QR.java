package com.example.tay.eventi4all_def.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tay.eventi4all_def.R;
import com.labo.kaji.swipeawaydialog.SwipeAwayDialogFragment;

import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class CustomDialogFragment_QR extends SwipeAwayDialogFragment{
    private static final String TAG = "MyCustomDialogQR";
    private Context context;
    private ImageView imgQr;
    private ImageView btnWhatsApp;
    private ImageView btnGmail;
    private ImageView btnBackAndCloseQR;
    private CustomDialogFragment_QREvents customDialogFragment_qrEvents;
    private ICustomDialogFragment_QRListener iCustomDialogFragment_qrListener;
    private Uri uriQr;

    public CustomDialogFragment_QR() {
        this.context = getActivity();


    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_fragment_show_eventqr,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.customDialogFragment_qrEvents = new CustomDialogFragment_QREvents(this);
        this.imgQr = v.findViewById(R.id.imgQRShareEvent);
        this.btnWhatsApp = v.findViewById(R.id.btnWhatsApp);
        this.btnWhatsApp.setOnClickListener(this.customDialogFragment_qrEvents);
        this.btnGmail = v.findViewById(R.id.btnGmail);
        this.btnGmail.setOnClickListener(this.customDialogFragment_qrEvents);
        this.btnBackAndCloseQR = v.findViewById(R.id.btnbackAndCloseQr);
        this.btnBackAndCloseQR.setOnClickListener(this.customDialogFragment_qrEvents);
        System.out.println("Componentes del Dialog QR cargados correctamente");
        Glide.with(this.getActivity().getApplication().getApplicationContext()).load(uriQr.toString()).apply(new RequestOptions().transforms( new CropSquareTransformation(),new RoundedCornersTransformation(40,15))).into(this.imgQr);
        return v;


    }

    @Override
    public boolean onSwipedAway(boolean toRight) {
        this.customDialogFragment_qrEvents.destroyCustomDialogFragmentQr();
        return false;
    }

    public static String getTAG() {
        return TAG;
    }

    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ImageView getImgQr() {
        return imgQr;
    }

    public void setImgQr(ImageView imgQr) {
        this.imgQr = imgQr;
    }

    public ImageView getBtnWhatsApp() {
        return btnWhatsApp;
    }

    public void setBtnWhatsApp(ImageView btnWhatsApp) {
        this.btnWhatsApp = btnWhatsApp;
    }

    public ImageView getBtnGmail() {
        return btnGmail;
    }

    public void setBtnGmail(ImageView btnGmail) {
        this.btnGmail = btnGmail;
    }

    public CustomDialogFragment_QREvents getCustomDialogFragment_qrEvents() {
        return customDialogFragment_qrEvents;
    }

    public void setCustomDialogFragment_qrEvents(CustomDialogFragment_QREvents customDialogFragment_qrEvents) {
        this.customDialogFragment_qrEvents = customDialogFragment_qrEvents;
    }

    public ICustomDialogFragment_QRListener getiCustomDialogFragment_qrListener() {
        return iCustomDialogFragment_qrListener;
    }

    public void setiCustomDialogFragment_qrListener(ICustomDialogFragment_QRListener iCustomDialogFragment_qrListener) {
        this.iCustomDialogFragment_qrListener = iCustomDialogFragment_qrListener;
    }

    public ImageView getBtnBackAndCloseQR() {
        return btnBackAndCloseQR;
    }

    public void setBtnBackAndCloseQR(ImageView btnBackAndCloseQR) {
        this.btnBackAndCloseQR = btnBackAndCloseQR;
    }

    public Uri getUriQr() {
        return uriQr;
    }

    public void setUriQr(Uri uriQr) {

        this.uriQr = uriQr;
        System.out.println("URI del QRsds: " + uriQr.toString());

    }
}
