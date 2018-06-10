package com.example.tay.eventi4all_def.GoogleVisionAPI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.tay.eventi4all_def.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity_GoogleVision extends AppCompatActivity {

    //cte que presenta la petición al activity de la cámara
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    //variable que representa la ruta d ela foto actualmente
    private String mCurrentPhotoPath;
    //cte que presenta la petición para los permisos de escritura de la img
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2;
    /*
    Los request serán devueltos en el onActivityResult si se han realizado con
    éxito estas peticiones.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_face_detection);

    }



    //el onclick llama a este método para inciar el activity d ela cámara
    public void onImageFromCameraClick(View view) {
        //Creamos un intent para abrir el activity de la cámara
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        /*
            Realizamos una comprobación previa mediante el filtro resolveActivity
            que nos asegura si hay una una actividad de cámara para manejar la intención
         */

        if (intent.resolveActivity(getPackageManager()) != null){
            /*
                La idea es crear un archivo de la imagen que capturemos.
                Para ello creamos primero un archivo de tipo imagen mediante el método
                que creamos createImageFile.
                Este archivo se lo vamos a pasar  al intent mediante la URI
                 junto a la petición
                para abrir la cámara de código 1.
             */
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            }
            /*
                Como contenido extra añadimos la URI (recurso) de la foto que será el conjunto
                entre URL + URN es decir la ruta que daremos a la imagen y su propia imagen.
                También añadiremos  EXTRA_OUTPUT que cogerá la imagen tomada y la
                 escribirá en esa ruta.

             */
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        //Comprobamos primero si tenemos permisos de escritura apra la img, es decir si son oguales a granted(conceidos)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Si no los tenemos concedidos entonces se inicia la petición para concederlos
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            // Si los permisos están concedidos entonces creamos:
            /*
            un timestamp para la img que es la fecha en el momento en el que se crea la img,
            el nombre de la imagen, el directorio donde queremos que se guarde
             */
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            //creamos el archivo temporal con estos parámetros mediante la clase FILE.
            File image = File.createTempFile(
                    imageFileName,  /* prefijo */
                    ".jpg",         /* sufijo */
                    storageDir      /* directorio */
            );
            mCurrentPhotoPath = image.getAbsolutePath(); // guardamos la ruta absoluta de la imagen para usarla en el intent de la cámara

            return image;
        }

        return null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
            Si la cámara ha podido ser abierta y se ha tomado la imagen entonces
            realizamos un intent al FaceDetectionActivity y le enviamos la ruta
            absoluta actual de la imagen.
         */

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, FaceDetectionActivity.class);
            intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);
            startActivity(intent);

        }
    }

    //el onclick del segundo botón llamada a este método para iniciar el activity VideoFaceDetectionActivity
    public void onVideoFromCameraClick(View view) {
        Intent intent = new Intent(this, VideoFaceDetectionActivity.class);
        startActivity(intent);
        System.out.println("start VideoFaceDetectionActivity realizado");
    }
}
