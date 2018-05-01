package com.example.tay.eventi4all_def.entity;

import java.util.HashMap;

public class Event {
    public String title;
    public boolean eventPrivate;
    public HashMap<String,User> users;
    public String until;



    public Event(){

    }

    public Event(String title, boolean eventPrivate, String until) {
        this.title = title;
        this.eventPrivate = eventPrivate;
        this.users = new HashMap<>();
        this.until = until;
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }
}
