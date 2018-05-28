package com.example.tay.eventi4all_def.entity;

public class User {
   public String nickName;
   public String urlImgProfile;
    public String token;

    public User() {
    }

    public User(String nickName, String urlImgProfile){
        this.nickName=nickName;
        this.urlImgProfile=urlImgProfile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUrlImgProfile() {
        return urlImgProfile;
    }

    public void setUrlImgProfile(String urlImgProfile) {
        this.urlImgProfile = urlImgProfile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
