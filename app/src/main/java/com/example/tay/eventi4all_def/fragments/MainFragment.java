package com.example.tay.eventi4all_def.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tay.eventi4all_def.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private MainFragmentEvents mainFragmentEvents;
    private IMainFragmentListener iMainFragmentListener;
    private Button btnLogout;
    private Button btnCreateEvent;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mainFragmentEvents = new MainFragmentEvents(this);
        this.btnLogout = v.findViewById(R.id.btnLo);
        this.btnLogout.setOnClickListener(this.mainFragmentEvents);
        this.btnCreateEvent = v.findViewById(R.id.btnCreateEvent);
        this.btnCreateEvent.setOnClickListener(this.mainFragmentEvents);
        return v;
    }

    public MainFragmentEvents getMainFragmentEvents() {
        return mainFragmentEvents;
    }

    public void setMainFragmentEvents(MainFragmentEvents mainFragmentEvents) {
        this.mainFragmentEvents = mainFragmentEvents;
    }

    public IMainFragmentListener getiMainFragmentListener() {
        return iMainFragmentListener;
    }

    public void setiMainFragmentListener(IMainFragmentListener iMainFragmentListener) {
        this.iMainFragmentListener = iMainFragmentListener;
    }

    public Button getBtnLogout() {
        return btnLogout;
    }

    public void setBtnLogout(Button btnLogout) {
        this.btnLogout = btnLogout;
    }



}
