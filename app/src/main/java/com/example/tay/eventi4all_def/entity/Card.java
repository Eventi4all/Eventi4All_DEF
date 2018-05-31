package com.example.tay.eventi4all_def.entity;

public class Card {
    public String createBy;
    public String addressee;
    public String eventTitle;
    public String imgEvent;
    public String uuid;


    public Card() {
    }

    public Card(String createBy, String addressee, String eventTitle, String imgEvent, String uuid) {
        this.createBy = createBy;
        this.addressee = addressee;
        this.eventTitle = eventTitle;
        this.imgEvent = imgEvent;
        this.uuid=uuid;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getImgEvent() {
        return imgEvent;
    }

    public void setImgEvent(String imgEvent) {
        this.imgEvent = imgEvent;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
