package com.twotr.twotr.tutorfiles;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
import com.wang.avi.AVLoadingIndicatorView;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile_Edit_Educational extends AppCompatActivity {
//EditText et_edu_specilaization;
    SharedPreferences Shared_user_details;
String Stoken;
TextView et_edu_startd,et_edu_enddate;
    Boolean BisProfessionalCompleted,BisEducationCompleted;

    private ArrayList<String> ASeducation,ASinstitute;
    Button Bsaveandconti;
    String Smajor,Sinstitution,SfieldOfStudy,SstartYear,SendYear;
    Boolean Biscertifiedtutor;
   // CheckBox Ccertitutor;
    SharedPreferences.Editor editor;
    AVLoadingIndicatorView avi;
    String edulevel_elec,institute_selec,sweetmessage;
    RelativeLayout relativeLayoutins,relativeLayoutedu;
    SearchBox searchboxins,searchboxedu;
    ImageButton IB_back;
    TextView textViewins,textViewedu,textViewskipedu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__edit__educational);
        Shared_user_details=getSharedPreferences("user_detail_mode",0);
        Stoken=  Shared_user_details.getString("token", null);
        BisProfessionalCompleted=  Shared_user_details.getBoolean("isProfessionalCompleted",false);
        BisEducationCompleted=  Shared_user_details.getBoolean("isEducationCompleted",false);

        avi=findViewById(R.id.avi);
        avi.hide();
        relativeLayoutins=findViewById(R.id.searchnewins);
        searchboxins =  findViewById(R.id.searchboxins);
        relativeLayoutedu=findViewById(R.id.searchnewedu);
        searchboxedu =  findViewById(R.id.searchboxedu);
        textViewskipedu=findViewById(R.id.skip_edu);
textViewedu=findViewById(R.id.per_edu_edlevel);
textViewins=findViewById(R.id.per_edu_inst);
        searchboxedu.setLogoText("Search Your Major here");
        searchboxins.setLogoText("Search Your Insitute here");

        et_edu_startd=findViewById(R.id.per_edu_startd);
        et_edu_enddate=findViewById(R.id.per_edu_endd);
      //  et_edu_specilaization=findViewById(R.id.per_edu_spcl);
        Bsaveandconti=findViewById(R.id.educational_compelete);
       // Ccertitutor=findViewById(R.id.checkBox);
        et_edu_enddate.setEnabled(false);


        ASeducation = new ArrayList<String>();
        ASinstitute=new ArrayList<String>();

        textViewins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avi.show();
                institution_list();
                searchboxins.setVisibility(View.VISIBLE);
                relativeLayoutins.setVisibility(View.VISIBLE);
            }
        });

        textViewedu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avi.show();
                educational_list();

                searchboxedu.setVisibility(View.VISIBLE);
                relativeLayoutedu.setVisibility(View.VISIBLE);
            }
        });
        textViewskipedu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile_Edit_Educational.this, Profile_Edit_Professional.class));
                finish();
            }
        });
        searchboxedu.setMenuListener(new SearchBox.MenuListener(){

            @Override
            public void onMenuClick() {
                //Hamburger has been clicked
                Toast.makeText(Profile_Edit_Educational.this, "Menu click", Toast.LENGTH_LONG).show();
            }

        });
        searchboxedu.setSearchListener(new SearchBox.SearchListener(){

            @Override
            public void onSearchOpened() {
                //Use this to tint the screen
            }

            @Override
            public void onSearchClosed() {
                //Use this to un-tint the screen
                searchboxedu.setVisibility(View.INVISIBLE);
                relativeLayoutedu.setVisibility(View.INVISIBLE);
                searchboxedu.clearSearchable();
                searchboxedu.clearResults();


            }

            @Override
            public void onSearchTermChanged(String s) {


            }

            @Override
            public void onSearch(String searchTerm) {
                textViewedu.setText(searchTerm);
                searchboxedu.setVisibility(View.INVISIBLE);
                relativeLayoutedu.setVisibility(View.INVISIBLE);
                //  Toast.makeText(getActivity(), searchTerm +" Searched", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onResultClick(SearchResult result){
              String  search_result_edu= String.valueOf(result);
                textViewedu.setText(search_result_edu);
                searchboxedu.setVisibility(View.INVISIBLE);
                relativeLayoutedu.setVisibility(View.INVISIBLE);
            }


            @Override
            public void onSearchCleared() {

            }

        });
        searchboxins.setSearchListener(new SearchBox.SearchListener(){

            @Override
            public void onSearchOpened() {
                //Use this to tint the screen
            }

            @Override
            public void onSearchClosed() {
                //Use this to un-tint the screen
                searchboxins.setVisibility(View.INVISIBLE);
                relativeLayoutins.setVisibility(View.INVISIBLE);
                searchboxins.clearSearchable();
                searchboxins.clearResults();


            }

            @Override
            public void onSearchTermChanged(String s) {


            }

            @Override
            public void onSearch(String searchTerm) {
                textViewins.setText(searchTerm);
                searchboxins.setVisibility(View.INVISIBLE);
                relativeLayoutins.setVisibility(View.INVISIBLE);
                //  Toast.makeText(getActivity(), searchTerm +" Searched", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onResultClick(SearchResult result){
                String  search_result_edu= String.valueOf(result);
                textViewins.setText(search_result_edu);
                searchboxins.setVisibility(View.INVISIBLE);
                relativeLayoutins.setVisibility(View.INVISIBLE);
            }


            @Override
            public void onSearchCleared() {

            }

        });


        Bsaveandconti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (textViewedu.getText().toString().equals(""))
                {
                    sweetmessage = "Please Select  Major In";
                    new SweetAlertDialog(Profile_Edit_Educational.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
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
                    if (textViewins.getText().toString().equals(""))
                    {
                        sweetmessage = "Please Select Institute";
                        new SweetAlertDialog(Profile_Edit_Educational.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
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
                        if(et_edu_startd.getText().toString().equals(""))
                        {
                            sweetmessage="Please Select Start Date";
                            new SweetAlertDialog(Profile_Edit_Educational.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
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
                            if(et_edu_enddate.getText().toString().equals(""))
                            {
                                sweetmessage="Please Select End Date";
                                new SweetAlertDialog(Profile_Edit_Educational.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismiss();
                                            }
                                        }).show();
                            }
//                            else
//                            {
//                                if(et_edu_specilaization.getText().toString().equals(""))
//                                {
//                                    sweetmessage="Please Select Your Specialization";
//                                }
                                else
                                {
//                                    if (!Ccertitutor.isChecked())
//                                    {
//                                        sweetmessage="Please Select CheckBox";
//                                    }
//                                    else
//                                    {
                                        //sweetmessage="Thank You";
                                        avi.show();
                                        Smajor = textViewedu.getText().toString();
                                        Sinstitution = textViewins.getText().toString();
                                     //   SfieldOfStudy = et_edu_specilaization.getText().toString();
                                        SstartYear = et_edu_startd.getText().toString();
                                        SendYear = et_edu_enddate.getText().toString();
                                     //   Biscertifiedtutor = false;
                                        educational_profile(Smajor,Sinstitution,SfieldOfStudy,SstartYear,SendYear);

                                   // }

                                }
                            }
                       // }

                    }
                }

            }
        });


        final int[] choosenYear = {2017};
        et_edu_startd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_edu_enddate.setEnabled(true);
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(Profile_Edit_Educational.this, new MonthPickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        et_edu_startd.setText(Integer.toString(selectedYear));
                        choosenYear[0] = selectedYear;
                    }
                }, choosenYear[0], 0);

                builder.showYearOnly()
                        .setYearRange(1920, 2018)
                        .build()
                        .show();
            }

        });
        final int[] choosenYearr = {2017};

        et_edu_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(Profile_Edit_Educational.this, new MonthPickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        et_edu_enddate.setText(Integer.toString(selectedYear));
                        choosenYearr[0] = selectedYear;
                    }
                }, choosenYearr[0], 0);

                builder.showYearOnly()
                        .setYearRange(choosenYear[0], 2018)
                        .build()
                        .show();
            }

        });
