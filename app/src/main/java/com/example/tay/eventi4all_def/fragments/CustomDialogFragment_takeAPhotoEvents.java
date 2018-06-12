package com.example.tay.eventi4all_def.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import com.example.tay.eventi4all_def.DataHolder;
import com.example.tay.eventi4all_def.R;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class CustomDialogFragment_takeAPhotoEvents implements View.OnClickListener{
    private CustomDialogFragment_takeAPhoto customDialogFragment_takeAPhoto;
    private String uuidEvent;

    public CustomDialogFragment_takeAPhotoEvents(CustomDialogFragment_takeAPhoto customDialogFragment_takeAPhoto) {
        this.customDialogFragment_takeAPhoto = customDialogFragment_takeAPhoto;

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

            this.customDialogFragment_takeAPhoto.getiCustomDialogFragment_takeAPhotoListener().openCameraTakeAphoto();

        }else if(view.getId()==R.id.btnSaveAPhoto){
            if(DataHolder.MyDataHolder.imgUri!=null){
                if(!this.customDialogFragment_takeAPhoto.getTxtTitleoFPhoto().getText().toString().trim().equals("")){
                    HashMap<String,Object> dataOfPhoto = new HashMap<String, Object>();
                    dataOfPhoto.put("photoCreatedBy", DataHolder.MyDataHolder.currentUserNickName);
                    dataOfPhoto.put("photoTitle",this.customDialogFragment_takeAPhoto.getTxtTitleoFPhoto().getText().toString().trim());
                    dataOfPhoto.put("uuidEvent",this.customDialogFragment_takeAPhoto.getUuidEvent());
                    this.customDialogFragment_takeAPhoto.getiCustomDialogFragment_takeAPhotoListener().uploadPhotoOfEvent(this.customDialogFragment_takeAPhoto.getUuidEvent(),dataOfPhoto);
                }else{
                    Toasty.info(this.customDialogFragment_takeAPhoto.getActivity(), "¡Opps! Debes de añadir un título a tu foto.", Toast.LENGTH_SHORT, true).show();
                }


            }else{
                Toasty.info(this.customDialogFragment_takeAPhoto.getActivity(), "¡Opps! No has añadido ninguna foto.", Toast.LENGTH_SHORT, true).show();
            }

        }else if(view.getId()==R.id.closeTakeAPhoto){
            this.customDialogFragment_takeAPhoto.getiCustomDialogFragment_takeAPhotoListener().closeTakeAPhoto();
        }
    }

    //Abrir un alert Dialog para mostrar las diferentes opciones (cámara/galería)
    public void showOptions() {
        //Contiene todas las opciones. Es un array ordenado de caracteres.
        final CharSequence[] options = {"Tomar foto", "Elegir de Galería", "Cancelar"};
        //Creamos el cuadro de dialogo con estas opciones pasándole coomo contecto el mainActivity
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getCustomDialogFragment_takeAPhoto().getActivity());
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
                    customDialogFragment_takeAPhoto.getiGalleryAndCapturePhotoListener().executeOptions(0, "photosOfEvent");
                } else if (options[which] == "Elegir de Galería") {
                    customDialogFragment_takeAPhoto.getiGalleryAndCapturePhotoListener().executeOptions(1, "photosOfEvent");
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

        if (this.customDialogFragment_takeAPhoto.getiGalleryAndCapturePhotoListener().mayRequestStoragePermission()) {
            return true;
        } else {
            return false;
        }

    }


}
