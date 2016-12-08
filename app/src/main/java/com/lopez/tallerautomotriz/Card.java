package com.lopez.tallerautomotriz;


public class Card {
    private int id,idusua;
    private String titulo;
    private String imagen;
    private double precio;
    String descripcion;
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdusua() {
        return idusua;
    }

    public void setIdusua(int idusua) {
        this.idusua = idusua;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
