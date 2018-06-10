package com.example.tay.eventi4all_def;


import android.annotation.TargetApi;


import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
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
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AndroidException;
import android.util.Log;

import android.view.MenuItem;

import android.view.Window;
import android.view.WindowManager;


import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.example.tay.eventi4all_def.Firebase.FirebaseAdmin;
import com.example.tay.eventi4all_def.Firebase.MyFirebaseInstanceIDService;
import com.example.tay.eventi4all_def.Firebase.MyFirebaseMessagingService;
import com.example.tay.eventi4all_def.adapter.ViewPagerAdapter;
import com.example.tay.eventi4all_def.fragments.CustomDialogFragment_CreateEvents;
import com.example.tay.eventi4all_def.fragments.CustomDialogFragment_QR;
import com.example.tay.eventi4all_def.fragments.CustomDialogFragment_takeAPhoto;
import com.example.tay.eventi4all_def.fragments.EventContentFragment;
import com.example.tay.eventi4all_def.fragments.ListPublicEventsFragment;
import com.example.tay.eventi4all_def.fragments.MainFragment;
import com.example.tay.eventi4all_def.fragments.NotificationFragment;
import com.example.tay.eventi4all_def.fragments.ProfileFragment;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

import me.leolin.shortcutbadger.ShortcutBadger;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


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
    private CustomDialogFragment_QR customDialogFragment_qr;
    private CustomDialogFragment_takeAPhoto customDialogFragment_takeAPhoto;
    private MyFirebaseInstanceIDService myFirebaseInstanceIDService;
    private NotificationFragment notificationFragment;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EventContentFragment eventContentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.eventContentFragment = (EventContentFragment)this.getSupportFragmentManager().findFragmentById(R.id.frgEventContent);
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(this.eventContentFragment);
        fragmentTransaction.commit();

        bottomNavigationView = findViewById(R.id.navigation);
        //bottomNavigationView.setItemTextColor(getColorStateList(R.color.colorGreen));

        mainActivityEvents = new MainActivityEvents(this);
        this.eventContentFragment.setiEventContentFragmentListener(this.mainActivityEvents);

        //Instancia de la clase SignIn
        //Instancia de FirebaseAdmin
        this.firebaseAdmin = new FirebaseAdmin();

        //Instanciamos el abstractFirebaseAdmin
        this.firebaseAdmin.setAbstractFirebaseAdminListener(this.mainActivityEvents);

        //this.createEventFragment = (CreateEventFragment)this.getSupportFragmentManager().findFragmentById(R.id.createEventFragment);

        customDialogFragment_createEvents= new CustomDialogFragment_CreateEvents();
        customDialogFragment_qr = new CustomDialogFragment_QR();
        customDialogFragment_takeAPhoto = new CustomDialogFragment_takeAPhoto();

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
        //Permisos de notificación que solo se piden al instalar la aplicación
        if(DataHolder.MyDataHolder.token!=null){
            notificationSettings(this);
            notificationApp(this);
            openPowerSettings(this);
            enableAutoStart();
        }




    }
