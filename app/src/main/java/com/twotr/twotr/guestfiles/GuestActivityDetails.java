package com.twotr.twotr.guestfiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
import com.twotr.twotr.globalpackfiles.SigninActivity;
import com.twotr.twotr.tutorfiles.Schedule_ShowMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class GuestActivityDetails extends AppCompatActivity {
    HorizontalCalendar horizontalCalendar;
    List<String> starttimeschednew;
    List<String> endtimeschednew;
    Context context;
    Schedule_class adapter;
    ArrayList<String> startdatearr=new ArrayList<String>();
    String Sstarttime;
    String checkstarttime;
    RelativeLayout relativeLayout;
    TextView Tvprofname;
    LinearLayout linearLayouthead;
    String selectedDateStr ;
    ImageButton IB_back;
    String subjectname,subjecttype,subjecttime,Slati,Slongi,Susername,Screateid,Simageurl,checkevendate,groupdate,
            Sstudentcount;
    Button Bmap_show;
CircleImageView profileimage;
    List<String> groupKeynew ;
    List<String> isAvailablenew;
    List<String> slotnew;
    List<String> availableCountnew ;
    List<String> schcalssid;
    List<String> schid ;
Button Bslotprice;
    SharedPreferences Shared_user_details;
    String Sroles,Stoken;
    ListView LVlistingravit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_details);

        relativeLayout=findViewById(R.id.relaymast);

        linearLayouthead=findViewById(R.id.linearLayout5);
Bmap_show=findViewById(R.id.map_show);
Bslotprice=findViewById(R.id.slotpricebook);
        IB_back=findViewById(R.id.back_ima_scedule);
Tvprofname=findViewById(R.id.prof_prof_name);
profileimage=findViewById(R.id.image_profile);
LVlistingravit=findViewById(R.id.listingravit);
        starttimeschednew=new ArrayList<>();
        endtimeschednew=new ArrayList<>();
        groupKeynew =new ArrayList<>();
        isAvailablenew=new ArrayList<>();
        slotnew =new ArrayList<>();
        availableCountnew =new ArrayList<>();

        schcalssid =new ArrayList<>();
        schid=new ArrayList<>();



        context=GuestActivityDetails.this;

        Shared_user_details=getSharedPreferences("user_detail_mode",0);
        Sroles=  Shared_user_details.getString("roles", null);
        Stoken=  Shared_user_details.getString("token", null);

        final Intent intent = getIntent();
        Bundle Bintent = intent.getExtras();
        if(Bintent != null) {
            starttimeschednew= Bintent.getStringArrayList("starttime");
            endtimeschednew= Bintent.getStringArrayList("endtime");
            isAvailablenew= Bintent.getStringArrayList("isAvailable");
            groupKeynew= Bintent.getStringArrayList("groupKey");
            slotnew= Bintent.getStringArrayList("slotPrice");
            availableCountnew= Bintent.getStringArrayList("availableCount");
            Sstudentcount=Bintent.getString("studentcount");
            subjectname= Bintent.getString("subjectname");
            subjecttype= Bintent.getString("type_subject");
            subjecttime= Bintent.getString("hrstot");
            Slati= Bintent.getString("latitude");
            Slongi= Bintent.getString("longitude");
            schcalssid=Bintent.getStringArrayList("schclassid");
            schid=Bintent.getStringArrayList("sch_id");

            Susername=Bintent.getString("createdbyname");
Screateid=Bintent.getString("createdby");

            Simageurl=Bintent.getString("imgurl");


        }

        Picasso
                .with(context)
                .load(Global_url_twotr.Image_Base_url+Simageurl)
                .fit()
                .error(getResources().getDrawable(R.drawable.profile_image_tutor))
                .centerCrop()
                .into( profileimage);
        linearLayouthead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcrea=new Intent(GuestActivityDetails.this,GuestProfile.class);
                intentcrea.putExtra("createdby",Screateid);
                startActivity(intentcrea);
            }
        });
Tvprofname.setText(Susername);
        IB_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bmap_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GuestActivityDetails.this, Schedule_ShowMap.class);
                intent.putExtra("latitude", Slati);
                intent.putExtra("longitude", Slongi);
                intent.putExtra("subjectname", subjectname);
                intent.putExtra("subjecttype", subjecttype);
                intent.putExtra("subjecthours", subjecttime);
                startActivity(intent);

