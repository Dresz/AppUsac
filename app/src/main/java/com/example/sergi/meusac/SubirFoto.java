package com.example.sergi.meusac;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class SubirFoto extends AppCompatActivity {
    Button button;//subir
    Button button2;//filechooser
    TextView textView;
    Bundle dato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_foto);
        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
        }
        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Seleccione una foto");


        //requestQueue = Volley.newRequestQueue(this);
        /*button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(SubirFoto.this)
                        .withRequestCode(1000)
                        .withFilter(Pattern.compile(".*\\.jpg$")) // Filtering files and directories by file name using regexp
                        .withFilterDirectories(false) // Set directories filterable (false by default)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();

            }
        });*/
        /*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MyTask().execute();
                Snackbar.make(view, "Archivo Subido", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                file = "";
                textView.setText("");
            }
        });*/


    }
    public void onClickBtn(View v)
    {
        new MyTask().execute();
        Toast.makeText(this, "Imagen Subida", Toast.LENGTH_LONG).show();
        file = "";
        textView.setText("");
    }
    public void onClickBtn2(View v)
    {
        new MaterialFilePicker()
                .withActivity(SubirFoto.this)
                .withRequestCode(1000)
                .withFilter(Pattern.compile(".*\\.jpg$")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(false) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            file = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            //textView.setText(file);
            Log.d("LOG_RESPONSE", String.valueOf(file));
            // textView = (TextView) findViewById(R.id.textView6);
            textView.setText(file);
        }
        catch (Exception e)
        {

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 1001:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this,"Permiso Permitido",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this,"Permiso Denegado",Toast.LENGTH_LONG).show();
                    finish();
                }
        }
    }

    public static String file="Def";

    private class MyTask extends AsyncTask<Void,Void,Void>
    {
        //RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        private DefaultHttpClient mHttpClient;

        @Override
        protected Void doInBackground(Void... voids) {
            //dato = getIntent().getExtras();
            String usuario = "201703166";//dato.getString("usuario");


            try {
                String url = getIp.ip+"upload2";
                //Spinner textbox=(Spinner) findViewById(R.id.spinner);
                Log.d("LOG_RESPONSE", String.valueOf(SubirFoto.file));
                File file = new File(SubirFoto.file);
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                InputStreamEntity reqEntity = new InputStreamEntity(
                        new FileInputStream(file), -1);
                reqEntity.setContentType("binary/octet-stream");
                reqEntity.setChunked(true); // Send in multiple parts if needed
                params.put("upfile", file);
                params.put("id", usuario);
                //params.put("more", "data");
                client.post(url, params, new TextHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String res) {
                                // called when response HTTP status is "200 OK"
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            }


                            // ----New Overridden method
                            @Override
                            public boolean getUseSynchronousMode() {
                                return false;
                            }
                        }
                );
                Log.d("LOG_RESPONSE", String.valueOf("win!!"));
            }
            catch (Exception e)
            {
                Log.d("LOG_RESPONSE", String.valueOf(e));
            }

            return null;
        }
        protected void onPostExecute(Void result) {

        }
    }

}
