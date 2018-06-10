
package com.example.tay.eventi4all_def.GoogleVisionAPI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tay.eventi4all_def.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import java.io.IOException;

//Activity donde enviamos la imagen tomada
public class FaceDetectionActivity extends AppCompatActivity {

    //Variable Tag que se mostrará en un Toast si se detecta la cara en la imagen
    private static final String TAG = "FaceDetection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detection);

        /*
            Creamos una la variable de la clase FaceDetector de la API Google Vision
            que se encargará de detectar los rostros.
            TrackingEnabled se establece a false dado que vamos a detectar el rostro en una
            imagen fija/estática.
            Con setLandmarkType podemos establecer
             si no detectan puntos de referencia o todos los puntos de referencia de la cara.
             Es decir estableces que queremos todos los puntos tales como ojos, boca, etc.
         */
        final FaceDetector detector = new FaceDetector.Builder(this)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .build();



/////////////////////1ª PARTE SETEO IMAGEN IMAGE VIEW//////////////////////////////////////
        //Recuremos del intent la ruta de la imagen
        Intent intent = getIntent();
        final ImageView imageView = findViewById(R.id.imageView);
        final String mCurrentPhotoPath = intent.getStringExtra("mCurrentPhotoPath");

        // ejecutamos el código relacionado con la imagen después de que se visualice en la vista
        // calculamos todas dimensiones que tendrá imagen
        imageView.post(new Runnable() {
            @Override
            public void run() {
                    if (mCurrentPhotoPath != null) {
                     /*
                        Mediante el método getBitmapFromPathForImageView que creamos
                        vamos a crear un mapa de bytes de esa imagen
                        mediante la ruta donde se encuentra y luego se la setearemos
                        al imageView para verla.
                      */
                        Bitmap bitmap = getBitmapFromPathForImageView(mCurrentPhotoPath, imageView);
                        //imageView.setImageBitmap(bitmap);

                    /*
                        Añadimos una variable de tipo Frame a la que le vamos a setear el bitmap
                        que hemos obtenido del nuetro método getBitmapFromPathForImageView.
                     */
                    /*
                        Vision Api utiliza su propio Marco llamo Frame que represneta los datos de
                        imagen asociados con su metadata.
                        Se construye un marco a través de la builder clase, especificando los datos de imagen,
                        las dimensiones y la información de secuencia ( es decir mediante nuestor bitmap
                        que proporciona estos valores) Estos datos se conocen como Metadata

                     */
                        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                        /*
                        Usaremos el método detect de la clase FaceDetector que ejecuta la detección de todos los
                        elementos de la cara asociados a ids que se vamos a almacenar en un tipo de de array que
                        es muy parecido a un hashmap, llamado SparseArray.
                        Si tenemos el seguimiento continua de las caras es decir por ejemplo cuando capturamos
                        video entonces los elementos de la cara se van sobreescribiendo en sus correspondientes ids
                        con el movimiento.
                         */
                        SparseArray<Face> faces = detector.detect(frame);
                        System.out.println("Faces detected: " + String.valueOf(faces.size()));





                        //Ahora que está detectada la cara podemos dibujar un recuadro rojo alrededor de ella:

                        /*
                            Primero vamos a crear un dibujado mediante la clase paint que dibujará
                            un trazo (Stroke) de color verde.
                            Este pintado representará cada punto de la cara.
                         */
                        Paint paint = new Paint();
                        paint.setColor(Color.GREEN);
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setStrokeWidth(5);
                        /*
                        Creamos un bitmap a partir de la copia del que ya tenemos con nuestra foto
                        pero este será mutable es lo mismo que String y StringBuffer.
                        Los bitmap mutables pueden cambiar su contenido de pixeles.
                        También vamos a especificar el formato del bitmap mediante ARGB_8888 que lo
                        que hace es devolver los valores alfa de esta imagen apra que esta pueda ser
                        manipulada dado que queremos pintar sobre ella.

                         */
                        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                        /*
                            Ahora que ya tenemos la herramienta para pintar (por así decirlo nuestra brocha),
                            vamos a crear un canvas que contendrá el bitmap mutable para pintar con la
                            brocha (variable paint) nuestros puntos.
                            Recorremos el array SparseArray que contiene todos los objetos cara con sus
                            correspondientes elementos de la cara.
                            En el for lo que hacemos es recorer todas las caras que detecte y por cada una,
                            la guardamos en una variable cara y sacamos esos elementos de la cara
                            conocimos como landmark.
                            Por último, obtenemos las posiciones x e y de estos puntos y pintamos un ciruclo
                            en el canvas mediante nuestra brocha "paint"

                         */
                        Canvas canvas = new Canvas(mutableBitmap);
                        Bitmap glasses = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.glasses);
                        for (int i = 0; i < faces.size(); ++i) {
                            Face face = faces.valueAt(i);
                            for (Landmark landmark : face.getLandmarks()) {

                                int cx = (int) (landmark.getPosition().x);
                                int cy = (int) (landmark.getPosition().y);
                                //canvas.drawCircle(cx, cy, 10, paint);



                            }


                            /*
                                Ahora vamos a pintar un recuadro sobre las caras para delimitarlas

                             */

                            /*
                                Primero creamos un trazo con la clase Path que hereda de Graphics.
                                No confundir con el path de ruta.
                                Establecemos el inicio del pintamos en las coordenads x e y de la cara.
                                Pintamos primero la lína de la posición x + el ancho hasta e y y tendremos
                                la lína horizontal superior, después pintamos a partir de la neuva x e y a lo
                                ancho y lo largo y tendremos también la altura derecha de la cara y una diagonal
                                hasta el final de esta línea vertial.
                                Por último, desde esa nueva posición pintamos la lína que nos queda.



                             */
                            Path path = new Path();
                            path.moveTo(face.getPosition().x, face.getPosition().y);
                            path.lineTo(face.getPosition().x + face.getWidth(), face.getPosition().y);
                            path.lineTo(face.getPosition().x + face.getWidth(), face.getPosition().y + face.getHeight());
                            path.lineTo(face.getPosition().x, face.getPosition().y + face.getHeight());
                            path.close();

                            //Creamos la brocha para pintar este trazo y la usamos en el canvas
                            Paint redPaint = new Paint();
                            redPaint.setColor(0XFFFF0000);
                            redPaint.setStyle(Paint.Style.STROKE);
                            redPaint.setStrokeWidth(8.0f);
                            canvas.drawPath(path, redPaint);



                            /*
                                Para poder setear una imagen como si se tratase de un filtro,
                                usamos las siguientes coordenadas.
                             */


                            float x = face.getPosition().x + face.getWidth() / 2;
                            float y = face.getPosition().y + face.getHeight() / 2;
                            float xOffset = face.getWidth() / 2.0f;
                            float yOffset = face.getHeight() / 2.0f;
                            float left = x - xOffset;
                            float top = y - yOffset;
                            float right = x + xOffset;
                            float bottom = y + yOffset;
                            //Creamos un bitmap con la imagen que vamos a pintar en el canvas
                            Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.glasses);
                            canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap2, Math.round(face.getWidth()), Math.round(face.getHeight()), true), left, top+100, new Paint());

                        }
                        //Por último, seteamos la imagen al image view apra ver el resultado.
                        imageView.setImageBitmap(mutableBitmap);
                    }
            }




        });
    }


    private Bitmap getBitmapFromPathForImageView(String mCurrentPhotoPath, ImageView imageView) {
        /*
         Rercogemos las dimensiones de la view donde va a ir seteada la imagen para ajustarla
         lo mejor posible
          */

        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        /*
            Cogemos las dimensiones del bitmap que vamos a crear a partir
            de la imagen que se encuentra en la ruta mCurrentPhotoPath
         */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        /*
            Para obtener el bitmap vamos a decodificar la imagen y podemos pasarle como
            parámetro ciertas opciones en nuestro caso vamos a inJustDecodeBonds true.
            Cada aplicación en el sistema tiene reservados aproximadamente 16 MB de memoria,
            la cual ya estamos usando para otras cosas como reservar variables y objetos, por
            lo que si empezamos a cargar bitmaps es probable que la acabemos pronto y entonces
            el sistema cerrará nuestra aplicación.
            inJustDecodeBounds nos va a permitir reducir el tamaño del bitmap antes de cargarlo.
            Imaginad por ejemplo que tenéis una foto de 13 Mpx, como es el caso de las que hace
            mi cámara, pero vosotros sólo queréis mostrar un recuadro de 300dp como vista previa.
            En este caso utilizaríamos inJustDecodeBonds

         */
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        //Y guardamos las dimensiones en el bitmap
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        /*
        Mediante  Math.min determinamos cuanto tenemos que ajustar el bitmap
        para que cuadre bien con las dimensiones del imageView.
         */
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        /*
            De esta forma al estar calculada el tamaño y dimensión perfecta que necesitamos
            para establecer la imagen en el imageView entonces ahora volveremos a decodificar
            el archivo de la imagen bitmap que habíamos decodificado para saber sus dimensiones.
            Para decodificarlo, ahora usaremos la dimensión que hemos guardado scaleFactor
            que es la dimensión idónea. Ya no queremos que se reduzca el tamaño porque ya lo reducimos
            en el bitmap y ademñas los hemos ajustado en scaleFsctor.
            Ahora le pasamos estas opciones extras y lo decodificamos para guardarlo en una variable
            bitmap.
         */
        /*
        Por lo tanto hemos usamos las opciones del bitmap para ajustar el tamaño con inJustDecodeBounds
        y scaleFactor = Math.min y una vez escalado el tamaño y reducido entonces volvemos a decodificar
        la foot con estas opciones y la guardamos en un bitmap que será
         */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        Bitmap rotatedBitmap = bitmap;

        // rotamos el bitmap si es necesario para que siempre se vea vertical y recto.
        /*
        Esto sirve por si hacemos la foto torcida, apra que siempre la setea al image view
        recta. por lo tanto mediante el método rotateImage que creamos pasamos el bitmap
        y el angulo en el que se encuentre echa la foto, si es 0 grados entonces directamente
        retornamos el rotatedBitmap pero si tiene alguna inclinación entonces la corregimos.
         */
        try {
            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;
            }
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return rotatedBitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        /*
        La clase Matrix sirve para escalar y rotar bitmaps
         */
        /*
        setRotate reemplazará las operaciones matriciales ya realizadas con
        la rotación especificada.
        postRotate usará los valores de matriz actuales
        y los transformará utilizando la rotación especificada.
         */
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
