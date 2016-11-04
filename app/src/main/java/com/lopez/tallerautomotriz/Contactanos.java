package com.lopez.tallerautomotriz;


import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contactanos extends Fragment {
    String url;
    View myView;
    TextView web;

    public Contactanos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        url="http://tallerservices.260mb.net/";
        myView=inflater.inflate(R.layout.fragment_contactanos, container, false);


        web=(TextView)myView.findViewById(R.id.textView4);
        web.setText(Html.fromHtml("Pagina Web: "+"<a href=http://tallerservices.260mb.net/>"+url+"</a>"));
        web.setMovementMethod(LinkMovementMethod.getInstance());

        return myView;
    }

}
