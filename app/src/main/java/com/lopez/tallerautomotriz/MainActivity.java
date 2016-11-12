package com.lopez.tallerautomotriz;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    EditText user,contra;
    SharedPreferences sp;
    RequestQueue requestQueue;

    StringRequest request;
    StringRequest request2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        user=(EditText)findViewById(R.id.usuario);
        contra=(EditText)findViewById(R.id.password);
        //  SharedPreferences

        sp=getSharedPreferences("datos", Context.MODE_PRIVATE);

        String valor=String.valueOf(sp.getString("usuario"," "));
        user.setText(valor);

        requestQueue=VolleySingleton.getInstance().getRequestQueue();


    }




    public void login(View v) {
        final String usuario = user.getText().toString();
        final String pass = contra.getText().toString();
       // final ProgressDialog progress;
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Iniciando Sesion");

        pDialog.setCancelable(false);
        //validar que los campos no esten vacios
        if (usuario.isEmpty()) {
            user.setError("Ingrese su correo electronico");
            user.setFocusable(true);
        }else if (pass.isEmpty()){
            contra.setError("Ingrese Contraseña");
            contra.setFocusable(true);
        }else {
            //progress = ProgressDialog.show(MainActivity.this, "Iniciando Sesion",
                   // "Espere...", true);
            pDialog.show();
            final String URL="https://tallerservice.000webhostapp.com/taller.php?correo="+usuario+"&pas="+pass;
            request=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject json=new JSONObject(response);
                        int id=json.getInt("id");
                        String rol=json.getString("rol");



                            switch (rol) {
                                case "cliente":
                                    Intent i = new Intent(MainActivity.this, Principal.class);
                                    i.putExtra("id", id);
                                    startActivity(i);
                                   // progress.dismiss();
                                    pDialog.dismissWithAnimation();


                                    break;
                                case "jefe_de_taller":
                                    Intent a = new Intent(MainActivity.this, Administrador.class);
                                    a.putExtra("id", id);
                                    startActivity(a);
                                    pDialog.dismissWithAnimation();



                                    break;
                            }

                        SharedPreferences sp=getSharedPreferences("datos", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString("usuario",usuario);
                        editor.commit();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        //progress.dismiss();
                        pDialog.dismissWithAnimation();
                        AlertDialog.Builder alertdialog=new AlertDialog.Builder(MainActivity.this);
                        alertdialog.setTitle("Datos Erroneos");
                        alertdialog.setMessage("El Correo y/o contraseña son erroneos por favor intentar de nuevo");
                        alertdialog.setCancelable(false);
                        alertdialog.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Accion al pulsar cancelar
                                finish();
                            }
                        });
                        alertdialog.setPositiveButton("Volver a Intentar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Accion al pulsar aceptar en cuadro de dialogo y darle al volver a intentar
                                dialog.dismiss();
                                contra.setText("");
                            }
                        });
                        alertdialog.show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(request);
        }

    }



    public void registrar(View v){
       final Dialog d=new Dialog(MainActivity.this);
        d.setContentView(R.layout.registrar);

        final SweetAlertDialog pd = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pd.setTitleText("Registrando");

       final EditText correo=(EditText)d.findViewById(R.id.email_registro);
       final EditText passw=(EditText)d.findViewById(R.id.password_registro);
        final EditText passw2=(EditText)d.findViewById(R.id.password_registro2);
       final EditText name=(EditText)d.findViewById(R.id.nombre);
       final EditText phone=(EditText)d.findViewById(R.id.telefono);
        Button aceptar=(Button)d.findViewById(R.id.acept);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail,pas,pas2,nombre,telefono;
                mail=correo.getText().toString();
                pas=passw.getText().toString();
                pas2=passw2.getText().toString();
                nombre=name.getText().toString();
                telefono=phone.getText().toString();

                if (mail.isEmpty()){
                    correo.setError("Ingrese su correo");
                    correo.setFocusable(true);
                }else if (pas.isEmpty()){
                    passw.setError("Ingrese su contraseña");
                    passw.setFocusable(true);
                }else if (pas2.isEmpty()){
                    passw2.setError("Repita su contraseña");
                    passw2.setFocusable(true);
                }
                else if (nombre.isEmpty()){
                    name.setError("Ingrese su Nombre");
                    name.setFocusable(true);
                }else if (telefono.isEmpty()){
                    phone.setError("Ingrese su correo");
                    phone.setFocusable(true);
                }else if (!pas.equals(pas2)){
                    passw2.setError("contraseñas deben ser iguales");
                }else{
                    pd.show();
                    final String Urlingreso="https://tallerservice.000webhostapp.com/ingresousuario.php?correo="+mail+"&pass="+pas+"&nombre="+nombre+"&telefono="+telefono+"";
                    final String url=Urlingreso.replace(" ","%20");
                    request2=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject json=new JSONObject(response);
                                int status=json.getInt("status");
                                String msg=json.getString("msg");

                                switch (status){
                                    case 1:
                                        pd.dismissWithAnimation();
                                        Toast.makeText(MainActivity.this, "Usuario registrado correctamente, Puede iniciar sesion", Toast.LENGTH_SHORT).show();
                                        d.dismiss();
                                        break;
                                    case 2:
                                        pd.dismissWithAnimation();
                                        Toast.makeText(MainActivity.this, "Algo Salio mal", Toast.LENGTH_SHORT).show();
                                        d.dismiss();
                                        break;
                                }





                            } catch (JSONException e) {
                                e.printStackTrace();


                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(request2);

                }

            }
        });

        Button cancelar=(Button)d.findViewById(R.id.cancel_action);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        d.show();
    }

}

