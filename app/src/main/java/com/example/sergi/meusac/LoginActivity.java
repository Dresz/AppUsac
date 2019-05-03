package com.example.sergi.meusac;

import android.os.AsyncTask;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonIOException;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button btnRegistrar;
    Button btnLogin;
    //private Button butlog;
    private EditText eduser, edpass;
    //private TextView regis;
    Boolean should = false;
    RequestQueue requestQueue;
    public static String muser = "";
    public static Boolean mmember;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eduser = (EditText) findViewById(R.id.txtUsuario);
        edpass = (EditText) findViewById(R.id.txtPassword);
        //regis = (TextView) findViewById(R.id.login_link_sign);

        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(i);
            }
        });

        requestQueue = Volley.newRequestQueue(this);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JsonParse();
            }
        });



    }

    private void JsonParse(){
        String URL = getIp.ip+"estudiante";
        //String URL = "http://192.168.43.72:3306/estudiante";
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i =0; i < jsonArray.length();i++) {
                            try{
                                JSONObject user = jsonArray.getJSONObject(i);
                                String username = user.getString("carnet");
                                String password = user.getString("contrasenia");

                                if (eduser.getText().toString().equals("") || edpass.getText().toString().equals("")){
                                    Toast.makeText(LoginActivity.this,"Debe ingresar sus datos",Toast.LENGTH_LONG).show();
                                }else if (username.equals(eduser.getText().toString()) && password.equals(edpass.getText().toString())){
                                    muser = username;
                                    Intent intent = new Intent(LoginActivity.this,
                                            PaginaPrincipalActivity.class);
                                    intent.putExtra("usuario",muser);
                                    startActivity(intent);
                                }
                                //regis.setText("");
                                //regis.append(username +"-"+eduser.getText().toString() + ";"+password+"-"+edpass.getText().toString());

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
