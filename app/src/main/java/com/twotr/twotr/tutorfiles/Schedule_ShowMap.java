package com.twotr.twotr.tutorfiles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.TrackGPS;

public class Schedule_ShowMap extends FragmentActivity implements OnMapReadyCallback {

    TrackGPS tgps;
    Double latitude_dou,longitude_double;
    String Slati,Slongi,type_subject,subname,hrschmon;
    TextView TVsubname,TVsubtype,TVhrsshow;
    Button Bgoogmap;
    ImageButton IB_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__show_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        TVsubname=findViewById(R.id.subject_name);
        TVsubtype=findViewById(R.id.type_detail_subject);
        TVhrsshow=findViewById(R.id.hours_sched);
        Bgoogmap=findViewById(R.id.opengoogmap);
        IB_back=findViewById(R.id.back_ima_scedule);
        IB_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bgoogmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:"+Slati+","+Slongi+"");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        Intent intent = getIntent();
        Bundle Bintent = intent.getExtras();
        if(Bintent != null)
        {

            Slati=(String) Bintent.get("latitude");
            Slongi=(String) Bintent.get("longitude");
            type_subject=(String) Bintent.get("subjecttype");
            subname=(String) Bintent.get("subjectname");
            hrschmon=(String) Bintent.get("subjecthours");
TVsubname.setText(subname);
TVsubtype.setText(type_subject);
TVhrsshow.setText(hrschmon);



        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        //        tgps = new TrackGPS(Schedule_ShowMap.this);
//        Double latm = tgps.getLatitude();
//        Double lngm = tgps.getLongitude();sl
        latitude_dou=Double.parseDouble(Slati);
        longitude_double=Double.parseDouble(Slongi);
        LatLng schedulemap = new LatLng(latitude_dou, longitude_double);
        googleMap.addMarker(new MarkerOptions().position(schedulemap));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(schedulemap, 6.5f));
        googleMap.setMaxZoomPreference(15.5f);
        googleMap.setMinZoomPreference(6.5f);
    }
}
