package com.example.tay.eventi4all_def.Firebase;

import android.app.Activity;
import android.net.Uri;

import android.support.annotation.NonNull;


import com.example.tay.eventi4all_def.DataHolder;
import com.example.tay.eventi4all_def.entity.Event;
import com.example.tay.eventi4all_def.entity.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
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
        final DocumentReference docRef = db.collection("users").document(this.getUidUser());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        System.out.println("------------>>>>>>El usuario existe");
                        //Guardamos el niockname para cuando lo necesitemos
                        DataHolder.MyDataHolder.currentUserNickName = task.getResult().getData().get("nickname").toString();
                        DataHolder.MyDataHolder.userImgProfile = task.getResult().getData().get("imgProfile").toString();
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

    public boolean checkIfDocumentNameExist(final Map collection) {
        final boolean[] result = new boolean[1];

        result[0] = false;
        Query check = db.collection("users").whereEqualTo("nickname", collection.get("nickname").toString());
        check.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                             @Override
                                             public void onSuccess(QuerySnapshot documentSnapshots) {

                                                 if (documentSnapshots.getDocuments().size() > 0) {
                                                     abstractFirebaseAdminListener.insertDocumentIsOK(false, "NickName exist");
                                                 } else {
                                                     insertProfileInFirebase(collection);
                                                 }
                                             }
                                         }


        );

        return result[0];
    }

    public void insertProfileInFirebase(final Map<String, Object> document) {

        try {
            String nameImage = UUID.randomUUID().toString() + ".jpg";
            Task uploadTask = (Task) createFile("images/profile/","upload",nameImage).get("uploadTask");
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    abstractFirebaseAdminListener.insertDocumentIsOK(false, "Firebase Exception");
                }
            }).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    document.put("imgProfile", (String) createFile("images/profile/","getUrl",nameImage).get("urlComplete"));
                    db.collection("users").document(getUidUser()).set(document).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            DataHolder.MyDataHolder.currentUserNickName = document.get("nickname").toString();
                            abstractFirebaseAdminListener.insertDocumentIsOK(true, "Document Insert");
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    abstractFirebaseAdminListener.insertDocumentIsOK(false, "Firebase Exception");
                                }
                            });


                }


            });
        } catch (Exception e) {
            abstractFirebaseAdminListener.insertDocumentIsOK(false, "Firebase Exception");
        }


    }

    public HashMap createFile(String url, String action, String nameImg) {

        HashMap<String, Object> result = new HashMap<>();
        if(action.equals("upload")){
            Uri file = DataHolder.MyDataHolder.imgUri;
            final StorageReference mountainImagesRef = storageRef.child(url + nameImg);
            result.put("uploadTask", mountainImagesRef.putFile(file));
            return result;
        }else{
            result.put("urlComplete", url + nameImg);
            return result;
        }






    }


    public void getAllUsers(CharSequence sequence) {
        final HashMap<String, User> users = new HashMap<String, User>();
        String search = sequence.toString().toLowerCase();
        db.collection("users").orderBy("nickname").startAt(search).endAt(search + '\uf8ff').addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (final DocumentSnapshot data : documentSnapshots.getDocuments()) {
                    if(!data.getData().get("nickname").toString().equals(DataHolder.MyDataHolder.currentUserNickName)){
                        storageRef.child(data.getData().get("imgProfile").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                users.put(data.getData().get("nickname").toString(), new User(data.getData().get("nickname").toString(), uri.toString()));
                                abstractFirebaseAdminListener.foundNickName(users);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }



                }


            }


        });


    }

    public void insertEventInFirebase(HashMap<String, Object> event) {

        try{
            String nameImage = UUID.randomUUID().toString() + ".jpg";
        Task uploadTask = (Task) createFile("images/events/","upload",nameImage).get("uploadTask");
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                abstractFirebaseAdminListener.insertEventOk(false, "Firebase Exception");
            }
        }).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                event.put("coverImg", (String) createFile("images/events/","getUrl",nameImage).get("urlComplete"));
               db.collection("events").document(event.get("uuid").toString()).set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        abstractFirebaseAdminListener.insertEventOk(true, "Document Insert");
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                abstractFirebaseAdminListener.insertEventOk(false, "Firebase Exception");
                            }
                        });


            }


        });
    }catch(Exception e)

    {
        abstractFirebaseAdminListener.insertDocumentIsOK(false, "Firebase Exception");
    }


}




    public void getEvents(String events) {
        final ArrayList<Event> arrEvents = new ArrayList<Event>();
        Query query=null;
        if(events.equals("createEvents")){
            query = db.collection("events").whereEqualTo("admin",DataHolder.MyDataHolder.currentUserNickName);


        }else if(events.equals("allAssistEvents")){
            query = db.collection("events").whereEqualTo("assistants."+DataHolder.MyDataHolder.currentUserNickName,true);

        }



        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot document : task.getResult()) {
                    storageRef.child(document.getData().get("coverImg").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Event event = new Event(document.get("title").toString(),(boolean)document.get("private"),document.get("limit").toString());
                            event.setCreateAt(document.get("createAt").toString());
                            event.setUrlCover(uri.toString());
                            event.setUuid("uuid");
                            arrEvents.add(event);
                            abstractFirebaseAdminListener.returnEventsFirebase(arrEvents);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });






                }

            }

        });



    }

    public void getUserInfo() {

        DocumentReference docRef = db.collection("users").document(this.getUidUser());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        storageRef.child(document.getData().get("imgProfile").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                abstractFirebaseAdminListener.returnInfoUserFirebase(new User(DataHolder.MyDataHolder.currentUserNickName,uri.toString()));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    } else {

                    }
                } else {

                }
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

