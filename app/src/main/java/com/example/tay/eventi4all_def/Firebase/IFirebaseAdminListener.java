package com.example.tay.eventi4all_def.Firebase;


import com.example.tay.eventi4all_def.entity.Card;
import com.example.tay.eventi4all_def.entity.Event;
import com.example.tay.eventi4all_def.entity.Photo;
import com.example.tay.eventi4all_def.entity.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface IFirebaseAdminListener {
    public boolean createNewEvent();
    public boolean deleteExistEvent();
    public void logout(boolean isLogout);
    public void checkUserExist(boolean isUserExist);
    public void insertDocumentIsOK(boolean isInsertok, String result);
    public void foundNickName(HashMap<String,User> users);
    public void insertEventOk(boolean isInsertOk, String result);
    public void returnEventsFirebase(ArrayList<Event> events, String destination);
    public void returnInfoUserFirebase(User user);
    public void pushNotification();
    public void giveBackCards(ArrayList<Card> arrCards);
    public void successDeleteInvitation(boolean isDelete, int position);
    public void addOkNewAssistant(boolean isOk, int position, String uuid);
    public void returnPhotosOfEvent(ArrayList<Photo> arrPhotos);
    public void insertPhotoOfEventOk(boolean isOk, String response);

}
