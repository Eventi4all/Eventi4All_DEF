package com.example.tay.eventi4all_def;

import android.app.Activity;
import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

//Las clases static deben de estar anidadas para su uso
class SignIn {

    //Establecemos un request code para para inicio de sesión con Google.
    private static final int RC_SIGN_IN = 123;

    private Activity activity;

    public SignIn(Activity activity) {
        this.activity = activity;
        this.signInAllProviders();
    }


    public void signInAllProviders() {

        //Añadimos los proveedores de autentificación a una lista
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build());

        // Creamos un "intent" de tipo acceso con los proveedores
        this.activity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTosUrl("https://superapp.example.com/terms-of-service.html")
                        .setPrivacyPolicyUrl("https://superapp.example.com/privacy-policy.html")
                        .build(),
                RC_SIGN_IN);


    }

    public static int getRcSignIn() {
        return RC_SIGN_IN;
    }


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}

