package com.twotr.twotr.guestfiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;

import com.twotr.twotr.tutorfiles.Profile_Update_Personal;
import com.twotr.twotr.tutorfiles.RecyclerViewAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class GuestProfile extends AppCompatActivity {
    Button but_personal;
    ScrollView scrollview_personal;
    String s_profile,Screatedid;
    SharedPreferences Shared_user_details;
    String Stoken,Sid,SfirstName,SmiddleName,SlastName,Sdob,Sdescription,SlineOne,SzipCode,Snumber,Scode,Sroles;
    TextView TVemailverify,TVmobileverify;
    String  Sgender;
    AVLoadingIndicatorView avi;
    Context context;
    TextView TVfirstname,TVmiddlename,TVlastname,TVgender,TVdob,TVaddress,TVpostalcode,TVmobile_number,TVemail,TVaboutme,TVaddress1,
    TVmobile_number1,TVemail1;
    TextView TVmajor,TVinsitute,TVyear;
    TextView TVtitle,TVproffinstitue,TVexperi;
    TextView TVheadernamefull;
    CircleImageView CIVprofimage;
    ImageButton IB_back;
    GifImageView pendinggiflist;
    TextView pendingtextlist;
    List<String> Listgrade ;
    List<String> ListSubject ;
    RecyclerView recyclerView,recyclerViewsub;
    RecyclerView.LayoutManager RecyclerViewLayoutManager,RecyclerViewLayoutManagersub;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter,RecyclerViewHorizontalAdaptersub;
    LinearLayoutManager HorizontalLayout,HorizontalLayoutsub ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_profile);
        context = this;
        but_personal = findViewById(R.id.prof_but_personal);

        scrollview_personal = findViewById(R.id.srcollview_personal);

        TVfirstname = findViewById(R.id.profile_name);
        TVmiddlename = findViewById(R.id.profile_middle);
        TVlastname = findViewById(R.id.profile_last);
        TVgender = findViewById(R.id.profile_gender);
        TVdob = findViewById(R.id.profile_dob);
        TVaddress = findViewById(R.id.profile_address);
        TVpostalcode = findViewById(R.id.profile_postcode);
        TVmobile_number = findViewById(R.id.profile_monumber);
        TVemail = findViewById(R.id.profile_email);

        TVaddress1 = findViewById(R.id.profile_address1);
        TVmobile_number1 = findViewById(R.id.profile_monumber1);
        TVemail1 = findViewById(R.id.profile_email1);

        TVaboutme = findViewById(R.id.profile_description);
        TVemailverify = findViewById(R.id.profile_email_verify);
        TVmobileverify = findViewById(R.id.profile_mobile_verify);
        TVheadernamefull = findViewById(R.id.prof_prof_name);
        pendinggiflist = findViewById(R.id.nodatagif);
        pendingtextlist = findViewById(R.id.nodatatext);

        Shared_user_details = getSharedPreferences("user_detail_mode", 0);
        Stoken = Shared_user_details.getString("token", null);
        Sid = Shared_user_details.getString("id", null);
        Sroles = Shared_user_details.getString("roles", null);

        TVmajor = findViewById(R.id.profile_edu_major);
        TVinsitute = findViewById(R.id.prof_edu_insitute);
        TVyear = findViewById(R.id.prof_edu_dyear);
        CIVprofimage = findViewById(R.id.image_profile);
        TVtitle = findViewById(R.id.prof_profess_title);
        TVproffinstitue = findViewById(R.id.prof_profess_insti);
        TVexperi = findViewById(R.id.prof_profess_experi);
        IB_back = findViewById(R.id.back_ima_scedule);
        recyclerView = findViewById(R.id.recyclerview1);
        recyclerViewsub = findViewById(R.id.recyclerviewsubject);
        final Intent intent = getIntent();
        Bundle Bintent = intent.getExtras();
        if(Bintent != null) {
            Screatedid=Bintent.getString("createdby");

        }
        IB_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        avi = findViewById(R.id.avi);
        Listgrade = new ArrayList<String>();
        ListSubject = new ArrayList<String>();
