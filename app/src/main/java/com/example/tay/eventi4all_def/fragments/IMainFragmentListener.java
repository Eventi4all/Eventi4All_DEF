package com.example.tay.eventi4all_def.fragments;

import java.util.Map;

public interface IMainFragmentListener {
    public void callLogoutMainActivity();
    public void saveEventFirebase(Map<String, Object> event);
    public void openCreateEventFragment();
}
