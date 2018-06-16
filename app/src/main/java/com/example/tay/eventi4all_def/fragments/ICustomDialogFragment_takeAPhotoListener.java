package com.example.tay.eventi4all_def.fragments;

import java.util.HashMap;

public interface ICustomDialogFragment_takeAPhotoListener {
    public void closeTakeAPhoto();
    public void openCameraTakeAphoto();
    public void uploadPhotoOfEvent(String uuidEvent, HashMap<String,Object> dataOgPhoto);
    public void destroyCustomDialogTakeAPhoto();
}
