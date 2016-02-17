package com.fousduvolant.androidcovoiturage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import model.User;
import service.InputStreamOperations;

/**
 * Created by jollier on 05/02/2016.
 */
public class LoginDisplayActivity extends Activity{
    final String EXTRA_LOGIN = "user_login";
    final String EXTRA_PASSWORD = "user_password";

    TextView loginDisplay;
    TextView passwordDisplay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        Intent intent = getIntent();
        loginDisplay = (TextView) findViewById(R.id.email);
        passwordDisplay = (TextView) findViewById(R.id.password);

        if (intent != null) {
            loginDisplay.setText(intent.getStringExtra(EXTRA_LOGIN));
            passwordDisplay.setText(intent.getStringExtra(EXTRA_PASSWORD));
        }

        final Button registerButton = (Button) findViewById(R.id.create_account);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginDisplayActivity.this, RegisterActivity.class);
                //intent.putExtra(EXTRA_LOGIN, loginDisplay.getText().toString());
                //intent.putExtra(EXTRA_PASSWORD, pass.getText().toString());

                startActivity(intent);
            }
        });

        final Button loginButton = (Button) findViewById(R.id.button_Connexion);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //User user = getUser();
                ContentValues values = new ContentValues();
                //values.put("email", "julien.ollier@berger-levrault.fr");
                //values.put("pwd1", "Azerty12");
                values.put("email", loginDisplay.getText().toString());
                values.put("pwd1", passwordDisplay.getText().toString());

                User user = new User();
                user = null;

                try {
                    user = new ConnexionFiles().execute(values).get();
                    //user = connect.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);


                if (user!=null) {
                    Toast.makeText(getApplicationContext(), "Le login est correct", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Le login est incorrect", Toast.LENGTH_LONG).show();
                }

                //Intent intent = new Intent(LoginDisplayActivity.this, TestDatabaseActivity.class);
                //intent.putExtra(EXTRA_LOGIN, loginDisplay.getText().toString());
                //intent.putExtra(EXTRA_PASSWORD, pass.getText().toString());

                //startActivity(intent);
            }
        });


        final Button annulerButton = (Button) findViewById(R.id.button_Annuler);
        annulerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginDisplayActivity.this, FousDuVolant.class);
                //intent.putExtra(EXTRA_LOGIN, loginDisplay.getText().toString());
                //intent.putExtra(EXTRA_PASSWORD, pass.getText().toString());

                startActivity(intent);
            }
        });
    }

    /**
     * Récupère un objet user.
     * @author François http://www.francoiscolin.fr/
     */
    //public static User getUser() {
    public static class ConnexionFiles extends AsyncTask<ContentValues, Integer, User> {
        public User doInBackground(ContentValues... cValues) {
            User user = new User();
            String myurl;
            myurl= "http://lesfousduvolant.cloudapp.net/Covoiturage/LoginAndroid";

            try {

                URL url = new URL(myurl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                //conn.addRequestProperty();

                    /*ContentValues values = new ContentValues();
                    values.put("email", "julien.ollier@berger-levrault.fr");
                    values.put("pwd1", "Azerty12");
                    */

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                StringBuilder sb = new StringBuilder();
                ContentValues cValue = cValues[0];

                Set<Map.Entry<String, Object>> s=cValue.valueSet();
                Iterator itr = s.iterator();
                int i=0;
                while(itr.hasNext())
                {
                    if (i>0) {
                        sb.append("&");
                    }
                    i++;
                    Map.Entry me = (Map.Entry)itr.next();
                    String key = me.getKey().toString();
                    String value =  me.getValue().toString();

                    sb.append(URLEncoder.encode(key,"UTF-8")+"="+URLEncoder.encode(value,"UTF-8"));
                }


                writer.write(sb.toString());
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

                int responseCode = conn.getResponseCode();
                String responseMessage = conn.getResponseMessage();

                InputStream is = null;
                if (responseCode != 200) {
                    is = conn.getErrorStream();
                    user = null;
                } else {
                    is = conn.getInputStream();
                    

                /*
             * InputStreamOperations est une classe complémentaire:
             * Elle contient une méthode InputStreamToString.
             */

                        /*
                         * InputStreamOperations est une classe complémentaire:
                         * Elle contient une méthode InputStreamToString.
                         */
                    String result = InputStreamOperations.InputStreamToString(is);

                    // On récupère le JSON complet
                    JSONObject jsonObject = new JSONObject(result);

                    // On récupère un objet JSON du tableau
                    //JSONObject obj = new JSONObject(jsonObject.getString("user"));


                    // On récupère un objet JSON du tableau
                    //JSONObject obj = new JSONObject(jsonObject.getString("user"));

                    user.setEmail(jsonObject.getString("email"));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            // On retourne le user ou null si le user ne remonte pas
            return user;
        }
        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(User result) {
            //showDialog("Downloaded " + result + " bytes");
        }

    }
}

