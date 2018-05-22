package com.example.tay.eventi4all_def.fragments;

import com.example.tay.eventi4all_def.entity.Event;
import com.example.tay.eventi4all_def.entity.User;

import java.util.ArrayList;
import java.util.Map;

public interface IMainFragmentListener {
    public void callLogoutMainActivity();
    public void getEvents(String events);
    public void getUserInfo();
    public void openCreateEventsFragment();

}
