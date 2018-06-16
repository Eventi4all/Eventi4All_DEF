package com.example.tay.eventi4all_def.fragments;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.tay.eventi4all_def.DataHolder;
import com.example.tay.eventi4all_def.MainActivity;
import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.adapter.ListAdapter;
import com.example.tay.eventi4all_def.entity.User;
import com.labo.kaji.swipeawaydialog.SwipeAwayDialogFragment;


import java.util.ArrayList;

import at.markushi.ui.CircleButton;
import es.dmoral.toasty.Toasty;

import static android.R.layout.simple_list_item_1;

public class CustomDialogFragment_CreateEvents extends SwipeAwayDialogFragment{
    private static final String TAG = "MyCustomDialog";
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
    private Context context;
    private ImageView btnBack;

    public CustomDialogFragment_CreateEvents() {
        arrUsers = new ArrayList<User>();
        this.context = getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_fragment_create_events,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        this.createEventFragmentEvents = new CreateEventFragmentEvents(this);
        this.listAdapter = new ListAdapter(arrUsers,this.getActivity().getApplicationContext(),this);

        myList = (RecyclerView) v.findViewById(R.id.listOfUsers);
        myList.setLayoutManager(new GridLayoutManager(this.getActivity().getApplicationContext(),2));
        this.btnAdd = v.findViewById(R.id.btnAddNewFriend);
        this.btnCreateEvent = v.findViewById(R.id.buttonNewEvent);
        this.btnCreateEvent.setOnClickListener(this.createEventFragmentEvents);

        this.btnBack = v.findViewById(R.id.btnback);
        this.btnBack.setOnClickListener(this.createEventFragmentEvents);

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

    public ICreateEventFragmentListener getiCreateEventFragmentListener() {
        return iCreateEventFragmentListener;
    }

    public void setiCreateEventFragmentListener(ICreateEventFragmentListener iCreateEventFragmentListener) {
        this.iCreateEventFragmentListener = iCreateEventFragmentListener;
    }

    @Override
    public boolean onSwipedAway(boolean toRight) {
        this.createEventFragmentEvents.destroyCreateEventDialogFragment();
        return false;
    }



}
