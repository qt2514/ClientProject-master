package com.twotr.twotr.tutorfiles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
import com.twotr.twotr.globalpackfiles.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleDetailPage extends AppCompatActivity {
TextView TVsubject_name,TVtypesubject,TVprice_amount,TVhrscal,TVstudentcount,TVnoofstutext,TVminamount,TVminamounttex;
EditText ETsched_desc;
ImageButton IBedit_sched,IBpre_post,IB_back;
LinearLayout linear_layout;
String Slati,Slongi,type_subject,subname,hrschmon,Sstudentcount,Sminprice,Sschedule_des,Sid;
Button But_showmap,But_showschedule,But_updatemap,Bupdate_details;
Context context;
    SharedPreferences Shared_user_details;
    public String Stoken;
    ArrayList<String> starttimee;
    ArrayList<String> endtime;
    ArrayList<String> prename,preurl,preid;
    ArrayList<String> posname,posurl,posid;
ImageButton preclassup,postclassup;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail_page);
        IBedit_sched=findViewById(R.id.edit_desc_sched);
        TVsubject_name=findViewById(R.id.subject_name);
        TVtypesubject=findViewById(R.id.type_detail_subject);
        TVhrscal=findViewById(R.id.hours_sched);
        ETsched_desc=findViewById(R.id.schedule_descripti);
        TVprice_amount=findViewById(R.id.price_amount);
        IBpre_post=findViewById(R.id.add_pre_post);
        TVstudentcount=findViewById(R.id.stu_count_sched);
        linear_layout=findViewById(R.id.post_pre_linear);
        preclassup=findViewById(R.id.preclass_upload);
        postclassup=findViewById(R.id.post_upload);
        But_showmap=findViewById(R.id.schedule_map);
        But_showschedule=findViewById(R.id.schedule_show);
        IB_back=findViewById(R.id.back_ima_scedule);
        But_updatemap=findViewById(R.id.map_update);
TVnoofstutext=findViewById(R.id.textView8);
TVminamount=findViewById(R.id.min_amount);
Bupdate_details=findViewById(R.id.update_details);
TVminamounttex=findViewById(R.id.min_price_amount);
context=this;
starttimee= new ArrayList<>();
        endtime= new ArrayList<>();
        prename= new ArrayList<>();
        preurl= new ArrayList<>();
        preid= new ArrayList<>();
        posname= new ArrayList<>();
        posurl= new ArrayList<>();
        posid= new ArrayList<>();
        Shared_user_details=getSharedPreferences("user_detail_mode",0);
        Stoken=  Shared_user_details.getString("token", null);
        IB_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        But_showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

    Intent intent = new Intent(ScheduleDetailPage.this, Schedule_ShowMap.class);
    intent.putExtra("latitude", Slati);
    intent.putExtra("longitude", Slongi);
    intent.putExtra("subjectname", subname);
    intent.putExtra("subjecttype", type_subject);
    intent.putExtra("subjecthours", hrschmon);
    startActivity(intent);

//                Toast.makeText(ScheduleDetailPage.this, "Coming Soon", Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(ScheduleDetailPage.this,Schedule_ShowMap.class));//
            }
        });

        ETsched_desc.setEnabled(false);
        final Intent intent = getIntent();
        Bundle Bintent = intent.getExtras();
        if(Bintent != null)
        {
             subname = (String) Bintent.get("subject_name");
             type_subject = (String) Bintent.get("type_subject");
            Slati=(String) Bintent.get("latitude");
            Slongi=(String) Bintent.get("longitude");
            Sstudentcount=(String) Bintent.get("studentscount");
Sminprice=(String) Bintent.get("minprice");
            assert type_subject != null;
            Sschedule_des = (String) Bintent.get("schedule_description");
            String sched_price = (String) Bintent.get("schedule_price");
             hrschmon = (String) Bintent.get("hrschmon");
             Sid=(String) Bintent.get("cateid");
             starttimee= Bintent.getStringArrayList("starttime");
            endtime= Bintent.getStringArrayList("endtime");
            prename= Bintent.getStringArrayList("prename");
            preurl= Bintent.getStringArrayList("preurl");
            preid= Bintent.getStringArrayList("preid");
            posname= Bintent.getStringArrayList("posname");
            posurl= Bintent.getStringArrayList("posurl");
            posid= Bintent.getStringArrayList("posid");


            TVhrscal.setText(hrschmon);
            if (type_subject.equals("oneonone"))
            {
                type_subject="1 on 1";
                TVstudentcount.setVisibility(View.GONE);
                TVnoofstutext.setVisibility(View.GONE);
                TVminamount.setVisibility(View.GONE);
                TVminamounttex.setVisibility(View.GONE);
            }
            else
            {
                TVstudentcount.setVisibility(View.VISIBLE);
                TVnoofstutext.setVisibility(View.VISIBLE);
                TVminamount.setVisibility(View.VISIBLE);
                TVminamounttex.setVisibility(View.VISIBLE);
            }
            TVminamounttex.setText(Sminprice + " KD");
            TVprice_amount.setText(sched_price+ " KD");
            TVtypesubject.setText(" "+ type_subject);
            TVsubject_name.setText(subname);
            ETsched_desc.setText(Sschedule_des);
            TVstudentcount.setText(Sstudentcount);
            if (Slati==null)
            {
                But_showmap.setVisibility(View.GONE);
            }
            else
            {
                But_showmap.setVisibility(View.VISIBLE);
            }

        }

        But_showschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent intentshow=new Intent(ScheduleDetailPage.this,Schedule_ShowSchedule.class);
