package com.example.tay.eventi4all_def.fragments;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.tay.eventi4all_def.entity.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface ICreateEventFragmentListener {
    public void getUsersFb(CharSequence sequence);
    public void saveEventInFirebase(HashMap<String, Object> event, ArrayList<User> notificationUsers, Uri uriQr);
    public void hideCreateEventDialogFragment();
    public Bitmap createQrFromEvent(String uuid);
    public void destroyCreateEventDialogFragment();
}