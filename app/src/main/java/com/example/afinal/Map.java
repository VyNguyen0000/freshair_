package com.example.afinal;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Map extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    SupportMapFragment mapFragment;
   Spinner sp_spinner;
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //addControls();
        //addEvents();

    }

    //private void addEvents() {
        //sp_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          //  @Override
            //public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position) {
//                    case 0:
//                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//                        break;
//                    case 1:
//                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//                        break;
//                    case 2:
//                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//                        break;
//                    case 3:
//                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // Không có phần tử nào được chọn
//            }
//        });
//    }
//
//    private void addControls() {
//        sp_spinner = findViewById(R.id.spinner2);
//
//        ArrayList<String> ds_StyleMap = new ArrayList<>();
//        ds_StyleMap.add("Style 1");
//        ds_StyleMap.add("Style 2");
//        ds_StyleMap.add("Style 3");
//        ds_StyleMap.add("Style 4");
//
//        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ds_StyleMap);
//        sp_spinner.setAdapter(arrayAdapter);
//    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap){
        mMap=googleMap;
        LatLng uitLocation = new LatLng(10.87,106.80324);
        mMap.addMarker(new MarkerOptions().position(uitLocation).title("UIT"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uitLocation,15));
    }

}
