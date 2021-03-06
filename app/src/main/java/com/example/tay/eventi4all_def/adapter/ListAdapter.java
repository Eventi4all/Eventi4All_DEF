package com.example.tay.eventi4all_def.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.entity.User;
import com.example.tay.eventi4all_def.fragments.CustomDialogFragment_CreateEvents;


import java.util.ArrayList;

/**
 * Created by tay on 28/11/17.
 */


public class ListAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<User> contenidoLista; //declaramos un array que contiene contenido que queremos que s epinte en las celdas de la lista
    private Context context; // esta variable la creamos dado que la librería Glide para cargar imagenes desde firebase necesita una variable de tipo contexto
    private CustomDialogFragment_CreateEvents createEventFragment;
    public ListAdapter(ArrayList<User> contenidoLista, Context context, CustomDialogFragment_CreateEvents createEventFragment) { // ListAdapter recibe como parámetro el context de Glide, en nuestro caso el contexto es el padre donde se encuentra que al fin de cuentas el list se encuentra en el second Activity
        this.contenidoLista = contenidoLista;
        this.context = context;
        this.createEventFragment=createEventFragment;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //creamos el molde de la celda y lo asociamos con su xml que es el que hemos creado llamado list_cell_layout.
        //Este xml es la parte visual de la celda (lo que queremos que contenga)
        // Linkamos el ViewHolder(controlador) con su xml(vista).Para hacerlo se tiene que usar el método inflate.
        //En Android se considera que los xml son una especie de globos desinchados que hay que hinchar para darle una forma
        //antes de meter los contenidos.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell_layout, null); // se pone null porque no tiene padre de jerarquía. Normalmente lo dejaremos a null.
        MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.setMyViewHolderListener(this.getCreateEventFragment().getCreateEventFragmentEvents());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {//pinta el contenido de los elementos de la celda a través del molde y para cada posición de las celda.
        // holder.getTxtNombre().setText(this.getContenidoLista().get(position)); // por cada posición se pinta una posición del arraylist
        holder.getTxtUsrNickname().setText(this.getContenidoLista().get(position).nickName);
        /*
        En glide, tenemos una función que recibe por parámetro la url de la imagen, la descarga y la
        introduce en la caché. Po último, la sete a la variable img que tengamos declarada.
         */

        Glide.with(getContext().getApplicationContext()).load(this.getContenidoLista().get(position).urlImgProfile).into(holder.getImageViewProfile());



    }

    @Override
    public int getItemCount() {
        return this.getContenidoLista().size();// decimos que pinte tantas columnas de dos en dos como pusimos antes como size sea del arraylist
    } //extendemos del padre genérico adapter.
    //Necesitamos que recoja datos de tipo VH--> View Holder que es un dataholder basicamente que contiene los datos de la colección


    public ArrayList<User> getContenidoLista() {
        return contenidoLista;
    }

    public void setContenidoLista(ArrayList<User> contenidoLista) {
        this.contenidoLista = contenidoLista;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public CustomDialogFragment_CreateEvents getCreateEventFragment() {
        return createEventFragment;
    }

    public void setCreateEventFragment(CustomDialogFragment_CreateEvents createEventFragment) {
        this.createEventFragment = createEventFragment;
    }
}
