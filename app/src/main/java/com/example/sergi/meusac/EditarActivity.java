package com.example.sergi.meusac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class EditarActivity extends AppCompatActivity {

    Button btnregistro;
    Bundle dato;
    String nombre1, nombre2, id;

    EditText numeroCarrera1;
    EditText direccion1;
    EditText numeroTelefono1;
    EditText fecha1;
    EditText password1;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        dato = getIntent().getExtras();
        id = dato.getString("usuario");
        nombre1 = dato.getString("nombre");
        nombre2 = dato.getString("apellido");

        requestQueue = Volley.newRequestQueue(EditarActivity.this);
        JsonParse();
        btnregistro = (Button)findViewById(R.id.btnEditarRegistro);



        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    requestQueue = Volley.newRequestQueue(EditarActivity.this);
                    ActualizarEstudiante();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void JsonParse(){
        String URL = "http://10.112.1.136:3306/estudiante/";
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
                                JSONObject user = jsonArray.getJSONObject(i);
                                String name = user.getString("nombre");
                                name += " ";
                                name+= user.getString("apellido");
                                String carnet = user.getString("carnet");
                                String telefono = user.getString("telefono");
                                String direccion = user.getString("direccion");
                                String fechaNacimiento = user.getString("fechaNacimiento");
                                String numeroCarrera = user.getString("numeroCarrera");
                                String password = user.getString("contrasenia");

                                numeroTelefono1 = (EditText) findViewById(R.id.txtEditarTelefono);
                                numeroTelefono1.setText(telefono,TextView.BufferType.EDITABLE);
                                direccion1 = (EditText) findViewById(R.id.txtEditaroDireccion);
                                direccion1.setText(direccion);
                                fecha1 = (EditText) findViewById(R.id.txtEditarFecha);
                                fecha1.setText(fechaNacimiento);
                                numeroCarrera1 = (EditText) findViewById(R.id.txtEditarCarrera);
                                numeroCarrera1.setText(numeroCarrera);
                                password1 = (EditText) findViewById(R.id.txtEditarPassword);
                                password1.setText(password);

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

    private void ActualizarEstudiante() throws JSONException {
        String URL = "http://10.112.1.136:3306/estudiante";

        //String URL = "http://192.168.43.72:3306/estudiante/";
        // Post params to be sent to the server
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("carnet", id);
        jsonBody.put("nombre", nombre1);
        jsonBody.put("apellido", nombre2);
        jsonBody.put("telefono", numeroTelefono1.getText().toString());
        jsonBody.put("direccion", direccion1.getText().toString());
        jsonBody.put("fechaNacimiento", fecha1.getText().toString());
        jsonBody.put("contrasenia", password1.getText().toString());
        jsonBody.put("numeroCarrera", numeroCarrera1.getText().toString());

        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LOG_RESPONSE", response);
                if (response.equals("Updated successfully")) {
                    //Redirect to next activity
                    Intent i = new Intent(EditarActivity.this, datosActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(EditarActivity.this, "Error: No se pudo agregar estudiante", Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_RESPONSE", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

           /* @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }*/
        };
        requestQueue.add(stringRequest);

    }
}

