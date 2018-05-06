package com.example.tay.eventi4all_def.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.tay.eventi4all_def.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private ImageView imgProfile;
    private EditText nickName;
    private ProfileFragmentEvents profileFragmentEvents;
    private IProfileFragmentListener iProfileFragmentListener;
    private IGalleryAndCapturePhotoListener iGalleryAndCapturePhotoListener;
    //Layout que contiene la imageView, el field y el botón de subida
    private RelativeLayout mRlView;
    //Btn para crear el perfil
    private Button btnCreateProfile;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_profile, container, false);
       this.profileFragmentEvents = new ProfileFragmentEvents(this);
       this.imgProfile = v.findViewById(R.id.imgViewProfile);
       this.imgProfile.setOnClickListener(this.profileFragmentEvents);
       this.nickName = v.findViewById(R.id.txtFieldNickName);
       this.btnCreateProfile = v.findViewById(R.id.btnCreateProfile);
       this.btnCreateProfile.setOnClickListener(this.profileFragmentEvents);
       this.mRlView= v.findViewById(R.id.relLayoutProfile);

        return v;
    }

    public ImageView getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(ImageView imgProfile) {
        this.imgProfile = imgProfile;
    }

    public EditText getNickName() {
        return nickName;
    }

    public void setNickName(EditText nickName) {
        this.nickName = nickName;
    }

    public ProfileFragmentEvents getProfileFragmentEvents() {
        return profileFragmentEvents;
    }

    public void setProfileFragmentEvents(ProfileFragmentEvents profileFragmentEvents) {
        this.profileFragmentEvents = profileFragmentEvents;
    }

    public IProfileFragmentListener getiProfileFragmentListener() {
        return iProfileFragmentListener;
    }

    public void setiProfileFragmentListener(IProfileFragmentListener iProfileFragmentListener) {
        this.iProfileFragmentListener = iProfileFragmentListener;
    }



    public RelativeLayout getmRlView() {
        return mRlView;
    }

    public void setmRlView(RelativeLayout mRlView) {
        this.mRlView = mRlView;
    }

    public Button getBtnCreateProfile() {
        return btnCreateProfile;
    }

    public void setBtnCreateProfile(Button btnCreateProfile) {
        this.btnCreateProfile = btnCreateProfile;
    }

    public IGalleryAndCapturePhotoListener getiGalleryAndCapturePhotoListener() {
        return iGalleryAndCapturePhotoListener;
    }

    public void setiGalleryAndCapturePhotoListener(IGalleryAndCapturePhotoListener iGalleryAndCapturePhotoListener) {
        this.iGalleryAndCapturePhotoListener = iGalleryAndCapturePhotoListener;
    }
}
