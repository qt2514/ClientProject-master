package com.twotr.twotr.studenttwotr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.twotr.twotr.R;
import com.twotr.twotr.db_handlers.SessionManager;
import com.twotr.twotr.globalpackfiles.SigninActivity;
import com.twotr.twotr.tutorfiles.HomePage;
import com.twotr.twotr.tutorfiles.Profile_Edit_Educational;
import com.twotr.twotr.tutorfiles.Profile_Edit_Personal;
import com.twotr.twotr.tutorfiles.TutorCreate;
import com.twotr.twotr.tutorfiles.TutorDashboard;
import com.twotr.twotr.tutorfiles.TutorSchedule;
import com.twotr.twotr.tutorfiles.TutorSettings;
import com.twotr.twotr.tutorfiles.Tutor_VerficationPage;

public class StudentHome extends AppCompatActivity {
    Boolean BisTeachingVerified,BisIdVerified,BisProfessionalCompleted,BisEducationCompleted,BisProfileCompleted,BisMobileVerified,BisEmailVerified;
    private SessionManager session;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    String Sid,Stoken,Sfirstname,Slastname,Susername,Sroles;
     BottomBar bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        bottomBar =  findViewById(R.id.bottomBar_student);

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

        Sroles=  Shared_user_details.getString("roles", null);

//        if (session.isLoggedIn()) {
//            Intent intent = new Intent(HomePage.this, MainActivity.class);
//            startActivity(intent);
//        }
        if (Sroles.equals("tutor"))
        {
            startActivity(new Intent(StudentHome.this, HomePage.class));
            finish();
        }

        if (!BisProfileCompleted)
        {
            startActivity(new Intent(StudentHome.this, Profile_Edit_Personal.class));
            finish();

        }


//
//        FloatingActionButton fab =  findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                session.setLogin(false);
//                editor.clear();
//                editor.apply();
//                startActivity(new Intent(StudentHome.this, SigninActivity.class));
//                finish();
//
//            }
//        });


        bottomBar.setDefaultTab(R.id.tab_dashboard);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                android.support.v4.app.Fragment selectedFragment = null;
                if (tabId == R.id.tab_schedules) {
                   // selectedFragment = TutorSchedule.newInstance();
                }
                else if (tabId == R.id.tab_dashboard) {
                   // selectedFragment = TutorDashboard.newInstance();
                }
                else if (tabId == R.id.tab_create) {
                  //  selectedFragment = TutorCreate.newInstance();
                }

                else if (tabId == R.id.tab_settings) {
                    selectedFragment = StudentSettings.newInstance();

                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentContainer, selectedFragment);
                transaction.commit();
            }
        });

    }

}
