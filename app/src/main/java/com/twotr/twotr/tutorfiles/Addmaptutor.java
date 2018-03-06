package com.twotr.twotr.tutorfiles;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.TinyDB;
import com.twotr.twotr.globalpackfiles.TrackGPS;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Addmaptutor extends FragmentActivity implements LocationListener, OnMapReadyCallback {
    private Button mylocationnav;
    private GoogleMap mMap;
    private TrackGPS gps;
    ListView list29;
    private Button picka;
    int PLACE_PICKER_REQUEST = 1;
    public LatLng mCenterLatLong;
    private Button ridenow;
    String address;
    String cityName;
    String stateName;
    String postlCode;
    String adminarea;
    protected String mAddressOutput;
    protected String mAreaOutput;
    protected String mCityOutput;
    protected String mStateOutput;
    private AddressResultReceiver mResultReceiver;
    TextView textlati,textlongi;
    private ProgressDialog dialog;
    AVLoadingIndicatorView avi;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmaptutor);
        if (isConnectedToNetwork()) {
            // Toast.makeText(getApplicationContext(), uidthree, Toast.LENGTH_SHORT).show();
            avi=findViewById(R.id.avi);
            avi.show();
            list29 = (ListView) findViewById(R.id.listview);
            textlati=findViewById(R.id.text_lat);
            textlongi=findViewById(R.id.text_lng);

            ridenow=findViewById(R.id.ridenow_but);
            ridenow.setVisibility(View.INVISIBLE);
context=this;
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync((OnMapReadyCallback) Addmaptutor.this);
            picka = (Button) findViewById(R.id.pick);
            picka.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    Intent inkl;
                    try {
                        inkl = builder.build(Addmaptutor.this);
                        startActivityForResult(inkl, PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    }
                }
            });
            ridenow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textlati.getText().toString().equals(""))
                    {
                        new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Please Select Location")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                }).show();
                    }
                    else
                    {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
            PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                    getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .build();
            autocompleteFragment.setFilter(typeFilter);
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // Toast.makeText(getApplicationContext(), "Place: " + place.getName(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getApplicationContext(), "Place: " + place.getLatLng(), Toast.LENGTH_SHORT).show();
                    mMap.clear();
                    LatLng newlatlng = place.getLatLng();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newlatlng, 6.5f));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(12.5f), 2000, null);
                    mMap.setMaxZoomPreference(15.5f);
                    mMap.setMinZoomPreference(6.5f);
                }
                @Override
                public void onError(Status status) {
                    Toast.makeText(getApplicationContext(), "An error occurred: " + status, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public void onLocationChanged(Location location) {
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }
    @Override
    public void onProviderEnabled(String s) {
    }
    @Override
    public void onProviderDisabled(String s) {
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gps = new TrackGPS(Addmaptutor.this);
        mMap = googleMap;
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Log.d("Camera postion change" + "", cameraPosition + "");
                mCenterLatLong = cameraPosition.target;
                mMap.clear();
                // getmygps();
                try {
                    Location mLocation = new Location("");
                    mLocation.setLatitude(mCenterLatLong.latitude);
                    mLocation.setLongitude(mCenterLatLong.longitude);
                    avi.hide();
                    startIntentService(mLocation);
                    Geocoder geocoder = new Geocoder(Addmaptutor.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(mCenterLatLong.latitude, mCenterLatLong.longitude, 1);
                        address = addresses.get(0).getSubLocality();
                        cityName = addresses.get(0).getLocality();
                        stateName = addresses.get(0).getAdminArea();
                        //    Toast.makeText(getApplicationContext(), "Your selected latitude: " + mCenterLatLong.latitude, Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(getApplicationContext(), "Your selected longitude: " + mCenterLatLong.longitude, Toast.LENGTH_SHORT).show();
                        String slati=Double.toString(mCenterLatLong.latitude);
                                String slongi=Double.toString(mCenterLatLong.longitude);
                        textlati.setText(slati);
                        textlongi.setText(slongi);

                        TinyDB tinydb = new TinyDB(context);
                          tinydb.putString("latitude",slati);
                         tinydb.putString("longitude",slongi);
                        ridenow.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getmygpsd();
    }
    protected void startIntentService(Location mLocation) {
        //getmygps();
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(AppUtils.LocationConstants.RECEIVER, mResultReceiver);
        intent.putExtra(AppUtils.LocationConstants.LOCATION_DATA_EXTRA, mLocation);
        startService(intent);
    }
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            mAddressOutput = resultData.getString(AppUtils.LocationConstants.RESULT_DATA_KEY);
            mAreaOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_AREA);
            mCityOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_CITY);
            mStateOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_STREET);
            getmygps();
            displayAddressOutput();
            getmygps();
            if (resultCode == AppUtils.LocationConstants.SUCCESS_RESULT) {

            }
        }
    }
    protected void displayAddressOutput() {
        try {
            if (mAreaOutput != null)
                Toast.makeText(getApplicationContext(),"mAddressOutput",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public void getmygps()
    {
        gps = new TrackGPS(Addmaptutor.this);
        Double lat = gps.getLatitude();
        Double lng = gps.getLongitude();
        LatLng sydney = new LatLng(lat, lng);
        float zoomLevel =10;
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses  = null;
        try {
            addresses = geocoder.getFromLocation(lat,lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.twotr_pin
        ))
                .position(new LatLng(lat,lng)));
        avi.hide();
        mMap.addCircle(new CircleOptions()
                .center(new LatLng(lat,lng))
                .radius(1000)
                .fillColor(Color.argb(20, 255, 0, 255))
                .strokeColor(Color.BLUE)
                .strokeWidth(2.0f));
    }
    public void getmygpsd()
    {
        gps = new TrackGPS(Addmaptutor.this);
        Double lat = gps.getLatitude();
        Double lng = gps.getLongitude();
        LatLng sydney = new LatLng(lat, lng);
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses  = null;
        try {
            addresses = geocoder.getFromLocation(lat,lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,6.5f));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.5f), 2000, null);
        mMap.setMaxZoomPreference(15.5f);
        mMap.setMinZoomPreference(6.5f);
    }
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}