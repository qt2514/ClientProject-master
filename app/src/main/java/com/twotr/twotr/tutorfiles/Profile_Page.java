package com.twotr.twotr.tutorfiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
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

public class Profile_Page extends AppCompatActivity {
    Button but_personal,but_educational,but_professional,but_profile_edit;
    ScrollView scrollview_personal,scrollView_educational,scrollview_professional;
    String s_profile;
    SharedPreferences Shared_user_details;
    String Stoken;
    TextView TVemailverify,TVmobileverify;
    AVLoadingIndicatorView avi;
    Context context;
    TextView TVfirstname,TVmiddlename,TVlastname,TVgender,TVdob,TVaddress,TVpostalcode,TVmobile_number,TVemail,TVaboutme;
   TextView TVmajor,TVinsitute,TVyear;
   TextView TVtitle,TVproffinstitue,TVexperi;
   TextView TVheadernamefull,TVheadernameshort;
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




    Boolean isEducationCompleted, isProfessionalCompleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        context=this;
        but_personal=findViewById(R.id.prof_but_personal);
        but_educational=findViewById(R.id.prof_nut_educational);
        but_professional=findViewById(R.id.prof_but_professional);
        scrollview_personal=findViewById(R.id.srcollview_personal);
        but_profile_edit=findViewById(R.id.profile_edit);
        TVfirstname=findViewById(R.id.profile_name);
        TVmiddlename=findViewById(R.id.profile_middle);
        TVlastname=findViewById(R.id.profile_last);
        TVgender=findViewById(R.id.profile_gender);
        TVdob=findViewById(R.id.profile_dob);
        TVaddress=findViewById(R.id.profile_address);
        TVpostalcode=findViewById(R.id.profile_postcode);
        TVmobile_number=findViewById(R.id.profile_monumber);
        TVemail=findViewById(R.id.profile_email);
        TVaboutme=findViewById(R.id.profile_description);
        TVemailverify=findViewById(R.id.profile_email_verify);
        TVmobileverify=findViewById(R.id.profile_mobile_verify);
TVheadernamefull=findViewById(R.id.prof_prof_name);
pendinggiflist=findViewById(R.id.nodatagif);
pendingtextlist=findViewById(R.id.nodatatext);
TVheadernameshort=findViewById(R.id.prof_prof_name_short);
        scrollView_educational=findViewById(R.id.srcollview_educational);
        scrollview_professional=findViewById(R.id.srcollview_professional);
        Shared_user_details=getSharedPreferences("user_detail_mode",0);
        Stoken=  Shared_user_details.getString("token", null);
 TVmajor=findViewById(R.id.profile_edu_major);
 TVinsitute=findViewById(R.id.prof_edu_insitute);
 TVyear=findViewById(R.id.prof_edu_dyear);
CIVprofimage=findViewById(R.id.image_profile);
 TVtitle=findViewById(R.id.prof_profess_title);
        TVproffinstitue=findViewById(R.id.prof_profess_insti);
 TVexperi=findViewById(R.id.prof_profess_experi);
 IB_back=findViewById(R.id.back_ima_scedule);
        recyclerView = findViewById(R.id.recyclerview1);
        recyclerViewsub=findViewById(R.id.recyclerviewsubject);

