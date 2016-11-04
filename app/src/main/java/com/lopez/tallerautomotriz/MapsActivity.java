package com.lopez.tallerautomotriz;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int PETICION_PERMISO_LOCALIZACION = 0;
    private GoogleMap mMap;
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;
    ImageButton btnMap;
    int tipo=1;
    String miubicacion="";
    String mapa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        overridePendingTransition(R.anim.open_traslate,R.anim.close_scale);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnMap=(ImageButton)findViewById(R.id.btnMapa);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tipo){
                    case 1:
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        mapa="Hibrido";
                        tipo=2;
                        break;
                    case 2:
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        mapa="Satelite";
                        tipo=3;
                        break;
                    case 3:
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        mapa="Terreno";
                        tipo=4;
                        break;
                    case 4:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        mapa="Normal";
                        tipo=1;
                        break;
                }
                showSnackBar(mapa);
            }
        });
/*
        Uri ruta = Uri.parse("google.navigation:q=13.698624, -89.200532");
        Intent mapa1 = new Intent(Intent.ACTION_VIEW, ruta);
        mapa1.setPackage("com.google.android.apps.maps");
        startActivity(mapa1);*/

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Marcador(13.698624, -89.200532, "Taller Central");
        Marcador(13.698537, -89.226904, "Sucursal Araujo");
        Marcador(13.693404, -89.221217, "Sucursal Venezuela");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},PETICION_PERMISO_LOCALIZACION);
        }
       /* Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        //miubicacion=mMap.getMyLocation().toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length>0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                onMapReady(mMap);
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_scale,R.anim.close_traslate);
    }

    private void Marcador(double lat, double lng, String pais)
    {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(pais).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.taller_icon2)));

    }

    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.frame_map), msg, Snackbar.LENGTH_LONG)
                .show();
    }


}