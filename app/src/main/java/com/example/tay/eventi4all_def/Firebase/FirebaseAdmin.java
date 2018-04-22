package com.example.tay.eventi4all_def.Firebase;

import android.app.Activity;
import android.net.Uri;
import android.os.health.SystemHealthManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;


import com.example.tay.eventi4all_def.DataHolder;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
       if(this.getmAuth().getCurrentUser()!=null){
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
    public void checkUserExist(){
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

    public void insertDocumentInFirebase(final Map<String, Object> document){
        final String nameImg = DataHolder.MyDataHolder.imgUri.getPath().substring(DataHolder.MyDataHolder.imgUri.getPath().lastIndexOf("/")+1);
        final StorageReference mountainImagesRef = storageRef.child("images/profile/" + nameImg);
        Uri file = DataHolder.MyDataHolder.imgUri;
        Task uploadTask = mountainImagesRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                document.put("imgProfile", "images/profile/" + nameImg);
                db.collection("users")
                        .add(document)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                               // Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                               // Log.w(TAG, "Error adding document", e);
                            }
                        });
            }


        });

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
