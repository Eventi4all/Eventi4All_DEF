package com.example.tay.eventi4all_def;


import android.annotation.TargetApi;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.MenuItem;

import android.view.Window;
import android.view.WindowManager;

import android.widget.Toast;

import com.example.tay.eventi4all_def.Firebase.FirebaseAdmin;
import com.example.tay.eventi4all_def.adapter.ViewPagerAdapter;
import com.example.tay.eventi4all_def.fragments.CreateEventFragment;
import com.example.tay.eventi4all_def.fragments.MainFragment;
import com.example.tay.eventi4all_def.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private MainActivityEvents mainActivityEvents;


    //Slide Between Fragments (Swipe)
    private ViewPager viewPager;

    //Navigation
    private MenuItem prevMenuItem;


    // Bottom Menu Navigation
    private BottomNavigationView bottomNavigationView;


    private SignIn signIn;
    private FirebaseAdmin firebaseAdmin;
    //Atributos fragments
    private ProfileFragment profileFragment;
    private MainFragment mainFragment;
    private CreateEventFragment createEventFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
        //bottomNavigationView.setItemTextColor(getColorStateList(R.color.colorGreen));

        mainActivityEvents = new MainActivityEvents(this);

        //Instancia de la clase SignIn
        this.signIn = new SignIn(this);
        //Instancia de FirebaseAdmin
        this.firebaseAdmin = new FirebaseAdmin();

        //Instanciamos el abstractFirebaseAdmin
        this.firebaseAdmin.setAbstractFirebaseAdminListener(this.mainActivityEvents);


        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.navigation);

// Poner invisible el navbottom
//        bottomNavigationView.setVisibility(View.INVISIBLE);
        bottomNavigationView.setOnNavigationItemSelectedListener(

                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.navigation_dashboard:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.navigation_notifications:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;


                    }
                });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });




        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color

        window.setStatusBarColor(ContextCompat.getColor(this,  R.color.colorGreen));

        getSupportActionBar().hide();
    }




    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        mainFragment = new MainFragment();
        createEventFragment = new CreateEventFragment();
        profileFragment = new ProfileFragment();

        this.mainFragment.setiMainFragmentListener(this.mainActivityEvents);
        this.profileFragment.setiProfileFragmentListener(this.mainActivityEvents);
        this.profileFragment.setiGalleryAndCapturePhotoListener(this.mainActivityEvents);

        this.createEventFragment.setiCreateEventFragmentListener(this.mainActivityEvents);
        this.createEventFragment.setiGalleryAndCapturePhotoListener(this.mainActivityEvents);


        adapter.addFragment(mainFragment);
        adapter.addFragment(createEventFragment);
        adapter.addFragment(profileFragment);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

    }


    /*
        Sobreescribimos el método de la clase AppCompatActivity. Recibe todas las respuestas de las llamadas
        callbacks de los distintos tipos de intentos de inicio de sesión según el proveedor.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == signIn.getRcSignIn()) {
            this.firebaseAdmin.onCreate();
            this.firebaseAdmin.checkUserExist();
            this.firebaseAdmin.getStorageRef();
            setupViewPager(viewPager);

        }
        //Si la respuesta de la cámara o galería es OK
        if (resultCode == RESULT_OK) {
          //  switch (requestCode) {
            //Si el requestCode es igual al PHOTO_CODE
            if(requestCode==200 || requestCode==201){
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
                if(requestCode==200){
                    this.profileFragment.getImgProfile().setImageBitmap(bitmap);
                }else if(requestCode==201){
                    this.createEventFragment.getEventImgMain().setImageBitmap(bitmap);
                }
                //Si el requestCode es el de SELECT_PICTURE
            }else if(requestCode==300 || requestCode==301){
                //Recibimos la URI de la imagen
                Uri path = data.getData();
                DataHolder.MyDataHolder.imgUri = path;
                //Seteamos la imagen
                if(requestCode==300){
                    this.profileFragment.getImgProfile().setImageURI(path);
                }else if(requestCode==301){
                    this.createEventFragment.getEventImgMain().setImageURI(path);
                }

            }




            }

    }


    /*
   Al igual que el OnActivityResut, método propio de los activities que reciben el resultado de aceptar/denegar un permiso
    */
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        //preguntamos si el requesCode que llega es el de nuestros permisos (WRITE_EXTERNAL_STORAGE + CAMERA)
        if (requestCode == this.mainActivityEvents.getMY_PERMISSIONS()) {
            /*
            El grantResult es el array String[] {WRITE_EXTERNAL_STORAGE,CAMERA} del método myRequestStoragePermission()
            del mainAcitvityEvents y comprobamos si es ese de verdad el que nos llega dado que tiene que tener 2 posiciones
            Preguntamos si en la posición 0 y 1 tienen permisos garantizados
             */
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
            } else {
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


    public SignIn getSignIn() {
        return signIn;
    }

    public void setSignIn(SignIn signIn) {
        this.signIn = signIn;
    }

    public void setFirebaseAdmin(FirebaseAdmin firebaseAdmin) {
        this.firebaseAdmin = firebaseAdmin;
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

    public CreateEventFragment getCreateEventFragment() {
        return createEventFragment;
    }

    public void setCreateEventFragment(CreateEventFragment createEventFragment) {
        this.createEventFragment = createEventFragment;
    }
}
