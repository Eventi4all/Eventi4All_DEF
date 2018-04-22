package com.example.tay.eventi4all_def.fragments;

import java.util.Map;

public interface IProfileFragmentListener {
    //Método que recibe un int dependiendo de la opción que pulsamos en el alertDialog para abrir la cámara, o galería
    public void executeOptions(int option);
    public boolean mayRequestStoragePermission();
    public void saveProfileInFirebase(Map<String, Object> user);
}
