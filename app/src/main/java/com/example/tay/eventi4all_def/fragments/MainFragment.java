package com.example.tay.eventi4all_def.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.adapter.ListAdapterMyCreatedEvents;
import com.example.tay.eventi4all_def.entity.Event;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private MainFragmentEvents mainFragmentEvents;
    private IMainFragmentListener iMainFragmentListener;
    private Button btnLogout;
    private ListAdapterMyCreatedEvents listAdapter;
    private ArrayList<Event> arrEvents;
    private RecyclerView myList;
    private Spinner spEvents;
    private ImageView userImgProfile;
    private TextView userTxtNickname;
    private FloatingActionButton fab;
    private FloatingActionButton fabOptions;




    public MainFragment() {
        this.arrEvents=new ArrayList<Event>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mainFragmentEvents = new MainFragmentEvents(this);
        this.userImgProfile = v.findViewById(R.id.profile_image);
        this.userTxtNickname = v.findViewById(R.id.txtNickNameProfile);
        this.btnLogout = v.findViewById(R.id.btnLo);
        this.btnLogout.setOnClickListener(this.mainFragmentEvents);
        this.listAdapter = new ListAdapterMyCreatedEvents(arrEvents,getActivity());
        myList = v.findViewById(R.id.myListOfEvents);
        myList.setLayoutManager(new GridLayoutManager(getContext(),1));



        this.spEvents = v.findViewById(R.id.spEvents);
        String[] items = new String[]{"Mostrar solamente eventos creados.", "Mostrar todos los eventos a los que pertenezco."};
        ArrayAdapter<String> adapterSp = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spEvents.setAdapter(adapterSp);
        spEvents.setSelection(0);
        spEvents.setOnItemSelectedListener(this.mainFragmentEvents);
        //SETEO ADAPTER IMPORTANTÍSIMO !!
        myList.setAdapter(listAdapter);
        this.mainFragmentEvents.getUserInfo();



        fab = v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(this.mainFragmentEvents);
        fabOptions = v.findViewById(R.id.fabOptions);
        fabOptions.setOnClickListener(this.mainFragmentEvents);

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

    public ListAdapterMyCreatedEvents getListAdapter() {
        return listAdapter;
    }

    public void setListAdapter(ListAdapterMyCreatedEvents listAdapter) {
        this.listAdapter = listAdapter;
    }

    public ArrayList<Event> getArrEvents() {
        return arrEvents;
    }

    public void setArrEvents(ArrayList<Event> arrEvents) {
        this.arrEvents = arrEvents;
    }

    public RecyclerView getMyList() {
        return myList;
    }

    public void setMyList(RecyclerView myList) {
        this.myList = myList;
    }

    public Spinner getSpEvents() {
        return spEvents;
    }

    public void setSpEvents(Spinner spEvents) {
        this.spEvents = spEvents;
    }

    public ImageView getUserImgProfile() {
        return userImgProfile;
    }

    public void setUserImgProfile(ImageView userImgProfile) {
        this.userImgProfile = userImgProfile;
    }

    public TextView getUserTxtNickname() {
        return userTxtNickname;
    }

    public void setUserTxtNickname(TextView userTxtNickname) {
        this.userTxtNickname = userTxtNickname;
    }
}