/*
Cuando se vuelva a abrir la aplicación sehace una llamada ver si hay invitaciones en la bbdd.
De esta forma da la sensación de quw al llegar una push notification se genera el card
 */
    @Override
    protected void onResume() {
        super.onResume();
        if(this.notificationFragment.getNotificationFragmentEvents()!=null){
            this.notificationFragment.getNotificationFragmentEvents().getInvitations();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.notificationFragment.getArrCards()!=null && this.notificationFragment.getArrCards().size()>0){
            int badgeCount = this.notificationFragment.getArrCards().size();
            ShortcutBadger.applyCount(this, badgeCount); //for 1.1.4+
        }else{
            ShortcutBadger.removeCount(this);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        mainFragment = new MainFragment();
        listPublicEventsFragment = new ListPublicEventsFragment(); //createEventFragment = new CreateEventFragment()
        notificationFragment = new NotificationFragment();
        this.mainFragment.setiMainFragmentListener(this.mainActivityEvents);
        this.notificationFragment.setiNofiticationFragmentListener(this.mainActivityEvents);

        //Profile sin uso de momento
        profileFragment = new ProfileFragment();
        this.profileFragment.setiProfileFragmentListener(this.mainActivityEvents);
        this.profileFragment.setiGalleryAndCapturePhotoListener(this.mainActivityEvents);


        this.listPublicEventsFragment.setIListPublicEventsFragmentListener(this.mainActivityEvents);

        this.customDialogFragment_createEvents.setiCreateEventFragmentListener(this.mainActivityEvents);


        this.customDialogFragment_createEvents.setiGalleryAndCapturePhotoListener(this.mainActivityEvents);

        this.customDialogFragment_qr.setiCustomDialogFragment_qrListener(this.mainActivityEvents);

        this.customDialogFragment_takeAPhoto.setiCustomDialogFragment_takeAPhotoListener(this.mainActivityEvents);

        adapter.addFragment(mainFragment);
        adapter.addFragment(listPublicEventsFragment);
        adapter.addFragment(notificationFragment);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        /*
        PARA ESTABLECER EL NUMERO DE PAGINAS QUE SE QUEDARÁN EN SEGUNDO PLANO
        Y DE ESTA FORMA QUE NO ESTÉ RECARGANDO
         */

        viewPager.setOffscreenPageLimit(2);







    }

    private void notificationApp(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Para un uso óptimo de Eventy4All, proporcione los permisos: 'Vista de prioridades', " +
                "'Banners' y 'Pantalla de bloqueo' que se encuentran en el apartado 'Notificaciones'.")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try{
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }catch(Exception e){
                        }

                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void notificationSettings(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Es recomendable proporcionar permisos a 'Permitir vista previa'.")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try{
                            Intent intent = new Intent();
                            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");


                            intent.putExtra("app_package", getPackageName());
                            intent.putExtra("app_uid", getApplicationInfo().uid);


                            intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());

                            startActivity(intent);
                        }catch(Exception e){
                        }

                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }


    private void openPowerSettings(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Para recibir notificaciones cuando la aplicación esté cerrada, " +
                "necesitamos que proporcione permisos de omisión de ahorro de batería. Marque 'se permite' para Eventy4All")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                        context.startActivity(intent);
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();


    }

    private void enableAutoStart() {
        if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
            new MaterialDialog.Builder(MainActivity.this).title("Proporcione permisos para poder ejecutar la aplicación en segundo plano.")
                    .content(
                            "Para el correcto funcionamiento de Eventy4All necesitamos que proporcione permisos a esta aplicación para que pueda ejecutarse en segundo plano cuando la pantalla de tu dispositivo esté apagada." +
                                    "Añada la aplicación a la lista de aplicaciones protegidas.")
                    .theme(Theme.LIGHT)
                    .positiveText("Aceptar")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName("com.miui.securitycenter",
                                    "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                            startActivity(intent);
                        }
                    })
                    .show();
        } else if (Build.BRAND.equalsIgnoreCase("Letv")) {
            new MaterialDialog.Builder(MainActivity.this).title("Proporcione permisos para poder ejecutar la aplicación en segundo plano.")
                    .content(
                            "Para el correcto funcionamiento de Eventy4All necesitamos que proporcione permisos a esta aplicación para que pueda ejecutarse en segundo plano cuando la pantalla de tu dispositivo esté apagada." +
                                    "Añada la aplicación a la lista de aplicaciones protegidas.")
                    .theme(Theme.LIGHT)
                    .positiveText("Aceptar")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName("com.letv.android.letvsafe",
                                    "com.letv.android.letvsafe.AutobootManageActivity"));
                            startActivity(intent);
                        }
                    })
                    .show();
        }else if (Build.BRAND.equalsIgnoreCase("Huawei")) {
            new MaterialDialog.Builder(MainActivity.this).title("Proporcione permisos para poder ejecutar la aplicación en segundo plano.")
                    .content(
                            "Para el correcto funcionamiento de Eventy4All necesitamos que proporcione permisos a esta aplicación para que pueda ejecutarse en segundo plano cuando la pantalla de tu dispositivo esté apagada." +
                                    "Añada la aplicación a la lista de aplicaciones protegidas.")
                    .theme(Theme.LIGHT)
                    .positiveText("Aceptar")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
                            startActivity(intent);
                        }
                    })
                    .show();
        } else if (Build.BRAND.equalsIgnoreCase("Asus")) {
            new MaterialDialog.Builder(MainActivity.this).title("Proporcione permisos para poder ejecutar la aplicación en segundo plano.")
                    .content(
                            "Para el correcto funcionamiento de Eventy4All necesitamos que proporcione permisos a esta aplicación para que pueda ejecutarse en segundo plano cuando la pantalla de tu dispositivo esté apagada." +
                                    "Añada la aplicación a la lista de aplicaciones protegidas.")
                    .theme(Theme.LIGHT)
                    .positiveText("Aceptar")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.MainActivity"));
                            startActivity(intent);
                        }
                    })
                    .show();
        }else if (Build.BRAND.equalsIgnoreCase("Igoo")) {
            new MaterialDialog.Builder(MainActivity.this).title("Proporcione permisos para poder ejecutar la aplicación en segundo plano.")
                    .content(
                            "Para el correcto funcionamiento de Eventy4All necesitamos que proporcione permisos a esta aplicación para que pueda ejecutarse en segundo plano cuando la pantalla de tu dispositivo esté apagada." +
                                    "Añada la aplicación a la lista de aplicaciones protegidas.")
                    .theme(Theme.LIGHT)
                    .positiveText("Aceptar")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            try{
                                Intent intent = new Intent();
                                intent.setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"));
                                startActivity(intent);
                            }catch(Exception e){
                                try{
                                    Intent intent = new Intent();
                                    intent.setComponent(  new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"));
                                    startActivity(intent);
                                }catch(Exception ex){

                                }


                            }

                        }
                    })
                    .show();
        }else if (Build.BRAND.equalsIgnoreCase("Honor")) {
            new MaterialDialog.Builder(MainActivity.this).title("Proporcione permisos para poder ejecutar la aplicación en segundo plano.")
                    .content(
                            "Para el correcto funcionamiento de Eventy4All necesitamos que proporcione permisos a esta aplicación para que pueda ejecutarse en segundo plano cuando la pantalla de tu dispositivo esté apagada." +
                                    "Añada la aplicación a la lista de aplicaciones protegidas.")
                    .theme(Theme.LIGHT)
                    .positiveText("Aceptar")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName("com.huawei.systemmanager",
                                    "com.huawei.systemmanager.optimize.process.ProtectActivity"));
                            startActivity(intent);
                        }
                    })
                    .show();
        } else if (Build.MANUFACTURER.equalsIgnoreCase("oppo")) {
            new MaterialDialog.Builder(MainActivity.this).title("Proporcione permisos para poder ejecutar la aplicación en segundo plano.")
                    .content(
                            "Para el correcto funcionamiento de Eventy4All necesitamos que proporcione permisos a esta aplicación para que pueda ejecutarse en segundo plano cuando la pantalla de tu dispositivo esté apagada." +
                                    "Añada la aplicación a la lista de aplicaciones protegidas.")
                    .theme(Theme.LIGHT)
                    .positiveText("Aceptar")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            try {
                                Intent intent = new Intent();
                                intent.setClassName("com.coloros.safecenter",
                                        "com.coloros.safecenter.permission.startup.StartupAppListActivity");
                                startActivity(intent);
                            } catch (Exception e) {
                                try {
                                    Intent intent = new Intent();
                                    intent.setClassName("com.oppo.safe",
                                            "com.oppo.safe.permission.startup.StartupAppListActivity");
                                    startActivity(intent);
                                } catch (Exception ex) {
                                    try {
                                        Intent intent = new Intent();
                                        intent.setClassName("com.coloros.safecenter",
                                                "com.coloros.safecenter.startupapp.StartupAppListActivity");
                                        startActivity(intent);
                                    } catch (Exception exx) {

                                    }
                                }
                            }
                        }
                    })
                    .show();
        } else if (Build.MANUFACTURER.contains("vivo")) {
            new MaterialDialog.Builder(MainActivity.this).title("Proporcione permisos para poder ejecutar la aplicación en segundo plano.")
                    .content(
                            "Para el correcto funcionamiento de Eventy4All necesitamos que proporcione permisos a esta aplicación para que pueda ejecutarse en segundo plano cuando la pantalla de tu dispositivo esté apagada." +
                                    "Añada la aplicación a la lista de aplicaciones protegidas.")
                    .theme(Theme.LIGHT)
                    .positiveText("Aceptar")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            try {
                                Intent intent = new Intent();
                                intent.setComponent(new ComponentName("com.iqoo.secure",
                                        "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"));
                                startActivity(intent);
                            } catch (Exception e) {
                                try {
                                    Intent intent = new Intent();
                                    intent.setComponent(new ComponentName("com.vivo.permissionmanager",
                                            "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                                    startActivity(intent);
                                } catch (Exception ex) {
                                    try {
                                        Intent intent = new Intent();
                                        intent.setClassName("com.iqoo.secure",
                                                "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager");
                                        startActivity(intent);
                                    } catch (Exception exx) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }
                    })
                    .show();
        }
    }











    public void setDataOfActivity() {
        this.firebaseAdmin.onCreate();

        this.firebaseAdmin.checkUserExist();

        this.firebaseAdmin.getStorageRef();



        setupViewPager(viewPager);
        if(DataHolder.MyDataHolder.token!=null){
            this.firebaseAdmin.insertDeviceToken(DataHolder.MyDataHolder.token);
        }

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

    public NotificationFragment getNotificationFragment() {
        return notificationFragment;
    }

    public void setNotificationFragment(NotificationFragment notificationFragment) {
        this.notificationFragment = notificationFragment;
    }

    public EventContentFragment getEventContentFragment() {
        return eventContentFragment;
    }

    public void setEventContentFragment(EventContentFragment eventContentFragment) {
        this.eventContentFragment = eventContentFragment;
    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public CustomDialogFragment_QR getCustomDialogFragment_qr() {
        return customDialogFragment_qr;
    }

    public void setCustomDialogFragment_qr(CustomDialogFragment_QR customDialogFragment_qr) {
        this.customDialogFragment_qr = customDialogFragment_qr;
    }

    public CustomDialogFragment_takeAPhoto getCustomDialogFragment_takeAPhoto() {
        return customDialogFragment_takeAPhoto;
    }

    public void setCustomDialogFragment_takeAPhoto(CustomDialogFragment_takeAPhoto customDialogFragment_takeAPhoto) {
        this.customDialogFragment_takeAPhoto = customDialogFragment_takeAPhoto;
    }
}
