package com.example.tay.eventi4all_def.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;



import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.adapter.ListAdapter;
import com.example.tay.eventi4all_def.entity.User;


import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends Fragment {
    private ICreateEventFragmentListener iCreateEventFragmentListener;
    private CreateEventFragmentEvents createEventFragmentEvents;
    private EditText txtEventName;
    private AutoCompleteTextView myFriends;
    private ArrayAdapter adapter;
    private ListAdapter listAdapter;
    private ArrayList<User> arrUsers;
    private Button btnAdd;
    private RecyclerView myList;


    public CreateEventFragment() {
        arrUsers=new ArrayList<User>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_event, container, false);
        this.listAdapter = new ListAdapter(arrUsers,getActivity());
        myList = (RecyclerView) v.findViewById(R.id.listOfUsers);
        myList.setLayoutManager(new GridLayoutManager(getContext(),2));
        this.btnAdd = v.findViewById(R.id.btnAddNewFriend);
        adapter = new ArrayAdapter(getActivity(), simple_list_item_1);
        this.createEventFragmentEvents = new CreateEventFragmentEvents(this);
        this.btnAdd.setOnClickListener(this.createEventFragmentEvents);
        this.txtEventName = v.findViewById(R.id.txtNameEvent);
        this.myFriends = v.findViewById(R.id.txtNameFriend);
        this.myFriends.setAdapter(adapter);
        this.myFriends.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createEventFragmentEvents.getAllUsers(s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


       this.myList.setAdapter(listAdapter);
        return v;
    }


    public ICreateEventFragmentListener getiCreateEventFragmentListener() {
        return iCreateEventFragmentListener;
    }

    public void setiCreateEventFragmentListener(ICreateEventFragmentListener iCreateEventFragmentListener) {
        this.iCreateEventFragmentListener = iCreateEventFragmentListener;
    }

    public CreateEventFragmentEvents getCreateEventFragmentEvents() {
        return createEventFragmentEvents;
    }

    public void setCreateEventFragmentEvents(CreateEventFragmentEvents createEventFragmentEvents) {
        this.createEventFragmentEvents = createEventFragmentEvents;
    }

    public EditText getTxtEventName() {
        return txtEventName;
    }

    public void setTxtEventName(EditText txtEventName) {
        this.txtEventName = txtEventName;
    }

    public AutoCompleteTextView getMyFriends() {
        return myFriends;
    }

    public void setMyFriends(AutoCompleteTextView myFriends) {
        this.myFriends = myFriends;
    }

    public ArrayAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ArrayAdapter adapter) {
        this.adapter = adapter;
    }

    public ArrayList<User> getArrUsers() {
        return arrUsers;
    }

    public void setArrUsers(ArrayList<User> arrUsers) {
        this.arrUsers = arrUsers;
    }

    public ListAdapter getListAdapter() {
        return listAdapter;
    }

    public void setListAdapter(ListAdapter listAdapter) {
        this.listAdapter = listAdapter;
    }


}
