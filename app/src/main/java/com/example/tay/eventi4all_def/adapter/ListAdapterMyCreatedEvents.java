package com.example.tay.eventi4all_def.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.tay.eventi4all_def.MainActivityEvents;
import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.entity.Event;
import com.example.tay.eventi4all_def.fragments.MainFragment;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by tay on 28/11/17.
 */


public class ListAdapterMyCreatedEvents extends RecyclerView.Adapter<MyViewHolderCreatedEvents> {
    private ArrayList<Event> contenidoLista; //declaramos un array que contiene contenido que queremos que s epinte en las celdas de la lista
    private Context context; // esta variable la creamos dado que la librería Glide para cargar imagenes desde firebase necesita una variable de tipo contexto
    private MainFragment mainFragment;
    private ListAdapterCreatedEventsListener listAdapterCreatedEventsListener;
    public ListAdapterMyCreatedEvents(ArrayList<Event> contenidoLista, Context context) { // ListAdapter recibe como parámetro el context de Glide, en nuestro caso el contexto es el padre donde se encuentra que al fin de cuentas el list se encuentra en el second Activity
        this.contenidoLista = contenidoLista;
        this.context = context;
    }


    @Override
    public MyViewHolderCreatedEvents onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell_layoutcreatedevents, null); // se pone null porque no tiene padre de jerarquía. Normalmente lo dejaremos a null.
        MyViewHolderCreatedEvents viewHolder = new MyViewHolderCreatedEvents(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolderCreatedEvents holder, int position) {
        if(this.contenidoLista.get(position).title.length()>20){
            holder.getTxtTitleEvent().setText(this.contenidoLista.get(position).title.substring(0,15) + "...");
        }else{
            holder.getTxtTitleEvent().setText(this.contenidoLista.get(position).title);
        }
        holder.getTxtCreateAt().setText(this.contenidoLista.get(position).createAt);
        holder.setListAdapterCreatedEventsListener(this.listAdapterCreatedEventsListener);
        Glide.with(context).load(this.getContenidoLista().get(position).urlCover).apply(new RequestOptions().transforms( new CropSquareTransformation(),new RoundedCornersTransformation(40,15))).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).apply(RequestOptions.skipMemoryCacheOf(true)).thumbnail(0.3f).into(holder.getImageViewCover());


    }

    @Override
    public int getItemCount() {
        return this.getContenidoLista().size();
    }


    public ArrayList<Event> getContenidoLista() {
        return contenidoLista;
    }

    public void setContenidoLista(ArrayList<Event> contenidoLista) {
        this.contenidoLista = contenidoLista;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MainFragment getMainFragment() {
        return mainFragment;
    }

    public void setMainFragment(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    public ListAdapterCreatedEventsListener getListAdapterCreatedEventsListener() {
        return listAdapterCreatedEventsListener;
    }

    public void setListAdapterCreatedEventsListener(ListAdapterCreatedEventsListener listAdapterCreatedEventsListener) {
        this.listAdapterCreatedEventsListener = listAdapterCreatedEventsListener;
    }
}
