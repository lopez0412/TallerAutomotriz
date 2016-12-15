package com.lopez.tallerautomotriz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Reservaciones extends AppCompatActivity {
    ListView lista;
    JsonObjectRequest array;
    RequestQueue requestQueue;
    int id_usuario;
    ArrayAdapter<String> adapter;
    List<Integer> lista2 = new ArrayList<>();
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservaciones);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Cargando...");
        pDialog.setCancelable(false);
        pDialog.show();
        sp=getSharedPreferences("datos", Context.MODE_PRIVATE);

        id_usuario=Integer.valueOf(sp.getInt("id", 0));


        lista=(ListView)findViewById(R.id.listareservaciones);
        final String url = "https://tallerservice.000webhostapp.com/misreservaciones.php?id_usuario="+id_usuario;
        requestQueue=VolleySingleton.getInstance().getRequestQueue();
        array=new JsonObjectRequest(Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,getReservaciones(response));
                pDialog.dismissWithAnimation();
                lista.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(array);

    }

    private List<String> getReservaciones(JSONObject jsonObject) {
        List<String> lista = new ArrayList<>();
        try {
            JSONArray array = jsonObject.getJSONArray("reservas");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String reservacion=object.getString("nombre_servicio")+"\n fecha: "+object.getString("fecha")+"\n hora: "+object.getString("hora");
                lista.add(reservacion);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lista;

    }
}
