package com.twotr.twotr.globalpackfiles;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fujiyuu75.sequent.Sequent;
import com.twotr.twotr.R;


public class MainActivity extends AbsRuntimePermission {
RelativeLayout layout;
TextView signupbut;
Button Bsignin;
    private static final int REQUEST_PERMISSION = 10;
    private Context context;
    private Activity activity;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        activity=this;
        layout =  findViewById(R.id.rel_layout);
       Sequent.origin(layout).anim(getApplicationContext(), com.fujiyuu75.sequent.Animation.FADE_IN_UP).start();
        signupbut=findViewById(R.id.sign_up);
        Bsignin=findViewById(R.id.main_signin);
        requestAppPermissions(new String[]{
                        Manifest.permission.READ_SMS,

                        Manifest.permission.ACCESS_COARSE_LOCATION,

                        Manifest.permission.READ_EXTERNAL_STORAGE,

                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,

                },

                R.string.msg,REQUEST_PERMISSION);
        Bsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
CheckEnableGPS();

            }
        });
        signupbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });





    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    private void CheckEnableGPS() {
        String provider = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.equals(""))
        {
            checkPermission();
            startActivity(new Intent(MainActivity.this, SigninActivity.class));

        } else {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    private void checkPermission(){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED){

        }
        else {

        }
    }






}
