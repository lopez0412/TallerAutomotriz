package com.lopez.tallerautomotriz;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashScreen extends Activity {
    ImageView image;
    TextView text;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setExitTransition(new Explode());
        image=(ImageView)findViewById(R.id.logo);
        text=(TextView)findViewById(R.id.bienvenida);
        relativeLayout=(RelativeLayout)findViewById(R.id.splash);
        Animation animacion= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animacion_splash);
        image.setAnimation(animacion);
        Animation animacion2= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bienvenidos);
        text.setAnimation(animacion2);
       Animation animacion3=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash);
        relativeLayout.setAnimation(animacion3);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
