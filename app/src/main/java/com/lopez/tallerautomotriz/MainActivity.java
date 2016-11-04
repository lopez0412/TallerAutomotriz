package com.lopez.tallerautomotriz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText user,contra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        user=(EditText)findViewById(R.id.usuario);
        contra=(EditText)findViewById(R.id.password);
        //  SharedPreferences
        SharedPreferences sp=getSharedPreferences("datos", Context.MODE_PRIVATE);

        String valor=String.valueOf(sp.getString("usuario"," "));
        user.setText(valor);


    }




    public void login(View v) {
        final String usuario = user.getText().toString();
        final String pass = contra.getText().toString();
        //validar que los campos no esten vacios
        if (usuario.isEmpty()) {
            user.setError("Ingrese Usuario");
            user.setFocusable(true);
        }else if (pass.isEmpty()){
            contra.setError("Ingrese Contraseña");
            contra.setFocusable(true);
        }else {

        //validar las credenciales
            if (usuario.equals("javier") && pass.equals("javier0412")){
                Intent i = new Intent(this, Principal.class);
                i.putExtra("Usuario",usuario);
                i.putExtra("Password",pass);
                startActivity(i);
//  SharedPreferences
                SharedPreferences sp=getSharedPreferences("datos", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor=sp.edit();
                editor.putString("usuario",usuario);
                editor.commit();
                //Toast.makeText(this,"Bienvenido "+usuario,Toast.LENGTH_SHORT).show();
            }else if (usuario.equals("admin") && pass.equals("admin")){
                Intent i = new Intent(this, Administrador.class);
                i.putExtra("Usuario",usuario);
                i.putExtra("Password",pass);
                startActivity(i);
                //Toast.makeText(MainActivity.this, "Bienvenido "+usuario, Toast.LENGTH_SHORT).show();
              //  SharedPreferences
                SharedPreferences sp=getSharedPreferences("datos", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor=sp.edit();
                editor.putString("usuario",usuario);
                editor.commit();

            }

                else{
                    //enviar alerta para notificar que usuario y/o contraseña son incorrectos
                    AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
                    alertdialog.setTitle("Datos Erroneos");
                    alertdialog.setMessage("El usuario y/o contraseña son erroneos por favor intentar de nuevo");
                    alertdialog.setCancelable(false);
                    alertdialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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
                            user.setText("");
                            contra.setText("");
                        }
                    });
                    alertdialog.show();
                }
            }
        }
    }

