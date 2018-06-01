package com.example.tay.eventi4all_def.fragments;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.tay.eventi4all_def.entity.Event;

import java.util.ArrayList;

public class ListPublicEventsFragmentEvents implements SwipeRefreshLayout.OnRefreshListener{
    private ListPublicEventsFragment listPublicEventsFragment;


    public ListPublicEventsFragmentEvents(ListPublicEventsFragment listPublicEventsFragment) {
        this.listPublicEventsFragment =listPublicEventsFragment;
    }


    public void callGetPublicEvents(){
        this.listPublicEventsFragment.getIListPublicEventsFragmentListener().callGetPublicEvents();
    }

    public void setEventsInListAdapter(ArrayList<Event> events){
        this.listPublicEventsFragment.getArrEvents().clear();
        this.listPublicEventsFragment.getArrEvents().addAll(events);
        this.listPublicEventsFragment.getListAdapter().notifyDataSetChanged();
        listPublicEventsFragment.getRefreshLayout().setRefreshing(false);

    }


    public ListPublicEventsFragment getListPublicEventsFragment() {
        return listPublicEventsFragment;
    }

    public void setListPublicEventsFragment(ListPublicEventsFragment listPublicEventsFragment) {
        this.listPublicEventsFragment = listPublicEventsFragment;
    }

    @Override
    public void onRefresh() {
        this.callGetPublicEvents();


    }


}
