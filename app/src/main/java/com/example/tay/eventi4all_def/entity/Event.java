package com.example.tay.eventi4all_def.entity;

import java.util.HashMap;

public class Event {
    public String title;
    public boolean eventPrivate;
    public HashMap<String,User> users;
    public String until;
    public String createAt;
    public String urlCover;
    public String uuid;



    public Event(){

    }

    public Event(String title, boolean eventPrivate, String until) {
        this.title = title;
        this.eventPrivate = eventPrivate;
        this.users = new HashMap<>();
        this.until = until;
        this.createAt="";
        this.urlCover="";
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isEventPrivate() {
        return eventPrivate;
    }

    public void setEventPrivate(boolean eventPrivate) {
        this.eventPrivate = eventPrivate;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUrlCover() {
        return urlCover;
    }

    public void setUrlCover(String urlCover) {
        this.urlCover = urlCover;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
