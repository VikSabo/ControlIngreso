package com.example.usuario.proyectoiimoviles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    AnalyticsTracker analyticsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        //Google Analytics
        analyticsTracker = AnalyticsTracker.getAnalyticsTracker(this.getApplicationContext());

        Intent intent = getIntent();
        String id = intent.getStringExtra("BitmapImage");
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");

        if (bitmap != null) {
            Toast.makeText(this, "Error al cargar QR", Toast.LENGTH_LONG).show();
        } else {
            try {
                byte[] decodedString = Base64.decode(id, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show();
                ImageView mImg = (ImageView) findViewById(R.id.imageView);
                mImg.setImageBitmap(decodedByte);
            } catch(IllegalArgumentException e) {
                Toast.makeText(this, "Error, al crear el QR", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
