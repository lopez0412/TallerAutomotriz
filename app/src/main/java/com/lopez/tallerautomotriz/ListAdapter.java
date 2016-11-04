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
public class ListAdapter extends ArrayAdapter<String> {
private final Activity context;
private final String[] itemname;
private final String[] itemsec;
private final Integer[] integers;

public ListAdapter(Activity context, String[] itemname, Integer[] integers,String[] itemsec) {
        super(context, R.layout.cardview, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.itemsec=itemsec;
        this.integers=integers;
        }
public View getView(int posicion, View view, ViewGroup parent){

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.cardview,null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.list_item_google_cards_shop_discount);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_item_google_cards_shop_image);
        TextView etxDescripcion = (TextView) rowView.findViewById(R.id.list_item_google_cards_shop_price);

        txtTitle.setText(itemname[posicion]);
        imageView.setImageResource(integers[posicion]);
        etxDescripcion.setText(itemsec[posicion]);

        return rowView;
        }
        }
