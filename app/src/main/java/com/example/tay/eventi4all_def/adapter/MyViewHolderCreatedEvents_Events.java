package com.example.tay.eventi4all_def.adapter;

import android.view.View;

public class MyViewHolderCreatedEvents_Events implements View.OnClickListener{

    private MyViewHolderCreatedEvents myViewHolderCreatedEvents;

    public MyViewHolderCreatedEvents_Events(MyViewHolderCreatedEvents myViewHolderCreatedEvents) {
        this.myViewHolderCreatedEvents = myViewHolderCreatedEvents;
    }

    public MyViewHolderCreatedEvents getMyViewHolderCreatedEvents() {
        return myViewHolderCreatedEvents;
    }

    public void setMyViewHolderCreatedEvents(MyViewHolderCreatedEvents myViewHolderCreatedEvents) {
        this.myViewHolderCreatedEvents = myViewHolderCreatedEvents;
    }

    @Override
    public void onClick(View view) {

       //Al pinchar en una celda (viewholder) llamaremos al escuchador que es el mainFragmentEvents
        this.myViewHolderCreatedEvents.getListAdapterCreatedEventsListener().openEvent(this.getMyViewHolderCreatedEvents());

    }
}
