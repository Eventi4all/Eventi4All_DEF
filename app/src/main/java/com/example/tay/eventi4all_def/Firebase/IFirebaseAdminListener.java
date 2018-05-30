package com.example.tay.eventi4all_def.Firebase;


import com.example.tay.eventi4all_def.entity.Event;
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

}
