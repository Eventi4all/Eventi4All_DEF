package com.example.tay.eventi4all_def.entity;

public class Photo {
    public String urlPhoto;
    public String titlePhoto;
    public String createdBy;
    public String uuidEvent;

    public Photo() {
    }

    public Photo(String urlPhoto, String titlePhoto, String createdBy, String uuidEvent) {
        this.urlPhoto = urlPhoto;
        this.titlePhoto = titlePhoto;
        this.createdBy = createdBy;
        this.uuidEvent=uuidEvent;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getTitlePhoto() {
        return titlePhoto;
    }

    public void setTitlePhoto(String titlePhoto) {
        this.titlePhoto = titlePhoto;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUuidEvent() {
        return uuidEvent;
    }

    public void setUuidEvent(String uuidEvent) {
        this.uuidEvent = uuidEvent;
    }
}
