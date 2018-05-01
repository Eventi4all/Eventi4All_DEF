package com.example.tay.eventi4all_def.fragments;

import android.view.View;
import com.example.tay.eventi4all_def.R;

public class MainFragmentEvents implements View.OnClickListener {

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
        }

    }
}