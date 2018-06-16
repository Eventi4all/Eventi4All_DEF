package com.example.tay.eventi4all_def;


import android.net.Uri;

import com.example.tay.eventi4all_def.Firebase.FirebaseAdmin;
import com.example.tay.eventi4all_def.entity.Event;
import com.example.tay.eventi4all_def.entity.User;

import java.util.ArrayList;

/**
 * Created by tay on 25/11/17.
 */

public class DataHolder {



    public static class MyDataHolder {
        public static Uri imgUri = null;
        public static String currentUserNickName;
        public static String userImgProfile;
        public static String token;
        public static ArrayList<User> notificationUsers;
        public static FirebaseAdmin firebaseAdmin;
        public static boolean belong;
        public static Event event;
        public static int requesCode;
    }


}