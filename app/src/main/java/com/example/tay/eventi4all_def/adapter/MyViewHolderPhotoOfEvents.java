package com.example.tay.eventi4all_def.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tay.eventi4all_def.R;

/**
 * Created by tay on 17/12/17.
 */

public class MyViewHolderPhotoOfEvents extends RecyclerView.ViewHolder {
    private TextView txtTitlePhoto;
    private TextView txtPhotoCreatedBy;
    private ImageView photo;
    private CardView cardViewPhoto;



    private View itemView;

    public MyViewHolderPhotoOfEvents(View itemView) {
        super(itemView);
        this.itemView=itemView;
        this.txtTitlePhoto = (TextView) itemView.findViewById(R.id.txtTitlePhoto);
        this.txtPhotoCreatedBy = (TextView) itemView.findViewById(R.id.createdPhotoBy);
        this.photo = (ImageView) itemView.findViewById(R.id.photoOfEvent);
        this.cardViewPhoto= itemView.findViewById(R.id.cardViewPhoto);
        this.cardViewPhoto.setPreventCornerOverlap(false);



    }

    public TextView getTxtTitlePhoto() {
        return txtTitlePhoto;
    }

    public void setTxtTitlePhoto(TextView txtTitlePhoto) {
        this.txtTitlePhoto = txtTitlePhoto;
    }

    public TextView getTxtPhotoCreatedBy() {
        return txtPhotoCreatedBy;
    }

    public void setTxtPhotoCreatedBy(TextView txtPhotoCreatedBy) {
        this.txtPhotoCreatedBy = txtPhotoCreatedBy;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

}
