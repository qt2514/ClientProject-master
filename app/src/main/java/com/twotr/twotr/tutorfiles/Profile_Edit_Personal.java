package com.twotr.twotr.tutorfiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
import com.twotr.twotr.globalpackfiles.TinyDB;
import com.twotr.twotr.globalpackfiles.TrackGPS;
import com.wang.avi.AVLoadingIndicatorView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_Edit_Personal extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Spinner gender_sspinner;
    private EditText et_per_desc,et_per_fname,et_per_mname,et_per_lname,et_per_mobile,
    et_per_city,et_per_address;
    DatePickerDialog dpd;
    Button Bsaveconti;
    public String Sid,Stoken,Sfirstname,Slastname,Susername,Smiddlename;
    TextView et_per_dob,TVsubject,searchableSpinner_grade;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    ImageButton IB_back;
    SearchableSpinner SStime_zone,SSmobilepin;
    private ArrayList<String> AStime_zone,ASmobile_zone;
    ImageView  IVupload_image ;
    CircleImageView IVimage_profileedit;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    SharedPreferences Shared_user_details;
    AVLoadingIndicatorView avi;
    final ArrayList<MultiSelectModel> subject_name= new ArrayList<>();
    final ArrayList<MultiSelectModel> ASgrade= new ArrayList<>();
    ArrayList aryGrade=new ArrayList();
    List<String> subjectnamelist;
    List<String> subjectnameid;

    Context context;
  //  ArrayList<Uri> path = new ArrayList<>();
    TrackGPS tgps;
String Profile_update_url;
    SharedPreferences.Editor editor;
    String Sdob,Sgender,Stimezone,Ssubjectkind,Smcode,Smobile_number,Saddline,Saddcity,
        Saddstate,Scountry,Sdefaultcountry,Sdecription,Sgrade;
    Boolean Scantutor;
  //  Multipart multipart;
    String sweetmessage,pin_selec,timezone_selec,Sroles;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__edit__personal);
        et_per_fname = findViewById(R.id.per_prof_fname);
        et_per_desc = findViewById(R.id.per_prof_description);
        et_per_mname = findViewById(R.id.per_prof_mname);
        et_per_lname = findViewById(R.id.per_prof_lname);
        et_per_dob = findViewById(R.id.per_prof_dob);
        context = Profile_Edit_Personal.this;
        et_per_mobile = findViewById(R.id.per_prof_mobile);
        et_per_address = findViewById(R.id.per_prof_address);
        et_per_city = findViewById(R.id.per_prof_city);
      //  et_per_zip = findViewById(R.id.per_prof_postal);
        TVsubject = findViewById(R.id.per_prof_subject);
        IVimage_profileedit = findViewById(R.id.image_profileedit);
        searchableSpinner_grade = findViewById(R.id.profile_gradespin);
        SStime_zone = findViewById(R.id.spinner_timezone);
        SSmobilepin = findViewById(R.id.per_prof_ccode);
        Bsaveconti = findViewById(R.id.saveandconti);
        Shared_user_details = getSharedPreferences("user_detail_mode", 0);
        Sid = Shared_user_details.getString("id", null);
        Stoken = Shared_user_details.getString("token", null);
        Sfirstname = Shared_user_details.getString("firstName", null);
        Slastname = Shared_user_details.getString("lastName", null);
        Susername = Shared_user_details.getString("username", null);
        Sroles=  Shared_user_details.getString("roles", null);

        et_per_fname.setText(Sfirstname);
        et_per_lname.setText(Slastname);
        imageView = findViewById(R.id.upload_image);
        avi = findViewById(R.id.avi);
        avi.hide();
        Grade_spinner();
        time_zone();
       // subject_name_list();
        mobile_zone();
        AStime_zone = new ArrayList<String>();
        ASmobile_zone = new ArrayList<String>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
          //  if (!prefs.getBoolean("firstTime", false)) {
             Profile_update_url= Global_url_twotr.Base_url +"userinfo/basic/create";
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.putBoolean("firstTime", true);
//                editor.apply();
//            } else {
//                Profile_update_url= Global_url_twotr.Base_url +"userinfo/basic/update";
//
//            }



        Calendar now = Calendar.getInstance();
         dpd = DatePickerDialog.newInstance(
                Profile_Edit_Personal.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );



imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //if everything is ok we will open image chooser
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 100);
    }
});

        SStime_zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                timezone_selec = parent.getItemAtPosition(position).toString();


            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        tgps = new TrackGPS(Profile_Edit_Personal.this);
//        Double latm = tgps.getLatitude();
//        Double lngm = tgps.getLongitude();
//        Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
//        List<Address> addresses = null;
//        try {
//            addresses = gcd.getFromLocation(latm, lngm, 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        }
//        assert addresses != null;
//        if (addresses.size() > 0) {
//            Sdefaultcountry= addresses.get(0).getCountryCode();
//         //   Toast.makeText(context, Sdefaultcountry, Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(context, "Location Not Found", Toast.LENGTH_SHORT).show();
//        }
        SSmobilepin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                pin_selec = parent.getItemAtPosition(position).toString();

            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Bsaveconti.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (et_per_fname.getText().toString().equals(""))
                {
                    sweetmessage = "Please Enter First Name";
                    new SweetAlertDialog(Profile_Edit_Personal.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
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


                            if (et_per_lname.getText().toString().equals(""))
                            {
                                sweetmessage = "Please Enter Last Name";
                                new SweetAlertDialog(Profile_Edit_Personal.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
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
                                if (TVsubject.getText().toString().equals(""))
                                {
                                    sweetmessage = "Please Select Your Subject";
                                    new SweetAlertDialog(Profile_Edit_Personal.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
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
                                    if (searchableSpinner_grade.getText().toString().equals(""))
                                    {
                                        sweetmessage = "Please Select Your Grade";
                                        new SweetAlertDialog(Profile_Edit_Personal.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
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
                                        if (et_per_dob.getText().toString().equals(""))
                                        {
                                            sweetmessage = "Please Select Your Date Of Birth";
                                            new SweetAlertDialog(Profile_Edit_Personal.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
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
                                            if(pin_selec==null)
                                            {
                                                sweetmessage = "Please Select Country Code";
                                                new SweetAlertDialog(Profile_Edit_Personal.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
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
                                                if (et_per_mobile.getText().toString().equals(""))
                                                {
                                                    sweetmessage = "Please Enter Your Mobile Number";
                                                    new SweetAlertDialog(Profile_Edit_Personal.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
                                                            .setConfirmText("OK")
                                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                    sweetAlertDialog.dismiss();
                                                                }
                                                            }).show();
                                                }
                                                else {
                                                    if (gender_sspinner.getSelectedItem().toString().equals("")) {
                                                        sweetmessage = "Please Select Your Gender";
                                                        new SweetAlertDialog(Profile_Edit_Personal.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
                                                                .setConfirmText("OK")
                                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                    @Override
                                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                        sweetAlertDialog.dismiss();
                                                                    }
                                                                }).show();
                                                    } else {
                                                        if (timezone_selec == null) {
                                                            sweetmessage = "Please Select Timezone";
                                                            new SweetAlertDialog(Profile_Edit_Personal.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
                                                                    .setConfirmText("OK")
                                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                        @Override
                                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                            sweetAlertDialog.dismiss();
                                                                        }
                                                                    }).show();
                                                        } else {
if (et_per_address.getText().toString().equals(""))

{
    sweetmessage = "Please Fill Your Address";
    new SweetAlertDialog(Profile_Edit_Personal.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
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
                                                                if (et_per_city.getText().toString().isEmpty())
                                                                {
                                                                    sweetmessage = "Please Fill Your City";
                                                                    new SweetAlertDialog(Profile_Edit_Personal.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
                                                                            .setConfirmText("OK")
                                                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                                @Override
                                                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                                    sweetAlertDialog.dismiss();
                                                                                }
                                                                            }).show();
                                                                }
else {
                                                                    avi.show();
                                                                    Sdob = et_per_dob.getText().toString();
                                                                    Stimezone = SStime_zone.getSelectedItem().toString();

                                                                    Scantutor = false;
                                                                    Smcode = SSmobilepin.getSelectedItem().toString();
                                                                    Smobile_number = et_per_mobile.getText().toString();
                                                                    Saddline = et_per_address.getText().toString();
                                                                    Saddcity = et_per_city.getText().toString();
                                                                    Saddstate = et_per_city.getText().toString();
                                                                    //  Saddzipcode = et_per_zip.getText().toString();
                                                                    Sdecription = et_per_desc.getText().toString();
                                                                    Sgender = gender_sspinner.getSelectedItem().toString();
                                                                    if (Sgender.equals("Male")) {
                                                                        Sgender = "male";
                                                                    } else {
                                                                        Sgender = "female";
                                                                    }
                                                                    Sgrade = searchableSpinner_grade.getText().toString().trim();
                                                                    Sfirstname = et_per_fname.getText().toString();
                                                                    Slastname = et_per_lname.getText().toString();
                                                                    Smiddlename = et_per_mname.getText().toString();

                                                                    personal_profile(Sdob, Sgender, Stimezone,
                                                                            Sgrade, Scantutor, Smcode, Smobile_number, Saddline, Saddcity,
                                                                            Saddstate, Scountry, Sdefaultcountry, Sdecription);
                                                                    profile_name(Sfirstname, Smiddlename, Slastname);

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }


                }

            }
        });
        TVsubject.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 Intent intent = new Intent(Profile_Edit_Personal.this, MultiSubjectSpinner.class);
                 startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);

             }
         });

        searchableSpinner_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                        .title("Grade Level") //setting_bot title for dialog
                        .titleSize(22)
                        .positiveText("Done")
                        .negativeText("Cancel")
                        .multiSelectList(ASgrade) // the multi select model list with ids and name
                        .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                            @Override
                            public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                                aryGrade=selectedNames;
                                //will return list of selected
                                List<String> mStrings = new ArrayList<String>();
                                for (int i = 0; i < selectedIds.size(); i++) {
                                    searchableSpinner_grade.setText(dataString);

                                }


                            }

                            @Override
                            public void onCancel() {
                                //  Log.d(TAG,"Dialog cancelled");
                            }


                        });

                multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");

            }
        });



