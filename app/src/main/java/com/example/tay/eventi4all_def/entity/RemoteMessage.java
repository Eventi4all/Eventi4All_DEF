package com.example.tay.eventi4all_def.entity;

/**
 * Created by tay on 29/1/18.
 */

public class RemoteMessage {
    /*
    Clase para la creación de objetos tipo RemoteMessage para luego usarlos para agruparlos
     */

    private String title;
    private String message;
    private String urlImage;


    public RemoteMessage() {
    }

    public RemoteMessage(String title, String message) {
        this.title = title;
        this.message = message;
    }


    public RemoteMessage(String title, String message, String urlImage) {
        this.title = title;
        this.message = message;
        this.urlImage = urlImage;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }


}
