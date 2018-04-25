package com.example.tay.eventi4all_def.Firebase;

public interface IFirebaseAdminListener {
    public boolean createNewEvent();
    public boolean deleteExistEvent();
    public void logout(boolean isLogout);
    public void checkUserExist(boolean isUserExist);
    public void insertDocumentIsOK(boolean isInsertok, String result);
}
