package com.twotr.twotr.tutorfiles;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
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
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
import com.twotr.twotr.globalpackfiles.TinyDB;
import com.wooplr.spotlight.SpotlightView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorCreate extends Fragment {

    private Button Boneonone;
    private Button Bgroup;
    private EditText ETtotalone,ETtotalamount,ETminamount,ETshortins,ETnofstudents;
    private TextView ETgrade;
    private TextView TVaddsched,TVaddmap;
    private TextView TVtypesearch;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private static final int ADD_SUBJECT_RESULT = 1;
    private static final int ADD_MAP_RESULT = 2;

    List<String> starttime;
    List<String> endtime;
    //SearchBox search;
    //String BaseSearchurl="http://twotr.com:5040/api/subject/search?key=";
    String search_result;
    public static TutorCreate newInstance() {
        return new TutorCreate();
    }
    final ArrayList<MultiSelectModel> ASgrade= new ArrayList<>();
    ArrayList aryGrade=new ArrayList();
    SharedPreferences Shared_user_details;
    public String Stoken;
   // RelativeLayout relativeLayout;
    List<String> starttimesched;
    List<String> endtimesched;
    String typeofstudteach;
Boolean Bisusersubject;
Button add_subject;
LinearLayout linearLayoutamount,linearLayoutaddsc,linearLayoutaddma;
    List<String> subjectnamelist;
    List<String> subjectnameid;
String Ssubjectkind;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_tutor_create, container, false);
        Shared_user_details=this.getActivity().getSharedPreferences("user_detail_mode",0);
        Stoken=  Shared_user_details.getString("token", null);

        Boneonone=view.findViewById(R.id.one_to_one);
        Bgroup=view.findViewById(R.id.group_button);
        ETtotalone=view.findViewById(R.id.amount_create);
        ETnofstudents=view.findViewById(R.id.group_students);

        ETtotalamount=view.findViewById(R.id.total_amount_group);
        ETminamount=view.findViewById(R.id.min_amount_group);
        TVaddsched=view.findViewById(R.id.add_schedule_create);
        TVaddmap=view.findViewById(R.id.add_map_create);
        ETshortins=view.findViewById(R.id.shrot_des_create);
        ETgrade=view.findViewById(R.id.grade_create);
       // relativeLayout=view.findViewById(R.id.searchnew);
        ImageView IVaddsch = view.findViewById(R.id.hint_sched);
        ImageView IVaddmap = view.findViewById(R.id.hint_map);
        ImageView IVaddminamout= view.findViewById(R.id.hint_minamount);
        //search =  view.findViewById(R.id.searchbox);
        add_subject=view.findViewById(R.id.add_subject_create);
        linearLayoutamount=view.findViewById(R.id.linear_amount);
        linearLayoutaddsc=view.findViewById(R.id.add_sch);
        linearLayoutaddma=view.findViewById(R.id.add_ma);
      //  search.setLogoText("Search Your Subject here");
        //search.enableVoiceRecognition(getActivity());
        starttimesched=new ArrayList<>();
        endtimesched=new ArrayList<>();
        typeofstudteach="oneonone";
        Bisusersubject=false;
//        search.setMenuListener(new SearchBox.MenuListener(){
//
//            @Override
//            public void onMenuClick() {
//
//            }
//
//        });
        starttime=new ArrayList<>();

        endtime=new ArrayList<>();
        TVtypesearch=view.findViewById(R.id.type_search);
        subject_grade_spinner();
//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                search.setVisibility(View.INVISIBLE);
//                relativeLayout.setVisibility(View.INVISIBLE);
//            }
//        });
        TVtypesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SingleSubjectSelect.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);

            }
        });

        ETgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                        .title("Grade Level") //setting title for dialog
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
                                    ETgrade.setText(dataString);
