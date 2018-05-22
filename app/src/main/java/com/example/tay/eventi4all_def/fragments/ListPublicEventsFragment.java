package com.example.tay.eventi4all_def.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tay.eventi4all_def.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListPublicEventsFragment extends Fragment {

    private IListPublicEventsFragmentListener IListPublicEventsFragmentListener;
    private ListPublicEventsFragmentEvents listPublicEventsFragmentEvents;


    public ListPublicEventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_public_events, container, false);
    }

    public IListPublicEventsFragmentListener getIListPublicEventsFragmentListener() {
        return IListPublicEventsFragmentListener;
    }

    public void setIListPublicEventsFragmentListener(IListPublicEventsFragmentListener IListPublicEventsFragmentListener) {
        this.IListPublicEventsFragmentListener = IListPublicEventsFragmentListener;
    }

    public ListPublicEventsFragmentEvents getListPublicEventsFragmentEvents() {
        return listPublicEventsFragmentEvents;
    }

    public void setListPublicEventsFragmentEvents(ListPublicEventsFragmentEvents listPublicEventsFragmentEvents) {
        this.listPublicEventsFragmentEvents = listPublicEventsFragmentEvents;
    }
}
