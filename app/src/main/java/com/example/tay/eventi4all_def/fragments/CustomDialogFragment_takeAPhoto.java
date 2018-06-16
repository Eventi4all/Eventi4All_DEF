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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tay.eventi4all_def.R;
import com.labo.kaji.swipeawaydialog.SwipeAwayDialogFragment;
import com.twitter.sdk.android.core.models.ImageValue;

import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class CustomDialogFragment_takeAPhoto extends SwipeAwayDialogFragment{
    private static final String TAG = "MyCustomDialogTakeAPhoto";
    private Context context;
    private CustomDialogFragment_takeAPhotoEvents customDialogFragment_takeAPhotoEvents;
    private ICustomDialogFragment_takeAPhotoListener iCustomDialogFragment_takeAPhotoListener;
    private ImageView imgTakeAPhoto;
    private EditText txtTitleoFPhoto;
    private Button btnSaveAPhoto;
    private ImageView btnBackAndCloseTakeAPhoto;
    private String uuidEvent;
    private IGalleryAndCapturePhotoListener iGalleryAndCapturePhotoListener;

    public CustomDialogFragment_takeAPhoto() {
        this.context = getActivity();


    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_fragment_take_a_photo,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.customDialogFragment_takeAPhotoEvents = new CustomDialogFragment_takeAPhotoEvents(this);
        this.imgTakeAPhoto = v.findViewById(R.id.imgTakeAPhoto);
        this.txtTitleoFPhoto = v.findViewById(R.id.txtAddTitleOfPhoto);
        this.btnSaveAPhoto = v.findViewById(R.id.btnSaveAPhoto);
        this.imgTakeAPhoto.setOnClickListener(this.customDialogFragment_takeAPhotoEvents);
        this.btnSaveAPhoto.setOnClickListener(this.customDialogFragment_takeAPhotoEvents);
        this.btnBackAndCloseTakeAPhoto = v.findViewById(R.id.closeTakeAPhoto);
        this.btnBackAndCloseTakeAPhoto.setOnClickListener(this.customDialogFragment_takeAPhotoEvents);
        return v;


    }

    @Override
    public boolean onSwipedAway(boolean toRight) {
        this.customDialogFragment_takeAPhotoEvents.destroyCustomDialogTakeAPhoto();
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

    public CustomDialogFragment_takeAPhotoEvents getCustomDialogFragment_takeAPhotoEvents() {
        return customDialogFragment_takeAPhotoEvents;
    }

    public void setCustomDialogFragment_takeAPhotoEvents(CustomDialogFragment_takeAPhotoEvents customDialogFragment_takeAPhotoEvents) {
        this.customDialogFragment_takeAPhotoEvents = customDialogFragment_takeAPhotoEvents;
    }

    public ICustomDialogFragment_takeAPhotoListener getiCustomDialogFragment_takeAPhotoListener() {
        return iCustomDialogFragment_takeAPhotoListener;
    }

    public void setiCustomDialogFragment_takeAPhotoListener(ICustomDialogFragment_takeAPhotoListener iCustomDialogFragment_takeAPhotoListener) {
        this.iCustomDialogFragment_takeAPhotoListener = iCustomDialogFragment_takeAPhotoListener;
    }

    public ImageView getImgTakeAPhoto() {
        return imgTakeAPhoto;
    }

    public void setImgTakeAPhoto(ImageView imgTakeAPhoto) {
        this.imgTakeAPhoto = imgTakeAPhoto;
    }

    public EditText getTxtTitleoFPhoto() {
        return txtTitleoFPhoto;
    }

    public void setTxtTitleoFPhoto(EditText txtTitleoFPhoto) {
        this.txtTitleoFPhoto = txtTitleoFPhoto;
    }

    public Button getBtnSaveAPhoto() {
        return btnSaveAPhoto;
    }

    public void setBtnSaveAPhoto(Button btnSaveAPhoto) {
        this.btnSaveAPhoto = btnSaveAPhoto;
    }

    public ImageView getBtnBackAndCloseTakeAPhoto() {
        return btnBackAndCloseTakeAPhoto;
    }

    public void setBtnBackAndCloseTakeAPhoto(ImageView btnBackAndCloseTakeAPhoto) {
        this.btnBackAndCloseTakeAPhoto = btnBackAndCloseTakeAPhoto;
    }

    public String getUuidEvent() {
        return uuidEvent;
    }

    public void setUuidEvent(String uuidEvent) {
        this.uuidEvent = uuidEvent;
    }

    public IGalleryAndCapturePhotoListener getiGalleryAndCapturePhotoListener() {
        return iGalleryAndCapturePhotoListener;
    }

    public void setiGalleryAndCapturePhotoListener(IGalleryAndCapturePhotoListener iGalleryAndCapturePhotoListener) {
        this.iGalleryAndCapturePhotoListener = iGalleryAndCapturePhotoListener;
    }
}
