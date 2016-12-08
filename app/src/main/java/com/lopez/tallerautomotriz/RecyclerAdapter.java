package com.lopez.tallerautomotriz;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALEJANDRO on 10/10/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  {
    private static final String DEBUG_TAG="RecyclerAdapter";
    public Context context;
    public List<Card> cardsList;
    CardView cardView;
    private int lastPosition = -1;
    NetworkImageView imageView;
    public RecyclerAdapter(Context context, List<Card> cardsList) {
        this.context = context;
        this.cardsList = cardsList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
        View v = li.inflate(R.layout.cardview, viewGroup, false);
        cardView= (CardView) v.findViewById(R.id.cardView);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        int id=cardsList.get(position).getId();
        String titulo1 = cardsList.get(position).getTitulo();
        String imag = cardsList.get(position).getImagen();
        double precio=cardsList.get(position).getPrecio();

        TextView titulo = viewHolder.titulo;
        TextView precio1 = viewHolder.precio;
        imageView =viewHolder.imagen;

        titulo.setText(titulo1);
        imageView.setImageUrl(imag,VolleySingleton.getInstance().getImageLoader());
        precio1.setText("$"+precio);

        setAnimation(viewHolder.itemView);

    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
        viewHolder.itemView.clearAnimation();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder viewHolder) {
        super.onViewAttachedToWindow(viewHolder);
        //animateCircularReveal(viewHolder.itemView);
    }

  /*  public void animateCircularReveal(View view) {
        int centerX = (view.getWidth())/2;
        int centerY = (view.getHeight())/2;
        int startRadius = 0;
        int endRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        view.setVisibility(View.VISIBLE);
        animation.start();
    }*/



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titulo;
        private TextView precio;
        private NetworkImageView imagen;

        public ViewHolder(View v) {
            super(v);
            titulo = (TextView) v.findViewById(R.id.list_item_google_cards_shop_discount);
            precio = (TextView) v.findViewById(R.id.list_item_google_cards_shop_price);
            imagen = (NetworkImageView) v.findViewById(R.id.list_item_google_cards_shop_image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pair<View, String> pair = Pair.create(v.findViewById(R.id.list_item_google_cards_shop_image), "card");


                    ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,pair);
                    int adapterPosition = getAdapterPosition();
                    String title = cardsList.get(adapterPosition).getTitulo();
                    String image = cardsList.get(adapterPosition).getImagen();
                    double price=cardsList.get(adapterPosition).getPrecio();
                    String descript=cardsList.get(adapterPosition).getDescripcion();
                    int id=cardsList.get(adapterPosition).getId();
                    int iduser=cardsList.get(adapterPosition).getIdusua();

                    Intent enviar=new Intent(context,Detalle.class);
                    enviar.putExtra("titulo",title);
                    enviar.putExtra("imagen",image);
                    enviar.putExtra("precio",price);
                    enviar.putExtra("id",id);
                    enviar.putExtra("descript",descript);
                    enviar.putExtra("idusuario",iduser);

                    (context).startActivity(enviar,optionsCompat.toBundle());

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        if (cardsList.isEmpty()) {
            return 0;
        } else {
            return cardsList.size();
        }
    }


    private void setAnimation(View viewToAnimate)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);

    }
}