et_per_fname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    et_per_fname.setBackgroundResource( R.drawable.edittext_selected);
                }
                else
                {
                    et_per_fname.setBackgroundResource( R.drawable.edittext_unselected);

                }
            }
        });

        et_per_mname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    et_per_mname.setBackgroundResource( R.drawable.edittext_selected);
                }
                else
                {
                    et_per_mname.setBackgroundResource( R.drawable.edittext_unselected);

                }
            }
        });
        et_per_lname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    et_per_lname.setBackgroundResource( R.drawable.edittext_selected);
                }
                else
                {
                    et_per_lname.setBackgroundResource( R.drawable.edittext_unselected);

                }
            }
        });
        et_per_dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {

                    et_per_dob.setBackgroundResource( R.drawable.edittext_selected);
                }
                else
                {
                    et_per_dob.setBackgroundResource( R.drawable.edittext_unselected);

                }
            }
        });


        et_per_mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    et_per_mobile.setBackgroundResource( R.drawable.edittext_selected);
                }
                else
                {
                    et_per_mobile.setBackgroundResource( R.drawable.edittext_unselected);

                }
            }
        });

        et_per_city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    et_per_city.setBackgroundResource( R.drawable.edittext_selected);
                }
                else
                {
                    et_per_city.setBackgroundResource( R.drawable.edittext_unselected);

                }
            }
        });



        et_per_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    et_per_address.setBackgroundResource( R.drawable.edittext_selected);
                }
                else
                {
                    et_per_address.setBackgroundResource( R.drawable.edittext_unselected);

                }
            }
        });
//        et_per_zip.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                {
//                    et_per_zip.setBackgroundResource( R.drawable.edittext_selected);
//                }
//                else
//                {
//                    et_per_zip.setBackgroundResource( R.drawable.edittext_unselected);
//
//                }
//            }
//        });


        et_per_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String genrateach= UUID.randomUUID().toString();
                dpd.show(getFragmentManager(),genrateach);
                dpd.setTitle("Date Of Birth");
            }
        });
