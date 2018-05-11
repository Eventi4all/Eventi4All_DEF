package com.example.tay.eventi4all_def.fragments;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
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
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


import com.example.tay.eventi4all_def.DataHolder;
import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.adapter.ListAdapter;
import com.example.tay.eventi4all_def.entity.User;


import java.net.URI;
import java.util.ArrayList;

import at.markushi.ui.CircleButton;
import es.dmoral.toasty.Toasty;

import static android.R.layout.simple_list_item_1;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends Fragment {
    private ICreateEventFragmentListener iCreateEventFragmentListener;
    private IGalleryAndCapturePhotoListener iGalleryAndCapturePhotoListener;
    private CreateEventFragmentEvents createEventFragmentEvents;
    private EditText txtEventName;
    private AutoCompleteTextView myFriends;
    private ArrayAdapter adapter;
    private ListAdapter listAdapter;
    private ArrayList<User> arrUsers;
    private CircleButton btnAdd;
    private RecyclerView myList;
    private Button btnCreateEvent;
    private Spinner spPax;
    private CheckBox checkboxPrivate;
    private ImageView eventImgMain;


    public CreateEventFragment() {

        arrUsers=new ArrayList<User>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_event, container, false);
        this.createEventFragmentEvents = new CreateEventFragmentEvents(this);
        this.listAdapter = new ListAdapter(arrUsers,getActivity(),this);
        myList = (RecyclerView) v.findViewById(R.id.listOfUsers);
        myList.setLayoutManager(new GridLayoutManager(getContext(),2));
        this.btnAdd = v.findViewById(R.id.btnAddNewFriend);
        this.btnCreateEvent = v.findViewById(R.id.buttonNewEvent);
        this.btnCreateEvent.setOnClickListener(this.createEventFragmentEvents);
        this.checkboxPrivate = v.findViewById(R.id.checkPrivate);
        this.eventImgMain = v.findViewById(R.id.eventImgMain);
        this.eventImgMain.setOnClickListener(this.createEventFragmentEvents);
        DataHolder.MyDataHolder.imgUri= Uri.parse("android.resource://com.example.tay.eventi4all_def/" + R.drawable.com_facebook_profile_picture_blank_square);


        this.spPax = v.findViewById(R.id.spPax);

        String[] items = new String[]{"15", "20", "50", "150", "Sin límites"};
        ArrayAdapter<String> adapterSp = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spPax.setAdapter(adapterSp);



        adapter = new ArrayAdapter(getActivity(), simple_list_item_1);

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




        Toasty.Config.getInstance().setErrorColor(getResources().getColor(R.color.colorRed)).setSuccessColor(getResources().getColor(R.color.colorGreen)).setTextColor(getResources().getColor(R.color.tw__solid_white)).apply(); // optional.apply();

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

    public CircleButton getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(CircleButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    public RecyclerView getMyList() {
        return myList;
    }

    public void setMyList(RecyclerView myList) {
        this.myList = myList;
    }

    public Button getBtnCreateEvent() {
        return btnCreateEvent;
    }

    public void setBtnCreateEvent(Button btnCreateEvent) {
        this.btnCreateEvent = btnCreateEvent;
    }

    public Spinner getSpPax() {
        return spPax;
    }

    public void setSpPax(Spinner spPax) {
        this.spPax = spPax;
    }

    public CheckBox getCheckboxPrivate() {
        return checkboxPrivate;
    }

    public void setCheckboxPrivate(CheckBox checkboxPrivate) {
        this.checkboxPrivate = checkboxPrivate;
    }

    public IGalleryAndCapturePhotoListener getiGalleryAndCapturePhotoListener() {
        return iGalleryAndCapturePhotoListener;
    }

    public void setiGalleryAndCapturePhotoListener(IGalleryAndCapturePhotoListener iGalleryAndCapturePhotoListener) {
        this.iGalleryAndCapturePhotoListener = iGalleryAndCapturePhotoListener;
    }

    public ImageView getEventImgMain() {
        return eventImgMain;
    }

    public void setEventImgMain(ImageView eventImgMain) {
        this.eventImgMain = eventImgMain;
    }
}