//        et_edu_specilaization.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                {
//                    et_edu_specilaization.setBackgroundResource( R.drawable.edittext_selected);
//                }
//                else
//                {
//                    et_edu_specilaization.setBackgroundResource( R.drawable.edittext_unselected);
//
//                }
//            }
//        });

    }
    public void educational_list() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_url_twotr.Profile_education_level, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);

                        String title = jsonobject.getString("title");
                        SearchResult searchedu = new SearchResult(title, getResources().getDrawable(R.drawable.ic_local_library_black_24dp));
                        searchboxedu.addSearchable(searchedu);
                        avi.hide();
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

    public void institution_list() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_url_twotr.Profile_institute_level, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        String title = jsonobject.getString("title");
                        SearchResult searchins = new SearchResult(title, getResources().getDrawable(R.drawable.ic_local_library_black_24dp));
                        searchboxins.addSearchable(searchins);
                        avi.hide();

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

    public void educational_profile(String smajor, String sinstitution, String sfieldOfStudy, String sstartYear, String sendYear)
    {

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            JSONArray array=new JSONArray();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("major",smajor);
            jsonObject.put("institution",sinstitution);
            jsonObject.put("fieldOfStudy","none");
            jsonObject.put("startYear",sstartYear);
            jsonObject.put("endYear",sendYear);
            array.put(jsonObject);
            JSONObject jsonObjectall=new JSONObject();
            jsonObjectall.put("isCertifiedTutor", (Boolean) false);
            jsonObjectall.put("degrees",array);
            jsonBody.put("educationInfo",jsonObjectall);


            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_url_twotr.User_Profile_education, new Response.Listener<String>() {

                public void onResponse(String response) {
                    editor = Shared_user_details.edit();
                    editor.putBoolean("isEducationCompleted",true);
                    editor.commit();
                    avi.hide();
                    startActivity(new Intent(Profile_Edit_Educational.this, Profile_Edit_Professional.class));
                    finish();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    avi.hide();

                    new SweetAlertDialog(Profile_Edit_Educational.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Server Error")
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
}
