package com.example.tay.eventi4all_def.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.adapter.GridSpacingItemDecoration;
import com.example.tay.eventi4all_def.adapter.ListAdapterListOfPublicEvents;
import com.example.tay.eventi4all_def.entity.Event;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListPublicEventsFragment extends Fragment {

    private IListPublicEventsFragmentListener IListPublicEventsFragmentListener;
    private ListPublicEventsFragmentEvents listPublicEventsFragmentEvents;
    private ListAdapterListOfPublicEvents listAdapter;
    private ArrayList<Event> arrEvents;
    private RecyclerView myList;
    private EditText search;


    public ListPublicEventsFragment() {
        this.arrEvents=new ArrayList<Event>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_public_events, container, false);
        this.listPublicEventsFragmentEvents = new ListPublicEventsFragmentEvents(this);
        this.listAdapter = new ListAdapterListOfPublicEvents(arrEvents,getActivity());
        myList = v.findViewById(R.id.listOfPublicEvents);
        myList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        myList.setItemAnimator(new DefaultItemAnimator());
        myList.setAdapter(listAdapter);
        int spanCount = 2; //num de columnas
        int spacing = 40; //espaciado entre columnas
        boolean includeEdge = true;
        myList.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        this.search = v.findViewById(R.id.searchEvent);
        this.search.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }

          @Override
          public void afterTextChanged(Editable editable) {
              filter(editable.toString());

          }
      });
        return v;
    }
    
    public void filter(String editable){
        ArrayList<Event> arrEventsFiltered = new ArrayList<>();
        for (Event aux: this.arrEvents) {
            if(aux.getTitle().toLowerCase().contains(editable.toLowerCase())){
                arrEventsFiltered.add(aux);
            }

        }
        this.listAdapter.setContenidoLista(arrEventsFiltered);
        this.getListAdapter().notifyDataSetChanged();

        
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

    public ListAdapterListOfPublicEvents getListAdapter() {
        return listAdapter;
    }

    public void setListAdapter(ListAdapterListOfPublicEvents listAdapter) {
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


}
