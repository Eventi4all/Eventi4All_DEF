package com.example.tay.eventi4all_def.fragments;

import android.net.Uri;

public interface ICustomDialogFragment_QRListener {
    public void shareEventWithQRWhatsApp(Uri qr);
    public void shareEventWithQRGmail(Uri uriQr);
    public void closeQRDialogFragment();
    public void destroyCustomDialogFragmentQr();
}
