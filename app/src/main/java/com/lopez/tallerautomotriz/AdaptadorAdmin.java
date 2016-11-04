package com.lopez.tallerautomotriz;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ALEJANDRO on 23/9/2016.
 */
public class AdaptadorAdmin extends ArrayAdapter<String> {
private final Activity context;
private final String[] itemname;
private final String[] itemsec;


public AdaptadorAdmin(Activity context, String[] itemname, String[] itemsec) {
        super(context, R.layout.row, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.itemsec=itemsec;

        }
public View getView(int posicion, View view, ViewGroup parent){

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.row,null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.texto_principal);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView etxDescripcion = (TextView) rowView.findViewById(R.id.texto_secundario);

        txtTitle.setText(itemname[posicion]);
        imageView.setImageResource(R.drawable.document_512);
        etxDescripcion.setText("Horario "+itemsec[posicion]);

        return rowView;
        }
        }
