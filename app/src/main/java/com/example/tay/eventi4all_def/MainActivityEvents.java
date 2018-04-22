package com.example.tay.eventi4all_def;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;


import com.example.tay.eventi4all_def.Firebase.AbstractFirebaseAdminListener;
import com.example.tay.eventi4all_def.fragments.IMainFragmentListener;
import com.example.tay.eventi4all_def.fragments.IProfileFragmentListener;

import java.io.File;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * Created by tay on 17/4/18.
 */

public class MainActivityEvents extends AbstractFirebaseAdminListener implements IMainFragmentListener, IProfileFragmentListener{
    private MainActivity mainActivity;
    //Directorio principal donde se almacenan las imagenes
    private static String APP_DIRECTORY = "Eventy4All/";
    //Subcarpeta del driectorio principal que contiene las imagenes
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "ProfilePicture";


    //Para propocionar un código de permiso para tomar una foto
    private final int MY_PERMISSIONS =100;
    //Para proporcionar permisos para abrir la cámara
    private final int PHOTO_CODE =200;
    //Para proporcionar permisos apra abrir la galería
    private final int SELECT_PICTURE =300;
    //Almacenamos la ruta donde se guarda la img
    private String mPath;

    public MainActivityEvents(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }



    public MainActivity getMainActivity() {

        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }



    @Override
    public void checkUserExist(boolean isUserExist) {
        FragmentTransaction transition = this.mainActivity.getSupportFragmentManager().beginTransaction();

        if(isUserExist){
            //redirigimos al fragment principal
            transition.show(this.mainActivity.getMainFragment());
            transition.hide(this.mainActivity.getProfileFragment());
            System.out.println("------------------------------------>>>>>>>>>>>>>EXISTE isUserExist" );

        }else{
            //redirigimos al fragment de creación de perfil
            transition.show(this.mainActivity.getProfileFragment());
            transition.hide(this.mainActivity.getMainFragment());
            System.out.println("------------------------------------>>>>>>>>>>>>> NO EXISTE isUserExist" );
        }
        transition.commit();
        System.out.println("------------------------------------>>>>>>>>>>>>> COMMIT isUserExist" );

    }

    @Override
    public void logout(boolean isLogout) {
        if(isLogout){
            System.out.println("----------> SESIÓN CERRADA SATISFACTORIAMENTE <----------");
            this.mainActivity.getSignIn().signInAllProviders();
        }else{
            //Se muestra mensaje de error al usuario
        }
    }

    @Override
    public void callLogoutMainActivity() {
        this.mainActivity.getFirebaseAdmin().logoutFirebase(this.mainActivity);
    }

