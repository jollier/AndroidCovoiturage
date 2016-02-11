package com.fousduvolant.androidcovoiturage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import model.User;


/**
 * Created by AREDON on 11/02/2016.
 */
public class LoginDisplayActivityAlex extends Activity{
    final String EXTRA_LOGIN = "user_login";
    final String EXTRA_PASSWORD = "user_password";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        Intent intent = getIntent();
        TextView loginDisplay = (TextView) findViewById(R.id.email);
        TextView passwordDisplay = (TextView) findViewById(R.id.password);

        if (intent != null) {
            loginDisplay.setText(intent.getStringExtra(EXTRA_LOGIN));
            passwordDisplay.setText(intent.getStringExtra(EXTRA_PASSWORD));
        }

        final Button registerButton = (Button) findViewById(R.id.create_account);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginDisplayActivityAlex.this, RegisterActivity.class);
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
//                ConnexionFiles connect = new ConnexionFiles();
//                connect.execute();
                ContentValues values = new ContentValues();
                values.put("email", "julien.ollier@berger-levrault.fr");
                values.put("pwd1", "Azerty12");
                new ConnexionFiles().execute(values);

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
                Intent intent = new Intent(LoginDisplayActivityAlex.this, FousDuVolant.class);
                //intent.putExtra(EXTRA_LOGIN, loginDisplay.getText().toString());
                //intent.putExtra(EXTRA_PASSWORD, pass.getText().toString());

                startActivity(intent);
            }
        });
    }

    //    http://developer.android.com/reference/android/os/AsyncTask.html
    /**
     * Récupère un objet user.
     * @author François http://www.francoiscolin.fr/
     */

    private class ConnexionFiles extends AsyncTask<ContentValues, Integer, Long> {
        protected Long doInBackground(ContentValues... values) {
            User user = new User();
            String myURL = "http://lesfousduvolant.cloudapp.net/Covoiturage/LoginAndroid";
            try {
                URL url = new URL(myURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "text/plain");
                conn.setRequestProperty("charset", "utf-8");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                String body = "?";
                for (String key : values.keySet()) {
                    String myKey = key;
                    String myValue = pPostContents.get(key);
                }



                for(ContentValues c : values){
                    Set<String> keyset = c.keySet();
                    body=body + keyset.

                    //do something with c
                    Map.Entry me = (Map.Entry)itr.next();
                    String key = me.getKey().toString();
                    Object value =  me.getValue();
                    if (body.endsWith("?")) {
                        body = body + key + "=" + value;
                    }

                }


//                String body = "?username=user&password=password"
//                conn.getOutputStream().write(body.getBytes("UTF8"));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
