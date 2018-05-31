package com.example.tay.eventi4all_def.fragments;

public interface INofiticationFragmentListener {
    public void acceptInvitation();
    public void declineInvitation();
    public void getInvitations();
    public void deleteInvitation(String uuid, int position);
    public void acceptInvitationAndDeleteCard(String uuid, int position);
}
