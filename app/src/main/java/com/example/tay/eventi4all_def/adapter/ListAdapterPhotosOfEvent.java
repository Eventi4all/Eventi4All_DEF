package com.example.tay.eventi4all_def.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.entity.Photo;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by tay on 28/11/17.
 */


public class ListAdapterPhotosOfEvent extends RecyclerView.Adapter<MyViewHolderPhotoOfEvents> {
    private ArrayList<Photo> contenidoLista; //declaramos un array que contiene contenido que queremos que s epinte en las celdas de la lista
    private Context context; // esta variable la creamos dado que la librería Glide para cargar imagenes desde firebase necesita una variable de tipo contexto
    public ListAdapterPhotosOfEvent(ArrayList<Photo> contenidoLista, Context context) { // ListAdapter recibe como parámetro el context de Glide, en nuestro caso el contexto es el padre donde se encuentra que al fin de cuentas el list se encuentra en el second Activity
        this.contenidoLista = contenidoLista;
        this.context = context;


    }

    @Override
    public MyViewHolderPhotoOfEvents onCreateViewHolder(ViewGroup parent, int viewType) { //creamos el molde de la celda y lo asociamos con su xml que es el que hemos creado llamado list_cell_layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell_layoutphotofevent, null); // se pone null porque no tiene padre de jerarquía. Normalmente lo dejaremos a null.
        MyViewHolderPhotoOfEvents viewHolder = new MyViewHolderPhotoOfEvents(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolderPhotoOfEvents holder, int position) {//pinta el contenido de los elementos de la celda a través del molde y para cada posición de las celda.
        holder.getTxtTitlePhoto().setText(this.getContenidoLista().get(position).titlePhoto);
        holder.getTxtPhotoCreatedBy().setText("Tomada por: " + this.getContenidoLista().get(position).createdBy);

        Glide.with(getContext().getApplicationContext()).load(this.getContenidoLista().get(position). urlPhoto).into(holder.getPhoto());



    }

    @Override
    public int getItemCount() {
        return this.getContenidoLista().size();// decimos que pinte tantas columnas de dos en dos como pusimos antes como size sea del arraylist
    } //extendemos del padre genérico adapter.
    //Necesitamos que recoja datos de tipo VH--> View Holder que es un dataholder basicamente que contiene los datos de la colección


    public ArrayList<Photo> getContenidoLista() {
        return contenidoLista;
    }

    public void setContenidoLista(ArrayList<Photo> contenidoLista) {
        this.contenidoLista = contenidoLista;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