//                                    Toast.makeText(Profile_Edit_Personal.this, "Selected Ids : " + selectedIds.get(i) + "\n" +
//                                            "Selected Names : " + selectedNames.get(i) + "\n" +
//                                            "DataString : " + dataString, Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onCancel() {
                                //  Log.d(TAG,"Dialog cancelled");
                            }


                        });

                multiSelectDialog.show(getFragmentManager(), "multiSelectDialog");

            }
        });


        Boneonone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeofstudteach="oneonone";
                Boneonone.setBackgroundResource(R.drawable.tab_selected);
                Bgroup.setBackgroundResource(R.drawable.tab_unselected_right);

                ETnofstudents.setVisibility(View.GONE);
                ETtotalone.setVisibility(View.VISIBLE);
                ETtotalamount.setVisibility(View.GONE);
                linearLayoutamount.setVisibility(View.GONE);
                TVaddsched.setVisibility(View.VISIBLE);
                TVaddmap.setVisibility(View.VISIBLE);
                ETshortins.setVisibility(View.VISIBLE);
                ETgrade.setVisibility(View.VISIBLE);
            }
        });

        Bgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeofstudteach="group";
                Bgroup.setBackgroundResource(R.drawable.tab_selected_right);
                Boneonone.setBackgroundResource(R.drawable.tab_unselected);

                ETnofstudents.setVisibility(View.VISIBLE);
                ETtotalone.setVisibility(View.INVISIBLE);
                ETtotalamount.setVisibility(View.VISIBLE);
                linearLayoutamount.setVisibility(View.VISIBLE);
                TVaddsched.setVisibility(View.VISIBLE);
                TVaddmap.setVisibility(View.VISIBLE);
                ETshortins.setVisibility(View.VISIBLE);
                ETgrade.setVisibility(View.VISIBLE);

            }
        });


        IVaddsch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String genrateach= UUID.randomUUID().toString();
                new SpotlightView.Builder(getActivity())
                        .introAnimationDuration(300)
                        //.enableRevealAnimation(isRevealEnabled)
                        .performClick(true)
                        .fadeinTextDuration(300)
                        .headingTvColor(Color.parseColor("#eb273f"))
                        .headingTvSize(32)
                        .headingTvText("Add Schedule")
                        .subHeadingTvColor(Color.parseColor("#ffffff"))
                        .subHeadingTvSize(16)
                        .subHeadingTvText("Add your Available Schedule time\nthis Will been shown to Users.")
                        .maskColor(Color.parseColor("#dc000000"))
                        .target(TVaddsched)
                        .lineAnimDuration(300)
                        .lineAndArcColor(Color.parseColor("#eb273f"))
                        .dismissOnTouch(true)
                        .dismissOnBackPress(true)
                        .enableDismissAfterShown(true)
                        .usageId(genrateach)
                        .show();

            }
        });

        IVaddminamout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String genrate= UUID.randomUUID().toString();
                new SpotlightView.Builder(getActivity())
                        .introAnimationDuration(300)
                        //.enableRevealAnimation(isRevealEnabled)
                        .performClick(true)
                        .fadeinTextDuration(300)
                        .headingTvColor(Color.parseColor("#eb273f"))
                        .headingTvSize(32)
                        .headingTvText("Minimum Amount")
                        .subHeadingTvColor(Color.parseColor("#ffffff"))
                        .subHeadingTvSize(16)
                        .subHeadingTvText("Minimum amount acceptable for entire group to be \n distributed evenly among all registered students")
                        .maskColor(Color.parseColor("#dc000000"))
                        .target(ETminamount)
                        .lineAnimDuration(300)
                        .lineAndArcColor(Color.parseColor("#eb273f"))
                        .dismissOnTouch(true)
                        .dismissOnBackPress(true)
                        .enableDismissAfterShown(true)
                        .usageId(genrate)
                        .show();

            }
        });


        IVaddmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String genrate= UUID.randomUUID().toString();
                new SpotlightView.Builder(getActivity())
                        .introAnimationDuration(300)
                        //.enableRevealAnimation(isRevealEnabled)
                        .performClick(true)
                        .fadeinTextDuration(300)
                        .headingTvColor(Color.parseColor("#eb273f"))
                        .headingTvSize(32)
                        .headingTvText("Add Map")
                        .subHeadingTvColor(Color.parseColor("#ffffff"))
                        .subHeadingTvSize(16)
                        .subHeadingTvText("To add location of your\n class going to add.")
                        .maskColor(Color.parseColor("#dc000000"))
                        .target(TVaddmap)
                        .lineAnimDuration(300)
                        .lineAndArcColor(Color.parseColor("#eb273f"))
                        .dismissOnTouch(true)
                        .dismissOnBackPress(true)
                        .enableDismissAfterShown(true)
                        .usageId(genrate)
                        .show();
            }
        });
        TVaddmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Addmaptutor.class);
                startActivityForResult(intent, ADD_MAP_RESULT);
            }
        });

        TVaddsched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Ssubject_name=TVtypesearch.getText().toString();

                if (Ssubject_name.isEmpty())
                {
                    Toast.makeText(getActivity(), "Type Subject Name", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(getActivity(), ScheduleStart.class);
                    intent.putExtra("sub_name",Ssubject_name);
                    startActivityForResult(intent, ADD_SUBJECT_RESULT);


                }
            }
        });

        add_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Ssubjectname=TVtypesearch.getText().toString();
                String Sgroup=typeofstudteach;
                String Sprice;

                if (typeofstudteach.equals("group")) {
                    Sprice = ETtotalamount.getText().toString();
                } else {
                    Sprice = ETtotalone.getText().toString();

                }

                if (!Ssubjectname.isEmpty()&&!Sprice.isEmpty()) {

if (aryGrade.size()>0)
{
    String minprice = ETminamount.getText().toString();
    if (minprice.isEmpty()) {
        minprice = "10";
    }
    String Snoofstudents = ETnofstudents.getText().toString();
    if (Snoofstudents.isEmpty()) {
        Snoofstudents = "1";
    }

    String Sshortdesc = ETshortins.getText().toString();
    TinyDB tinydb = new TinyDB(getContext());
    starttimesched = tinydb.getListString("starttime");
    endtimesched = tinydb.getListString("endtime");
    String lati = tinydb.getString("latitude");
    String longi = tinydb.getString("longitude");


    tutor_create(Sgroup, Sprice, Snoofstudents, Sshortdesc, lati, longi, minprice);

}
else {
    new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE).setTitleText("Please Select Grade Level")
            .setConfirmText("OK")
            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                }
            }).show();
}
                }
                else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE).setTitleText("Please fill all Details")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                }
            }
        });

        TinyDB tinydb = new TinyDB(getContext());


        tinydb.putString("latitude","");
        tinydb.putString("longitude","");
        tinydb.putListString("starttime", (ArrayList<String>) starttime);
        tinydb.putListString("endtime", (ArrayList<String>) endtime);
        return  view;
    }



    public void tutor_create( String sgroup, String sprice, String snoofstudents, String sshortdesc, String lati, String longi,String minprice)
    {
        try {

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            JSONObject jsonBody = new JSONObject();


            JSONArray array2=new JSONArray(aryGrade);
            //array2.put(Sgrade);


            JSONObject jsonObjectall=new JSONObject();
            if (!(lati.isEmpty()))
            {
                jsonObjectall.put("lat",lati);
                jsonObjectall.put("lng",longi);
                jsonBody.put("location",jsonObjectall);
            }

            JSONArray startendarray=new JSONArray();
            for(int i=0;i<starttimesched.size();i++){
                JSONObject obj=new JSONObject();
                try {
                    obj.put("start",starttimesched.get(i));
                    obj.put("end",endtimesched.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startendarray.put(obj);
            }


            jsonBody.put("schedules",startendarray);
            jsonBody.put("gradeLevel",array2);
            jsonBody.put("type",sgroup);
            jsonBody.put("isUserSubject",Bisusersubject);
            jsonBody.put("price",sprice);
            jsonBody.put("studentsCount",snoofstudents);
            jsonBody.put("description",sshortdesc);
            jsonBody.put("minPrice",minprice);



            String subid=subjectnameid.toString();
           subid=subid.replaceAll("\\[", "").replaceAll("\\]","");
            jsonBody.put(  "subjectId",subid);


            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_url_twotr.Profile_subject_create_class, new Response.Listener<String>() {
                public void onResponse(String response) {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("Subject Added SuccessFully")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    TinyDB tinydb = new TinyDB(getContext());
                                    tinydb.putString("latitude","");
                                    tinydb.putString("longitude","");
                                    tinydb.putListString("starttime", (ArrayList<String>) starttime);
                                    tinydb.putListString("endtime", (ArrayList<String>) endtime);
                                    sweetAlertDialog.dismiss();
                                    Fragment mFragment = new TutorDashboard();
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, mFragment).commit();

                                }
                            }).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();

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


    public void subject_grade_spinner() {


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_url_twotr.Profile_subject_grade_spin, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);


                    JSONArray jsonArray1 = jObj.getJSONArray("gradeLevel");

                    for (int i = 0; i < jsonArray1.length(); i++) {
                        if(jsonArray1.length()==0)
                        {
                            ETgrade.setEnabled(false);
                            ETgrade.setText(String.valueOf(jsonArray1.get(i)));
                        }
                        else {
                            ASgrade.add(new MultiSelectModel(i,String.valueOf(jsonArray1.get(i))));
                        }
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                TinyDB tinydb = new TinyDB(getContext());
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
                        builder.append("").append(subjectnamelist.get(i)).append("");

                        //   } catch (JSONException e) {
                        //      e.printStackTrace();
                    }

                    TVtypesearch.setText(builder);
                }
                else
                {
                    Ssubjectkind=tinydb.getString("subjecttype");
                    Bisusersubject=true;
                    String Ssubjectnamei=tinydb.getString("subjectnamei");
                    subjectnameid = tinydb.getListString("subjectnameid");
                    TVtypesearch.setText(Ssubjectnamei);
                }

                //   startendarray.put(obj);
                //   }
                //Toast.makeText(context, "i got you", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == ADD_SUBJECT_RESULT) {
            if (resultCode == RESULT_OK) {
                TinyDB tinydb = new TinyDB(getContext());
                starttimesched=   tinydb.getListString("starttime");
                if (!starttimesched.isEmpty())
                {
                    TVaddsched.setText("Scheduled");
                    linearLayoutaddsc.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    // TVaddsched.setBackground(getResources().getDrawable(R.drawable.tab_button_selected));
                }

                }


                //   startendarray.put(obj);
                //   }
                //Toast.makeText(context, "i got you", Toast.LENGTH_SHORT).show();
            }
        if (requestCode == ADD_MAP_RESULT) {
            if (resultCode == RESULT_OK) {
                TinyDB tinydb = new TinyDB(getContext());

                String lati= tinydb.getString("latitude");

                if (!lati.isEmpty())
                {
                    TVaddmap.setText("Located");
                    linearLayoutaddma.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                    //  TVaddmap.setBackground(getResources().getDrawable(R.drawable.tab_button_selected));

                }
            }

            //   startendarray.put(obj);
            //   }
            //Toast.makeText(context, "i got you", Toast.LENGTH_SHORT).show();
        }
        }
    }






