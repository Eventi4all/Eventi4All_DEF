package com.example.tay.eventi4all_def.fragments;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.entity.Event;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import java.util.ArrayList;

public class MainFragmentEvents implements View.OnClickListener,AdapterView.OnItemSelectedListener, OnMenuSelectedListener {

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
        /*if (v.getId() == R.id.btnLo) {
            animBtnEffect = AnimationUtils.loadAnimation(this.getMainFragment().getActivity(), R.anim.alpha);
           // this.mainFragment.getBtnLogout().startAnimation(animBtnEffect);
            //this.mainFragment.getiMainFragmentListener().callLogoutMainActivity();
        }else if(v.getId() == R.id.floatingActionButton){

        }else if(v.getId() == R.id.btnOptions){


        }
        */

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

    @Override
    public void onMenuSelected(int index) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mainFragment.getArrIcons()[index].equals("Logout")){
                    mainFragment.getiMainFragmentListener().callLogoutMainActivity();

                }else if(mainFragment.getArrIcons()[index].equals("EditProfile")){

                }

            }
        }, 1000);


    }
}