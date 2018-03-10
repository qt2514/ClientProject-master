package com.twotr.twotr.globalpackfiles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
import com.twotr.twotr.studenttwotr.StudentHome;
import com.twotr.twotr.tutorfiles.HomePage;
import com.twotr.twotr.tutorfiles.Profile_Page;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
LinearLayout linearLayoutzahaab,linearLayoutfacebook;
    String[] selectlogin={"Student","Tutor"};
    ArrayList anylogin=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        avi=findViewById(R.id.avi);
        session = new SessionManager(getApplicationContext());
        Shared_user_details = getSharedPreferences("user_detail_mode", 0);
linearLayoutzahaab=findViewById(R.id.zahaab_layout);
linearLayoutfacebook=findViewById(R.id.facebook_layout);
        Bsignin=findViewById(R.id.signin_button);
        IBsignin_back=findViewById(R.id.signin_back);
        ETusername=findViewById(R.id.username_signin);
        ETpassword=findViewById(R.id.password_signin);
        TVstart_here=findViewById(R.id.start_here);
        IVfacebook_signin=findViewById(R.id.facebook_signin);
        IVtwitter_signi=findViewById(R.id.twitter_signin);
        IVzhaab_signin=findViewById(R.id.zhahab_signin);


        linearLayoutzahaab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(SigninActivity.this)
                        .content("Please Enter your Zahhab Email id")
                        .inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                        .icon(getResources().getDrawable(R.drawable.zahhab_logo))
                        .title("")
                        .input("Email Id","", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                             String   SemailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                             if (input.toString().matches(SemailPattern))
                             {
                                 socialmedianetwork(input.toString(),"zahhab");
                             }
                             else
                             {
                                 sweetmessage = "Please Check Your EmailId";
                                 new SweetAlertDialog(SigninActivity.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
                                         .setConfirmText("OK")
                                         .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                             @Override
                                             public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                 sweetAlertDialog.dismiss();
                                             }
                                         }).show();
                             }


                            }
                        }
                        ).show();
            }
        });

linearLayoutfacebook.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});
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
                finish();
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
                    JSONArray jsonArray = jObj.getJSONArray("roles");

                      String roles=String.valueOf(jsonArray.get(0));

                        //   Number.add(String.valueOf(jsonArray.get(i)));

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
//
//                   if (roles.equals("tutor"))
//                   {
                       startActivity(new Intent(SigninActivity.this, HubPlace.class));
                       finish();
//                   }
//                   else {
//                       startActivity(new Intent(SigninActivity.this, StudentHome.class));
//                       finish();
//                   }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                avi.hide();
                Log.i("checkerror",String.valueOf(error));
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
                //// headers.put("Content-Type", "application/json");
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

    public void socialmedianetwork(final String email, final String sntype) {

        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("type", sntype);
            jsonBody.put("deviceType", "android");
            jsonBody.put("deviceId", "an123");

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_url_twotr.User_network, new Response.Listener<String>() {

                public void onResponse(String response) {
                    avi.hide();

                    try {
                       JSONObject jObj = new JSONObject(response);
                        Log.i("repis",response);
                        String errorCode=jObj.getString("errorCode");
                        String message=jObj.getString("message");
                        String description=jObj.getString("description");
if (errorCode.equals("300404"))
{
    avi.show();
zahhabcheck(email,sntype);
}
else
{
    String id=jObj.getString("_id");
    String token=jObj.getString("token");
    String firstName=jObj.getString("firstName");
    String lastName=jObj.getString("lastName");
    String username=jObj.getString("username");
    JSONArray jsonArray = jObj.getJSONArray("roles");

    String roles=String.valueOf(jsonArray.get(0));

    String referralCode=jObj.getString("referralCode");

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

    startActivity(new Intent(SigninActivity.this, HubPlace.class));
    finish();
}



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    avi.hide();

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
  //                   headers.put("content-Type", "application/json");
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
    public void zahhabcheck(String email, final String sntype) {
        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("EmailID", email);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_url_twotr.Zahab_login, new Response.Listener<String>() {

                public void onResponse(final String response) {
                    avi.hide();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        final String UserID=jObj.getString("UserID");
                        final String EmailID=jObj.getString("EmailID");
                        final String FirstName=jObj.getString("FirstName");
                        String MobileNumber=jObj.getString("MobileNumber");
                        final String LastName=jObj.getString("LastName");

                        new MaterialDialog.Builder(SigninActivity.this)
                                .inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                                .icon(getResources().getDrawable(R.drawable.back))
.items(selectlogin)
                                .content("Select Your Type")
                                .cancelable(false)
                           .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                               @Override
                               public boolean onSelection(MaterialDialog dialog, View itemView, final int which, final CharSequence text) {
                                   new MaterialDialog.Builder(SigninActivity.this)
                                           .inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                                           .icon(getResources().getDrawable(R.drawable.back))

                                           .input("Share Code (Optional)","", new MaterialDialog.InputCallback() {
                                                       @Override
                                                       public void onInput(MaterialDialog dialog, CharSequence input) {

                                                           networkcreate(FirstName,LastName,EmailID,selectlogin[which],UserID,input.toString(),sntype,response);


                                                       }
                                                   }
                                           ).show();

                                   return true;
                               }
                           })  .show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    avi.hide();

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
                        //               headers.put("content-Type", "application/json");
                    headers.put("AccessKey", "98918ce0-2a15-4e25-99bf-2e6b79a48eb6");
                    headers.put("authorization", "ZahhabService**2017!sab");

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

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public void networkcreate(String firstname, String lastname, final String email,String sroles, String password, String referdby, String stype, String sresponse)
{
    try {


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("firstName", firstname);
        jsonBody.put("lastName", lastname);
        jsonBody.put("email", email);
        jsonBody.put("password", password+"1234");
        jsonBody.put("referredBy", referdby);
        JSONArray array2=new JSONArray("["+sroles.toLowerCase()+"]");
        jsonBody.put("roles", array2);
        JSONObject jsonObjectall=new JSONObject();
        jsonObjectall.put("type", stype);
        jsonObjectall.put("response", sresponse);
        jsonBody.put("socialMedia",jsonObjectall);
        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_url_twotr.NetworkCreate, new Response.Listener<String>() {

            public void onResponse(String response) {
                avi.hide();
                session.setLogin(true);

                try {

                 JSONObject    jObj = new JSONObject(response);

                    String id=jObj.getString("_id");
                    String token=jObj.getString("token");
                    String firstName=jObj.getString("firstName");
                    String lastName=jObj.getString("lastName");
                    String username=jObj.getString("username");
                    JSONArray jsonArray = jObj.getJSONArray("roles");
                    String roles=String.valueOf(jsonArray.get(0));
                    String referralCode=jObj.getString("referralCode");
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

                    startActivity(new Intent(SigninActivity.this, HubPlace.class));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                avi.hide();

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
                //                   headers.put("content-Type", "application/json");
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

