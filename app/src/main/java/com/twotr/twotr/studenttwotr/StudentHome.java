package com.twotr.twotr.studenttwotr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.twotr.twotr.R;
import com.twotr.twotr.db_handlers.SessionManager;
import com.twotr.twotr.globalpackfiles.SigninActivity;
import com.twotr.twotr.tutorfiles.HomePage;
import com.twotr.twotr.tutorfiles.Profile_Edit_Educational;
import com.twotr.twotr.tutorfiles.Profile_Edit_Personal;
import com.twotr.twotr.tutorfiles.Tutor_VerficationPage;

public class StudentHome extends AppCompatActivity {
    Boolean BisTeachingVerified,BisIdVerified,BisProfessionalCompleted,BisEducationCompleted,BisProfileCompleted,BisMobileVerified,BisEmailVerified;
    private SessionManager session;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    String Sid,Stoken,Sfirstname,Slastname,Susername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        session = new SessionManager(getApplicationContext());
        Shared_user_details=getSharedPreferences("user_detail_mode",0);
        editor = Shared_user_details.edit();
        Sid=  Shared_user_details.getString("id", null);
        Stoken=  Shared_user_details.getString("token", null);
        Sfirstname= Shared_user_details.getString("firstName", null);
        Slastname= Shared_user_details.getString("lastName", null);
        Susername=  Shared_user_details.getString("username", null);
        BisTeachingVerified=    Shared_user_details.getBoolean("isTeachingVerified",false);
        BisIdVerified= Shared_user_details.getBoolean("isIdVerified",false);
        BisProfessionalCompleted=  Shared_user_details.getBoolean("isProfessionalCompleted",false);
        BisEducationCompleted=  Shared_user_details.getBoolean("isEducationCompleted",false);
        BisProfileCompleted=  Shared_user_details.getBoolean("isProfileCompleted",false);
        BisMobileVerified=  Shared_user_details.getBoolean("isMobileVerified",false);
        BisEmailVerified=   Shared_user_details.getBoolean("isEmailVerified",false);
//        if (session.isLoggedIn()) {
//            Intent intent = new Intent(HomePage.this, MainActivity.class);
//            startActivity(intent);
//        }

        if (!BisProfileCompleted)
        {
            startActivity(new Intent(StudentHome.this, Profile_Edit_Personal.class));
            finish();

        }



        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setLogin(false);
                editor.clear();
                editor.apply();
                startActivity(new Intent(StudentHome.this, SigninActivity.class));
                finish();

            }
        });




    }

}
