package com.example.usuario.proyectoiimoviles;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class SignUpActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private File path;

    public SignUpActivity(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {
        MultiFormatWriter  multiFormatWriter  = new MultiFormatWriter();
        //byte[] bArray = "".getBytes();
        String temp = "";
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(arg0[0], BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            temp = Base64.encodeToString(bArray, Base64.DEFAULT);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        String id = arg0[0];
        String fullName = arg0[1];
        String userName = arg0[2];
        String passWord = arg0[3];
        String phoneNumber = arg0[4];
        String emailAddress = arg0[5];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;
        
        try {
            data = "?id=" + URLEncoder.encode(id, "UTF-8");
            data += "&fullname=" + URLEncoder.encode(fullName, "UTF-8");
            data += "&username=" + URLEncoder.encode(userName, "UTF-8");
            data += "&password=" + URLEncoder.encode(passWord, "UTF-8");
            data += "&phonenumber=" + URLEncoder.encode(phoneNumber, "UTF-8");
            data += "&emailaddress=" + URLEncoder.encode(emailAddress, "UTF-8");
            data += "&qr=" + URLEncoder.encode(temp,"UTF-8");

            Log.d("QR", temp);

            link = "http://movilesgrupo40.esy.es/signup.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Registro completado.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Los datos no se pudieron almacenar. Registro falló.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No se puede conectar actualmente a la Base de Datos.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error al parsear los datos del JSON.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "No se encuentra ninguna información.", Toast.LENGTH_SHORT).show();
        }
    }
}