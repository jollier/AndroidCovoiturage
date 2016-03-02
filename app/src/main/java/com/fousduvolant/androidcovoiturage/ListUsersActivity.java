package com.fousduvolant.androidcovoiturage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import model.UserList;
import service.InputStreamOperations;

/**
 * Created by AREDON on 17/02/2016.
 */
public class ListUsersActivity extends AppCompatActivity implements OnMapReadyCallback{
    ListView myListView;
    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_users_activity_layout_listview_main);
        Intent intent = getIntent();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        myListView = (ListView) findViewById(R.id.listView);

        ContentValues values = new ContentValues();
        values.put("session", "session");

        ArrayList<User> listeServerUsers = new ArrayList<User>();
        ArrayList<UserList> listeAAfficher = new ArrayList<UserList>();

        try {
            listeServerUsers = new ListUsersActivity.ConnexionFiles().execute(values).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(int i=0;i < listeServerUsers.size();i++) {
            User user = listeServerUsers.get(i);
            if (user.getIsConducteur().equalsIgnoreCase("O")) {
                String lastName = user.getLastName();
                String firstName = user.getFirstName();
                String email=user.getEmail();
                String phoneNumber = user.getPhoneNumber();
                String address = user.getAddressCP() + " " + user.getAddressCity();
                listeAAfficher.add(new UserList(lastName + " " + firstName, address));
            }
        }

        // 1. pass context and data to the custom adapter
        ListUsersActivityAdapter adapter = new ListUsersActivityAdapter(this, listeAAfficher);

        //2. setListAdapter
        ListView listView = (ListView) findViewById(R.id.listView);

        // 3. setListAdapter
        listView.setAdapter(adapter);

        // 4. setListener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = "Clicked entry = " + position;
                Log.i("ListView", message);
            }
        });

    }

    /**
     * Récupère un objet user.
     * @author François http://www.francoiscolin.fr/
     */
    //public static User getUser() {
    public static class ConnexionFiles extends AsyncTask<ContentValues, Integer, ArrayList<User>> {
        public ArrayList<User> doInBackground(ContentValues... cValues) {
            User user = new User();
            String myurl;
            myurl= "http://lesfousduvolant.cloudapp.net/Covoiturage/ListJson";
            ArrayList<User> myListeUsers = null;

            try {

                URL url = new URL(myurl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

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
                } else {
                    is = conn.getInputStream();
                    /*
                     * InputStreamOperations est une classe complémentaire:
                     * Elle contient une méthode InputStreamToString.
                     */

                    String result = InputStreamOperations.InputStreamToString(is);

                    JSONArray data = null;
                    User myUser = null;
                    JSONObject jsonObject = null;
                    try {
                        data = new JSONArray(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    myListeUsers = new ArrayList<>();
                    if (data != null) {
                        for (int j = 0; j < data.length(); j++) {
                            try {
                                jsonObject = data.getJSONObject(j);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                myUser = new User();
                                myUser.setLastName(jsonObject.getString("lastName"));
                                myUser.setFirstName(jsonObject.getString("firstName"));
                                myUser.setEmail(jsonObject.getString("email"));
                                myUser.setAddressNumber(jsonObject.getString("addressNumber"));
                                myUser.setAddressWay(jsonObject.getString("addressWay"));
                                myUser.setAddressCP(jsonObject.getString("addressCP"));
                                myUser.setAddressCity(jsonObject.getString("addressCity"));
                                myUser.setLongitude(jsonObject.getString("longitude"));
                                myUser.setLatitude(jsonObject.getString("latitude"));
                                myUser.setPhoneNumber(jsonObject.getString("phoneNumber"));
                                myUser.setIsConducteur(jsonObject.getString("isConducteur"));
                                myUser.setIsSmoker(jsonObject.getString("isSmoker"));
                                myUser.setArea(jsonObject.getString("area"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (myUser != null) {
                                myListeUsers.add(myUser);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            // On retourne les user ou null
            return myListeUsers;
        }
        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(User result) {
            //showDialog("Downloaded " + result + " bytes");
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng BL = new LatLng(43.5432556,1.5100196);
        mMap.addMarker(new MarkerOptions()
                .position(BL)
                .title("Berger-Levrault"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(BL));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        CameraPosition cameraPosition = new CameraPosition.Builder() .
                target(BL).tilt(45).zoom(13).bearing(0).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }


}
