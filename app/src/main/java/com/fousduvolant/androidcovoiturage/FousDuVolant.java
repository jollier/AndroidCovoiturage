package com.fousduvolant.androidcovoiturage;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FousDuVolant extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private static final String editArrivee = "64 rue Jean Rostand, 31670 Labège";
    private EditText txtAdresse;
    private Spinner spinner;
    private Button btnRechercher;
    private GoogleMap mMap;
    ArrayList<LatLng> markerPoints;
    Geocoder geocoder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fous_du_volant);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Récupération du Spinner déclaré dans le fichier main.xml de res/layout
        spinner = (Spinner) findViewById(R.id.rayon);
        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        List rayonList = new ArrayList();
        rayonList.add("1");
        rayonList.add("2");
        rayonList.add("5");
        rayonList.add("10");
        rayonList.add("30");

        // Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this)
        // et un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
        // avec la liste des elements (rayonList)
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rayonList);

        // On definit une présentation du spinner quand il est déroulé  (android.R.layout.simple_spinner_dropdown_item) */
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        //Enfin on passe l'adapter au Spinner et c'est tout
        spinner.setAdapter(adapter);

        // --- GOOGLE MAP ---

        // Initializing
        markerPoints = new ArrayList<LatLng>();
        geocoder = new Geocoder(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnRechercher = (Button) findViewById(R.id.button);
        btnRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAdresse =  (EditText) findViewById(R.id.editDepart);
                if (txtAdresse.getText().length() == 0) {
                    Toast.makeText(FousDuVolant.this, "Veuillez entrer une adresse", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        // Ajout du marqueur de l'adresse saisie
                        String adresse = txtAdresse.getText().toString();
                        List<Address> addressList = geocoder.getFromLocationName(adresse, 5);
                        Address location = addressList.get(0);
                        location.getLatitude();
                        location.getLongitude();

                        LatLng adrLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        ajoutMarker(adrLatLng, txtAdresse.getText().toString());



                        //FousDuVolant.this.startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }


    public void ajoutMarker(LatLng latLng, String nom) {
        MarkerOptions marker = new MarkerOptions().position(latLng).title(nom);
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(marker);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(latLng.latitude, latLng.longitude)).zoom(11).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_connecter) {
            Intent intent = new Intent(FousDuVolant.this, LoginDisplayActivity.class);
            //intent.putExtra(EXTRA_LOGIN, loginDisplay.getText().toString());
            //intent.putExtra(EXTRA_PASSWORD, pass.getText().toString());

            startActivity(intent);

        } else if (id == R.id.nav_inscrire) {
            Intent intent = new Intent(FousDuVolant.this, RegisterActivity.class);
            //intent.putExtra(EXTRA_LOGIN, loginDisplay.getText().toString());
            //intent.putExtra(EXTRA_PASSWORD, pass.getText().toString());

            startActivity(intent);

        } else if (id == R.id.nav_close) {
            // Fermer l'application
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
