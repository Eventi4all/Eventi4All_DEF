package com.example.tay.eventi4all_def.fragments;

import android.view.View;
import com.example.tay.eventi4all_def.R;

public class MainFragmentEvents implements View.OnClickListener{

    private MainFragment mainFragment;
    private IMainFragmentListener iMainFragmentListener;

    public MainFragmentEvents(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    public MainFragment getMainFragment() {
        return mainFragment;
    }

    public void setMainFragment(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    public IMainFragmentListener getiMainFragmentListener() {
        return iMainFragmentListener;
    }

    public void setiMainFragmentListener(IMainFragmentListener iMainFragmentListener) {
        this.iMainFragmentListener = iMainFragmentListener;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.btnLogout){
            this.iMainFragmentListener.callLogoutMainActivity();
        }
    }
}
