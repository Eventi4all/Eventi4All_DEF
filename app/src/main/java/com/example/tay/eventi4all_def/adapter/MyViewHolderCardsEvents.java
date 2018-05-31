package com.example.tay.eventi4all_def.adapter;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.tay.eventi4all_def.R;

public class MyViewHolderCardsEvents implements View.OnClickListener {
    private MyViewHolderCards myViewHolderCards;

    public MyViewHolderCardsEvents(MyViewHolderCards myViewHolderCards) {
        this.myViewHolderCards = myViewHolderCards;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btnDeclineCard){
            Animation animation = AnimationUtils.loadAnimation(this.myViewHolderCards.getItemView().getContext().getApplicationContext(), R.anim.alpha);
            this.myViewHolderCards.getBtnDecline().startAnimation(animation);
            this.myViewHolderCards.getMyViewHolderCardListener().declineInvitation(this.myViewHolderCards.getAdapterPosition());
        }else if(v.getId()==R.id.btnAcceptCard){
            Animation animation = AnimationUtils.loadAnimation(this.myViewHolderCards.getItemView().getContext().getApplicationContext(), R.anim.alpha);
            this.myViewHolderCards.getBtnAccept().startAnimation(animation);
            this.myViewHolderCards.getMyViewHolderCardListener().acceptInvitation(this.myViewHolderCards.getAdapterPosition());
        }

    }

    public MyViewHolderCards getMyViewHolderCards() {
        return myViewHolderCards;
    }

    public void setMyViewHolderCards(MyViewHolderCards myViewHolderCards) {
        this.myViewHolderCards = myViewHolderCards;
    }
}
