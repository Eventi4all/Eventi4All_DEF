package com.example.tay.eventi4all_def.fragments;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.tay.eventi4all_def.adapter.IMyViewHolderCardListener;
import com.example.tay.eventi4all_def.entity.Card;

import java.util.ArrayList;

public class NotificationFragmentEvents implements IMyViewHolderCardListener, SwipeRefreshLayout.OnRefreshListener{
    private NotificationFragment notificationFragment;

    public NotificationFragmentEvents(NotificationFragment notificationFragment) {
        this.notificationFragment = notificationFragment;
    }

    public NotificationFragment getNotificationFragment() {
        return notificationFragment;
    }

    public void setNotificationFragment(NotificationFragment notificationFragment) {
        this.notificationFragment = notificationFragment;
    }

    @Override
    public void acceptInvitation(int position) {
        this.notificationFragment.getiNofiticationFragmentListener().acceptInvitationAndDeleteCard(this.notificationFragment.getArrCards().get(position).getUuid(),position);
    }

    @Override
    public void declineInvitation(int position) {
        this.notificationFragment.getiNofiticationFragmentListener().deleteInvitation(this.notificationFragment.getArrCards().get(position).getUuid(),position);

    }

    public void removeCard(int position){
        this.notificationFragment.getArrCards().remove(position);
        this.notificationFragment.getListAdapter().notifyDataSetChanged();
    }

    public void getInvitations(){
        this.notificationFragment.getiNofiticationFragmentListener().getInvitations();

    }

    public void createCard(ArrayList<Card> cards){
        System.out.println("cards:" + cards.size());
        this.notificationFragment.getArrCards().clear();
        this.notificationFragment.getArrCards().addAll(cards);
        this.notificationFragment.getListAdapter().notifyDataSetChanged();
        System.out.println("refreshing cards");
        notificationFragment.getRefreshLayout().setRefreshing(false);
    }

    @Override
    public void onRefresh() {

        if(this.notificationFragment.getArrCards().size()>0){
            this.getInvitations();
        }else{
            notificationFragment.getRefreshLayout().setRefreshing(false);
        }
    }
}
