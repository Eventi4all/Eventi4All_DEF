package com.example.tay.eventi4all_def.fragments;

import com.example.tay.eventi4all_def.entity.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface ICreateEventFragmentListener {
    public void getUsersFb(CharSequence sequence);
    public void saveEventInFirebase(HashMap<String, Object> event);

   public void hideCreateEventDialogFragment();
}