//        et_per_address.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
////                try {
////                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
////                            .setCountry("KW")
////                            .build();
////
////                    Intent intent =
////                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(typeFilter)
////
////                                    .build(Profile_Edit_Personal.this);
////
////                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
////                } catch (GooglePlayServicesRepairableException e) {
////             //Testing
////                } catch (GooglePlayServicesNotAvailableException e) {
////                  //Testing
////                }
//            }
//        });


        gender_sspinner =  findViewById(R.id.spinner_gender);
        gender_sspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = (monthOfYear+1)+"/"+dayOfMonth+"/"+year;
        et_per_dob.setText(date);
    }



    public void Grade_spinner() {


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_url_twotr.Profile_grades, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {
                    JSONArray jObj = new JSONArray(response);
                    for (int i = 0; i < jObj.length(); i++) {

                            ASgrade.add(new MultiSelectModel(i,String.valueOf(jObj.get(i))));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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


    public void time_zone() {


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_url_twotr.Profile_timezone, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {
                    JSONArray jObj = new JSONArray(response);
                    for (int i = 0; i < jObj.length(); i++) {
                        AStime_zone.add(String.valueOf(jObj.get(i)));
                    }
                    SStime_zone.setAdapter(new ArrayAdapter<String>(Profile_Edit_Personal.this, android.R.layout.simple_dropdown_item_1line, AStime_zone));
                  //  SSmobilepin.setAdapter(new ArrayAdapter<String>(Profile_Edit_Personal.this, android.R.layout.simple_dropdown_item_1line, AStime_zone));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
    public void mobile_zone() {


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_url_twotr.Profile_countrycode, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {
                    JSONArray jObj = new JSONArray(response);
                    for (int i = 0; i < jObj.length(); i++) {
                        JSONObject jsonobject = jObj.getJSONObject(i);

                        String name = jsonobject.getString("phoneCode");
                        // String id = jsonobject.getString("_id");
                        ASmobile_zone.add(name);
                    }
                   // SStime_zone.setAdapter(new ArrayAdapter<String>(Profile_Edit_Personal.this, android.R.layout.simple_dropdown_item_1line, AStime_zone));
                    SSmobilepin.setAdapter(new ArrayAdapter<String>(Profile_Edit_Personal.this, android.R.layout.simple_dropdown_item_1line, ASmobile_zone));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                et_per_address.setText(place.getAddress());
                LatLng latLng=place.getLatLng();
                double lat=latLng.latitude;
                double lng=latLng.longitude;
                Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());

                List<Address> addresses = null;
                try {
                    addresses = gcd.getFromLocation(lat, lng, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert addresses != null;
                if (addresses.size() > 0) {
                    et_per_city.setText(addresses.get(0).getLocality());
               //  et_per_zip.setText(addresses.get(0).getPostalCode());
               Scountry= addresses.get(0).getCountryCode();
                }
                else {
                    Toast.makeText(context, "Location Not Found", Toast.LENGTH_SHORT).show();
                }
              //  Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
               // Log.i(TAG, status.getStatusMessage());

            }
            else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }



        }

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//               More then one file
                List<Bitmap> listBitmap = new ArrayList<>();
                listBitmap.add(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri));

                //displaying selected image to imageview
                IVimage_profileedit.setImageBitmap(bitmap);
                //calling the method uploadBitmap to upload image
                uploadBitmap(listBitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                TinyDB tinydb = new TinyDB(context);
                Ssubjectkind=tinydb.getString("subjecttype");
                if (Ssubjectkind.equals("Subject")) {
                    subjectnamelist = tinydb.getListString("subjectnamelist");
                    subjectnameid = tinydb.getListString("subjectnameid");
                    StringBuilder builder = new StringBuilder();
                    // JSONArray startendarray=new JSONArray();
                    for (int i = 0; i < subjectnamelist.size(); i++) {
                        //   JSONObject obj=new JSONObject();
                        // try {
//                obj.put("start",starttimesched.get(i));
//                obj.put("end",endtimesched.get(i));
                        builder.append("").append(subjectnamelist.get(i)).append(",");

                        //   } catch (JSONException e) {
                        //      e.printStackTrace();
                    }

                    TVsubject.setText(builder);
                }
                else
                {
                    Ssubjectkind=tinydb.getString("subjecttype");
                    String Ssubjectnamei=tinydb.getString("subjectnamei");
                    subjectnameid = tinydb.getListString("subjectnameid");
TVsubject.setText(Ssubjectnamei);
                }

                //   startendarray.put(obj);
                //   }
                //Toast.makeText(context, "i got you", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void personal_profile(String sdob, String sgender, String stimezone, String sgrade, Boolean scantutor, String smcode, String smobile_number, String saddline, String saddcity, String saddstate, String scountry, String sdefaultcountry, String sdecription)
    {
        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();

            JSONArray array = new JSONArray();

            for (int i = 0; i < subjectnameid.size(); i++) {
                // JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("kind", Ssubjectkind);
                jsonObject.put("id", subjectnameid.get(i));
                array.put(jsonObject);




            }

            JSONArray array2=new JSONArray(aryGrade);
            //array2.put(Sgrade);

                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("code", smcode);
                jsonObject2.put("number", smobile_number);

            JSONObject jsonObjectall=new JSONObject();
            jsonObjectall.put("dob",sdob);
            jsonObjectall.put("gender",sgender);
            jsonObjectall.put("timezone",stimezone);

            jsonObjectall.put("subjectIds",array);
            jsonObjectall.put("gradeLevel",array2);
            jsonObjectall.put("canTutor",scantutor);
            jsonObjectall.put("mobileNumber",jsonObject2);

            JSONObject jsonObject1 = new JSONObject();
           // if (!(saddline.isEmpty())) {


                jsonObject1.put("lineOne", saddline);
                jsonObject1.put("lineTwo", " ");
                if (!saddcity.isEmpty())
                {
                    jsonObject1.put("city", saddcity);
                }

                jsonObject1.put("state", saddstate);
                jsonObject1.put("zipCode", "60076");
                jsonObject1.put("country", "KW");
                jsonObjectall.put("address",jsonObject1);

            //}
            jsonObjectall.put("defaultCountry","KW");
            jsonObjectall.put("description",sdecription);
            jsonBody.put("profileInfo",jsonObjectall);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Profile_update_url, new Response.Listener<String>() {

                public void onResponse(String response) {
                    avi.hide();
                    editor = Shared_user_details.edit();
                    editor.putBoolean("isProfileCompleted",true);
                    editor.commit();
                    avi.hide();
                    startActivity(new Intent(Profile_Edit_Personal.this,Profile_Edit_Educational.class));
finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    avi.hide();

                    new SweetAlertDialog(Profile_Edit_Personal.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Server Error")
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

    public void profile_name(String sfirstname, String smiddlename, String slastname)
    {

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("firstName", sfirstname);
            jsonBody.put("middleName", smiddlename);
            jsonBody.put("lastName", slastname);


            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_url_twotr.Profile_names, new Response.Listener<String>() {

                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    avi.hide();

                    new SweetAlertDialog(Profile_Edit_Personal.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Server Error")
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

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final List<Bitmap> listBitmap) {

        Map<String, String> myheader = new HashMap<String, String>();
        myheader.put("x-tutor-app-id","tutor-app-android");
        myheader.put("Authorization","Bearer "+Stoken);
//        myheader.put("content-type", "application/json");


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,Global_url_twotr.Profile_image_profile+Sid, myheader,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Log.i("myresult", "onResponse: " + obj);
//                            Log.d(TAG, "onResponse: " + obj.toString());
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("myresult", "onError: " + error);

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tags", "file");
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                for(Bitmap bitm : listBitmap){
                    long imagename = System.currentTimeMillis();
                    params.put("file" + imagename , new DataPart(imagename + ".png", getFileDataFromDrawable(bitm)));

                }
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }

}
