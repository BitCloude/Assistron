package com.appers.ayvaz.assistron;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsDest extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String address;
    private double lat;
    private double lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_dest);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Mydata", Context.MODE_PRIVATE);
        address = sharedPreferences.getString("GOT","DEF");
        Toast.makeText(getApplicationContext(),address+"", Toast.LENGTH_LONG).show();
        String LatAndLong = convertAddress(address);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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


        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        // Add a marker in Sydney and move the camera
        LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(loc).title("You Are Here"));
        LatLng dest = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(dest).title("Destination"));



        mMap.moveCamera(CameraUpdateFactory.newLatLng(dest));
    }

    public String convertAddress(String address) {
        String location = "location";
        //Instantiating GeoCoder class.
        Geocoder geoCoder = new Geocoder(this);

        //Checking for empty user input
        if (address != null && !address.isEmpty()) {
            try {
                //using GeoCoder to get location information
                List<Address> addressList = geoCoder.getFromLocationName(
                        address, 1);


                if (addressList != null && addressList.size() > 0) {
                    //Executed when GeoCoder returns a valid Location
                    lat = addressList.get(0).getLatitude();
                    lng = addressList.get(0).getLongitude();
                    location = "" + lat + " " + lng;
                } else {
                    location = "No Location Received";
                    Toast.makeText(getApplicationContext(),"No Location", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } // end catch
        } // end if
        return location;
    }



}


