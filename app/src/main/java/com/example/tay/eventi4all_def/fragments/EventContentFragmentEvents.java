package com.example.tay.eventi4all_def.fragments;

import android.net.Uri;
import android.view.View;

import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.entity.Photo;

import java.util.ArrayList;

public class EventContentFragmentEvents implements View.OnClickListener {


    private EventContentFragment eventContentFragment;
    //Url del  QR del evento
    private Uri qr;
    private String uuidEvent;

    EventContentFragmentEvents(EventContentFragment eventContentFragment) {
        this.eventContentFragment = eventContentFragment;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnBackAndClose) {
            this.eventContentFragment.getiEventContentFragmentListener().closeEventContent();
        } else if (view.getId() == R.id.btnShareEvent) {
            this.eventContentFragment.getiEventContentFragmentListener().openQR(qr);
        }


    }

    public void setPhotosOfEvent(ArrayList<Photo> arrPhotos) {
        System.out.println("hola el sice es: "+ arrPhotos.size());
        this.eventContentFragment.getArrPhotos().clear();
        if (arrPhotos.size() > 0) {

            this.eventContentFragment.getArrPhotos().addAll(arrPhotos);



        }else{
            this.eventContentFragment.getArrPhotos().addAll( this.eventContentFragment.getListAdapter().getContenidoLista());
        }
        this.eventContentFragment.getListAdapter().notifyDataSetChanged();
        //this.mainFragment.getSwipeRefreshLayout().setRefreshing(false);

    }

    public EventContentFragment getEventContentFragment() {
        return eventContentFragment;
    }

    public void setEventContentFragment(EventContentFragment eventContentFragment) {
        this.eventContentFragment = eventContentFragment;
    }

    public Uri getQr() {
        return qr;
    }

    public void setQr(Uri qr) {
        this.qr = qr;
    }

    public String getUuidEvent() {
        return uuidEvent;
    }

    public void setUuidEvent(String uuidEvent) {
        this.uuidEvent = uuidEvent;
    }
}
