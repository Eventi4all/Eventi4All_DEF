package com.example.tay.eventi4all_def;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tay.eventi4all_def.Firebase.AbstractFirebaseAdminListener;
import com.example.tay.eventi4all_def.Firebase.FirebaseAdmin;
import com.example.tay.eventi4all_def.fragments.IMainFragmentListener;
import com.example.tay.eventi4all_def.fragments.MainFragment;
import com.example.tay.eventi4all_def.fragments.ProfileFragment;
import com.firebase.ui.auth.AuthUI;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityEvents mainActivityEvents;
    private TextView mTextMessage;
    private SignIn signIn;
    private FirebaseAdmin firebaseAdmin;
    //Atributos fragments
    private ProfileFragment profileFragment;
    private MainFragment mainFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mainActivityEvents = new MainActivityEvents(this);




        //Instanciamos los fragments asignandoles su componente visual

        this.mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frgMain);
        this.mainFragment.setiMainFragmentListener(this.mainActivityEvents);


        this.profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentById(R.id.frgProfile);
        this.profileFragment.setiProfileFragmentListener(this.mainActivityEvents);

        android.support.v4.app.FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
        transition.hide(profileFragment);
        transition.show(mainFragment);

        //Instancia de la clase SignIn
        this.signIn = new SignIn(this);
        //Instancia de FirebaseAdmin
        this.firebaseAdmin = new FirebaseAdmin();

        //Instanciamos el abstractFirebaseAdmin
        this.firebaseAdmin.setAbstractFirebaseAdminListener(this.mainActivityEvents);




    }


    /*
        Sobreescribimos el método de la clase AppCompatActivity. Recibe todas las respuestas de las llamadas
        callbacks de los distintos tipos de intentos de inicio de sesión según el proveedor.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == signIn.getRcSignIn()) {
            this.firebaseAdmin.onCreate();
            this.firebaseAdmin.checkUserExist();

        }
        //Si la respuesta de la cámara o galería es OK
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //Si el requestCode es igual al PHOTO_CODE
                case 200:
                    //Escaneamos la nueva imagen que hemos tomado porque si no, no la encuentra
                    MediaScannerConnection.scanFile(this, new String[]{this.mainActivityEvents.getmPath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            System.out.println("Externa Storage scanned " + path + ":");
                            System.out.println("ExternalStorage Uri:v " + uri);
                        }
                    });
                    //Vamos a meter en el imageView del profileFragment la foto:

                    //Primero decodificamos la ruta y saca la imagen para guardarla en el bitmap.
                    Bitmap bitmap = BitmapFactory.decodeFile(this.mainActivityEvents.getmPath());
                    //Le seteamos el bitmap al imageView
                    this.profileFragment.getImgProfile().setImageBitmap(bitmap);
                    break;
                //Si el requestCode es el de SELECT_PICTURE
                case 300:
                    //Recibimos la URI de la imagen
                    Uri path = data.getData();
                    //Seteamos la imagen
                    this.profileFragment.getImgProfile().setImageURI(path);
                    break;
            }
        }
    }

    /*
   Al igual que el OnActivityResut, método propio de los activities que reciben el resultado de aceptar/denegar un permiso
    */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //preguntamos si el requesCode que llega es el de nuestros permisos (WRITE_EXTERNAL_STORAGE + CAMERA)
        if (requestCode == this.mainActivityEvents.getMY_PERMISSIONS()) {
            /*
            El grantResult es el array String[] {WRITE_EXTERNAL_STORAGE,CAMERA} del método myRequestStoragePermission()
            del mainAcitvityEvents y comprobamos si es ese de verdad el que nos llega dado que tiene que tener 2 posiciones
            Preguntamos si en la posición 0 y 1 tienen permisos garantizados
             */
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this,"Permisos aceptados",Toast.LENGTH_SHORT).show();
            }else{
                this.mainActivityEvents.showExplanation();
            }
        }
    }




    public FirebaseAdmin getFirebaseAdmin() {
        return firebaseAdmin;
    }


    public MainActivityEvents getMainActivityEvents() {
        return mainActivityEvents;
    }

    public void setMainActivityEvents(MainActivityEvents mainActivityEvents) {
        this.mainActivityEvents = mainActivityEvents;
    }

    public TextView getmTextMessage() {
        return mTextMessage;
    }

    public void setmTextMessage(TextView mTextMessage) {
        this.mTextMessage = mTextMessage;
    }

    public SignIn getSignIn() {
        return signIn;
    }

    public void setSignIn(SignIn signIn) {
        this.signIn = signIn;
    }

    public void setFirebaseAdmin(FirebaseAdmin firebaseAdmin) {
        this.firebaseAdmin = firebaseAdmin;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener getmOnNavigationItemSelectedListener() {
        return mOnNavigationItemSelectedListener;
    }

    public void setmOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener) {
        this.mOnNavigationItemSelectedListener = mOnNavigationItemSelectedListener;
    }

    public ProfileFragment getProfileFragment() {
        return profileFragment;
    }

    public void setProfileFragment(ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
    }

    public MainFragment getMainFragment() {
        return mainFragment;
    }

    public void setMainFragment(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }


}
