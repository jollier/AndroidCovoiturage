package com.fousduvolant.androidcovoiturage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.Gravity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import model.User;
import model.UserConnect;
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
    TextView addressCpDisplay;
    TextView addressCityDisplay;
    TextView longitudeDisplay;
    TextView latitudeDisplay;
    TextView phoneNumberDisplay;
    String sexeDisplay;
    String isconducteurDisplay;
    String issmokerDisplay;
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
        passwordDisplay2 = (TextView) findViewById(R.id.Pwd2);
        firstNameDisplay = (TextView) findViewById(R.id.firstName);
        lastNameDisplay = (TextView) findViewById(R.id.lastName);
        addressNumberDisplay = (TextView) findViewById(R.id.addressNumber);
        addressWayDisplay = (TextView) findViewById(R.id.addressWay);
        addressCpDisplay = (TextView) findViewById(R.id.addressCp);
        addressCityDisplay = (TextView) findViewById(R.id.addressCity);
        phoneNumberDisplay = (TextView) findViewById(R.id.phoneNumber);
        final CheckBox homme = (CheckBox) findViewById(R.id.sexeH);
        final CheckBox smoker =(CheckBox)findViewById(R.id.smokeOk);
        final CheckBox driver = (CheckBox) findViewById(R.id.driverY);
        areaDisplay= (TextView) findViewById(R.id.area);


        if (intent != null) {

            User user = UserConnect.getUser();
            if (user!=null) {
                emailDisplay.setText(user.getEmail());
                lastNameDisplay.setText(user.getLastName());
                firstNameDisplay.setText(user.getFirstName());
                addressNumberDisplay.setText(user.getAddressNumber());
                addressWayDisplay.setText(user.getAddressWay());
                addressCpDisplay.setText(user.getAddressCp());
                addressCityDisplay.setText(user.getAddressCity());
                phoneNumberDisplay.setText(user.getPhoneNumber());
                homme.setChecked((user.getSexe().equals("1")));
                smoker.setChecked((user.getIsSmoker().equals("1")));
                driver.setChecked((user.getIsConducteur().equals("1")));
                areaDisplay.setText(user.getArea());
            }
            //Bundle extras = intent.getExtras();
                /*
            emailDisplay.setText(intent.getStringExtra(EXTRA_EMAIL));
            passwordDisplay.setText(intent.getStringExtra(EXTRA_PASSWORD));
            firstNameDisplay.setText(intent.getStringExtra(EXTRA_FIRSTNAME));
            lastNameDisplay.setText(intent.getStringExtra(EXTRA_LASTNAME));
            addressNumberDisplay.setText(intent.getStringExtra(EXTRA_ADDRESSNUMBER));
            addressWayDisplay.setText(intent.getStringExtra(EXTRA_ADDRESSWAY));
            addressCpDisplay.setText(intent.getStringExtra(EXTRA_ADDRESSCP));
            addressCityDisplay.setText(intent.getStringExtra(EXTRA_ADDRESSCITY));
            phoneNumberDisplay.setText(intent.getStringExtra(EXTRA_PHONENUMBER));
            intent.putExtra(EXTRA_SEXE,sexeDisplay);
            intent.putExtra(EXTRA_ISCONDUCTEUR,isconducteurDisplay);
            intent.putExtra(EXTRA_ISSMOKER,issmokerDisplay);
            areaDisplay.setText(intent.getStringExtra(EXTRA_AREA));
            */
        }

        final Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<String> reponse = new ArrayList<String>();

                if (homme.isChecked()) {
                    sexeDisplay="1";
                }else {

                    sexeDisplay ="2";
                }
                if (smoker.isChecked()){
                    issmokerDisplay = "1";
                }else {
                    issmokerDisplay = "0";
                }
                if (driver.isChecked()){
                    isconducteurDisplay ="1";
                }else {
                    isconducteurDisplay="0";
                }

                // Test que les champs soient saisis
                if (firstNameDisplay.getText().length()==0) {
                    firstNameDisplay.requestFocus();
                    firstNameDisplay.setError("Vous devez renseigner ce champs");
                } else if (lastNameDisplay.getText().length()==0){
                    lastNameDisplay.requestFocus();
                    lastNameDisplay.setError("Vousdevez renseigner ce champs");
                } else if (passwordDisplay.getText().length()==0) {
                    passwordDisplay.requestFocus();
                    passwordDisplay.setError("Vous devez renseigner ce champs");
                } else if (!(passwordDisplay.getText().toString().equals(passwordDisplay2.getText().toString()))) {
                    passwordDisplay2.requestFocus();
                    passwordDisplay2.setError("Mots de passe différents");
                } else if (validatePassword(passwordDisplay.getText().toString())!=null){
                    String retour = validatePassword(passwordDisplay.getText().toString());
                    passwordDisplay.requestFocus();
                    passwordDisplay.setError(retour);
                } else if (emailDisplay.getText().toString().isEmpty()) {
                    emailDisplay.requestFocus();
                    emailDisplay.setError("Vous devez renseigner ce champs");
                } else if (!emailDisplay.getText().toString().trim().matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
                    emailDisplay.requestFocus();
                    emailDisplay.setError("Vous devez renseigner une adresse mail valide");
                }
                else {
                    //User user = getUser();
                    ContentValues values = new ContentValues();
                    //values.put("email", "julien.ollier@berger-levrault.fr");
                    //values.put("pwd1", "Azerty12");
                    values.put("email", emailDisplay.getText().toString());
                    values.put("pwd1", passwordDisplay.getText().toString());
                    values.put("lastName", lastNameDisplay.getText().toString());
                    values.put("firstName", firstNameDisplay.getText().toString());
                    values.put("addressNumber", addressNumberDisplay.getText().toString());
                    values.put("addressWay", addressWayDisplay.getText().toString());
                    values.put("addressCp", addressCpDisplay.getText().toString());
                    values.put("addressCity", addressCityDisplay.getText().toString());
                    values.put("phoneNumber", phoneNumberDisplay.getText().toString());
                    values.put("sexe", sexeDisplay);
                    values.put("isConducteur",isconducteurDisplay);
                    values.put("isSmoker",issmokerDisplay);
                    values.put("area", areaDisplay.getText().toString());

                   try {
                        reponse = new RegisterActivity.ConnexionFiles().execute(values).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);


                    if (reponse.get(0).equals("200")) {
                        affichageToast(R.layout.toast_valid, reponse.get(1).toString());
                    } else {
                        affichageToast(R.layout.toast_erreur, reponse.get(1).toString());
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
     * RÃ©cupÃ¨re un objet user.
     * @author FranÃ§ois http://www.francoiscolin.fr/
     */
    //public static User getUser() {
    public static class ConnexionFiles extends AsyncTask<ContentValues, Integer, ArrayList<String>> {
        public ArrayList<String> doInBackground(ContentValues... cValues) {
            User user = new User();
            ArrayList<String> reponse = new ArrayList<String>();
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
                }else {
                    is = conn.getInputStream();
                }
                String result = InputStreamOperations.InputStreamToString(is);
                is.close();
                reponse.add(""+responseCode);
                reponse.add(result);

            } catch (Exception e) {
                e.printStackTrace();
            }
            // On retourne un tableau avec le code reponse et le message de reponse
            return reponse;
        }
        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(User result) {
            //showDialog("Downloaded " + result + " bytes");
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

    public String validatePassword(String password){
        String retour=null;

        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasNumber = password.matches(".*\\d.*");

        if ((password.length() < 8)|| (!hasUppercase)|| (!hasLowercase) || (!hasNumber)) {
            retour = "Le mot de passe est incorrect. Il doit contenir au minimum 8 caractères, une majuscule, des minuscules et un chiffre";
        }

        return retour;
    }

}

