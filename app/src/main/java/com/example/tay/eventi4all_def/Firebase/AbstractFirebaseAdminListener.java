package com.example.tay.eventi4all_def.Firebase;

import com.example.tay.eventi4all_def.entity.Card;
import com.example.tay.eventi4all_def.entity.Event;
import com.example.tay.eventi4all_def.entity.User;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractFirebaseAdminListener implements IFirebaseAdminListener{

    private IFirebaseAdminListener iFirebaseAdminListener;

    @Override
    public boolean createNewEvent() {
        return false;
    }

    @Override
    public boolean deleteExistEvent() {
        return false;
    }

    @Override
    public void logout(boolean isLogout) {
    }

    @Override
    public void checkUserExist(boolean isUserExist) {

    }

    public IFirebaseAdminListener getiFirebaseAdminListener() {
        return iFirebaseAdminListener;
    }

    public void setiFirebaseAdminListener(IFirebaseAdminListener iFirebaseAdminListener) {
        this.iFirebaseAdminListener = iFirebaseAdminListener;
    }

    @Override
    public void insertDocumentIsOK(boolean isInsertok, String result) {

    }

    @Override
    public void foundNickName(HashMap<String,User> users) {

    }

    @Override
    public void insertEventOk(boolean isInsertOk, String result) {

    }

    @Override
    public void returnEventsFirebase(ArrayList<Event> events, String destination) {

    }

    @Override
    public void returnInfoUserFirebase(User user) {

    }

    @Override
    public void pushNotification() {

    }

    @Override
    public void giveBackCards(ArrayList<Card> arrCards) {

    }

    @Override
    public void successDeleteInvitation(boolean isDelete, int position) {

    }

    @Override
    public void addOkNewAssistant(boolean isOk, int position, String uuid) {

    }
}
