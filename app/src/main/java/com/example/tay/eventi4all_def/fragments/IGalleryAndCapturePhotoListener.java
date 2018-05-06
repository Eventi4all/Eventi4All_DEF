package com.example.tay.eventi4all_def.fragments;

public interface IGalleryAndCapturePhotoListener {
    //Método que recibe un int dependiendo de la opción que pulsamos en el alertDialog para abrir la cámara, o galería
    public void executeOptions(int option,String call);
    public boolean mayRequestStoragePermission();
    public void showExplanation();
}
