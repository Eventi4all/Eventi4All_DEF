package com.example.tay.eventi4all_def.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tay.eventi4all_def.R;

/**
 * Created by tay on 17/12/17.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView txtUsrNickname;
    private ImageView imageViewProfile;

    private View itemView;

    public MyViewHolder(View itemView) {
        super(itemView);
        this.itemView=itemView;
        this.txtUsrNickname = (TextView) itemView.findViewById(R.id.txtUsrNick);
        this.imageViewProfile = (ImageView) itemView.findViewById(R.id.imgProfile);

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




}
