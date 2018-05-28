package com.example.tay.eventi4all_def.fragments;

import com.example.tay.eventi4all_def.entity.Event;

import java.util.ArrayList;

public class ListPublicEventsFragmentEvents {
    private ListPublicEventsFragment listPublicEventsFragment;


    public ListPublicEventsFragmentEvents(ListPublicEventsFragment listPublicEventsFragment) {
        this.listPublicEventsFragment =listPublicEventsFragment;
    }


    public void callGetPublicEvents(){
        this.listPublicEventsFragment.getIListPublicEventsFragmentListener().callGetPublicEvents();
    }

    public void setEventsInListAdapter(ArrayList<Event> events){
        System.out.println("LLegan los siguientes eventos: " + events.size());
        this.listPublicEventsFragment.getArrEvents().clear();
        this.listPublicEventsFragment.getArrEvents().addAll(events);
        this.listPublicEventsFragment.getListAdapter().notifyDataSetChanged();

    }

    public ListPublicEventsFragment getListPublicEventsFragment() {
        return listPublicEventsFragment;
    }

    public void setListPublicEventsFragment(ListPublicEventsFragment listPublicEventsFragment) {
        this.listPublicEventsFragment = listPublicEventsFragment;
    }
}
