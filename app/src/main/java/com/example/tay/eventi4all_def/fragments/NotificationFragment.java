package com.example.tay.eventi4all_def.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tay.eventi4all_def.R;

import com.example.tay.eventi4all_def.adapter.GridSpacingItemDecoration;
import com.example.tay.eventi4all_def.adapter.ListAdapterCards;
import com.example.tay.eventi4all_def.entity.Card;

import com.tabassum.shimmerRecyclerView.ShimmerRecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    private NotificationFragmentEvents notificationFragmentEvents;
    private INofiticationFragmentListener iNofiticationFragmentListener;
    private ListAdapterCards listAdapter;
    private ArrayList<Card> arrCards;
    private ShimmerRecyclerView myList;
    private SwipeRefreshLayout refreshLayout;




    public NotificationFragment() {
        this.arrCards = new ArrayList<Card>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        notificationFragmentEvents = new NotificationFragmentEvents(this);
        this.listAdapter = new ListAdapterCards(arrCards,getActivity(),this);
        myList = v.findViewById(R.id.listOfCards);
        myList.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        myList.setAdapter(listAdapter);
        myList.addItemDecoration(new GridSpacingItemDecoration(1, 30, true));

        refreshLayout = v.findViewById(R.id.swipeRefreshCards);
        refreshLayout.setOnRefreshListener(this.notificationFragmentEvents);
        //Colores para el refresh
        refreshLayout.setColorSchemeResources(
                R.color.s1,
                R.color.s2,
                R.color.s3,
                R.color.s4);




        return v;
    }

    public NotificationFragmentEvents getNotificationFragmentEvents() {
        return notificationFragmentEvents;
    }

    public void setNotificationFragmentEvents(NotificationFragmentEvents notificationFragmentEvents) {
        this.notificationFragmentEvents = notificationFragmentEvents;
    }

    public INofiticationFragmentListener getiNofiticationFragmentListener() {
        return iNofiticationFragmentListener;
    }

    public void setiNofiticationFragmentListener(INofiticationFragmentListener iNofiticationFragmentListener) {
        this.iNofiticationFragmentListener = iNofiticationFragmentListener;
    }

    public ListAdapterCards getListAdapter() {
        return listAdapter;
    }

    public void setListAdapter(ListAdapterCards listAdapter) {
        this.listAdapter = listAdapter;
    }

    public ArrayList<Card> getArrCards() {
        return arrCards;
    }

    public void setArrCards(ArrayList<Card> arrCards) {
        this.arrCards = arrCards;
    }

    public ShimmerRecyclerView getMyList() {
        return myList;
    }

    public void setMyList(ShimmerRecyclerView myList) {
        this.myList = myList;
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public void setRefreshLayout(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }
}
