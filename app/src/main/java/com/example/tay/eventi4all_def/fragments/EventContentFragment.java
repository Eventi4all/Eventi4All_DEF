package com.example.tay.eventi4all_def.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.adapter.GridSpacingItemDecoration;
import com.example.tay.eventi4all_def.adapter.ListAdapterMyCreatedEvents;
import com.example.tay.eventi4all_def.adapter.ListAdapterPhotosOfEvent;
import com.example.tay.eventi4all_def.entity.Photo;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventContentFragment extends Fragment {
    private TextView txtTitleOfEvent;
    private CircleImageView imgEvent;
    private ImageView btnShareEvent;
    private FloatingActionButton btnTakePhoto;
    private ImageView btnBack;
    private EventContentFragmentEvents eventContentFragmentEvents;
    private IEventContentFragmentListener iEventContentFragmentListener;
    private ListAdapterPhotosOfEvent listAdapter;
    private ArrayList<Photo> arrPhotos;
    private RecyclerView myList;
    private SwipeRefreshLayout swipeRefreshLayout;

    public EventContentFragment() {
        this.arrPhotos = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_content, container, false);
        this.eventContentFragmentEvents = new EventContentFragmentEvents(this);
        this.txtTitleOfEvent = view.findViewById(R.id.txtTtileOfOpenEvent);
        this.imgEvent = view.findViewById(R.id.imgOfEvent);
        this.btnShareEvent = view.findViewById(R.id.btnShareEvent);
        this.btnTakePhoto = view.findViewById(R.id.btnTakeNewPhoto);
        this.btnBack = view.findViewById(R.id.btnBackAndClose);
        this.btnShareEvent.setOnClickListener(this.eventContentFragmentEvents);
        this.btnTakePhoto.setOnClickListener(this.eventContentFragmentEvents);
        this.btnBack.setOnClickListener(this.eventContentFragmentEvents);


        this.listAdapter = new ListAdapterPhotosOfEvent(arrPhotos, getActivity());
        myList = view.findViewById(R.id.listPhotosOfEvent);
        int spanCount = 1; //num de columnas
        int spacing = 30; //espaciado entre columnas
        boolean includeEdge = true; //incluir borde
        myList.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        myList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        myList.setItemAnimator(new DefaultItemAnimator());
        myList.setAdapter(listAdapter);

        this.swipeRefreshLayout = view.findViewById(R.id.swipeRefreshMyPhotosOfEvent);
        this.swipeRefreshLayout.setOnRefreshListener(this.eventContentFragmentEvents);
        //Colores para el refresh
        swipeRefreshLayout.setColorSchemeResources(
                R.color.s1,
                R.color.s2,
                R.color.s3,
                R.color.s4);



        return view;
    }

    public TextView getTxtTitleOfEvent() {
        return txtTitleOfEvent;
    }

    public void setTxtTitleOfEvent(TextView txtTitleOfEvent) {
        this.txtTitleOfEvent = txtTitleOfEvent;
    }

    public CircleImageView getImgEvent() {
        return imgEvent;
    }

    public void setImgEvent(CircleImageView imgEvent) {
        this.imgEvent = imgEvent;
    }

    public ImageView getBtnShareEvent() {
        return btnShareEvent;
    }

    public void setBtnShareEvent(ImageView btnShareEvent) {
        this.btnShareEvent = btnShareEvent;
    }

    public FloatingActionButton getBtnTakePhoto() {
        return btnTakePhoto;
    }

    public void setBtnTakePhoto(FloatingActionButton btnTakePhoto) {
        this.btnTakePhoto = btnTakePhoto;
    }

    public ImageView getBtnBack() {
        return btnBack;
    }

    public void setBtnBack(ImageView btnBack) {
        this.btnBack = btnBack;
    }

    public EventContentFragmentEvents getEventContentFragmentEvents() {
        return eventContentFragmentEvents;
    }

    public void setEventContentFragmentEvents(EventContentFragmentEvents eventContentFragmentEvents) {
        this.eventContentFragmentEvents = eventContentFragmentEvents;
    }

    public IEventContentFragmentListener getiEventContentFragmentListener() {
        return iEventContentFragmentListener;
    }

    public void setiEventContentFragmentListener(IEventContentFragmentListener iEventContentFragmentListener) {
        this.iEventContentFragmentListener = iEventContentFragmentListener;
    }

    public ListAdapterPhotosOfEvent getListAdapter() {
        return listAdapter;
    }

    public void setListAdapter(ListAdapterPhotosOfEvent listAdapter) {
        this.listAdapter = listAdapter;
    }

    public ArrayList<Photo> getArrPhotos() {
        return arrPhotos;
    }

    public void setArrPhotos(ArrayList<Photo> arrPhotos) {
        this.arrPhotos = arrPhotos;
    }

    public RecyclerView getMyList() {
        return myList;
    }

    public void setMyList(RecyclerView myList) {
        this.myList = myList;

    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

}
