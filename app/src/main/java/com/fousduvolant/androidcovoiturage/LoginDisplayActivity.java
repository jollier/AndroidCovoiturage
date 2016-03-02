package com.fousduvolant.androidcovoiturage;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import model.User;
import service.InputStreamOperations;

/**
 * Created by jollier on 05/02/2016.
 */
public class LoginDisplayActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    final String EXTRA_LOGIN = "user_login";
    final String EXTRA_PASSWORD = "user_password";

    TextView loginDisplay;
    TextView passwordDisplay;
    User     receivedUser = null;

    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(final Message msg) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("EPITEZ", "handleMessage receivedUser setted" + msg.arg1);
                    if (receivedUser == null) {
                        Log.i("EPITEZ", "aucun utilisateur reçu");
                        affichageToast(R.layout.toast_erreur, "Erreur : Utilisateur non trouvé ! ");
                    } else {
                        Log.i("EPITEZ", "reçu " + receivedUser.getEmail());
                        affichageToast(R.layout.toast_valid,"Connexion réussie ! ");
                        Intent intent = new Intent(LoginDisplayActivity.this, ListUsersActivity.class);
                        startActivity(intent);
                    }
                }
            });
            return false;
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);


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

                // Test que les champs soient saisis
                if (loginDisplay.getText().length() == 0) {
                    loginDisplay.requestFocus();
                    loginDisplay.setError("Vous devez renseigner ce champs");
                } else if (passwordDisplay.getText().length() == 0) {
                    passwordDisplay.requestFocus();
                    passwordDisplay.setError("Vous devez renseigner ce champs");
                } else {
                    //User user = getUser();
                    ContentValues values = new ContentValues();
                    //values.put("email", "julien.ollier@berger-levrault.fr");
                    //values.put("pwd1", "Azerty12");
                    values.put("email", loginDisplay.getText().toString());
                    values.put("pwd1", passwordDisplay.getText().toString());

                    ConnexionFiles connexion;
                    connexion = new ConnexionFiles(values);
                    connexion.start();

                    //Intent intent = new Intent(LoginDisplayActivity.this, TestDatabaseActivity.class);
                    //intent.putExtra(EXTRA_LOGIN, loginDisplay.getText().toString());
                    //intent.putExtra(EXTRA_PASSWORD, pass.getText().toString());

                    //startActivity(intent);
                }
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
    public class ConnexionFiles extends Thread implements Runnable {
        ContentValues [] cValues;
        public ConnexionFiles(ContentValues ... values) {
            cValues = values;
        }

        @Override
        public void run() {
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
            receivedUser = user;
            Message msg = new Message();
            handler.sendMessage(msg);
        }
    }

    public void affichageToast(int numToast, String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(numToast, (ViewGroup) findViewById(R.id.custom_toast_layout_id));

        // set a message
        TextView text = (TextView) layout.findViewById(R.id.text_toast);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_connecter) {
            Intent intent = new Intent(LoginDisplayActivity.this, FousDuVolant.class);
            //intent.putExtra(EXTRA_LOGIN, loginDisplay.getText().toString());
            //intent.putExtra(EXTRA_PASSWORD, pass.getText().toString());

            startActivity(intent);

        } else if (id == R.id.nav_inscrire) {
            Intent intent = new Intent(LoginDisplayActivity.this, RegisterActivity.class);
            //intent.putExtra(EXTRA_LOGIN, loginDisplay.getText().toString());
            //intent.putExtra(EXTRA_PASSWORD, pass.getText().toString());

            startActivity(intent);

        } else if (id == R.id.nav_listuseers) {
            Intent intent = new Intent(LoginDisplayActivity.this, ListUsersActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_close) {
            // Fermer l'application
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}

