package com.lopez.tallerautomotriz;

import android.app.Activity;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;

/**
 * Created by ALEJANDRO on 24/7/2016.
 */
public class GridAdapterView extends ArrayAdapter<Item> {

    private  Context context;
    int layoutResourceId;
    ArrayList<Item> datos=new ArrayList<Item>();

    public GridAdapterView(Context context, int layoutResourceId,ArrayList<Item> datos) {
        super(context, layoutResourceId,datos);

        this.layoutResourceId=layoutResourceId;
        this.context=context;
        this.datos=datos;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        View fila=convertView;
        RecordHolder holder=null;
        if(fila==null){
            LayoutInflater inflater=((Activity)context).getLayoutInflater();
            fila=inflater.inflate(layoutResourceId,parent,false);
            holder=new RecordHolder();
            holder.titulo=(TextView)fila.findViewById(R.id.titulo);
            holder.imagen=(ImageView)fila.findViewById(R.id.imageView);
            fila.setTag(holder);
        }else{
            holder=(RecordHolder)fila.getTag();
        }
        Item item=datos.get(position);
        holder.titulo.setText(item.getTitulo());
        holder.imagen.setImageBitmap(item.getImagen());
        return fila;
    }

    static class RecordHolder{
        TextView titulo;
        ImageView imagen;
    }
}
