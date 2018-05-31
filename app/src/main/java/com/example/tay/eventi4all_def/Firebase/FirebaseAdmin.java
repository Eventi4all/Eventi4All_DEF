package com.example.tay.eventi4all_def.Firebase;

import android.app.Activity;
import android.net.Uri;

import android.support.annotation.NonNull;


import com.example.tay.eventi4all_def.DataHolder;
import com.example.tay.eventi4all_def.entity.Card;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.io.File;
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
            Task uploadTask = (Task) createFile("images/profile/", "upload", nameImage).get("uploadTask");
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    abstractFirebaseAdminListener.insertDocumentIsOK(false, "Firebase Exception");
                }
            }).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    document.put("imgProfile", (String) createFile("images/profile/", "getUrl", nameImage).get("urlComplete"));
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
        if (action.equals("upload")) {
            Uri file = DataHolder.MyDataHolder.imgUri;
            final StorageReference mountainImagesRef = storageRef.child(url + nameImg);
            result.put("uploadTask", mountainImagesRef.putFile(file));
            return result;
        } else {
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
                    if (!data.getData().get("nickname").toString().equals(DataHolder.MyDataHolder.currentUserNickName)) {
                        storageRef.child(data.getData().get("imgProfile").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                User auxUser = new User(data.getData().get("nickname").toString(), uri.toString());
                                auxUser.setToken(data.getData().get("token").toString());
                                users.put(data.getData().get("nickname").toString(), auxUser);
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

    public void insertEventInFirebase(HashMap<String, Object> event, Uri uriQr) {

        try {
            String nameImage = UUID.randomUUID().toString() + ".jpg";
            Task uploadTask = (Task) createFile("images/events/", "upload", nameImage).get("uploadTask");

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    abstractFirebaseAdminListener.insertEventOk(false, "Firebase Exception");
                }
            }).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    String urlQr = "images/QR/" + event.get("uuid").toString() + ".jpg";
                    StorageReference mountainImagesRefQr = storageRef.child(urlQr);
                    Task uploadTaskQr = mountainImagesRefQr.putFile(uriQr);
                    uploadTaskQr.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            abstractFirebaseAdminListener.insertEventOk(false, "Firebase Exception");
                        }
                    }).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            event.put("coverImg", (String) createFile("images/events/", "getUrl", nameImage).get("urlComplete"));
                            event.put("qr", urlQr);
                            db.collection("events").document(event.get("uuid").toString()).set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    System.out.println("insert ok");
                                    abstractFirebaseAdminListener.insertEventOk(true, "Document Insert");
                                    if (DataHolder.MyDataHolder.notificationUsers.size() > 0) {
                                        System.out.println("dentro del piush");
                                        abstractFirebaseAdminListener.pushNotification();
                                    }


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


                }


            });
        } catch (Exception e)

        {
            abstractFirebaseAdminListener.insertDocumentIsOK(false, "Firebase Exception");
        }


    }


    public void getEvents(String events) {
        String destination = "";
        final HashMap<String, Event> hsEvents = new HashMap<>();
        Query query = null;

        if (events.equals("createEvents")) {
            query = db.collection("events").whereEqualTo("admin", DataHolder.MyDataHolder.currentUserNickName);
            destination = "mainFragment";

        } else if (events.equals("allAssistEvents")) {
            query = db.collection("events").whereEqualTo("assistants." + DataHolder.MyDataHolder.currentUserNickName, true);
            destination = "mainFragment";
        } else if (events.equals("publicEvents")) {

            query = db.collection("events").whereEqualTo("private", false);

            destination = "listPublicEventsFragment";
        }


        String finalDestination = destination;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot document : task.getResult()) {
                    storageRef.child(document.getData().get("coverImg").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Event event = new Event(document.get("title").toString(), (boolean) document.get("private"), document.get("limit").toString());
                            event.setCreateAt(document.get("createAt").toString());
                            event.setUrlCover(uri.toString());
                            event.setUuid(document.get("uuid").toString());

                            hsEvents.put(document.get("uuid").toString(), event);
                            if (finalDestination.equals("listPublicEventsFragment")) {
                                Query queryAux = queryAux = db.collection("events").whereEqualTo("assistants." + DataHolder.MyDataHolder.currentUserNickName, true);
                                queryAux.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for (DocumentSnapshot document : task.getResult()) {

                                            if (hsEvents.containsKey(document.get("uuid").toString())) {
                                                hsEvents.remove(document.get("uuid").toString());

                                            }


                                        }
                                        abstractFirebaseAdminListener.returnEventsFirebase(new ArrayList<Event>(hsEvents.values()), finalDestination);
                                    }

                                });

                            } else {
                                abstractFirebaseAdminListener.returnEventsFirebase(new ArrayList<Event>(hsEvents.values()), finalDestination);
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("Exception: " + e.getMessage());

                        }
                    });


                }


            }


        });
        query.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Onfailure: " + e.getMessage());
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
                                User user = new User(DataHolder.MyDataHolder.currentUserNickName, uri.toString());
                                if (document.getData().get("token") != null) {
                                    user.setToken(document.getData().get("token").toString());
                                }
                                abstractFirebaseAdminListener.returnInfoUserFirebase(user);
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


    public void insertDeviceToken(String token) {
        HashMap<String, String> hsToken = new HashMap<String, String>();
        hsToken.put("token", token);
        //Hacemos un merge apra que no borre el contenido de este usuario y solamente añada el token cada vez que cambie
        Task<Void> docRef = db.collection("users").document(this.getUidUser()).set(hsToken, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }

    public void getInvitationsFirebase() {
        Query query = db.collection("events").whereEqualTo("invitations." + DataHolder.MyDataHolder.currentUserNickName, false);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Card> arrCards = new ArrayList<>();
                System.out.println("size task cards: " + task.getResult().size());
                for (DocumentSnapshot document : task.getResult()) {
                    storageRef.child(document.getData().get("coverImg").toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Card card = new Card(document.getData().get("admin").toString(), DataHolder.MyDataHolder.currentUserNickName, document.getData().get("title").toString(), uri.toString(), document.getData().get("uuid").toString());
                            arrCards.add(card);
                            abstractFirebaseAdminListener.giveBackCards(arrCards);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

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


    public void deleteInvitation(String uuid, int position) {
        DocumentReference docRef = db.collection("events").document(uuid);

// Remove the 'capital' field from the document
        Map<String, Object> updates = new HashMap<>();
        updates.put("invitations." + DataHolder.MyDataHolder.currentUserNickName, FieldValue.delete());

        docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                abstractFirebaseAdminListener.successDeleteInvitation(true, position);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                abstractFirebaseAdminListener.successDeleteInvitation(false, position);
            }
        });

    }

    public void addAssistant(String uuid, int position) {
        HashMap<String,Object> addAssisantIntoEvent = new HashMap<String, Object>();
        HashMap<String,Boolean> newAssistant = new HashMap<String,Boolean>();
        newAssistant.put(DataHolder.MyDataHolder.currentUserNickName,true);
        addAssisantIntoEvent.put("assistants",newAssistant);
        //Hacemos un merge apra que no borre el contenido de este usuario y solamente añada el token cada vez que cambie
        Task<Void> docRef = db.collection("events").document(uuid).set(addAssisantIntoEvent, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                abstractFirebaseAdminListener.addOkNewAssistant(true,position, uuid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                abstractFirebaseAdminListener.addOkNewAssistant(false,position, uuid);
            }
        });

    }
}