        IB_back.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         finish();
     }
 });

        avi=findViewById(R.id.avi);
       Listgrade= new ArrayList<String>();
        ListSubject= new ArrayList<String>();

        gettallprofiledetails();
        s_profile="personal";
        but_personal.setTextColor(getResources().getColor(R.color.buttonColorPrimary));
        but_personal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                s_profile="personal";
                but_personal.setTextColor(getResources().getColor(R.color.buttonColorPrimary));
                but_educational.setTextColor(getResources().getColor(R.color.colorwhite));
                but_professional.setTextColor(getResources().getColor(R.color.colorwhite));
                scrollview_personal.setVisibility(View.VISIBLE);
                scrollView_educational.setVisibility(View.GONE);
                scrollview_professional.setVisibility(View.GONE);
                pendinggiflist.setVisibility(View.INVISIBLE);
                pendingtextlist.setVisibility(View.INVISIBLE);

            }
        });
        but_educational.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                s_profile="educational";
                but_educational.setTextColor(getResources().getColor(R.color.buttonColorPrimary));
                but_personal.setTextColor(getResources().getColor(R.color.colorwhite));
                but_professional.setTextColor(getResources().getColor(R.color.colorwhite));
                scrollView_educational.setVisibility(View.VISIBLE);
                scrollview_personal.setVisibility(View.GONE);
                scrollview_professional.setVisibility(View.GONE);
                if (!isEducationCompleted)
                {
                    scrollView_educational.setVisibility(View.INVISIBLE);

                    pendinggiflist.setVisibility(View.VISIBLE);
                    pendingtextlist.setVisibility(View.VISIBLE);
                }
                else
                {
                    scrollView_educational.setVisibility(View.VISIBLE);

                    pendinggiflist.setVisibility(View.INVISIBLE);
                    pendingtextlist.setVisibility(View.INVISIBLE);
                }
            }
        });
        but_professional.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                s_profile="professional";
                but_professional.setTextColor(getResources().getColor(R.color.buttonColorPrimary));
                but_personal.setTextColor(getResources().getColor(R.color.colorwhite));
                but_educational.setTextColor(getResources().getColor(R.color.colorwhite));
                scrollview_professional.setVisibility(View.VISIBLE);
                scrollview_personal.setVisibility(View.GONE);
                scrollView_educational.setVisibility(View.GONE);
                if (!isProfessionalCompleted)
                {
                    scrollview_professional.setVisibility(View.INVISIBLE);

                    pendinggiflist.setVisibility(View.VISIBLE);
                    pendingtextlist.setVisibility(View.VISIBLE);
                }
                else
                {
                    scrollview_professional.setVisibility(View.VISIBLE);

                    pendinggiflist.setVisibility(View.INVISIBLE);
                    pendingtextlist.setVisibility(View.INVISIBLE);
                }
            }
        });
        but_profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(s_profile.matches("personal")||s_profile.equals("personal"))
                {
                    startActivity(new Intent(Profile_Page.this,Profile_Edit_Personal.class));
                }
                else if(s_profile.matches("educational")||s_profile.equals("educational"))
                {
                    startActivity(new Intent(Profile_Page.this,Profile_Edit_Educational.class));
                }
                else if(s_profile.matches("professional")||s_profile.equals("professional"))
                {
                    startActivity(new Intent(Profile_Page.this,Profile_Edit_Professional.class));
                }
            }
        });



        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(Listgrade);

        HorizontalLayout = new LinearLayoutManager(Profile_Page.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
recyclerView.setHorizontalScrollBarEnabled(false);

        RecyclerViewLayoutManagersub = new LinearLayoutManager(getApplicationContext());

        recyclerViewsub.setLayoutManager(RecyclerViewLayoutManagersub);

        RecyclerViewHorizontalAdaptersub = new RecyclerViewAdapter(ListSubject);

        HorizontalLayoutsub = new LinearLayoutManager(Profile_Page.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewsub.setLayoutManager(HorizontalLayoutsub);
        recyclerViewsub.setHorizontalScrollBarEnabled(false);


    }


    public void gettallprofiledetails()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_url_twotr.Profile_getdetails, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {
                    avi.hide();
                    JSONObject jObj = new JSONObject(response);
                    String id = jObj.getString("_id");
                    String userId = jObj.getString("userId");
                    String updatedAt = jObj.getString("updatedAt");
                    String createdAt = jObj.getString("createdAt");

                    JSONObject profile = jObj.getJSONObject("profileInfo");
                    String Sdob =profile.getString("dob");
                    String  Sgender=profile.getString("gender");
                    String Stimezone =profile.getString("timezone");
                    String Scantutor =profile.getString("canTutor");
                    String  SdefaultCountry=profile.getString("defaultCountry");
                    String  Sdescription=profile.getString("description");


                    JSONObject paddress = profile.getJSONObject("address");
                    String  SlineOne=paddress.getString("lineOne");
                    String  Scity=paddress.getString("city");
                    String  Sstate=paddress.getString("state");
                    String  SzipCode=paddress.getString("zipCode");
                    String  Scountry=paddress.getString("country");

                    JSONObject pmonum = profile.getJSONObject("mobileNumber");
                    String  Scode=pmonum.getString("code");
                    String  Snumber=pmonum.getString("number");

                    JSONArray jsonArray = profile.getJSONArray("gradeLevel");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        Listgrade.add(String.valueOf(jsonArray.get(i)));
                     //   Number.add(String.valueOf(jsonArray.get(i)));
                    }
                    recyclerView.setAdapter(RecyclerViewHorizontalAdapter);

                    JSONArray jsonArray1 = profile.getJSONArray("subjectIds");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(i);
                        String Skind =jsonObject.getString("title");
                        String Sid =jsonObject.getString("_id");
                        ListSubject.add(Skind);

                    }
                    recyclerViewsub.setAdapter(RecyclerViewHorizontalAdaptersub);
                    JSONObject userprofile = jObj.getJSONObject("userProfile");
                    String  SfirstName=userprofile.getString("firstName");
                    String  SmiddleName=userprofile.getString("middleName");
                    String  SlastName=userprofile.getString("lastName");

