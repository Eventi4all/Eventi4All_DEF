package com.example.tay.eventi4all_def.fragments;

import android.net.Uri;

public interface IEventContentFragmentListener {
    public void openQR(Uri uriQR);
    public void closeEventContent();
    public void openTakeAPhoto(String uuidEvent);
}
