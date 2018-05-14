package com.example.tay.eventi4all_def;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

//Las clases static deben de estar anidadas para su uso
public class SignIn extends AppCompatActivity{

    //Establecemos un request code para para inicio de sesión con Google.
    private static final int RC_SIGN_IN = 123;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Añadimos los proveedores de autentificación a una lista
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build());

        // Creamos un "intent" de tipo acceso con los proveedores
        this.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.applogo)      // Set logo drawable
                        .setTheme(R.style.LoginTheme)
                        .setTosUrl("https://superapp.example.com/terms-of-service.html")
                        .setPrivacyPolicyUrl("https://superapp.example.com/privacy-policy.html")
                        .build(),
                RC_SIGN_IN);
    }




    public void signInAllProviders() {




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == this.getRcSignIn()) {
            System.out.println("cambio manuehh");
            SignIn.this.startActivity(new Intent(SignIn.this,MainActivity.class));
            SignIn.this.finish();

        }
    }

    public static int getRcSignIn() {
        return RC_SIGN_IN;
    }


}

