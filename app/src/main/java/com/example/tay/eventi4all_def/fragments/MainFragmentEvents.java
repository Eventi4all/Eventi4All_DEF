package com.example.tay.eventi4all_def.fragments;

import android.view.View;
import android.widget.AdapterView;

import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.adapter.ListAdapterMyCreatedEvents;
import com.example.tay.eventi4all_def.entity.Event;

import java.util.ArrayList;

public class MainFragmentEvents implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    private MainFragment mainFragment;

    public MainFragmentEvents(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    public MainFragment getMainFragment() {
        return mainFragment;
    }

    public void setMainFragment(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLo) {
            this.mainFragment.getiMainFragmentListener().callLogoutMainActivity();
        }else if(v.getId() == R.id.floatingActionButton){

        }else if(v.getId() == R.id.fabOptions){

        }

    }

    public void getEvents(int position) {
        if(position==0){
            this.mainFragment.getiMainFragmentListener().getEvents("createEvents");
        }else if(position==1){
            this.mainFragment.getiMainFragmentListener().getEvents("allAssistEvents");
        }


    }
    public void setEventsInListAdapter(ArrayList<Event> events){
        this.mainFragment.getArrEvents().clear();
        this.mainFragment.getArrEvents().addAll(events);
        this.mainFragment.getListAdapter().notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.getEvents(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getUserInfo(){
        this.getMainFragment().getiMainFragmentListener().getUserInfo();
    }
}