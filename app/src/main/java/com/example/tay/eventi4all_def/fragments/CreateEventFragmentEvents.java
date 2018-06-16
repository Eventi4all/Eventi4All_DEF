package com.example.tay.eventi4all_def.fragments;


import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tay.eventi4all_def.DataHolder;
import com.example.tay.eventi4all_def.R;

import com.example.tay.eventi4all_def.adapter.IMyViewHolderListener;
import com.example.tay.eventi4all_def.entity.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class CreateEventFragmentEvents implements View.OnClickListener, IMyViewHolderListener {

    private CustomDialogFragment_CreateEvents createEventFragment;
    private HashMap<String, User> users;

    public CreateEventFragmentEvents(CustomDialogFragment_CreateEvents createEventFragment) {
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
                //Data of Event
                HashMap<String, Object> event = new HashMap<String, Object>();
                HashMap<String,Object> assistants = new HashMap<>();
                HashMap<String,Object> invitations = new HashMap<>();
                event.put("uuid", UUID.randomUUID().toString());
                event.put("title", this.createEventFragment.getTxtEventName().getText().toString().trim());
                event.put("admin", DataHolder.MyDataHolder.currentUserNickName);
                event.put("createAt", new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime()));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                event.put("timeStamp",  Float.parseFloat(simpleDateFormat.format(timestamp)));
                event.put("private", this.createEventFragment.getCheckboxPrivate().isChecked());
                event.put("limit", this.createEventFragment.getSpPax().getSelectedItem().toString());
                assistants.put(DataHolder.MyDataHolder.currentUserNickName,true);
                event.put("assistants", assistants);
                if(this.createEventFragment.getArrUsers().size()>0){
                    for(int i=0; i< createEventFragment.getArrUsers().size(); i++){
                        invitations.put(createEventFragment.getArrUsers().get(i).nickName.toString(),false);
                    }
                    event.put("invitations",invitations);

                }

                //Event QR
                Uri uriQr= createImageFile(this.createEventFragment.getiCreateEventFragmentListener().createQrFromEvent(event.get("uuid").toString()),event.get("uuid").toString());

                this.createEventFragment.getiCreateEventFragmentListener().saveEventInFirebase(event, this.createEventFragment.getArrUsers(),uriQr);
            }


        }
        else if(v.getId()==R.id.btnback){
            this.createEventFragment.getiCreateEventFragmentListener().hideCreateEventDialogFragment();
            this.destroyCreateEventDialogFragment();
        }

    }

    public Uri createImageFile(Bitmap bitmap, String uuid) {
        Uri uri=null;
        String imageFileName = uuid;
        File mFileTemp = null;
        String root = this.createEventFragment.getActivity().getDir("my_sub_dir", Context.MODE_PRIVATE).getAbsolutePath();
        File myDir = new File(root + "/Img");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        try {
            mFileTemp = File.createTempFile(imageFileName, ".jpg", myDir.getAbsoluteFile());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (mFileTemp != null) {
            FileOutputStream fout;
            try {
                fout = new FileOutputStream(mFileTemp);
                bitmap.compress(Bitmap.CompressFormat.PNG, 70, fout);
                fout.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
             uri = Uri.fromFile(mFileTemp);

        }
        return uri;
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


    public void destroyCreateEventDialogFragment() {
        this.createEventFragment.getiCreateEventFragmentListener().destroyCreateEventDialogFragment();
    }
}