    @Override
    public void executeOptions(int option) {
        if(option == 0){
           this.openCamera();
        }else if(option == 1){
            /*
            Hacemos un intent abrir la galería(ACTION_PICK es una listas de cosas de las que se puede seleccionar,
            y EXTERNAL_CONTENT_URI es para abrir el elemento seleccionado de la lista.
             */
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //Tipo de archivo que queremos seleccionar "/*" cualquier extensión
            intent.setType("image/*");
            /*
            Iniciamos el intent(createChooser es para que me salgan todas las imagenes de cualqueir aplicación
            para poder seleccinarlas
             */
           this.mainActivity.startActivityForResult(Intent.createChooser(intent, "Elige una imagen de la galería"),SELECT_PICTURE);

        }
    }



    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(),MEDIA_DIRECTORY);
        //Nos devuelve true/false si el directorio APP_DIRECTORY está creado
        boolean isDirectoryCreated = file.exists();
        if(!isDirectoryCreated){
            System.out.println("------------------------------>>>>>>>>>>>El directorio no existe");
            //Si no está creado, lo creamos y la variable la establecemos a true
            System.out.println("------------------------------>>>>>>>>>>>CREAMOS EL DIRECTORIO");
            isDirectoryCreated = file.mkdirs();


        }

        if(isDirectoryCreated){
            System.out.println("------------------------------>>>>>>>>>>>El directorio YA EXISTE");

            /*
            Formato de fecha y hora que usaremos para poner de nombre a nuestras imágenes y
            de esta manera no se repiten los nombres de las imagenes, podriamos usar un uid aleatorio.
             */

            Long timestamp = System.currentTimeMillis()/1000;
            //Obtenemos el nombre de la imagen cy le damos una extensión
            String imageName = timestamp.toString() + ".jpg";

            /*
            Especificamos la ruta completa donde queremos que se guarden las imágenes.
            En el directorio externo de almacenamiento seguida de nuestra ruta y el nombre
            de la imagen. El file.separator es la barra /. Se podría poner / directamente concatenado
             */
            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + imageName;

            //Creamos un archivo pasándole la ruta de escritura de esta imagen. Este archivo será la imagen que capturemos de la cámara
            File newFile = new File(mPath);

            //Abrimos la cámara
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            /*
            Para pasar valores entre dos activities. El mainAcitvity y el activity de la cámara.
            Le pasamos al activity de la cámara un URI es decir un recurso (URL + URN).
            Este recurso es nuestro "newFile" al cual se seteará la imagen que tomemos de la cámara.
             */
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            DataHolder.MyDataHolder.imgUri = Uri.fromFile(newFile);
            this.mainActivity.startActivityForResult(intent, PHOTO_CODE);
            System.out.println("------------------------------>>>>>>>>>>>START ACTIVITY CAMARA");
        }
    }


    //Método para proporcionar permisos a la cámara y galería
    @Override
    public boolean mayRequestStoragePermission(){
        /*
        si la versión del S.0 es inferior a la 6.0, entonces no tenemos que proporcionar permisos porque
          ya los tiene. Dado que en las versiones anteriores de Android directamente busca en el AndroidManifiest
          los permisos que hemos agregado.
         */

        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.M){
            return true;
        }

        //Verificamos is nuestro dispositivo con Android 6.0 ya tiene los permisos configurados
        // el de la cámara y el de escritura.
        //Al dar permisos para un tipo de permiso se aceptan automaticamente toos los permisos que estén en el mismo grupo de permisos.

        if((this.mainActivity.checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && (this.mainActivity.checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        /*
        Si es la primera vez que el usuario ha accedido a la aplicación tiene que aceptar
        los permisos de escritura y cámara mediante un cuadro de diálogo.
        El mensaje de dialogo será un snackbar en el profile fragment que además permanecerá durante
        un tiempo indefinido hasta que el usuario permisione el botoón ok.
        ESTE MENSAJE APARECE SI DENEGÓ LOS PERMISOS Y ENTONCES SE MUESTRA EL SNACKBAR EXPLICANDO POR QUÉ SON NECESARIOS
         */
        if(this.mainActivity.shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) || this.mainActivity.shouldShowRequestPermissionRationale(CAMERA)){
            Snackbar.make(this.mainActivity.getProfileFragment().getmRlView(),"Los permisos son necesarios para el uso de esta aplicación.", Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
               //Añadimos la anotación @TargeApi porque el método requestPermissions solo se puede usar a partir de la api 23(android 6.0)
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    //Si le da al "Ok" entonces proporcionamos los permisos
                    mainActivity.requestPermissions(new String[] {WRITE_EXTERNAL_STORAGE,CAMERA}, MY_PERMISSIONS);
                }
            }).show();

        //AGREGA LOS PERMISOS POR PRIMERA VEZ (IGUAL QUE EL ANTERIOR PERO EN EL ANTERIOR HAY UN MENSAJE DE INSISTENCIA SI DENIEGA LOS PERMISOS)
        }else {
            mainActivity.requestPermissions(new String[] {WRITE_EXTERNAL_STORAGE,CAMERA}, MY_PERMISSIONS);
        }
        return false;
    }

    @Override
    public void saveProfileInFirebase(Map<String, Object> user) {
        this.mainActivity.getFirebaseAdmin().insertDocumentInFirebase(user);
    }


    /*
    Método para crear el alertDialog que explica que si no damos permisos no podemos usar la aplicación
     */
    public void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mainActivity);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la aplicación necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                /*
                Hacemos un intent para abrir el activity con los ajustes de la aplicación
                para pdoer darle permisos manualmente.
                 */
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                /*
                Mediante estas dos líneas lo que hacemos es decirle el contexto del paquete que tiene que abrir
                es decir los ajustes de la aplicación que tiene que abrir que son lops de esta esta aplicación.
                 */
                Uri uri = Uri.fromParts("package", mainActivity.getPackageName(), null);
                intent.setData(uri);
                mainActivity.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //no podrá usar la aplicación si los permisos están denegados
                mainActivity.finish();
            }
        });

        builder.show();

    }

    public static String getAppDirectory() {
        return APP_DIRECTORY;
    }

    public static void setAppDirectory(String appDirectory) {
        APP_DIRECTORY = appDirectory;
    }

    public static String getMediaDirectory() {
        return MEDIA_DIRECTORY;
    }

    public static void setMediaDirectory(String mediaDirectory) {
        MEDIA_DIRECTORY = mediaDirectory;
    }

    public int getMY_PERMISSIONS() {
        return MY_PERMISSIONS;
    }

    public int getPHOTO_CODE() {
        return PHOTO_CODE;
    }

    public int getSELECT_PICTURE() {
        return SELECT_PICTURE;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }


}