//                Toast.makeText(ScheduleDetailPage.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(ScheduleDetailPage.this,Schedule_ShowMap.class));//
            }
        });



        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 0);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, 25);
        final Calendar defaultSelectedDate = Calendar.getInstance();
        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .defaultSelectedDate(defaultSelectedDate)
                .addEvents(new CalendarEventsPredicate() {
                    @Override
                    public List<CalendarEvent> events(Calendar date) {
                        List<CalendarEvent> events = new ArrayList<>();
for (String a:groupKeynew) {
    checkevendate=DateFormat.format("dd-MM-yyyy", date).toString();
    if (checkevendate.matches(a)) {
        events.add(new CalendarEvent(getResources().getColor(R.color.colorPrimaryDark), "event"));
    }

}
                        return events;

                    }
                })
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSelected(Calendar date, int position) {
                relativeLayout.setVisibility(View.INVISIBLE);
                selectedDateStr = DateFormat.format("dd-MM-yyyy", date).toString();
                    for (int da=0;da<groupKeynew.size();da++) {
                        groupdate=groupKeynew.get(da);

    if (selectedDateStr.contains(groupdate)) {
        startdatearr.clear();
        Sstarttime = starttimeschednew.get(da);
        checkstarttime = Sstarttime.substring(11, 16);
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
        Date _24HourDt = null;
        try {
            _24HourDt = _24HourSDF.parse(checkstarttime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String S12form=_12HourSDF.format(_24HourDt);
        startdatearr.add(S12form);
        Bslotprice.setText("Book For "+slotnew.get(da)+" KW");
        adapter = new Schedule_class(GuestActivityDetails.this, R.layout.calendertime_guestlist, startdatearr);
        LVlistingravit.setAdapter(adapter);
        relativeLayout.setVisibility(View.VISIBLE);
        final int finalDa = da;

        Bslotprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if (isAvailablenew.get(finalDa).matches("false"))
{
    new SweetAlertDialog(GuestActivityDetails.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Not Available ")
            .setContentText("Slot is Not Available")
            .setConfirmText("OK")
            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                }
            }).show();
}
               else if (TextUtils.isEmpty(Sroles))
                {
                    new SweetAlertDialog(GuestActivityDetails.this, SweetAlertDialog.NORMAL_TYPE).setTitleText("Sign in!")
                            .setContentText("Please Signin Now!")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    startActivity(new Intent(GuestActivityDetails.this, SigninActivity.class));
                                    finish();
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                }
                else if(Sroles.matches("student"))
                {
if (subjecttype.matches("group"))
{
    new SweetAlertDialog(GuestActivityDetails.this, SweetAlertDialog.NORMAL_TYPE).setTitleText("Request Confirmation!")
            .setContentText("Total Capacity of class is "+ Sstudentcount+". There are "+availableCountnew.get(finalDa)+" slot left."+"Are you sure you want to Book this Slot!")
            .setConfirmText("OK")
            .setCancelText("Cancel")
            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    String myclassid,myschid;
                    myclassid=schcalssid.get(finalDa);
                    myschid=schid.get(finalDa);
                    enrollslot(myclassid,myschid);
                    sweetAlertDialog.dismiss();

                }

            })
            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                }
            })
            .show();
}
else
{
    new SweetAlertDialog(GuestActivityDetails.this, SweetAlertDialog.NORMAL_TYPE).setTitleText("Request Confirmation!")
            .setContentText("Are you sure you want to Book this Slot!")
            .setConfirmText("OK")
            .setCancelText("Cancel")
            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    String myclassid,myschid;
                    myclassid=schcalssid.get(finalDa);
                    myschid=schid.get(finalDa);
                    enrollslot(myclassid,myschid);
                    sweetAlertDialog.dismiss();

                }

            })
            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                }
            })
            .show();
}


                    }


            }

        });
        break;

    } else {
        startdatearr.clear();
        relativeLayout.setVisibility(View.INVISIBLE);

    }

                    }

//                for (String a:groupKeynew) {
//
//                    Toast.makeText(context, a, Toast.LENGTH_SHORT).show();
//
//                }



           }
        });
    }
    public class Schedule_class extends ArrayAdapter {
        private ArrayList<String> ScheduleModeList;
        private int resource;
        Context context;
        private LayoutInflater inflater;

        Schedule_class(Context context, int resource, ArrayList<String> objects) {
            super(context, resource, objects);
            ScheduleModeList = objects;
            this.context = context;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
                holder = new ViewHolder();

                holder.TVtitle = convertView.findViewById(R.id.starttime1);

//                holder.TVschedule_des = convertView.findViewById(R.id.schedule_description);
//                holder.TVprice_schedule = convertView.findViewById(R.id.price_schedule);
//                holder.TVhours=convertView.findViewById(R.id.hours_sched);
//                holder.TVmonth=convertView.findViewById(R.id.month_day_sched);
//                holder.TVtime_sched=convertView.findViewById(R.id.time_sched);
                //     holder.TVstart_time = convertView.findViewById(R.id.hours_sched);

                convertView.setTag(holder);
            }//ino
            else {
                holder = (ViewHolder) convertView.getTag();
            }


            final String preupl = ScheduleModeList.get(position);


            holder.TVtitle.setText(preupl);



            return convertView;
        }

        class ViewHolder {
            private TextView TVtitle;


        }
    }

    public void enrollslot(String myclassid, String myschid)
    {

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            JSONArray arrayid = new JSONArray();
            jsonBody.put("classId", myclassid);

arrayid.put(myschid);
jsonBody.put("scheduleIds",arrayid);

            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_url_twotr.Student_enroll, new Response.Listener<String>() {

                public void onResponse(String response) {
                    new SweetAlertDialog(GuestActivityDetails.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Booking Confirmed")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    new SweetAlertDialog(GuestActivityDetails.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Not Available ")
                            .setContentText("Slot is Not Available")
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
