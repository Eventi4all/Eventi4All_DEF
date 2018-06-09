package com.example.tay.eventi4all_def.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tay.eventi4all_def.R;

/**
 * Created by tay on 17/12/17.
 */

public class MyViewHolderCreatedEvents extends RecyclerView.ViewHolder {
    private TextView txtTitleEvent;
    private TextView txtCreateAt;
    private ImageView imageViewCover;
    private MyViewHolderCreatedEvents_Events myViewHolderCreatedEvents_events;
    private ListAdapterCreatedEventsListener listAdapterCreatedEventsListener;





    private View itemView;

    public MyViewHolderCreatedEvents(View itemView) {
        super(itemView);

        this.itemView=itemView;
        this.txtTitleEvent = (TextView) itemView.findViewById(R.id.txtTitleMyEvent);
        this.txtCreateAt = (TextView) itemView.findViewById(R.id.txtCreatedAt);
        this.imageViewCover=(ImageView)itemView.findViewById(R.id.imgCoverEvent);
        this.myViewHolderCreatedEvents_events = new MyViewHolderCreatedEvents_Events(this);
        itemView.setOnClickListener(myViewHolderCreatedEvents_events);


    }


    public TextView getTxtTitleEvent() {
        return txtTitleEvent;
    }

    public void setTxtTitleEvent(TextView txtTitleEvent) {
        this.txtTitleEvent = txtTitleEvent;
    }

    public TextView getTxtCreateAt() {
        return txtCreateAt;
    }

    public void setTxtCreateAt(TextView txtCreateAt) {
        this.txtCreateAt = txtCreateAt;
    }

    public ImageView getImageViewCover() {
        return imageViewCover;
    }

    public void setImageViewCover(ImageView imageViewCover) {
        this.imageViewCover = imageViewCover;
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

    public MyViewHolderCreatedEvents_Events getMyViewHolderCreatedEvents_events() {
        return myViewHolderCreatedEvents_events;
    }

    public void setMyViewHolderCreatedEvents_events(MyViewHolderCreatedEvents_Events myViewHolderCreatedEvents_events) {
        this.myViewHolderCreatedEvents_events = myViewHolderCreatedEvents_events;
    }

    public ListAdapterCreatedEventsListener getListAdapterCreatedEventsListener() {
        return listAdapterCreatedEventsListener;
    }

    public void setListAdapterCreatedEventsListener(ListAdapterCreatedEventsListener listAdapterCreatedEventsListener) {
        this.listAdapterCreatedEventsListener = listAdapterCreatedEventsListener;
    }
}
