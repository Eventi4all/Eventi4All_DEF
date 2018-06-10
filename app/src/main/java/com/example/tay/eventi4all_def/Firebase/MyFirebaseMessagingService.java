package com.example.tay.eventi4all_def.Firebase;

import android.app.Notification;

import android.app.PendingIntent;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;


import com.example.tay.eventi4all_def.MainActivity;
import com.example.tay.eventi4all_def.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Map;




/**
 * Created by tay on 26/1/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    /*
    Mediante el uso de esta clase vamos a administrar los push, tanto las entradas de mensajes como las
    saldias desde nuestro dispositivo.
    Desde Firebase podemos adminsitrar los push que podemos enviar, filtrar estos push a determinados dispositivos,
    incluso programarlos para que se envíen una fecha concreta.
     */


    private static final String TAG = "MyFirebaseMsgService";







    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]


    /*
    Cuando recibimos un mensaje de firebase se ejecuta este método.
    Mediante el remoteMessage que recibimos por parámetro podemos recibir
    la procedencia del mensaje o el contenido e incluso más información.

     */

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println("push notification recibida");
      System.out.println("data-------------->> " + remoteMessage.getData());

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                // scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            System.out.println("el remoteMessage que recibo es: " + remoteMessage);
            //Convertimos los datos recibidos del mensaje en un jsonObject
            Map<String, String> params;
            JSONObject objectData;
            params = remoteMessage.getData();
            objectData = new JSONObject(params);
            //Creamos un intent apra abrir la aplicación cuandro rpesionemos la notificación
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            //Con autocanal hacemos que la notificación se elimine automaticamente al presionar sobre ella
            try{
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,"Default")
                        .setSmallIcon(R.drawable.applogo)
                        .setContentTitle(objectData.get("title").toString())
                        .setContentText(objectData.get("message").toString())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent)
                        .setAutoCancel(true).addAction(R.drawable.common_full_open_on_phone, "Aceptar la invitación",
                                pendingIntent).addAction(R.drawable.common_full_open_on_phone, "Declinar la invitación",
                                pendingIntent);
                ;


                //Mediante  NotificationManagerCompat mostramos la notificación
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                notificationManager.notify(Integer.parseInt(objectData.get("badge").toString()), mBuilder.build());
            }catch (Exception e){
                System.out.println("Error al recibir la notificacion: "  + e.getMessage());
            }




        }else{

            /*
            Dado que para el manejo de código en nuestra app necesitamos erecibir el contenido de las notificaciones
             de tipo payload Data y no del tipo notification dado que sino, no podemos trabajar con ellas en segundo plano
             dado que el gestor de notificaciones de android trabaja directamente con ellas, vamos a  guardar cada notificación que
             recibamos con el mismo id en una tabla de SQLite. Para poder concatenar estos mensajes en un mismo InboxStyle necesitamos
             que solo se instancie una vez y por tanto desde esta clase no se puede concatenar pues cada vez que recibimos la notificación
             se vuelve a hacer un new por así decirlo de esta clase.
             Vamos a llevar cada mensaje a un método de DatabaseHandler que inserte el nuevo emnsaje en la tabla Message;

             */

            System.out.println("el remoteMessage que recibo es: " + remoteMessage);
            //Convertimos los datos recibidos del mensaje en un jsonObject
            Map<String, String> params;
            JSONObject objectData;
            params = remoteMessage.getData();
            objectData = new JSONObject(params);
            //Creamos un intent apra abrir la aplicación cuandro rpesionemos la notificación
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            //Con autocanal hacemos que la notificación se elimine automaticamente al presionar sobre ella
            try{
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,"Default")
                        .setSmallIcon(R.drawable.applogo)
                        .setContentTitle(objectData.get("title").toString())
                        .setContentText(objectData.get("message").toString())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent)
                        .setAutoCancel(true).addAction(R.drawable.common_full_open_on_phone, "Aceptar la invitación",
                                pendingIntent).addAction(R.drawable.common_full_open_on_phone, "Declinar la invitación",
                                pendingIntent);
                ;


                //Mediante  NotificationManagerCompat mostramos la notificación
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(Integer.parseInt(objectData.get("badge").toString()), mBuilder.build());
            }catch (Exception e){
                System.out.println("Error al recibir la notificacion: "  + e.getMessage());
            }




System.out.println("todo bien y correcto");











        }




        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
   /* private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

   */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }






    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     *
     *
     *
     */

    /*
    Mediante el método sendNotification, podemos enviar notificaciones desde nuestro dispositivo a otro.
     */
    /*private void sendNotification(String messageBody) {
       Intent intent = new Intent(this, MainActivity_GoogleVision.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code *///, intent,
        /*        PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.common_google_signin_btn_text_light_focused)
                        .setContentTitle("FCM Message")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification *///,// notificationBuilder.build());
    // }
}
