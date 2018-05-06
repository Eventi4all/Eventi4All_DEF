package com.example.tay.eventi4all_def.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.tay.eventi4all_def.DataHolder;
import com.example.tay.eventi4all_def.R;

//Para poder usar los permisos de la cámara hay que importarlos:
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ProfileFragmentEvents implements View.OnClickListener {
    private ProfileFragment profileFragment;

    public ProfileFragmentEvents(ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgViewProfile) {

            if (this.isPermissionAccepted()) {
                this.showOptions();
            }

        }
        if (v.getId() == R.id.btnCreateProfile) {
            Map<String, Object> profile = new HashMap<String, Object>();
            profile.put("nickname", this.profileFragment.getNickName().getText().toString().trim());
            this.profileFragment.getiProfileFragmentListener().saveProfileInFirebase(profile);

        }
    }

    //Abrir un alert Dialog para mostrar las diferentes opciones (cámara/galería)
    public void showOptions() {
        //Contiene todas las opciones. Es un array ordenado de caracteres.
        final CharSequence[] options = {"Tomar foto", "Elegir de Galería", "Cancelar"};
        //Creamos el cuadro de dialogo con estas opciones pasándole coomo contecto el mainActivity
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.profileFragment.getActivity());
        builder.setTitle("Elige una opción");
        //Configuramos el alertDialog con las opciones con el charSequence y un onclick listener
        //
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*Cuando se seleciona una opción, se llama a este onclick.
                  El parámetro witch representa la opción escogida (0,1,2)
                 */
                if (options[which] == "Tomar foto") {
                    profileFragment.getiGalleryAndCapturePhotoListener().executeOptions(0,"profile");
                } else if (options[which] == "Elegir de Galería") {
                    profileFragment.getiGalleryAndCapturePhotoListener().executeOptions(1,"profile");
                } else {
                    dialog.dismiss();
                }
            }
        });

        //Mostramos el alertDialog
        builder.show();
    }
      /*
        Preguntamos por los permisos de cámara, galería,etc y si no los tenemos deshabilitamos el imageView para
        poder abrir la cámara o la galería
        */

    public boolean isPermissionAccepted() {

        if (this.profileFragment.getiGalleryAndCapturePhotoListener().mayRequestStoragePermission()) {
            return true;
        } else {
            return false;
        }

    }


}
