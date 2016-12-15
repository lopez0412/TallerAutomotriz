package com.lopez.tallerautomotriz;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    GridView gridView;
    ArrayList<Item> gridArray=new ArrayList<Item>();
    GridAdapterView gridAdapterView;
    SharedPreferences sp;
    int idusu;
    String nombre,correo,telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        overridePendingTransition(R.anim.open_traslate,R.anim.close_scale);

        //trae las variables enviadas por el intent del login

        sp=getSharedPreferences("datos", Context.MODE_PRIVATE);

        nombre=String.valueOf(sp.getString("nombre",""));
        idusu=Integer.valueOf(sp.getInt("id", 0));

        correo=String.valueOf(sp.getString("usuario",""));
        telefono=String.valueOf(sp.getString("telefono",""));




        //iconos de gridView
        Bitmap Servicios = BitmapFactory.decodeResource(this.getResources(),R.drawable.screwdriver_and_wrench_crossed);
        Bitmap Promociones = BitmapFactory.decodeResource(this.getResources(),R.drawable.black_shop_tag);
        Bitmap Contact = BitmapFactory.decodeResource(this.getResources(),R.drawable.phone_receiver);
        Bitmap Location = BitmapFactory.decodeResource(this.getResources(),R.drawable.placeholder);



        //añadimos los elementos al array para pasar al adaptador del grid view
        gridArray.add(new Item(Servicios,R.string.servicios));
        gridArray.add(new Item(Promociones,R.string.promociones));
        gridArray.add(new Item(Contact,R.string.contactanos));
        gridArray.add(new Item(Location,R.string.encuentranos));



        gridView=(GridView)findViewById(R.id.gridView);
        gridAdapterView=new GridAdapterView(this,R.layout.fila_item,gridArray);
        gridView.setAdapter(gridAdapterView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean fragmentTransaction = false;
                Fragment fragment = null;
                String titulo="";
                if(position==0){
                    fragment = new Servicios();
                    fragmentTransaction = true;

                    titulo=getResources().getString(R.string.servicios);

                }
                else if(position==1){
                    fragment = new Promociones();
                    fragmentTransaction = true;
                    titulo=getResources().getString(R.string.promociones);
                }
                else if (position==2){
                    fragment = new Contactanos();
                    fragmentTransaction = true;
                    titulo=getResources().getString(R.string.contactanos);
                }
                else if (position==3){
                    Intent i=new Intent(getApplicationContext(),MapsActivity.class);
                    startActivity(i);
                    /*fragment = new Encuentranos();
                    fragmentTransaction = true;*/
                    titulo=getResources().getString(R.string.encuentranos);
                }
                if(fragmentTransaction) {
                    /*RelativeLayout layout=(RelativeLayout)findViewById(R.id.content_frame);
                    layout.removeAllViewsInLayout();*/
                    Bundle args=new Bundle();
                    fragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.open_traslate,R.anim.close_scale,R.anim.open_fregment_scale,R.anim.close_traslate)
                            .replace(R.id.content_frame, fragment)
                            .addToBackStack(null)
                            .commit();


                    getSupportActionBar().setTitle(titulo);


                }
            }
        });



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        TextView nav_nombre = (TextView)hView.findViewById(R.id.usu);
        nav_nombre.setText(nombre);
        TextView nav_correo =(TextView)hView.findViewById(R.id.pwrd);
        nav_correo.setText(correo);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.open_scale,R.anim.close_traslate);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.grupo) {
            Intent i= new Intent(this,Integrantes.class);
            startActivity(i);

        }if (id==R.id.misreservaciones){
            Intent b= new Intent(this,Reservaciones.class);
            b.putExtra("idUsuario",idusu);
            startActivity(b);
        }
        if(id==R.id.action_settings){
            Intent c=new Intent(this,mostrarPreferencias.class);
            startActivity(c);
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        boolean fragmentTransaction = false;
        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.nav_home) {
        Intent i=new Intent(this,Principal.class);
            startActivity(i);
        } else if (id == R.id.nav_servicios) {
            fragment = new Servicios();
            fragmentTransaction = true;
        } else if (id == R.id.nav_promotions) {
            fragment = new Promociones();
            fragmentTransaction = true;
        } else if (id == R.id.nav_contact) {
            fragment = new Contactanos();
            fragmentTransaction = true;
        } else if (id == R.id.nav_buscanos) {
            Intent i=new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(i);
            /*
            fragment = new Encuentranos();
            fragmentTransaction = true;*/
        }
        else if (id == R.id.cerrarsesion) {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            SharedPreferences.Editor editor=sp.edit();
            editor.clear();
            editor.commit();
            startActivity(i);

        }
        if(fragmentTransaction) {

            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.open_traslate,R.anim.close_fragment_scale,R.anim.open_fregment_scale,R.anim.close_traslate)
                    .replace(R.id.content_frame, fragment)
                    .addToBackStack(null)
                    .commit();


            getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
