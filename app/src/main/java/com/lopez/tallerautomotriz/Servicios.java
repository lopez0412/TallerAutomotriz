package com.lopez.tallerautomotriz;


import android.animation.Animator;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.victor.loading.book.BookLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Servicios extends Fragment {
    JsonObjectRequest array;
    RequestQueue requestQueue;
    ProgressBar progressBar;
    private final String url = "https://tallerservice.000webhostapp.com/servicios.php";
    private final String TAG = "Prueba";
    private RecyclerView recyclerView;
    private ArrayList<Card> cardsList = new ArrayList<>();
    private RecyclerAdapter adapter;

    BookLoading bookLoading;
    int idusuario;

    View myFragmentView;

    public Servicios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_servicios, container, false);

        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.lista);
        idusuario=getArguments().getInt("idusu");

        bookLoading= (BookLoading)myFragmentView.findViewById(R.id.progressBar3);
        bookLoading.start();
        recyclerView.setClipToPadding(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //metodo para llenar el recycler view desde web services
        requestQueue=VolleySingleton.getInstance().getRequestQueue();
        array=new JsonObjectRequest(Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                adapter = new RecyclerAdapter(getContext(), getServicios(response));
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,error.toString());
            }
        });
        requestQueue.add(array);
        // Inflate the layout for this fragment
        return myFragmentView;
    }


    //metodo para llenar el recycler view desde web services
    private List<Card> getServicios(JSONObject jsonObject) {
        List<Card> lista = new ArrayList<>();
        try {
            JSONArray array = jsonObject.getJSONArray("servicio");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Card card = new Card();
                card.setId(object.getInt("id"));
                card.setTitulo(object.getString("nombre_servicio"));
                card.setPrecio(object.getDouble("precio"));
                card.setImagen(object.getString("imagen"));
                card.setDescripcion(object.getString("descripcion"));
                card.setIdusua(idusuario);
                lista.add(card);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bookLoading.setVisibility(View.INVISIBLE);
        return lista;
    }


}




