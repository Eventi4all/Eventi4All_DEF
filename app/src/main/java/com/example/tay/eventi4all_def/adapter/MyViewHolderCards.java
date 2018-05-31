package com.example.tay.eventi4all_def.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tay.eventi4all_def.R;
import com.tabassum.shimmerRecyclerView.ShimmerRecyclerView;

/**
 * Created by tay on 17/12/17.
 */

public class MyViewHolderCards extends ShimmerRecyclerView.ViewHolder {
    private TextView txtTitleEvent;
    private TextView txtCreateAt;
    private ImageView imageViewCover;
    private ImageView btnAccept;
    private ImageView btnDecline;
    private View itemView;
    private MyViewHolderCardsEvents myViewHolderCardsEvents;
    private IMyViewHolderCardListener myViewHolderCardListener;

    public MyViewHolderCards(View itemView) {
        super(itemView);
        this.itemView=itemView;
        this.myViewHolderCardsEvents = new MyViewHolderCardsEvents(this);
        this.txtTitleEvent = (TextView) itemView.findViewById(R.id.titleEventCard);
        this.txtCreateAt = (TextView) itemView.findViewById(R.id.createAtCard);
        this.imageViewCover=(ImageView)itemView.findViewById(R.id.imgCoverCard);
        this.btnAccept = (ImageView) itemView.findViewById(R.id.btnAcceptCard);
        this.btnDecline = (ImageView) itemView.findViewById(R.id.btnDeclineCard);
        this.btnAccept.setOnClickListener(this.myViewHolderCardsEvents);
        this.btnDecline.setOnClickListener(this.myViewHolderCardsEvents);
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

    public ImageView getBtnAccept() {
        return btnAccept;
    }

    public void setBtnAccept(ImageView btnAccept) {
        this.btnAccept = btnAccept;
    }

    public ImageView getBtnDecline() {
        return btnDecline;
    }

    public void setBtnDecline(ImageView btnDecline) {
        this.btnDecline = btnDecline;
    }

    public MyViewHolderCardsEvents getMyViewHolderCardsEvents() {
        return myViewHolderCardsEvents;
    }

    public void setMyViewHolderCardsEvents(MyViewHolderCardsEvents myViewHolderCardsEvents) {
        this.myViewHolderCardsEvents = myViewHolderCardsEvents;
    }

    public IMyViewHolderCardListener getMyViewHolderCardListener() {
        return myViewHolderCardListener;
    }

    public void setMyViewHolderCardListener(IMyViewHolderCardListener myViewHolderCardListener) {
        this.myViewHolderCardListener = myViewHolderCardListener;
    }
}
