package com.example.tay.eventi4all_def.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tay.eventi4all_def.R;

/**
 * Created by tay on 17/12/17.
 */

public class MyViewHolderListOfPublicEvents extends RecyclerView.ViewHolder {
    private TextView txtTitleEvent;
    private TextView txtCreateAt;
    private ImageView imageViewCover;
    private MyViewHolderListOfPublicEvents_Events myViewHolderListOfPublicEvents_events;
    private ListAdapterListOfPublicEventsListener listAdapterListOfPublicEventsListener;





    private View itemView;

    public MyViewHolderListOfPublicEvents(View itemView) {
        super(itemView);
        this.itemView=itemView;
        this.txtTitleEvent = (TextView) itemView.findViewById(R.id.txtTitlePublicEvent);
        this.txtCreateAt = (TextView) itemView.findViewById(R.id.txtCreatedAtPublicEvent);
        this.imageViewCover=(ImageView)itemView.findViewById(R.id.imgCoverPublicEvent);
        this.myViewHolderListOfPublicEvents_events = new MyViewHolderListOfPublicEvents_Events(this);
        itemView.setOnClickListener(myViewHolderListOfPublicEvents_events);


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

    public MyViewHolderListOfPublicEvents_Events getMyViewHolderListOfPublicEvents_events() {
        return myViewHolderListOfPublicEvents_events;
    }

    public void setMyViewHolderListOfPublicEvents_events(MyViewHolderListOfPublicEvents_Events myViewHolderListOfPublicEvents_events) {
        this.myViewHolderListOfPublicEvents_events = myViewHolderListOfPublicEvents_events;
    }

    public ListAdapterListOfPublicEventsListener getListAdapterListOfPublicEventsListener() {
        return listAdapterListOfPublicEventsListener;
    }

    public void setListAdapterListOfPublicEventsListener(ListAdapterListOfPublicEventsListener listAdapterListOfPublicEventsListener) {
        this.listAdapterListOfPublicEventsListener = listAdapterListOfPublicEventsListener;
    }
}
