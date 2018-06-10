package com.example.tay.eventi4all_def.fragments;

import android.view.View;

import com.example.tay.eventi4all_def.R;

public class CustomDialogFragment_takeAPhotoEvents implements View.OnClickListener{
    private CustomDialogFragment_takeAPhoto customDialogFragment_takeAPhoto;
    private String uuidEvent;

    public CustomDialogFragment_takeAPhotoEvents(CustomDialogFragment_takeAPhoto customDialogFragment_takeAPhoto) {
        this.customDialogFragment_takeAPhoto = customDialogFragment_takeAPhoto;
        this.uuidEvent="";
    }

    public CustomDialogFragment_takeAPhoto getCustomDialogFragment_takeAPhoto() {
        return customDialogFragment_takeAPhoto;
    }

    public void setCustomDialogFragment_takeAPhoto(CustomDialogFragment_takeAPhoto customDialogFragment_takeAPhoto) {
        this.customDialogFragment_takeAPhoto = customDialogFragment_takeAPhoto;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.imgTakeAPhoto){
            this.customDialogFragment_takeAPhoto.getiCustomDialogFragment_takeAPhotoListener().openMainActivityGoogleVision();

        }else if(view.getId()==R.id.btnSaveAPhoto){

        }else if(view.getId()==R.id.closeTakeAPhoto){
            System.out.println("take a photo");
            this.customDialogFragment_takeAPhoto.getiCustomDialogFragment_takeAPhotoListener().closeTakeAPhoto();
        }
    }

    public String getUuidEvent() {
        return uuidEvent;
    }

    public void setUuidEvent(String uuidEvent) {
        this.uuidEvent = uuidEvent;
    }
}
