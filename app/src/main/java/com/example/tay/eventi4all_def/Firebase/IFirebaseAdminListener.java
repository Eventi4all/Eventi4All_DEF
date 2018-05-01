package com.example.tay.eventi4all_def.Firebase;


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
}
