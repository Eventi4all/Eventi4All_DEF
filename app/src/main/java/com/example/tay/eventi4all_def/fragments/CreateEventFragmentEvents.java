package com.example.tay.eventi4all_def.fragments;

import android.view.View;

import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.entity.Event;
import com.example.tay.eventi4all_def.entity.User;

import java.util.HashMap;

public class CreateEventFragmentEvents implements View.OnClickListener {

    private CreateEventFragment createEventFragment;
    private HashMap<String,User> users;

    public CreateEventFragmentEvents(CreateEventFragment createEventFragment) {
        this.createEventFragment = createEventFragment;
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnAddNewFriend){
            this.createEventFragment.getArrUsers().add(users.get(this.createEventFragment.getMyFriends().getText().toString().trim()));
            this.createEventFragment.getListAdapter().notifyDataSetChanged();
        }else if(v.getId()== R.id.btnCreateEvent){
            new Event(this.createEventFragment.getTxtEventName().getText().toString(),this.createEventFragment.getCheckboxPrivate().isChecked(),this.createEventFragment.getSpPax().getSelectedItem().toString());
        }

    }


    public void getAllUsers(CharSequence s) {
        this.createEventFragment.getiCreateEventFragmentListener().getUsersFb(s);


    }
    public void foundNickname(HashMap<String,User> users){
        this.users=users;
        this.createEventFragment.getAdapter().clear();
        this.createEventFragment.getAdapter().addAll(users.keySet());
        this.createEventFragment.getAdapter().notifyDataSetChanged();
    }
}