package com.lopez.tallerautomotriz;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Integrantes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //preferencias de usuario
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(this);

        String tema=pref.getString("list_preference","");
        switch (tema){
            case "Original": setTheme(R.style.AppTheme);
                break;
            case "Indigo": setTheme(R.style.AppThemeIndigo2);
                break;
            case "Rojo": setTheme(R.style.AppThemeRed2);
                break;
            case "Verde": setTheme(R.style.AppThemeGreen2);
                break;
        }
        setContentView(R.layout.integrantes);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
