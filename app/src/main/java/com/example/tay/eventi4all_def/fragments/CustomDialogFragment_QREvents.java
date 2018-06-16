package com.example.tay.eventi4all_def.fragments;

import android.view.View;

import com.example.tay.eventi4all_def.R;

public class CustomDialogFragment_QREvents implements View.OnClickListener{
    private CustomDialogFragment_QR customDialogFragment_qr;

    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.btnWhatsApp){
            this.customDialogFragment_qr.getiCustomDialogFragment_qrListener().shareEventWithQRWhatsApp(this.customDialogFragment_qr.getUriQr());
        }else if(view.getId()== R.id.btnGmail){
            this.customDialogFragment_qr.getiCustomDialogFragment_qrListener().shareEventWithQRGmail(this.customDialogFragment_qr.getUriQr());
        }else if(view.getId()== R.id.btnbackAndCloseQr){
            this.customDialogFragment_qr.getiCustomDialogFragment_qrListener().closeQRDialogFragment();
            this.destroyCustomDialogFragmentQr();
        }
    }

    public CustomDialogFragment_QREvents(CustomDialogFragment_QR customDialogFragment_qr) {
        this.customDialogFragment_qr = customDialogFragment_qr;
    }

    public CustomDialogFragment_QR getCustomDialogFragment_qr() {
        return customDialogFragment_qr;
    }

    public void setCustomDialogFragment_qr(CustomDialogFragment_QR customDialogFragment_qr) {
        this.customDialogFragment_qr = customDialogFragment_qr;
    }


    public void destroyCustomDialogFragmentQr() {
        this.customDialogFragment_qr.getiCustomDialogFragment_qrListener().destroyCustomDialogFragmentQr();
    }
}
