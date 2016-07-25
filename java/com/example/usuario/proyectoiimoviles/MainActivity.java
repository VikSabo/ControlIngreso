package com.example.usuario.proyectoiimoviles;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText usernameField, passwordField;
    public static ProgressBar progressBar;
    public static Button btnInicio, btnRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        usernameField = (EditText)findViewById(R.id.usernameText);
        passwordField = (EditText)findViewById(R.id.passText);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        btnInicio = (Button)findViewById(R.id.InicioSesionBtn);
        btnRegistro = (Button)findViewById(R.id.RegistroBtn);
        progressBar.setVisibility(View.GONE);
    }

    private boolean checkInternet(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }

    public void login(View view){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        if (checkInternet() == true){
            if (username.isEmpty()) {
                Toast.makeText(this, "Debe ingresar un nombre de usuario", Toast.LENGTH_LONG).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "Debe ingresar la contraseña", Toast.LENGTH_LONG).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            btnInicio.setEnabled(false);
            btnRegistro.setEnabled(false);
            new SignInActivity(this).execute(username,password);
        } else {
            Toast.makeText(this,"Verifica tu conexión a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
