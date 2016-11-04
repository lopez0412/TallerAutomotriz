package com.lopez.tallerautomotriz;

import android.graphics.Bitmap;

/**
 * Created by ALEJANDRO on 24/7/2016.
 */
public class Item {
    Bitmap imagen;
    int Titulo;


    public Item(Bitmap imagen, int Titulo){
        super();
        this.imagen=imagen;
        this.Titulo=Titulo;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public int getTitulo() {
        return Titulo;
    }

    public void setTitulo(int titulo) {
        Titulo = titulo;
    }

}