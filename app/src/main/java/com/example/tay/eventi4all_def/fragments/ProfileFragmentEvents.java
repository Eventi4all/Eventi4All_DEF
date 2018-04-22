package com.example.tay.eventi4all_def.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.tay.eventi4all_def.R;

//Para poder usar los permisos de la cámara hay que importarlos:
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ProfileFragmentEvents implements View.OnClickListener{
    private ProfileFragment profileFragment;

    public ProfileFragmentEvents(ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.imgViewProfile){
            this.isPermissionAccepted();
            this.showOptions();
        }
        if(v.getId()== R.id.btnCreateProfile){


        }
    }
    //Abrir un alert Dialog para mostrar las diferentes opciones (cámara/galería)
    public void showOptions(){
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
                if(options[which]=="Tomar foto"){
                    profileFragment.getiProfileFragmentListener().executeOptions(0);
                }else if(options[which]=="Elegir de Galería"){
                    profileFragment.getiProfileFragmentListener().executeOptions(1);
                }else{
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

        public void isPermissionAccepted(){
            if(this.profileFragment.getiProfileFragmentListener().mayRequestStoragePermission()){
                this.profileFragment.getImgProfile().setEnabled(true);
            }else{
                this.profileFragment.getImgProfile().setEnabled(false);
            }

        }



}