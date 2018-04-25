package com.example.tay.eventi4all_def.Firebase;

import android.app.Activity;
import android.net.Uri;

import android.support.annotation.NonNull;


import com.example.tay.eventi4all_def.DataHolder;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.Map;
import java.util.UUID;


public class FirebaseAdmin {

    private FirebaseAuth mAuth;
    private AbstractFirebaseAdminListener abstractFirebaseAdminListener;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private String uidUser;

    public FirebaseAdmin() {
    }

    //Devuelve la instancia de la BBDD
    public void onCreate() {
        if (this.getmAuth().getCurrentUser() != null) {
            db = FirebaseFirestore.getInstance();
        }

    }

    //Cerrar sesión en Firebase
    public void logoutFirebase(Activity activity) {
        AuthUI.getInstance()
                .signOut(activity)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        abstractFirebaseAdminListener.logout(true);
                    }
                });
    }


    //Comprobar si es la primera vez que se loguea el usuario
    public void checkUserExist() {
        DocumentReference docRef = db.collection("users").document(this.getUidUser());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        System.out.println("------------>>>>>>El usuario existe");
                        abstractFirebaseAdminListener.checkUserExist(true);
                    } else {
                        System.out.println("------------>>>>>>El usuario no existe");
                        abstractFirebaseAdminListener.checkUserExist(false);
                    }
                } else {
                    System.out.println("------------>>>>>>No se ha podido comprobar si el usuario existe: " + task.getException());
                }
            }
        });

    }

    public boolean checkIfNickNameExist(final Map user){

        final boolean[] result = new boolean[1];
        result[0]=false;
        Query check = db.collection("users").whereEqualTo("nickname", user.get("nickname").toString());
        check.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
         @Override
         public void onSuccess(QuerySnapshot documentSnapshots) {

             if(documentSnapshots.getDocuments().size()>0){
                 abstractFirebaseAdminListener.insertDocumentIsOK(false,"NickName exist");
             }else{
                 insertDocumentInFirebase(user);
             }
         }
     });
      return result[0];
    }

    public void insertDocumentInFirebase(final Map<String, Object> document) {

            try{
                final String nameImg = UUID.randomUUID().toString() + ".jpg";
                final StorageReference mountainImagesRef = storageRef.child("images/profile/" + nameImg);
                Uri file = DataHolder.MyDataHolder.imgUri;
                Task uploadTask = mountainImagesRef.putFile(file);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        abstractFirebaseAdminListener.insertDocumentIsOK(false,"Firebase Exception");
                    }
                }).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        document.put("imgProfile", "images/profile/" + nameImg);
                        db.collection("users").document(getUidUser()).set(document).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                abstractFirebaseAdminListener.insertDocumentIsOK(true,"Document Insert");
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        abstractFirebaseAdminListener.insertDocumentIsOK(false,"Firebase Exception");
                                    }
                                });


                    }


                });
            }catch(Exception e){
                abstractFirebaseAdminListener.insertDocumentIsOK(false, "Firebase Exception");
            }




    }





    //Devuelva la instancia del uid del usuairo
    public String getUidUser() {
        return getmAuth().getCurrentUser().getUid();
    }


    //Devolvemos la instancia del usuario logueado
    public FirebaseAuth getmAuth() {
        mAuth = FirebaseAuth.getInstance();
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public AbstractFirebaseAdminListener getAbstractFirebaseAdminListener() {
        return abstractFirebaseAdminListener;
    }

    public void setAbstractFirebaseAdminListener(AbstractFirebaseAdminListener abstractFirebaseAdminListener) {
        this.abstractFirebaseAdminListener = abstractFirebaseAdminListener;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }


    public void setIdUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public StorageReference getStorageRef() {
        storageRef = FirebaseStorage.getInstance().getReference();
        return storageRef;
    }

    public void setStorageRef(StorageReference storageRef) {
        this.storageRef = storageRef;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

}
