package com.lopez.tallerautomotriz;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragmentPreferencias extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.configuracion);

        //preferencias de usuario
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        pref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Intent i=new Intent(getActivity(),Principal.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        //preferencias de usuario
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        pref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Intent i=new Intent(getActivity(),Principal.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        pref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Intent i=new Intent(getActivity(),Principal.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        pref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Intent i=new Intent(getActivity(),Principal.class);
                startActivity(i);
            }
        });
    }
}
