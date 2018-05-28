package com.example.tay.eventi4all_def.Firebase;

import android.util.Log;

import com.example.tay.eventi4all_def.DataHolder;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by tay on 26/1/18.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService  {
    /*
    Clase que hereda de InstanceIdService y que servirá para administrar cuando nos damos de alto en firebase para guardar el token
    es decir esa id correspondiente aal dispositivo que se logueo y que se vinculará con el usuario correspondiente.
    Para ello se llama al método onTokenRefresh que contiene FirebaseInstanceId.getInstance().getToken();

     */

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]


    //Genera el token es decir id que se registra en el firebase. Se ejecutará siempre que iniciemos la app o se refresque el token
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("el token es: " + FirebaseInstanceId.getInstance().getToken());
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */

    /*
    Con este método podemos guardar el token en la propia base de datos de ese usuario para tener visible el token desde allí,
    dado que cunado se proporciona el token con el método onRTokenRefressh este lo trata de forma interna firebase sin poder verlo.
    Por eso lo podemos almacenar.

    Si guardamos el token en la bbdd referida a un usuario entonces desde las notificaciones de firebase podemos filtrar por ese token
    por ejemplo si queremos y solo enviarselo a ese dispositivo
     */
    private void sendRegistrationToServer(String token) {

        System.out.println("el token es: " + token);
        DataHolder.MyDataHolder.token=token;


    }
}
