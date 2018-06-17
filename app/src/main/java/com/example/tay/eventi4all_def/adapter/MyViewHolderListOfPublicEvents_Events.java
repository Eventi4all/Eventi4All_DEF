package com.example.tay.eventi4all_def.adapter;

import android.view.View;

public class MyViewHolderListOfPublicEvents_Events implements View.OnClickListener{
    private MyViewHolderListOfPublicEvents myViewHolderListOfPublicEvents;

    public MyViewHolderListOfPublicEvents_Events(MyViewHolderListOfPublicEvents myViewHolderListOfPublicEvents) {
        this.myViewHolderListOfPublicEvents = myViewHolderListOfPublicEvents;
    }

    public MyViewHolderListOfPublicEvents getMyViewHolderListOfPublicEvents() {
        return myViewHolderListOfPublicEvents;
    }

    public void setMyViewHolderListOfPublicEvents(MyViewHolderListOfPublicEvents myViewHolderListOfPublicEvents) {
        this.myViewHolderListOfPublicEvents = myViewHolderListOfPublicEvents;
    }

    @Override
    public void onClick(View view) {
        //Al pinchar en una celda (viewholder) llamaremos al escuchador que es el listOfPublicEvents_Events
        this.myViewHolderListOfPublicEvents.getListAdapterListOfPublicEventsListener().accesToEvent(this.getMyViewHolderListOfPublicEvents());
    }
}
