package com.example.tay.eventi4all_def.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.fragments.CreateEventFragment;

/**
 * Created by tay on 17/12/17.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView txtUsrNickname;
    private ImageView imageViewProfile;
    private ImageView btnDeleteUser;
    private MyViewHolderEvents myViewHolderEvents;
    private IMyViewHolderListener myViewHolderListener;



    private View itemView;

    public MyViewHolder(View itemView) {
        super(itemView);
        this.itemView=itemView;
        this.myViewHolderEvents = new MyViewHolderEvents(this);
        this.itemView.setOnClickListener(this.myViewHolderEvents);
        this.txtUsrNickname = (TextView) itemView.findViewById(R.id.txtUsrNick);
        this.imageViewProfile = (ImageView) itemView.findViewById(R.id.imgProfile);
        this.btnDeleteUser = (ImageView) itemView.findViewById(R.id.btnDeleteUser);
       this.btnDeleteUser.setOnClickListener(this.myViewHolderEvents);

    }

    public TextView getTxtUsrNickname() {
        return txtUsrNickname;
    }

    public void setTxtUsrNickname(TextView txtUsrNickname) {
        this.txtUsrNickname = txtUsrNickname;
    }


    public ImageView getImageViewProfile() {
        return imageViewProfile;
    }

    public void setImageViewProfile(ImageView imageViewProfile) {
        this.imageViewProfile = imageViewProfile;
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

    public ImageView getBtnDeleteUser() {
        return btnDeleteUser;
    }

    public void setBtnDeleteUser(ImageView btnDeleteUser) {
        this.btnDeleteUser = btnDeleteUser;
    }

    public MyViewHolderEvents getMyViewHolderEvents() {
        return myViewHolderEvents;
    }

    public void setMyViewHolderEvents(MyViewHolderEvents myViewHolderEvents) {
        this.myViewHolderEvents = myViewHolderEvents;
    }

    public IMyViewHolderListener getMyViewHolderListener() {
        return myViewHolderListener;
    }

    public void setMyViewHolderListener(IMyViewHolderListener myViewHolderListener) {
        this.myViewHolderListener = myViewHolderListener;
    }
}
