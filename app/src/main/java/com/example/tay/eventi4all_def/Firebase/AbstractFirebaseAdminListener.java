package com.example.tay.eventi4all_def.Firebase;

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
}
