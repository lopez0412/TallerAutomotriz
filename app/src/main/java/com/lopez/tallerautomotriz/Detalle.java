package com.lopez.tallerautomotriz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.util.TimeUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Detalle extends AppCompatActivity {
    String name;
    String url;
    TextView descrip,costo;
    View fabicon;
    EditText fechadereservacion,horadereservacion;
    double precio;
    int id, idusuario;
    String descripcion;
    ViewGroup newview;
    RequestQueue requestQueue;
    FloatingActionButton fab;
    CheckBox checkBox;
    public final static float SCALE_FACTOR      = 13f;
    public final static int ANIMATION_DURATION  = 300;
    public final static int MINIMUN_X_DISTANCE  = 200;
    private boolean mRevealFlag;
    private float mFabSize;
    private int año;
    private int mes;
    private int dia;
    private int hora;
    private int minuto;
    boolean repuesto;
    boolean check;
    NetworkImageView image;
    View viewRoot;
    LinearLayout linearLayout;
    Spinner marca,modelo;
    CardView notaimp;
    Button enviarres,cancel;
    StringRequest request;

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int Año, int monthOfaño, int dayOfMonth) {

                    año = Año;
                    mes = monthOfaño;
                    dia = dayOfMonth;
                    fechadereservacion.setText((dia) + "-" + (mes+1) + "-" + año );
                }
            };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
      new TimePickerDialog.OnTimeSetListener() {
          @Override
          public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
              hora=hourOfDay;
              minuto=minute;
              if (hora<8 || hora>16){
                  Toast.makeText(Detalle.this, "Hora debe ser entre las 8:00 am y las 04:00pm", Toast.LENGTH_SHORT).show();
              }else {
              horadereservacion.setText(((hora<10)?"0"+hora:hora) + ":" + ((minuto<10)?"0"+minuto:minuto) );
          }
      }
      };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);


        Intent recibe = getIntent();
        name = recibe.getStringExtra("titulo");
        url = recibe.getStringExtra("imagen");
        precio = recibe.getDoubleExtra("precio",0.0);
        id=recibe.getIntExtra("id",0);
        descripcion=recibe.getStringExtra("descript");
        idusuario=recibe.getIntExtra("idusuario",0);

        requestQueue=VolleySingleton.getInstance().getRequestQueue();

        setToolbar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapser =
                (CollapsingToolbarLayout) findViewById(R.id.collapser);
        collapser.setTitle(name);
        // Cargar Imagen
        viewRoot = (View) findViewById(R.id.scroll);
        newview = (ViewGroup) findViewById(R.id.contain);
        image = (NetworkImageView) findViewById(R.id.image_paralax);
        descrip = (TextView) findViewById(R.id.descripcion);
        costo = (TextView) findViewById(R.id.price);
        fechadereservacion=(EditText)findViewById(R.id.fecha);
        horadereservacion=(EditText)findViewById(R.id.hora);
        checkBox=(CheckBox)findViewById(R.id.checkBox);
        linearLayout=(LinearLayout)findViewById(R.id.elegir_marca);
        marca=(Spinner)findViewById(R.id.marca);
        modelo=(Spinner)findViewById(R.id.modelo);
        notaimp=(CardView)findViewById(R.id.notaimportante);

        enviarres=(Button)findViewById(R.id.envia_res);
        cancel=(Button)findViewById(R.id.cancelar);

        //condicion en la que ve el servicio y se evalua si se va a usar repuesto o no
        if(id==4 ||id==3 || id==7 || id==10 || id==11 || id==12 || id==13){
            checkBox.setVisibility(View.GONE);
            repuesto=false;
        }else{
            checkBox.setVisibility(View.VISIBLE);
            repuesto=true;
        }
        //si el checkbox esta marcado habilitara otras vistas
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    linearLayout.setVisibility(View.VISIBLE);
                    check =false;
                }else {
                    linearLayout.setVisibility(View.INVISIBLE);
                    check =true;

                }
            }
        });

        String []marcas={"audi","bmw","chevrolet","kia","mazda","toyota"};

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,marcas);
        marca.setAdapter(adapter);



        //Obtiene fecha actual y coloca en el textview
        Calendar c = Calendar.getInstance();
        año = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        dia = (c.get(Calendar.DAY_OF_MONTH))+1;
        hora=c.get(Calendar.HOUR_OF_DAY);
        minuto=c.get(Calendar.MINUTE);



        //muestra la fecha de la forma 00/00/0000
        fechadereservacion.setText( (dia) + "-" + (mes+1) + "-" + año );
        fechadereservacion.setFocusable(false);
        if (hora<7 || hora>15){
            hora=12;
            horadereservacion.setText(hora+":"+minuto);
        }else {
            horadereservacion.setText(((hora < 10) ? "0" + hora : hora) + ":" + ((minuto < 10) ? "0" + minuto : minuto));
            horadereservacion.setFocusable(false);
        }


        fechadereservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verDatePicker();
            }
        });

        horadereservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verTimePicker();
            }
        });



        mFabSize = getResources().getDimensionPixelSize(R.dimen.size_fab);

        descrip.setText(descripcion);
        costo.setText("$"+precio);
        image.setImageUrl(url, VolleySingleton.getInstance().getImageLoader());

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setAnimation(anim);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabPressed(v);
                fab.setImageAlpha(0);
            }
        });
    }


    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }
    private void setToolbar() {
        // Añadir la Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }





    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                //finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onFabPressed(View view) {
        final float startX = fab.getX();

        AnimatorPath path = new AnimatorPath();
        path.moveTo(0, 0);
        path.curveTo(-200, 200, -400, 100, -600, 50);

        final ObjectAnimator anim = ObjectAnimator.ofObject(this, "fabLoc",
                new PathEvaluator(), path.getPoints().toArray());

        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(ANIMATION_DURATION);
        anim.start();

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (Math.abs(startX - fab.getX()) > MINIMUN_X_DISTANCE) {
                    if (!mRevealFlag) {

                        viewRoot.setY(viewRoot.getY() + mFabSize / 2);
                        fab.animate()
                                .scaleXBy(SCALE_FACTOR)
                                .scaleYBy(SCALE_FACTOR)
                                .setListener(mEndRevealListener)
                                .setDuration(ANIMATION_DURATION);

                        mRevealFlag = true;
                    }
                }
            }
        });
    }

    private AnimatorListenerAdapter mEndRevealListener = new AnimatorListenerAdapter() {

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);

            fab.setVisibility(View.INVISIBLE);
            newview.setVisibility(View.VISIBLE);
            viewRoot.setBackgroundResource(R.color.colorAccent );
            notaimp.setVisibility(View.INVISIBLE);

            for (int i = 0; i < newview.getChildCount(); i++) {
                View v = newview.getChildAt(i);
                ViewPropertyAnimator animator = v.animate()
                        .scaleX(1).scaleY(1)
                        .setDuration(ANIMATION_DURATION);

                animator.setStartDelay(i * 50);
                animator.start();
            }
        }
    };
    public void setFabLoc(PathPoint newLoc) {
        fab.setTranslationX(newLoc.mX);

        if (mRevealFlag)
            fab.setTranslationY(newLoc.mY - (mFabSize / 2));
        else
            fab.setTranslationY(newLoc.mY);
    }


    /**
     * Metodo para mostrar un DatePickerDialog
     * */
    public void verDatePicker()
    {
        DatePickerDialog d = new DatePickerDialog( this , mDateSetListener, año, mes, dia );
        d.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        d.show();
    }

    public void verTimePicker()
    {
        TimePickerDialog t = new TimePickerDialog( this, mTimeSetListener, hora,minuto,false);
        t.show();
    }
    public void Registroreserva(View v) throws ParseException {
        String urlingreso;
        String day=fechadereservacion.getText().toString();
        String hour = horadereservacion.getText().toString();


        final SweetAlertDialog pd = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pd.setTitleText("Reservando");
        pd.show();


        if(repuesto){
            //requiere repuesto
            if(check){
                //lleva el repuesto
                urlingreso="http://tallerservice.000webhostapp.com/ingresoreservaconrepuesto.php?usu="+idusuario+"&serv="+id+"&fech="+day+"&hour="+hour;
            }else {
                //no lleva el repuesto
                urlingreso="http://tallerservice.000webhostapp.com/ingresoreservasinrepuesto.php?usu="+idusuario+"&serv="+id+"&fech="+day+"&hour="+hour+"&marca=6&modelo=11&anio=2006";
            }
        }else {
            //no requiere repuesto
            urlingreso="http://tallerservice.000webhostapp.com/ingresoreserva.php?usu="+idusuario+"&serv="+id+"&fech="+day+"&hour="+hour;
        }


        request=new StringRequest(Request.Method.GET, urlingreso, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json=new JSONObject(response);
                    int status=json.getInt("status");
                    switch (status){
                        case 1:
                            pd.dismissWithAnimation();
                            Toast.makeText(Detalle.this, "Su reservacion fue realizada correctamente", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            pd.dismissWithAnimation();
                            Toast.makeText(Detalle.this, "Algo Salio mal, intenta de nuevo", Toast.LENGTH_SHORT).show();
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
        requestQueue.add(request);



    }

}
