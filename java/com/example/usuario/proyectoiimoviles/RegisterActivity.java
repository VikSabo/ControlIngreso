package com.example.usuario.proyectoiimoviles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    AnalyticsTracker analyticsTracker;
    private EditText etID, etFullName, etUserName, etPassword, etPasswordConfirm, etPhone, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        //Google Analytics
        analyticsTracker = AnalyticsTracker.getAnalyticsTracker(this.getApplicationContext());

        etID = (EditText) findViewById(R.id.etID);
        etFullName = (EditText) findViewById(R.id.etFullName);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
    }

    public void signup(View v) {
        String id = etID.getText().toString();
        String fullName = etFullName.getText().toString();
        String userName = etUserName.getText().toString();
        String passWord = etPassword.getText().toString();
        String passWordConfirm = etPasswordConfirm.getText().toString();
        String phoneNumber = etPhone.getText().toString();
        String emailAddress = etEmail.getText().toString();

        if (!isValidEmail(emailAddress)) {
            Toast.makeText(this, "El formato de correo es inválido", Toast.LENGTH_LONG).show();
            return;
        }

        if (id.isEmpty()) {
            Toast.makeText(this, "Debe ingresar una cédula", Toast.LENGTH_LONG).show();
            return;
        }
        if (fullName.isEmpty()) {
            Toast.makeText(this, "Debe ingresar un nombre de usuario", Toast.LENGTH_LONG).show();
            return;
        }
        if (userName.isEmpty()) {
            Toast.makeText(this, "Debe ingresar un nombre de usuario", Toast.LENGTH_LONG).show();
            return;
        }
        if (passWord.isEmpty()) {
            Toast.makeText(this, "Debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        if (passWordConfirm.isEmpty()) {
            Toast.makeText(this, "Debe de confirmar la contraseña ingresada", Toast.LENGTH_LONG).show();
            return;
        }
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Debe ingresar un número de teléfono", Toast.LENGTH_LONG).show();
            return;
        }
        if (emailAddress.isEmpty()) {
            Toast.makeText(this, "Debe ingresar un correo electrónico", Toast.LENGTH_LONG).show();
            return;
        }
        if (!passWord.contentEquals(passWordConfirm)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Registrando...", Toast.LENGTH_SHORT).show();
        new SignUpActivity(this).execute(id,fullName, userName, passWord, phoneNumber, emailAddress);
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
