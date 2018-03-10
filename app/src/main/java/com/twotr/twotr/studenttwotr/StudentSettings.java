package com.twotr.twotr.studenttwotr;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thefinestartist.finestwebview.FinestWebView;
import com.twotr.twotr.R;
import com.twotr.twotr.db_handlers.SessionManager;
import com.twotr.twotr.globalpackfiles.SigninActivity;
import com.twotr.twotr.tutorfiles.Profile_Page;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentSettings extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    TextView TVprofile_link,TVsignout;
    DrawerLayout drawer;

    TextView TVtermsandcondi,TVabout,TVshare;
    private SessionManager session;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    public static StudentSettings newInstance() {
        StudentSettings fragment= new StudentSettings();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_student_settings, container, false);
        drawer =view.findViewById(R.id.drawer_layout);
        session = new SessionManager(getContext());
        Shared_user_details=this.getActivity().getSharedPreferences("user_detail_mode",0);
        editor = Shared_user_details.edit();
        TVprofile_link=view.findViewById(R.id.profile_link);
        TVsignout=view.findViewById(R.id.signout_settings);
        TVtermsandcondi=view.findViewById(R.id.termsandcondi_settings);
        TVabout=view.findViewById(R.id.about_settings);
        TVshare=view.findViewById(R.id.shareapp);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toggle.syncState();
        TVabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FinestWebView.Builder(getContext())
                        .titleDefault("Twotr")
                        .showUrl(false)
                        .dividerHeight(0)
                        .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                        .gradientDivider(false)
                        .titleColorRes(R.color.finestWhite)
                        .urlColorRes(R.color.finestWhite)
                        .statusBarColorRes(R.color.colorPrimaryDark)
                        .toolbarColorRes(R.color.colorPrimary)
                        .iconPressedColorRes(R.color.colorPrimary)
                        .progressBarColorRes(R.color.colorPrimary)
                        .backPressToClose(false)
                        .gradientDivider(false)
                        .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit, R.anim.activity_close_enter, R.anim.activity_close_exit)
                        .show("https://www.twotr.com/about.html");
            }
        });
        TVshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "I Love Twotr";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"TWOTR");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, " This is about service"));
            }
        });
        TVtermsandcondi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FinestWebView.Builder(getContext())
                        .titleDefault("Twotr")
                        .showUrl(false)
                        .dividerHeight(0)
                        .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                        .gradientDivider(false)
                        .titleColorRes(R.color.finestWhite)
                        .urlColorRes(R.color.finestWhite)
                        .statusBarColorRes(R.color.colorPrimaryDark)
                        .toolbarColorRes(R.color.colorPrimary)
                        .iconPressedColorRes(R.color.colorPrimary)
                        .progressBarColorRes(R.color.colorPrimary)
                        .backPressToClose(false)
                        .gradientDivider(false)
                        .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit, R.anim.activity_close_enter, R.anim.activity_close_exit)
                        .show("https://www.twotr.com/condition.html");
            }
        });
        TVprofile_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), Profile_Page.class));

            }
        });
        TVsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                session.setLogin(false);
                editor.clear();
                editor.apply();

                startActivity(new Intent(getActivity(), SigninActivity.class));
                getActivity().finish();

            }
        });
        NavigationView navigationView = view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            drawer.openDrawer(GravityCompat.END);
        }



//display(view);
        return view;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // startActivity(new Intent(getActivity(), Profile_Page.class));

        }
        else if (id == R.id.nav_subscribe) {

        }
        else if (id == R.id.nav_ac_details) {

        }
        else if (id == R.id.nav_reminder) {

        }
        else if (id == R.id.nav_notifications) {

        }
        else if (id == R.id.nav_language) {

        }
        else if (id == R.id.nav_sign_out) {

        }
        else if (id == R.id.nav_about_us) {

        }
        else if (id == R.id.nav_terms_conditions) {

        }
        return true;
    }




}