intentshow.putExtra("subjectname",subname);
intentshow.putExtra("typesub",type_subject);
intentshow.putExtra("hrstot",hrschmon);
intentshow.putStringArrayListExtra("starttime",starttimee);
intentshow.putStringArrayListExtra("endtime",endtime);
startActivity(intentshow);
                //          Toast.makeText(ScheduleDetailPage.this, "Coming Soon", Toast.LENGTH_SHORT).show();
              //  startActivity(new Intent(ScheduleDetailPage.this,Schedule_ShowSchedule.class));
            }
        });

        preclassup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tutormaterial=new Intent(ScheduleDetailPage.this,TutorMaterialUpload.class);
                tutormaterial.putExtra("classtype","pre/");
                tutormaterial.putExtra("classtypetest","Pre Class Materials");
                tutormaterial.putStringArrayListExtra("prename",prename);
                tutormaterial.putStringArrayListExtra("preurl", preurl);
                tutormaterial.putStringArrayListExtra("preid",preid);

                tutormaterial.putExtra("calid",Sid);
       startActivity(tutormaterial);
            }
        });
        postclassup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tutormaterial=new Intent(ScheduleDetailPage.this,TutorMaterialUpload.class);
                tutormaterial.putExtra("classtype","post/");
                tutormaterial.putExtra("classtypetest","Post Class Materials");
                tutormaterial.putStringArrayListExtra("posname", posname);
                tutormaterial.putStringArrayListExtra("posurl",posurl);
                tutormaterial.putStringArrayListExtra("posid", posid);
                tutormaterial.putExtra("calid",Sid);
                startActivity(tutormaterial);
            }
        });
        IBedit_sched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ETsched_desc.setEnabled(true);
                But_showmap.setVisibility(View.GONE);
                Bupdate_details.setVisibility(View.VISIBLE);
But_updatemap.setVisibility(View.VISIBLE);
                //   IBedit_sched.setBackgroundResource(R.drawable.save_schedule_edit);

            }
        });
But_updatemap.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ScheduleDetailPage.this, Addmaptutor.class);
        startActivity(intent);

    }
});

Bupdate_details.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Sschedule_des=ETsched_desc.getText().toString();
        TinyDB tinydb = new TinyDB(context);
         Slati= tinydb.getString("latitude");
         Slongi=tinydb.getString("longitude");

        update_schedule();




    }
});
        IBpre_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
linear_layout.setVisibility(View.VISIBLE);
            }
        });
    }



    public void update_schedule() {

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonObjectall = new JSONObject();
            jsonObjectall.put("description", Sschedule_des);
            if (!Slati.isEmpty()&&!Slongi.isEmpty()) {
                JSONObject jsonObject2 = new JSONObject();

                jsonObject2.put("lat", Slati);
                jsonObject2.put("lng", Slongi);
                jsonObjectall.put("location", jsonObject2);
            }
            final String requestBody = jsonObjectall.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_url_twotr.Tutor_Schedule_update+Sid, new Response.Listener<String>() {

                public void onResponse(String response) {
                    startActivity(new Intent(ScheduleDetailPage.this,HomePage.class));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {

                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                 //  headers.put("content-Type", "application/json");
                    headers.put("x-tutor-app-id", "tutor-app-android");
                    headers.put("authorization","Bearer "+Stoken);

                    return headers;

                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");

                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }




            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
