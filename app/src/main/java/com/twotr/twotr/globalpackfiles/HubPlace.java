package com.twotr.twotr.globalpackfiles;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.twotr.twotr.R;
import com.twotr.twotr.db_handlers.SessionManager;
import com.twotr.twotr.studenttwotr.StudentHome;
import com.twotr.twotr.tutorfiles.HomePage;

public class HubPlace extends Activity {
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    String Sroles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_place);
        SessionManager session = new SessionManager(getApplicationContext());
        Shared_user_details=getSharedPreferences("user_detail_mode",0);
        Sroles=  Shared_user_details.getString("roles", null);

//        if (session.isLoggedIn()) {
//            Intent intent = new Intent(HomePage.this, MainActivity.class);
//            startActivity(intent);
//        }

        assert Sroles != null;
        if(session.isLoggedIn())
            {

        if (Sroles.equals("tutor"))
        {


                startActivity(new Intent(HubPlace.this, HomePage.class));
                finish();

        }

        else
        {
            startActivity(new Intent(HubPlace.this, StudentHome.class));
            finish();

        }
        }

    }
}
