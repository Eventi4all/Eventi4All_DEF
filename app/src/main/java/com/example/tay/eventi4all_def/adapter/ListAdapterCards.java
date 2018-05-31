package com.example.tay.eventi4all_def.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.tay.eventi4all_def.R;
import com.example.tay.eventi4all_def.entity.Card;
import com.example.tay.eventi4all_def.entity.Event;
import com.example.tay.eventi4all_def.fragments.NotificationFragment;
import com.tabassum.shimmerRecyclerView.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by tay on 28/11/17.
 */


public class ListAdapterCards extends ShimmerRecyclerView.Adapter<MyViewHolderCards> {
    private ArrayList<Card> contenidoLista; //declaramos un array que contiene contenido que queremos que s epinte en las celdas de la lista
    private Context context; // esta variable la creamos dado que la librería Glide para cargar imagenes desde firebase necesita una variable de tipo contexto
    private NotificationFragment notificationFragment;
    public ListAdapterCards(ArrayList<Card> contenidoLista, Context context, NotificationFragment notificationFragment) { // ListAdapter recibe como parámetro el context de Glide, en nuestro caso el contexto es el padre donde se encuentra que al fin de cuentas el list se encuentra en el second Activity
        this.contenidoLista = contenidoLista;
        this.context = context;
        this.notificationFragment=notificationFragment;
    }


    @Override
    public MyViewHolderCards onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell_layoutcardsnotification, null); // se pone null porque no tiene padre de jerarquía. Normalmente lo dejaremos a null.
        MyViewHolderCards viewHolder = new MyViewHolderCards(view);
        viewHolder.setMyViewHolderCardListener(this.getNotificationFragment().getNotificationFragmentEvents());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolderCards holder, int position) {

        if(this.contenidoLista.get(position).eventTitle.length()>20){
            holder.getTxtTitleEvent().setText(this.contenidoLista.get(position).eventTitle.substring(0,15) + "...");
        }else{
            holder.getTxtTitleEvent().setText(this.contenidoLista.get(position).eventTitle);
        }
        holder.getTxtCreateAt().setText(this.contenidoLista.get(position).createBy);
        Glide.with(getContext().getApplicationContext()).load(this.getContenidoLista().get(position).imgEvent).into(holder.getImageViewCover());

    }

    @Override
    public int getItemCount() {
        return this.getContenidoLista().size();
    }


    public ArrayList<Card> getContenidoLista() {
        return contenidoLista;
    }

    public void setContenidoLista(ArrayList<Card> contenidoLista) {
        this.contenidoLista = contenidoLista;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public NotificationFragment getNotificationFragment() {
        return notificationFragment;
    }

    public void setNotificationFragment(NotificationFragment notificationFragment) {
        this.notificationFragment = notificationFragment;
    }


}
