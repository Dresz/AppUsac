package com.example.sergi.meusac;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class codQRActivity extends AppCompatActivity {

    ImageView image;
    Bundle dato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cod_qr);
        image = (ImageView) findViewById(R.id.image);
        //text2Qr = getIntent().getStringExtra("usuario");
        dato = getIntent().getExtras();
        String usuario = dato.getString("usuario");
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(usuario, BarcodeFormat.QR_CODE, 200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }
    }

}
