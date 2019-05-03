package com.example.sergi.meusac;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.os.Build.ID;

public class PaginaPrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Bundle dato;
    RequestQueue requestQueue;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new SendHttpRequestTask().execute();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //--------------------------------------------------------------
        requestQueue = Volley.newRequestQueue(this);
        JsonParse();

    }

    private void JsonParse(){
        String URL = getIp.ip+"estudiante/";
        //String URL = "http://192.168.43.72:3306/estudiante/";
        dato = getIntent().getExtras();
        String usuario = dato.getString("usuario");
        URL+=usuario;
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i =0; i < jsonArray.length();i++) {
                            try{
                                Log.d("Aqui","Entro al Try");
                                JSONObject user = jsonArray.getJSONObject(i);
                                String name = user.getString("nombre");
                                name += " ";
                                name+= user.getString("apellido");
                                String carnet = user.getString("carnet");

                                TextView nombre = (TextView) findViewById(R.id.txtNombre);
                                nombre.setText(name);
                                TextView id = (TextView) findViewById(R.id.txtCorreo);
                                id.setText(carnet);
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                    }
                });



        //Toast.makeText(Login.this,jsonObject.toString(),Toast.LENGTH_LONG).show();
        //Log.e("Rest Response:", jsonObject.toString());
        requestQueue.add(arrayRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pagina_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,
                    LoginActivity.class);
            startActivity(intent);
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(this,
                    codQRActivity.class);
            dato = getIntent().getExtras();
            String usuario = dato.getString("usuario");
            intent.putExtra("usuario",usuario);
            startActivity(intent);
        }
        else if (id == R.id.subir) {
            // Handle the camera action
            Intent intent = new Intent(this,
                    SubirFoto.class);
            dato = getIntent().getExtras();
            String usuario = dato.getString("usuario");
            intent.putExtra("usuario",usuario);
            startActivity(intent);
        }else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this,
                    datosActivity.class);
            dato = getIntent().getExtras();
            String usuario = dato.getString("usuario");
            intent.putExtra("usuario",usuario);
            startActivity(intent);
        }
        else if (id == R.id.Descargas) {
            Intent intent = new Intent(this,
                    download.class);
            dato = getIntent().getExtras();
            String usuario = dato.getString("usuario");
            intent.putExtra("usuario",usuario);
            startActivity(intent);
        }else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this,
                    Actividades.class);
            String usuario = dato.getString("usuario");
            intent.putExtra("usuario",usuario);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this,
                    Cursos.class);
            String usuario = dato.getString("usuario");
            intent.putExtra("usuario",usuario);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private class SendHttpRequestTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                dato = getIntent().getExtras();
                String usuario = dato.getString("usuario");
                URL url = new URL(getIp.ip+"download/"+usuario+".jpg");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            }catch (Exception e){
                Log.d("Aqui",e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
           try
           {
               ImageView imageView = (ImageView) findViewById(R.id.imageView);
               imageView.setImageBitmap(result);
           }
           catch (Exception e){
               Log.d("Aqui",e.getMessage());
           }

        }
    }
}
