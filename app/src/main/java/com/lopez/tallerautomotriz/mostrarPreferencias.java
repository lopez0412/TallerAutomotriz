package com.lopez.tallerautomotriz;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class mostrarPreferencias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_preferencias);

        FragmentTransaction ft= getFragmentManager().beginTransaction();
        ft.add(android.R.id.content,new fragmentPreferencias());
        ft.commit();
    }
}
