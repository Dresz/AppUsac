package com.example.sergi.meusac;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class download extends AppCompatActivity {

    public class Hilo extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
           //String URL1 = "http://10.112.1.136:3306/download/";
           Spinner textbox=(Spinner) findViewById(R.id.archivos);
            String nombre=textbox.getSelectedItem().toString();
            String URL1 = getIp.ip+"download/"+nombre;
            Log.d("Aqui",nombre);
            try {
                byte[] todo=null;
                byte[] parte=new byte[1024];
                ByteArrayOutputStream bos=new ByteArrayOutputStream();

                URL url=new URL(URL1);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.connect();

                int cont=0;
                while((cont=connection.getInputStream().read(parte)) != -1){
                    bos.write(parte,0,cont);
                    bos.flush();
                }

                todo=bos.toByteArray();
                Log.d("Aqui","va por aqui");
                File file=new File(Environment.getExternalStorageDirectory(),nombre);
                FileOutputStream fos=new FileOutputStream(file);
                BufferedOutputStream bos1=new BufferedOutputStream(fos);
                bos1.write(todo);
                bos1.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        requestQueue = Volley.newRequestQueue(this);
        JsonParse2();

        //
    }

    Bundle dato;
    static ArrayList<String> dataModels = new ArrayList<>();;
    ListView listView;
    private static CustomAdapter adapter;
    RequestQueue requestQueue;

    private void JsonParse2( ) {
        String URL = getIp.ip+"archivos";
        //dato = getIntent().getExtras();
        //String usuario = dato.getString("usuario");
        JsonArrayRequest arrayRequest2 = new JsonArrayRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        download.dataModels = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.d("Aqui","*******************************Entro al onResponses");
                            try {
                                JSONObject activity = jsonArray.getJSONObject(i);
                                String archivo = activity.getString("nombre");
                                Log.d("Aqui",archivo);
                                dataModels.add(archivo);
                                Log.d("Aqui",download.dataModels.size()+"");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("Aqui",download.dataModels.size()+" segunda ves");
                        ArrayAdapter<String> comboAdapter = new ArrayAdapter<String>
                                (download.this, android.R.layout.simple_spinner_item, download.dataModels);
                        comboAdapter.setDropDownViewResource(android.R.layout
                                .simple_spinner_dropdown_item);
                        Spinner textbox=(Spinner) findViewById(R.id.archivos);
                        textbox.setAdapter(comboAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                    }
                });
        requestQueue.add(arrayRequest2);
    }

    public void btnonclick(View view) {
        Log.d("Aqui","boton");
        Hilo ex = new Hilo();
        ex.execute();
        Snackbar.make(view, "Archivo Descargado", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

}