avi.show();
TVaddress1.setVisibility(View.GONE);
TVemail1.setVisibility(View.GONE);
TVmobile_number1.setVisibility(View.GONE);

        TVaddress.setVisibility(View.GONE);
        TVpostalcode.setVisibility(View.GONE);
        TVmobile_number.setVisibility(View.GONE);
        TVemail.setVisibility(View.GONE);

        TVemailverify.setVisibility(View.GONE);

        TVmobileverify.setVisibility(View.GONE);
        gettallprofiledetails( Screatedid);


        s_profile = "personal";

        but_personal.setTextColor(getResources().getColor(R.color.buttonColorPrimary));
        but_personal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                s_profile = "personal";
                but_personal.setTextColor(getResources().getColor(R.color.buttonColorPrimary));

                scrollview_personal.setVisibility(View.VISIBLE);
                pendinggiflist.setVisibility(View.INVISIBLE);
                pendingtextlist.setVisibility(View.INVISIBLE);

            }
        });



        TVemailverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MaterialDialog.Builder(GuestProfile.this)

                        .title("Email Verification")
                        .content("Verification Mail has been sent to your mail id.Please Check !")
                        .positiveText("Ok")
                        .negativeText("Resend")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Toast.makeText(context, "Dismissed", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Toast.makeText(context, "EMail Resend", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .show();

            }
        });



        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(Listgrade);

        HorizontalLayout = new LinearLayoutManager(GuestProfile.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setHorizontalScrollBarEnabled(false);

        RecyclerViewLayoutManagersub = new LinearLayoutManager(getApplicationContext());
        recyclerViewsub.setLayoutManager(RecyclerViewLayoutManagersub);
        RecyclerViewHorizontalAdaptersub = new RecyclerViewAdapter(ListSubject);
        HorizontalLayoutsub = new LinearLayoutManager(GuestProfile.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewsub.setLayoutManager(HorizontalLayoutsub);
        recyclerViewsub.setHorizontalScrollBarEnabled(false);


    }


    public void gettallprofiledetails(String creatid) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_url_twotr.Guest_profile_details+creatid, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    //  String id = jObj.getString("_id");
                    // String userId = jObj.getString("userId");
                    //  String updatedAt = jObj.getString("updatedAt");
                    //  String createdAt = jObj.getString("createdAt");

                    JSONObject profile = jObj.getJSONObject("profileInfo");
                    Sdob = profile.getString("dob");
                    Sgender = profile.getString("gender");
                    //       String Stimezone =profile.getString("timezone");
                    //     String Scantutor =profile.getString("canTutor");
                    //    String  SdefaultCountry=profile.getString("defaultCountry");
                    Sdescription = profile.getString("description");


                    //     String  Scity=paddress.getString("city");
                    //   String  Sstate=paddress.getString("state");


                    JSONArray jsonArray = profile.getJSONArray("gradeLevel");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        Listgrade.add(String.valueOf(jsonArray.get(i)));
                        //   Number.add(String.valueOf(jsonArray.get(i)));
                    }
                    recyclerView.setAdapter(RecyclerViewHorizontalAdapter);

                    JSONArray jsonArray1 = profile.getJSONArray("subjectIds");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(i);
                        String Skind = jsonObject.getString("title");
                        String Sid = jsonObject.getString("_id");
                        ListSubject.add(Skind);

                    }
                    recyclerViewsub.setAdapter(RecyclerViewHorizontalAdaptersub);


//roles of tutor is left
                    JSONObject userprofile = jObj.getJSONObject("userProfile");
                    SfirstName = userprofile.getString("firstName");
                    try {
                        SlastName = userprofile.getString("lastName");
                        SmiddleName = userprofile.getString("middleName");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    TVfirstname.setText(SfirstName);
                    TVmiddlename.setText(SmiddleName);
                    TVlastname.setText(SlastName);
                    TVgender.setText(Sgender);
                    String monthformating = DateTimeUtils.formatWithPattern(Sdob, "MMM dd, yyyy");
                    TVdob.setText(monthformating);

                    TVaboutme.setText(Sdescription);
                    TVheadernamefull.setText(SfirstName + " " + SlastName);

                    avi.hide();



//                    try {
//                        JSONObject educationinfo = jObj.getJSONObject("educationInfo");
//                        JSONArray jsonArray2 = educationinfo.getJSONArray("degrees");
//                        for (int i = 0; i < jsonArray2.length(); i++) {
//                            JSONObject jsonObjectedu = jsonArray2.getJSONObject(i);
//                            String Smajor = jsonObjectedu.getString("major");
//                            String Sinstitution = jsonObjectedu.getString("institution");
//                            String SfieldOfStudy = jsonObjectedu.getString("fieldOfStudy");
//                            String SstartYear = jsonObjectedu.getString("startYear");
//                            String SendYear = jsonObjectedu.getString("endYear");
//                            Boolean SisStudyingCurrently = jsonObjectedu.getBoolean("isStudyingCurrently");
//
//                            TVmajor.setText(Smajor);
//                            TVinsitute.setText(Sinstitution);
//                            TVyear.setText(SstartYear + "-" + SendYear);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    try {
//                        JSONObject professionalInfo = jObj.getJSONObject("professionalInfo");
//                        String Stitle = professionalInfo.getString("title");
//                        String SinstitutionName = professionalInfo.getString("institutionName");
//                        String Sexperience = professionalInfo.getString("experience");
//
//                        TVtitle.setText(Stitle);
//                        TVproffinstitue.setText(SinstitutionName);
//                        TVexperi.setText(Sexperience);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                    String Surl = null;
                    try {
                        JSONObject profilePicture = userprofile.getJSONObject("profilePicture");
                        String profileimage = profilePicture.getString("url");

                        Surl = Global_url_twotr.Image_Base_url + profileimage;

                        Picasso
                                .with(context)
                                .load(Surl)
                                .fit()
                                .centerCrop()
                                .into(CIVprofimage);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                avi.hide();
                new SweetAlertDialog(GuestProfile.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Password Mismatch")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).show();
            }
        }) {
            @Override
            public String getBodyContentType() {

                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("content-Type", "application/json");
                headers.put("x-tutor-app-id", "tutor-app-android");
                return headers;

            }


        };

        requestQueue.add(stringRequest);
    }




}