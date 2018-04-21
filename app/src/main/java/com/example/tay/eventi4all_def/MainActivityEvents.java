package com.example.tay.eventi4all_def;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.tay.eventi4all_def.Firebase.AbstractFirebaseAdminListener;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by tay on 17/4/18.
 */

public class MainActivityEvents extends AbstractFirebaseAdminListener implements View.OnClickListener{
    private MainActivity mainActivity;

    public MainActivityEvents(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnLogout){
            this.mainActivity.getFirebaseAdmin().logoutFirebase(this.mainActivity);
        }
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Override
    public void logout(boolean isLogout) {
        if(isLogout){
           System.out.println("----------> SESIÓN CERRADA SATISFACTORIAMENTE <----------");
        }else{
            //Se muestra mensaje de error al usuario
        }

    }
}
