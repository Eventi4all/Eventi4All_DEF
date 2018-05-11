package com.example.tay.eventi4all_def.fragments;


import android.app.AlertDialog;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import com.example.tay.eventi4all_def.DataHolder;
import com.example.tay.eventi4all_def.R;

import com.example.tay.eventi4all_def.adapter.IMyViewHolderListener;
import com.example.tay.eventi4all_def.entity.User;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import java.util.HashMap;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class CreateEventFragmentEvents implements View.OnClickListener, IMyViewHolderListener {

    private CreateEventFragment createEventFragment;
    private HashMap<String, User> users;

    public CreateEventFragmentEvents(CreateEventFragment createEventFragment) {
        this.createEventFragment = createEventFragment;
        users = new HashMap<>();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddNewFriend) {
            boolean content = false;
            if (this.createEventFragment.getArrUsers().size() > 0) {
                for (int i = 0; i < this.createEventFragment.getArrUsers().size(); i++) {
                    if (this.createEventFragment.getArrUsers().get(i).nickName.equals(this.createEventFragment.getMyFriends().getText().toString().trim())) {
                        content = true;
                        break;
                    }
                }
                if (!(this.createEventFragment.getMyFriends().getText().toString().trim().equals("")) && users.containsKey(this.createEventFragment.getMyFriends().getText().toString().trim()) && content == false) {
                    this.createEventFragment.getArrUsers().add(users.get(this.createEventFragment.getMyFriends().getText().toString().trim()));
                    this.createEventFragment.getListAdapter().notifyDataSetChanged();
                    this.createEventFragment.getMyFriends().setText("");
                }


            } else {
                if (!(this.createEventFragment.getMyFriends().getText().toString().trim().equals("")) && users.containsKey(this.createEventFragment.getMyFriends().getText().toString().trim())) {
                    this.createEventFragment.getArrUsers().add(users.get(this.createEventFragment.getMyFriends().getText().toString().trim()));
                    this.createEventFragment.getListAdapter().notifyDataSetChanged();
                    this.createEventFragment.getMyFriends().setText("");
                }
            }

        } else if (v.getId() == R.id.eventImgMain) {
            if (this.isPermissionAccepted()) {
                this.showOptions();
            }


        } else if (v.getId() == R.id.buttonNewEvent) {
            if (this.createEventFragment.getTxtEventName().getText().toString().trim().equals("")) {
                Toasty.info(this.createEventFragment.getActivity(), "¡Opps! Debes de añadir un título a tu evento.", Toast.LENGTH_SHORT, true).show();
            } else {
                HashMap<String, Object> event = new HashMap<String, Object>();
                HashMap<String,Object> assistants = new HashMap<>();
                event.put("uuid", UUID.randomUUID().toString());
                event.put("title", this.createEventFragment.getTxtEventName().getText().toString().trim());
                event.put("admin", DataHolder.MyDataHolder.currentUserNickName);
                event.put("createAt", new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime()));
                event.put("private", this.createEventFragment.getCheckboxPrivate().isChecked());
                event.put("limit", this.createEventFragment.getSpPax().getSelectedItem().toString());
               assistants.put(DataHolder.MyDataHolder.currentUserNickName,true);
                event.put("assistants", assistants);
                this.createEventFragment.getiCreateEventFragmentListener().saveEventInFirebase(event);
            }


        }

    }


    public void getAllUsers(CharSequence s) {
        this.createEventFragment.getiCreateEventFragmentListener().getUsersFb(s);


    }

    public void foundNickname(HashMap<String, User> users) {
        this.users = users;
        this.createEventFragment.getAdapter().clear();
        this.createEventFragment.getAdapter().addAll(users.keySet());
        this.createEventFragment.getAdapter().notifyDataSetChanged();
    }


    @Override
    public void deleteUser(int cellPosition) {
        this.createEventFragment.getArrUsers().remove(cellPosition);
        this.createEventFragment.getListAdapter().notifyDataSetChanged();

    }

    //Abrir un alert Dialog para mostrar las diferentes opciones (cámara/galería)
    public void showOptions() {
        //Contiene todas las opciones. Es un array ordenado de caracteres.
        final CharSequence[] options = {"Tomar foto", "Elegir de Galería", "Cancelar"};
        //Creamos el cuadro de dialogo con estas opciones pasándole coomo contecto el mainActivity
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.createEventFragment.getActivity());
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
                    createEventFragment.getiGalleryAndCapturePhotoListener().executeOptions(0, "coverEvent");
                } else if (options[which] == "Elegir de Galería") {
                    createEventFragment.getiGalleryAndCapturePhotoListener().executeOptions(1, "coverEvent");
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

        if (this.createEventFragment.getiGalleryAndCapturePhotoListener().mayRequestStoragePermission()) {
            return true;
        } else {
            return false;
        }

    }
}