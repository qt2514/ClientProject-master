package com.twotr.twotr.globalpackfiles;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.twotr.twotr.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
ImageButton IBsignup_back;
EditText ETusername,ETpassword,ETconfirm_pass,ETfullname,ETlastname,ETreferby;
String Susername,Spass_word,Sconfirm_pass,Sfull_name,Slast_name,SreferBy,SemailPattern,SemailInput;
    Button BSignup,BTutor,BStudent;
    AVLoadingIndicatorView avi;
    String sweetmessage,s_module;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
IBsignup_back=findViewById(R.id.signup_back);
ETusername=findViewById(R.id.username_signup);
avi=findViewById(R.id.avi);
ETpassword=findViewById(R.id.pass_signup);
ETconfirm_pass=findViewById(R.id.confpass_signup);
BSignup=findViewById(R.id.singup_buton_true);
ETfullname=findViewById(R.id.ed_fullname);
ETlastname=findViewById(R.id.ed_lastname);
ETreferby=findViewById(R.id.referal_signup);
        BTutor=findViewById(R.id.butsignup_tutor);
        BStudent=findViewById(R.id.butsignup_student);

        avi.hide();

        s_module="tutor";

        ETfullname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ETfullname.setBackgroundResource( R.drawable.edittext_selected);
                }
                else
                {
                    ETfullname.setBackgroundResource( R.drawable.edittext_unselected);
                }
            }
        });
        ETlastname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ETlastname.setBackgroundResource( R.drawable.edittext_selected);
                }
                else
                {
                    ETlastname.setBackgroundResource( R.drawable.edittext_unselected);
                }
            }
        });
        ETusername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ETusername.setBackgroundResource( R.drawable.edittext_selected);
                }
                else
                {
                    ETusername.setBackgroundResource( R.drawable.edittext_unselected);
                }
            }
        });
        ETpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ETpassword.setBackgroundResource( R.drawable.edittext_selected);
                }
                else
                {
                    ETpassword.setBackgroundResource( R.drawable.edittext_unselected);
                }
            }
        });
        ETconfirm_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ETconfirm_pass.setBackgroundResource( R.drawable.edittext_selected);
                }
                else
                {
                    ETconfirm_pass.setBackgroundResource( R.drawable.edittext_unselected);
                }
            }
        });
        ETreferby.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ETreferby.setBackgroundResource( R.drawable.edittext_selected);
                }
                else
                {
                    ETreferby.setBackgroundResource( R.drawable.edittext_unselected);
                }
            }
        });

        BTutor.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                s_module="tutor";
                BTutor.setBackgroundColor(getResources().getColor(R.color.buttonColorPrimary));
                BStudent.setBackgroundColor(getResources().getColor(R.color.buttonColorSecondary));
            }
        });
        BStudent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                s_module="student";
                BStudent.setBackgroundColor(getResources().getColor(R.color.buttonColorPrimary));
                BTutor.setBackgroundColor(getResources().getColor(R.color.buttonColorSecondary));
            }
        });
        IBsignup_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
            BSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SemailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    SemailInput = ETusername.getText().toString().trim();

                    if (ETusername.getText().toString().equals("") || ETpassword.getText().toString().equals("")
                            || ETconfirm_pass.getText().toString().equals("")||ETfullname.getText().toString().equals("")
                            || ETlastname.getText().toString().equals(""))
                    {
                        sweetmessage="Please enter all fields";

                    }
                    else
                        {
                            if (ETfullname.getText().toString().equals(""))
                            {
                                sweetmessage="Please Enter Your Full Name";

                            } else {

                                if (ETlastname.getText().toString().equals("")) {
                                    sweetmessage="Please Enter Your Last Name";

                                }
                                else {

                                    if (ETusername.getText().toString().equals("")) {
                                       sweetmessage="Please Enter Your EmailId";

                                    }
                                    else {
                                        if (SemailInput.matches(SemailPattern))
                                        {

                                            if(ETpassword.length()<6)
                                            {
                                                sweetmessage="Please Enter 6 digit Password";
                                            }
                                            else
                                            {
                                                Susername = ETusername.getText().toString();
                                                Spass_word = ETpassword.getText().toString();
                                                Sconfirm_pass = ETconfirm_pass.getText().toString();
                                                Sfull_name = ETfullname.getText().toString();
                                                Slast_name = ETlastname.getText().toString();
                                                SreferBy = ETreferby.getText().toString();
                                                if (!Spass_word.equals(Sconfirm_pass))
                                                {
                                                    sweetmessage = "Password Mismatch";
                                                }
                                                else
                                                {
                                                    avi.show();
                                                    sweetmessage = "Thank you for Signup,Please Signin Now!";
                                                    signup_twotr(Sfull_name, Slast_name, Susername, Spass_word, SreferBy);
                                                }
                                            }
                                        }
                                        else
                                        {
                                            sweetmessage = "Please Check Your EmailId";
                                        }
                                    }
                                }
                            }
                        }
                    new SweetAlertDialog(SignupActivity.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                }
            });
    }

public void signup_twotr(String sfull_name, String slast_name, String susername, String spass_word, String sreferBy) {

    try {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONArray jsonArray= new JSONArray();
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("firstName", sfull_name);
        jsonBody.put("lastName", slast_name);
        jsonBody.put("email", susername);
        jsonBody.put("roles", jsonArray.put("tutor"));
        jsonBody.put("referredBy", sreferBy);
        jsonBody.put("password", spass_word);
        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_url_twotr.User_signup, new Response.Listener<String>() {

            public void onResponse(String response) {
                avi.hide();
                startActivity(new Intent(SignupActivity.this, SigninActivity.class));



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
            // headers.put("content-Type", "application/json");
            headers.put("x-tutor-app-id", "tutor-app-android");
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
