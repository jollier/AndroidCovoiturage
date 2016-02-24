package com.fousduvolant.androidcovoiturage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.concurrent.ExecutionException;

import model.User;
import service.InputStreamOperations;

/**
 * Created by jollier on 10/02/2016.
 */
public class RegisterActivity extends Activity {


    final String EXTRA_EMAIL = "user_email";
    final String EXTRA_PASSWORD = "user_password";
    final String EXTRA_FIRSTNAME = "user_firstName";
    final String EXTRA_LASTNAME = "user_lastName";
    final String EXTRA_ADDRESSNUMBER = "user_addressNumber";
    final String EXTRA_ADDRESSWAY = "user_addressWay";
    final String EXTRA_ADDRESSCP = "user_addressCp";
    final String EXTRA_ADDRESSCITY = "user_addressCity";
    final String EXTRA_LONGITUDE = "user_longitude";
    final String EXTRA_LATITUDE = "user_latitude";
    final String EXTRA_PHONENUMBER = "user_phoneNumber";
    final String EXTRA_SEXE = "user_sexe";
    final String EXTRA_ISCONDUCTEUR = "user_isConducteur";
    final String EXTRA_ISSMOKER = "user_isSmoker";
    final String EXTRA_AREA = "user_area";


    TextView emailDisplay;
    TextView passwordDisplay;
    TextView firstNameDisplay;
    TextView lastNameDisplay;
    TextView addressNumberDisplay;
    TextView addressWayDisplay;
    TextView addressCPDisplay;
    TextView addressCityDisplay;
    TextView longitudeDisplay;
    TextView latitudeDisplay;
    TextView phoneNumberDisplay;
    TextView sexeDisplay;
    RadioGroup sexeGroup;
    RadioButton sexeH,sexeF;
    TextView isConducteurDisplay;
    TextView isSmokerDisplay;
    TextView areaDisplay;
    TextView passwordDisplay2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);
