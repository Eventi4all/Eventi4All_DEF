package com.example.tay.eventi4all_def;


import android.annotation.TargetApi;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AndroidException;
import android.util.Log;

import android.view.MenuItem;

import android.view.Window;
import android.view.WindowManager;


import android.widget.Toast;

import com.example.tay.eventi4all_def.Firebase.FirebaseAdmin;
import com.example.tay.eventi4all_def.adapter.ViewPagerAdapter;
import com.example.tay.eventi4all_def.fragments.CustomDialogFragment_CreateEvents;
import com.example.tay.eventi4all_def.fragments.ListPublicEventsFragment;
import com.example.tay.eventi4all_def.fragments.MainFragment;
import com.example.tay.eventi4all_def.fragments.ProfileFragment;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;


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
    private ListPublicEventsFragment listPublicEventsFragment;
    private UCrop uCrop;
    int request = 0;
    private CustomDialogFragment_CreateEvents customDialogFragment_createEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
        //bottomNavigationView.setItemTextColor(getColorStateList(R.color.colorGreen));

        mainActivityEvents = new MainActivityEvents(this);

        //Instancia de la clase SignIn
        //Instancia de FirebaseAdmin
        this.firebaseAdmin = new FirebaseAdmin();

        //Instanciamos el abstractFirebaseAdmin
        this.firebaseAdmin.setAbstractFirebaseAdminListener(this.mainActivityEvents);

        //this.createEventFragment = (CreateEventFragment)this.getSupportFragmentManager().findFragmentById(R.id.createEventFragment);

        customDialogFragment_createEvents= new CustomDialogFragment_CreateEvents();

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

        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorGreen));

        getSupportActionBar().hide();


        this.setDataOfActivity();





    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        mainFragment = new MainFragment();
        listPublicEventsFragment = new ListPublicEventsFragment(); //createEventFragment = new CreateEventFragment();


        profileFragment = new ProfileFragment();


        this.mainFragment.setiMainFragmentListener(this.mainActivityEvents);

        this.profileFragment.setiProfileFragmentListener(this.mainActivityEvents);

        this.profileFragment.setiGalleryAndCapturePhotoListener(this.mainActivityEvents);


        this.listPublicEventsFragment.setIListPublicEventsFragmentListener(this.mainActivityEvents);

        this.customDialogFragment_createEvents.setiCreateEventFragmentListener(this.mainActivityEvents);

        this.customDialogFragment_createEvents.setiGalleryAndCapturePhotoListener(this.mainActivityEvents);



        adapter.addFragment(mainFragment);
        adapter.addFragment(listPublicEventsFragment);
        adapter.addFragment(profileFragment);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        /*
        PARA ESTABLECER EL NUMERO DE PAGINAS QUE SE QUEDARÁN EN SEGUNDO PLANO
        Y DE ESTA FORMA QUE NO ESTÉ RECARGANDO
         */

        viewPager.setOffscreenPageLimit(2);


    }

    public void setDataOfActivity() {
        this.firebaseAdmin.onCreate();

        this.firebaseAdmin.checkUserExist();

        this.firebaseAdmin.getStorageRef();



        setupViewPager(viewPager);

    }


    /*
        Sobreescribimos el método de la clase AppCompatActivity. Recibe todas las respuestas de las llamadas
        callbacks de los distintos tipos de intentos de inicio de sesión según el proveedor.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //System.out.println("resultCode" + resultCode + "equestcode: " + requestCode + "y request" + request);


        //Si la respuesta de la cámara o galería es OK o se llama al crop
        if (resultCode == RESULT_OK || resultCode == 96) {

            //Si el requestCode es igual al PHOTO_CODE
            if (requestCode == 200 || requestCode == 201) {
                request = requestCode;

                //Escaneamos la nueva imagen que hemos tomado porque si no, no la encuentra
                MediaScannerConnection.scanFile(this, new String[]{this.mainActivityEvents.getmPath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        System.out.println("Externa Storage scanned " + path + ":");
                        System.out.println("ExternalStorage Uri:v " + uri);

                        DataHolder.MyDataHolder.imgUri = Uri.parse(Environment.getExternalStorageDirectory() + File.separator + "Eventy4All/" + "ProfilePicture" + File.separator + UUID.randomUUID().toString() + ".jpg");
                        UCrop.of(uri, DataHolder.MyDataHolder.imgUri)
                                .withAspectRatio(10, 10)
                                .withMaxResultSize(250, 250)
                                .start(customDialogFragment_createEvents.getActivity());

                    }
                });

                //Si el requestCode es el de SELECT_PICTURE
            } else if (requestCode == 300 || requestCode == 301) {
                request = requestCode;
                Long timestamp = System.currentTimeMillis()/1000;
                String mPath = Environment.getExternalStorageDirectory() + File.separator + "Eventy4All/Profilepicture" + File.separator + timestamp.toString() + ".jpg";


                //Antes de pasarle la uri de la imagen vamos a  comprimirla para que no ocupe tanto espacio y nos de una excepción de memoria
                File compressedImageFile=null;

                //Recibimos la URI de la imagen
                Uri path = data.getData();

                DataHolder.MyDataHolder.imgUri = Uri.parse(Environment.getExternalStorageDirectory() + File.separator + "Eventy4All/" + "ProfilePicture" + File.separator + UUID.randomUUID().toString() + ".jpg");
                UCrop.of(path, DataHolder.MyDataHolder.imgUri)
                        .withAspectRatio(10, 10)
                        .withMaxResultSize(250, 250)
                        .start(customDialogFragment_createEvents.getActivity());



            } else if (requestCode == UCrop.REQUEST_CROP) {








                DataHolder.MyDataHolder.imgUri = UCrop.getOutput(data);
                //Vamos a meter en el imageView del profileFragment la foto:

                //Primero decodificamos la ruta y saca la imagen para guardarla en el bitmap.
                Bitmap bitmap = BitmapFactory.decodeFile(DataHolder.MyDataHolder.imgUri.getPath());
                //Le seteamos el bitmap al imageView
                if (request == 200) {
                    this.profileFragment.getImgProfile().setImageBitmap(bitmap);
                } else if (request == 201) {

                    this.customDialogFragment_createEvents.getEventImgMain().setImageBitmap(bitmap);
                } else if (request == 300) {
                    this.profileFragment.getImgProfile().setImageURI(DataHolder.MyDataHolder.imgUri);
                } else if (request == 301) {
                    this.customDialogFragment_createEvents.getEventImgMain().setImageURI(DataHolder.MyDataHolder.imgUri);
                }
                request = 0;
            } else if (resultCode == UCrop.RESULT_ERROR) {

                final Throwable cropError = UCrop.getError(data);
                System.out.println("Error:" + cropError.getMessage());

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



    public CustomDialogFragment_CreateEvents getCustomDialogFragment_createEvents() {
        return customDialogFragment_createEvents;
    }

    public void setCustomDialogFragment_createEvents(CustomDialogFragment_CreateEvents customDialogFragment_createEvents) {
        this.customDialogFragment_createEvents = customDialogFragment_createEvents;
    }

    public ListPublicEventsFragment getListPublicEventsFragment() {
        return listPublicEventsFragment;
    }

    public void setListPublicEventsFragment(ListPublicEventsFragment listPublicEventsFragment) {
        this.listPublicEventsFragment = listPublicEventsFragment;
    }
}
