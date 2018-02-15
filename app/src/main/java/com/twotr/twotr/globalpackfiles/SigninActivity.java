package com.twotr.twotr.globalpackfiles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.twotr.twotr.R;
import com.twotr.twotr.db_handlers.SessionManager;
import com.twotr.twotr.tutorfiles.HomePage;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {
    ImageButton IBsignin_back;
    Button Bsignin;
    EditText ETusername,ETpassword;
    String Susername,Spass;
    AVLoadingIndicatorView avi;
    private SessionManager session;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    TextView TVstart_here;
    ImageView Ivfacebook;
    String sweetmessage;
    ImageView IVfacebook_signin,IVtwitter_signi,IVzhaab_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        avi=findViewById(R.id.avi);

        session = new SessionManager(getApplicationContext());
        Shared_user_details = getSharedPreferences("user_detail_mode", 0);

        Bsignin=findViewById(R.id.signin_button);
        IBsignin_back=findViewById(R.id.signin_back);
        ETusername=findViewById(R.id.username_signin);
        ETpassword=findViewById(R.id.password_signin);
        TVstart_here=findViewById(R.id.start_here);
        IVfacebook_signin=findViewById(R.id.facebook_signin);
        IVtwitter_signi=findViewById(R.id.twitter_signin);
        IVzhaab_signin=findViewById(R.id.zhahab_signin);
        IVfacebook_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SigninActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });


        IVtwitter_signi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SigninActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();

            }
        });
        IVzhaab_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SigninActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();

            }
        });
        avi.hide();
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
        TVstart_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this,SignupActivity.class));
            }
        });
        IBsignin_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        Bsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ETusername.getText().toString().equals("") ||
                        ETpassword.getText().toString().equals(""))
                {

                    sweetmessage="Please Enter all Fields";
                    new SweetAlertDialog(SigninActivity.this, SweetAlertDialog.WARNING_TYPE).setTitleText(sweetmessage)
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                }
                else
                {
                    if (ETusername.getText().toString().equals(""))
                    {
                        sweetmessage="Please Enter Username";
                        new SweetAlertDialog(SigninActivity.this, SweetAlertDialog.WARNING_TYPE).setTitleText(sweetmessage)
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                }).show();
                    }
                    else
                    {
                        if (ETpassword.getText().toString().equals(""))
                        {
                            sweetmessage="Please Enter Password";
                            new SweetAlertDialog(SigninActivity.this, SweetAlertDialog.WARNING_TYPE).setTitleText(sweetmessage)
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    }).show();
                        }
                        else
                        {
                            avi.show();
                                                      Susername=ETusername.getText().toString();
                            Spass=ETpassword.getText().toString();
                            signin_verif(Susername,Spass);
                        }
                    }
                }



            }
        });
    }
public void signin_verif(String susername, String spass)
{

    try {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("username", susername);
        jsonBody.put("password", spass);
        jsonBody.put("deviceType", "android");
        jsonBody.put("deviceId", "dummyid");

        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_url_twotr.User_signin, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {
                    session.setLogin(true);
                    JSONObject jObj = new JSONObject(response);
                    String id=jObj.getString("_id");
                    String token=jObj.getString("token");
                    String firstName=jObj.getString("firstName");
                    String lastName=jObj.getString("lastName");
                    String username=jObj.getString("username");
                    String roles=jObj.getString("roles");
                    String referralCode=jObj.getString("referralCode");
//                    JSONObject profilePicture = jObj.getJSONObject("profilePicture");
//                    String profile_image=profilePicture.getString("url");
                    JSONObject verification = jObj.getJSONObject("verification");
                    boolean isTeachingVerified=verification.getBoolean("isTeachingVerified");
                    boolean isIdVerified=verification.getBoolean("isIdVerified");
                    boolean isProfessionalCompleted=verification.getBoolean("isProfessionalCompleted");
                    boolean isEducationCompleted=verification.getBoolean("isEducationCompleted");
                    boolean isProfileCompleted=verification.getBoolean("isProfileCompleted");
                    boolean isMobileVerified=verification.getBoolean("isMobileVerified");
                    boolean isEmailVerified=verification.getBoolean("isEmailVerified");
                    editor = Shared_user_details.edit();
                    editor.putString("id",id);
                    editor.putString("token",token);
                    editor.putString("firstName",firstName);
                    editor.putString("lastName",lastName);
                    editor.putString("username",username);
                    editor.putString("roles",roles);
                    editor.putString("referralCode",referralCode);
                    editor.putBoolean("isTeachingVerified",isTeachingVerified);
                    editor.putBoolean("isIdVerified",isIdVerified);
                    editor.putBoolean("isProfessionalCompleted",isProfessionalCompleted);
                    editor.putBoolean("isEducationCompleted",isEducationCompleted);
                    editor.putBoolean("isProfileCompleted",isProfileCompleted);
                    editor.putBoolean("isMobileVerified",isMobileVerified);
                    editor.putBoolean("isEmailVerified",isEmailVerified);
                    editor.apply();
                    editor.commit();
                   avi.hide();
                    startActivity(new Intent(SigninActivity.this, HomePage.class));
                    finish();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                avi.hide();
                new SweetAlertDialog(SigninActivity.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Please Enter Proper Credentials!")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).show();            }
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