//roles of tutor is left


                    JSONObject verification = userprofile.getJSONObject("verification");
                     Boolean isEmailVerified =verification.getBoolean("isEmailVerified");
                    Boolean isMobileVerified=verification.getBoolean("isMobileVerified");
                    Boolean isProfileCompleted=verification.getBoolean("isProfileCompleted");
                     isEducationCompleted=verification.getBoolean("isEducationCompleted");
                     isProfessionalCompleted=verification.getBoolean("isProfessionalCompleted");
                    Boolean isIdVerified=verification.getBoolean("isIdVerified");
                    Boolean isTeachingVerified=verification.getBoolean("isTeachingVerified");



                    TVfirstname.setText(SfirstName);
                    TVmiddlename.setText(SmiddleName);
                    TVlastname.setText(SlastName);
                    TVgender.setText(Sgender);
                    TVdob.setText(Sdob);
                    TVaddress.setText(SlineOne);
                    TVpostalcode.setText(SzipCode);
                    TVmobile_number.setText(Snumber);
                    TVemail.setText("");
                    TVaboutme.setText(Sdescription);
                    TVheadernamefull.setText(SfirstName+" "+SmiddleName+" "+SlastName);
                    TVheadernameshort.setText(SfirstName +" "+SlastName);
if (isEmailVerified)
{


    TVemailverify.setVisibility(View.INVISIBLE);
}
if (isMobileVerified)
{
    TVmobileverify.setVisibility(View.INVISIBLE);

}

    JSONObject educationinfo = jObj.getJSONObject("educationInfo");
    JSONArray jsonArray2 = educationinfo.getJSONArray("degrees");
    for (int i = 0; i < jsonArray2.length(); i++) {
        JSONObject jsonObjectedu = jsonArray2.getJSONObject(i);
        String Smajor =jsonObjectedu.getString("major");
        String Sinstitution =jsonObjectedu.getString("institution");
        String SfieldOfStudy =jsonObjectedu.getString("fieldOfStudy");
        String SstartYear =jsonObjectedu.getString("startYear");
        String SendYear =jsonObjectedu.getString("endYear");
        Boolean SisStudyingCurrently =jsonObjectedu.getBoolean("isStudyingCurrently");

        TVmajor.setText(Smajor);
        TVinsitute.setText(Sinstitution);
        TVyear.setText(SstartYear+"-"+SendYear);
    }

                    JSONObject professionalInfo = jObj.getJSONObject("professionalInfo");
String Stitle=professionalInfo.getString("title");
                    String SinstitutionName=professionalInfo.getString("institutionName");
                    String Sexperience=professionalInfo.getString("experience");

TVtitle.setText(Stitle);
                    TVproffinstitue.setText(SinstitutionName);
TVexperi.setText(Sexperience);
                    JSONObject profilePicture = userprofile.getJSONObject("profilePicture");
                    String  Smime=profilePicture.getString("mime");
                        String  Surl=Global_url_twotr.Image_Base_url+profilePicture.getString("url");
                        Picasso
                                .with(context)
                                .load(Surl)
                                .fit()
                                .centerCrop()
                                .into(CIVprofimage);




                }

                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                avi.hide();
                new SweetAlertDialog(Profile_Page.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Password Mismatch")
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
                headers.put("authorization", "Bearer "+Stoken);
                return headers;

            }



        };

        requestQueue.add(stringRequest);
    }

}