/********************************************************************************************/
        Intent intent = getIntent();
        emailDisplay = (TextView) findViewById(R.id.email);
        passwordDisplay = (TextView) findViewById(R.id.password);
        passwordDisplay2=(TextView) findViewById(R.id.Pwd2);
        firstNameDisplay= (TextView) findViewById(R.id.firstName);
        lastNameDisplay= (TextView) findViewById(R.id.lastName);
        addressNumberDisplay= (TextView) findViewById(R.id.addressNumber);
        addressWayDisplay= (TextView) findViewById(R.id.addressWay);
        addressCPDisplay= (TextView) findViewById(R.id.addressCP);
        addressCityDisplay= (TextView) findViewById(R.id.addressCity);
        phoneNumberDisplay= (TextView) findViewById(R.id.phoneNumber);
        //sexeGroup = (RadioGroup)findViewById(R.id.sexe);
        RadioGroup sexeGroup1 = (RadioGroup) findViewById(R.id.sexe);
        int selecteId = sexeGroup1.getCheckedRadioButtonId();
        RadioButton sexeGroup = (RadioButton) findViewById(selecteId);
 /*       int selecteId = sexeGroup.getCheckedRadioButtonId();
        sexeDisplay = (TextView)findViewById(selecteId);
        Log.d("Sexe", String.valueOf(sexeDisplay));  */
        //sexeH = (RadioButton) findViewById(R.id.sexeH);
        //sexeF = (RadioButton) findViewById(R.id.sexeF);
      //  isConducteurDisplay= (TextView) findViewById(R.id.isConducteur);
      //  isSmokerDisplay= (TextView) findViewById(R.id.isSmoker);
        areaDisplay= (TextView) findViewById(R.id.area);

      //http://tutorielsandroid.com/les-radio-buttons-dans-android-affichage-et-clics/


        if (intent != null) {
            emailDisplay.setText(intent.getStringExtra(EXTRA_EMAIL));
            passwordDisplay.setText(intent.getStringExtra(EXTRA_PASSWORD));
            firstNameDisplay.setText(intent.getStringExtra(EXTRA_FIRSTNAME));
            lastNameDisplay.setText(intent.getStringExtra(EXTRA_LASTNAME));
            addressNumberDisplay.setText(intent.getStringExtra(EXTRA_ADDRESSNUMBER));
            addressWayDisplay.setText(intent.getStringExtra(EXTRA_ADDRESSWAY));
            addressCPDisplay.setText(intent.getStringExtra(EXTRA_ADDRESSCP));
            addressCityDisplay.setText(intent.getStringExtra(EXTRA_ADDRESSCITY));
            phoneNumberDisplay.setText(intent.getStringExtra(EXTRA_PHONENUMBER));

            sexeDisplay = (TextView)findViewById(selecteId);
            //sexeDisplay.setText(intent.getStringExtra(EXTRA_SEXE));
            //  isConducteurDisplay= (TextView) findViewById(R.id.isConducteur);
            //  isSmokerDisplay= (TextView) findViewById(R.id.isSmoker);
            areaDisplay.setText(intent.getStringExtra(EXTRA_AREA));
        }

        final Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Boolean resultRequest = true;

                // Test que les champs soient saisis
                if (firstNameDisplay.getText().length()==0) {
                    firstNameDisplay.requestFocus();
                    firstNameDisplay.setError("Vous devez renseigner ce champs");
                } else if (passwordDisplay.getText().length()==0) {
                    passwordDisplay.requestFocus();
                    passwordDisplay.setError("Vous devez renseigner ce champs");
                //} else if (!(passwordDisplay.getText().equals(passwordDisplay2.getText()))) {
                    //passwordDisplay.requestFocus();
                    //passwordDisplay.setError("Mots de passe différents");
                }else {

                    //User user = getUser();
                    ContentValues values = new ContentValues();
                    //values.put("email", "julien.ollier@berger-levrault.fr");
                    //values.put("pwd1", "Azerty12");
                    values.put("email", emailDisplay.getText().toString());
                    values.put("pwd1", passwordDisplay.getText().toString());
                    values.put("firstName", firstNameDisplay.getText().toString());
                    values.put("lastName", lastNameDisplay.getText().toString());
                    values.put("addressNumber", addressNumberDisplay.getText().toString());
                    values.put("addressWay", addressWayDisplay.getText().toString());
                    values.put("addressCP", addressCPDisplay.getText().toString());
                    values.put("addressCity", addressCityDisplay.getText().toString());
                    values.put("phoneNumber", phoneNumberDisplay.getText().toString());
                    //values.put("sexe", StringsexeDisplay.getText().toString());
                    values.put("area", areaDisplay.getText().toString());

                    User user = new User();
                    user = null;

                    try {
                        resultRequest = new RegisterActivity.ConnexionFiles().execute(values).get();
                        //user = connect.execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);


                    if (resultRequest) {
                        Toast.makeText(getApplicationContext(), "Utilisateur créé", Toast.LENGTH_LONG).show();
                    } else {
                        //Log.d("Nom", firstNameDisplay.getText().toString());
                        //Log.d("Sexe", String.valueOf(sexeDisplay));
                        Toast.makeText(getApplicationContext(), "Erreur de création de l'utilisateur", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


/********************************************************************************************/

        final Button annulerButton = (Button) findViewById(R.id.buttonAnnuler);
        annulerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(RegisterActivity.this, LoginDisplayActivity.class);
                //intent.putExtra(EXTRA_LOGIN, loginDisplay.getText().toString());
                //intent.putExtra(EXTRA_PASSWORD, pass.getText().toString());
                //startActivity(intent);
                finish();
            }
        });
    }
/********************************************************************************************/

    /**
     * Récupère un objet user.
     * @author François http://www.francoiscolin.fr/
     */
    //public static User getUser() {
    public static class ConnexionFiles extends AsyncTask<ContentValues, Integer, Boolean> {
        public Boolean doInBackground(ContentValues... cValues) {
            User user = new User();
            Boolean resultRequest = true;
            String myurl;
            myurl= "http://lesfousduvolant.cloudapp.net/Covoiturage/RegisterAndroid";

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

                    sb.append(URLEncoder.encode(key, "UTF-8")+"="+URLEncoder.encode(value,"UTF-8"));
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
                    resultRequest = false;
                } else {
                    resultRequest = true;
                    is = conn.getInputStream();
                        /*
                         * InputStreamOperations est une classe complémentaire:
                         * Elle contient une méthode InputStreamToString.
                         */
                    String result = InputStreamOperations.InputStreamToString(is);

                    // On récupère le JSON complet
                    JSONObject jsonObject = new JSONObject(result);

                    // On récupère un objet JSON du tableau
                    //JSONObject obj = new JSONObject(jsonObject.getString("user"));

                    user.setEmail(jsonObject.getString("email"));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            // On retourne le user ou null si le user ne remonte pas
            return resultRequest;
        }
        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(User result) {
            //showDialog("Downloaded " + result + " bytes");
        }

    }

}
 

