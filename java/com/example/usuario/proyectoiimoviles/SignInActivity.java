package com.example.usuario.proyectoiimoviles;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class SignInActivity  extends AsyncTask<String,Void,String> {
    AnalyticsTracker analyticsTracker;
    private TextView statusField,roleField;
    private Context context;
    protected Bitmap QR;
    protected MainActivity cont;

    public SignInActivity(Context context) {
        this.context = context;
    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... arg0) {
        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        try{
            String username = (String)arg0[0];
            String password = (String)arg0[1];
            String link = "http://movilesgrupo40.esy.es/signIn.php?username="+username+"&password="+password;

            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            HttpPost httpPost = new HttpPost(link);
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = in.readLine();

            httpPost.setEntity(new UrlEncodedFormEntity(param));
            HttpResponse httpResponse = client.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            // Read content & Log
            InputStream inputStream = httpEntity.getContent();
            QR = BitmapFactory.decodeStream(inputStream);

            while (line != null) {
                sb.append(line);
                line = in.readLine();
            }
            String everything = sb.toString();
            return everything;
        }

        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result){
        cont.progressBar.setVisibility(View.GONE);
        cont.btnInicio.setEnabled(true);
        cont.btnRegistro.setEnabled(true);
        if(result != "") {
            Intent intent = new Intent(context, HomeActivity.class);
            intent.putExtra("BitmapImage", result);
            context.startActivity(intent);
            Log.d("QR", result);
        } else {
            Toast.makeText(context, "El correo no se encuentra registrado en la base de datos o la contraseña es incorrecta", Toast.LENGTH_LONG).show();
        }
    }
}
