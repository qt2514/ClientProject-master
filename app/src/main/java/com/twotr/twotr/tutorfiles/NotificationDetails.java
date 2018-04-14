package com.twotr.twotr.tutorfiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationDetails extends AppCompatActivity {
ImageButton IB_back;
String Sstud_name,Sstud_prof,Ssubject_name,Sstaus,Sclassid,Ssubid,Sstuid,Snotifiid,Sstud_fname,Sstud_lname,Sstar_time,Swhoiam;
TextView TVstuname,TVsub_name,TV_status,TV_sched;
CircleImageView CIprofpic;
Context context;
Button IBdecline,IBaccept,IBconfir;
    SharedPreferences Shared_user_details;
    public String Stoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        Shared_user_details = getSharedPreferences("user_detail_mode", 0);
        Stoken = Shared_user_details.getString("token", null);

        IB_back = findViewById(R.id.back_ima_scedule);
        TVstuname = findViewById(R.id.name_text_notifi);
        TVsub_name = findViewById(R.id.subject_name_notifi);
        TV_status = findViewById(R.id.status_notifi);
        TV_sched = findViewById(R.id.schedule_notifi);
        CIprofpic = findViewById(R.id.image_profileedit);
        IBdecline = findViewById(R.id.but_decli);
        IBaccept = findViewById(R.id.but_accep);
IBconfir=findViewById(R.id.but_confirm);

        context = NotificationDetails.this;
        IB_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Intent intent = getIntent();
        Bundle Bintent = intent.getExtras();
        if (Bintent != null) {
            Sstud_fname = (String) Bintent.get("stufname");

            Sstud_lname = (String) Bintent.get("stulname");
            Sstud_name = Sstud_fname + " " + Sstud_lname;
            Sstud_prof = (String) Bintent.get("stuimage");
            Ssubject_name = (String) Bintent.get("subject_name");
            Ssubid = (String) Bintent.get("subject_id");
            Sstuid = (String) Bintent.get("student_id");
            Sstaus = (String) Bintent.get("status_rec");
            Snotifiid = (String) Bintent.get("notifi_id");
            Sclassid = (String) Bintent.get("class_id");
            Sstar_time = (String) Bintent.get("start_time");
            Swhoiam = (String) Bintent.get("whoiam");
        }

        TVstuname.setText(Sstud_name);

        TVsub_name.setText(Ssubject_name);

        TV_status.setText(Sstaus);

        TV_sched.setText(Sstar_time);

        Picasso
                .with(context)
                .load(Global_url_twotr.Image_Base_url + Sstud_prof)
                .fit()
                .error(getResources().getDrawable(R.drawable.profile_image_tutor))
                .centerCrop()
                .into(CIprofpic);

        if (Swhoiam.equals("tutor")) {
            if (Sstaus.equals("Accepted") || Sstaus.equals("Confirmed") || Sstaus.equals("Rejected")) {
                IBaccept.setVisibility(View.GONE);
                IBdecline.setVisibility(View.GONE);
                IBconfir.setVisibility(View.GONE);
            } else {
                IBconfir.setVisibility(View.GONE);
                IBaccept.setVisibility(View.VISIBLE);
                IBdecline.setVisibility(View.VISIBLE);

            }
            IBaccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    approvalnotif(Sclassid, Sstuid, Snotifiid, "accepted");
                }
            });
            IBdecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    approvalnotif(Sclassid, Sstuid, Snotifiid, "rejected");

                }
            });

        }
        else
        {
            if (Sstaus.equals("Accepted") || Sstaus.equals("Confirmed") || Sstaus.equals("Rejected"))
            {
                IBconfir.setVisibility(View.GONE);

            }
            else
            {
                IBconfir.setVisibility(View.VISIBLE);

            }
        }
    }

    public void approvalnotif(String sclassid, String sstuid, String snotifiid, String accepted)
    {

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("classId", sclassid);
            jsonBody.put("studentId", sstuid);
            jsonBody.put("notificationId", snotifiid);
            jsonBody.put("status", accepted);

            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_url_twotr.Tutor_notification_approval, new Response.Listener<String>() {

                public void onResponse(String response) {
Intent intent=new Intent(NotificationDetails.this,HomePage.class);
startActivity(intent);
finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        try {
                            JSONObject jsonObject=new JSONObject(new String(networkResponse.data));
                            String message=jsonObject.getString("message");
                            if (message.equals("invalid_token"))
                            {
                                HashMap params = new HashMap();
                                params.put("token", Stoken);

                                JSONObject parameters = new JSONObject(params);

                                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Global_url_twotr.Tutor_token_Reset, parameters, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.i("igotres",response.toString());
                                        //TODO: handle success
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                        //TODO: handle failure
                                    }
                                })

                                {
                                    @Override
                                    public String getBodyContentType() {

                                        return "application/json; charset=utf-8";
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        HashMap<String, String> headers = new HashMap<String, String>();
                                        // headers.put("content-Type", "application/json");
                                        headers.put("x-tutor-app-id", "tutor-app-android");

                                        return headers;

                                    }};

                                Volley.newRequestQueue(NotificationDetails.this).add(jsonRequest);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    new SweetAlertDialog(NotificationDetails.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Server Error")
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
                    // headers.put("content-Type", "application/json");
                    headers.put("x-tutor-app-id", "tutor-app-android");
                    headers.put("authorization", "Bearer "+Stoken);


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
