package com.example.sergi.meusac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class datosActivity extends AppCompatActivity {

    Bundle dato;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        //***********************************
        requestQueue = Volley.newRequestQueue(this);
        JsonParse();
        //***********************************
    }

    private void JsonParse(){
        String URL = getIp.ip+"estudiante/";
        //String URL = "http://192.168.43.72:3306/estudiante/";
        dato = getIntent().getExtras();
        String usuario = dato.getString("usuario");
        URL+=usuario;
        Log.d("Aqui","-----------------------------Entro al JsonParse");
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i =0; i < jsonArray.length();i++) {
                            try{
                                JSONObject user = jsonArray.getJSONObject(i);
                                String name = user.getString("nombre");
                                name += " ";
                                name+= user.getString("apellido");
                                String carnet = user.getString("carnet");
                                String telefono = user.getString("telefono");
                                String direccion = user.getString("direccion");
                                String fechaNacimiento = user.getString("fechaNacimiento");
                                String numeroCarrera = user.getString("numeroCarrera");

                                TextView nombre = (TextView) findViewById(R.id.txtDatosNombre);
                                nombre.setText("Nombre: "+name);
                                TextView id = (TextView) findViewById(R.id.txtDatosCarnet);
                                id.setText("Carnet: "+carnet);
                                TextView numero = (TextView) findViewById(R.id.txtDatosTelefono);
                                numero.setText("Telefono: "+telefono);
                                TextView dir = (TextView) findViewById(R.id.txtDatosDireccion);
                                dir.setText("Direccion: "+direccion);
                                TextView fecha = (TextView) findViewById(R.id.txtDatosFecha);
                                fecha.setText("Fecha: "+fechaNacimiento);
                                TextView num = (TextView) findViewById(R.id.txtDatosCarrera);
                                num.setText("Numero de Carrera: "+numeroCarrera);

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

}
