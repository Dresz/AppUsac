package com.example.sergi.meusac;

import android.annotation.TargetApi;
import android.media.audiofx.BassBoost;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.internal.http.multipart.MultipartEntity;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.XMLReader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;
public class Archivos extends AppCompatActivity {
    String url = "http://192.168.0.4:7000/upload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new MyTask().execute();
            }
        });
    }


    private class MyTask extends AsyncTask<Void,Void,Void>
    {
        //RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        private DefaultHttpClient mHttpClient;
        @Override
        protected Void doInBackground(Void... voids) {

           // String urlString = "http://192.168.0.4:7000/upload?imagename";
/*
            Log.d("Aqui","Archivos");
            String url = "http://192.168.0.4:7000/upload";
            Log.d("LOG_RESPONSE", String.valueOf(url));
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                    "Download/1.jpg");
            RequestParams params = new RequestParams();
            try {
                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(url);

                InputStreamEntity reqEntity = new InputStreamEntity(
                        new FileInputStream(file), -1);
                reqEntity.setContentType("binary/octet-stream");
                reqEntity.setChunked(true); // Send in multiple parts if needed
                httppost.setEntity(reqEntity);
                HttpResponse response = httpclient.execute(httppost);
                //Do something with response...
                Log.d("LOG_RESPONSE", String.valueOf("si jalo"));

            } catch (Exception e) {
                // show error
                Log.d("LOG_RESPONSE", String.valueOf(e));
            }*/

            try {
                String url = getIp.ip+"upload";
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                        "Download/1.jpg");
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                InputStreamEntity reqEntity = new InputStreamEntity(
                        new FileInputStream(file), -1);
                reqEntity.setContentType("binary/octet-stream");
                reqEntity.setChunked(true); // Send in multiple parts if needed
                params.put("upfile", file);
                params.put("curso","1");
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
