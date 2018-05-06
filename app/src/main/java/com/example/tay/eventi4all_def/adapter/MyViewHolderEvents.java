package com.example.tay.eventi4all_def.adapter;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.tay.eventi4all_def.R;

public class MyViewHolderEvents implements View.OnClickListener {
    private MyViewHolder myViewHolder;

    public MyViewHolderEvents(MyViewHolder myViewHolder) {
        this.myViewHolder = myViewHolder;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btnDeleteUser){
            Animation animation = AnimationUtils.loadAnimation(this.myViewHolder.getItemView().getContext().getApplicationContext(), R.anim.alpha);
            this.myViewHolder.getBtnDeleteUser().startAnimation(animation);
            this.myViewHolder.getAdapterPosition();
            this.myViewHolder.getMyViewHolderListener().deleteUser(this.myViewHolder.getAdapterPosition());
        }

    }

    public MyViewHolder getMyViewHolder() {
        return myViewHolder;
    }

    public void setMyViewHolder(MyViewHolder myViewHolder) {
        this.myViewHolder = myViewHolder;
    }
}
