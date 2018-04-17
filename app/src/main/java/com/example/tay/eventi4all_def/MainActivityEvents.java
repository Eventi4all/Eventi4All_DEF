package com.example.tay.eventi4all_def;

import android.support.annotation.NonNull;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by tay on 17/4/18.
 */

public class MainActivityEvents implements View.OnClickListener{
    private MainActivity mainActivity;

    public MainActivityEvents(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnLogout){
            AuthUI.getInstance()
                    .signOut(mainActivity)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            //user is now signed out
                            // startActivity(new Intent(MainActivity, SignInActivity.class));
                            // finish();
                        }
                    });
        }
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
