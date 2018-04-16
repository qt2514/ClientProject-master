package com.twotr.twotr.studenttwotr;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.github.thunder413.datetimeutils.DateTimeUnits;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.google.gson.Gson;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.squareup.picasso.Picasso;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
import com.twotr.twotr.globalpackfiles.TinyDB;
import com.twotr.twotr.tutorfiles.Addmaptutor;
import com.twotr.twotr.tutorfiles.HomePage;
import com.twotr.twotr.tutorfiles.NotificationDetails;
import com.twotr.twotr.tutorfiles.Notification_global_class;
import com.twotr.twotr.tutorfiles.Schedule_upcoming_list;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.pawelkleczkowski.customgauge.CustomGauge;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentSearch extends Fragment {
    SharedPreferences Shared_user_details;
    public String Stoken;
    ImageButton IBnotification_view,IBnotification_close;
ListView LVNotification_view;
    RelativeLayout notificationpanel;
    AVLoadingIndicatorView avi;
    ListView LVtutordashboard;

    String Slati,Slongi,Sid;
    ImageView imageView,imageViewloc,imageViewnoloc;
    TextView textViewstartdate,textViewstarttime,textViewsubjectname,textViewsubjecttype;
    RelativeLayout relativeLayoutdashbord;
    SwipyRefreshLayout swipyRefreshLayout;
    private static final int ADD_MAP_RES = 4;

    ProgressBar footer;
    private CustomGauge CGgauge;
    public StudentSearch() {
        // Required empty public constructor
    }
    public static StudentSearch newInstance() {
        StudentSearch fragment= new StudentSearch();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_student_search, container, false);
        Shared_user_details=this.getActivity().getSharedPreferences("user_detail_mode",0);
        Stoken=  Shared_user_details.getString("token", null);
        imageView=view.findViewById(R.id.creadte_dashboard_image);
        imageViewloc=view.findViewById(R.id.dashboard_location);
        imageViewnoloc=view.findViewById(R.id.dashborad_noloac);
        CGgauge=view.findViewById(R.id.gauge2);
        textViewstartdate=view.findViewById(R.id.subject_start_date);
        textViewstarttime=view.findViewById(R.id.subject_start_time);
        textViewsubjectname=view.findViewById(R.id.dashboard_subject_name);
        textViewsubjecttype=view.findViewById(R.id.subject_type);
        relativeLayoutdashbord=view.findViewById(R.id.list_dashboard);
        LVNotification_view=view.findViewById(R.id.notification_listview);
        IBnotification_close=view.findViewById(R.id.close_notification);
        notificationpanel=view.findViewById(R.id.notification_masterpanel);
        IBnotification_view=view.findViewById(R.id.notification_dashboard_load);

        avi=view.findViewById(R.id.avi);
        LVtutordashboard=view.findViewById(R.id.dashboard_list);
        swipyRefreshLayout=view.findViewById(R.id.swipyrefreshlayout);
        new ScheduleAsyncList().execute(Global_url_twotr.Profile_dashboard_student);
        LVtutordashboard.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                new ScheduleAsyncList().execute(Global_url_twotr.Profile_dashboard_student);
            }

        });
        new NotifiAsyncList().execute(Global_url_twotr.Student_Notification+"?page=1&size=20");
        IBnotification_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationpanel.setVisibility(View.VISIBLE);
                IBnotification_view.setVisibility(View.GONE);
                IBnotification_close.setVisibility(View.VISIBLE);
            }
        });

        IBnotification_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationpanel.setVisibility(View.GONE);
                IBnotification_view.setVisibility(View.VISIBLE);


            }
        });
        return view;
    }








    public class Schedule_class extends ArrayAdapter {
        private List<Schedule_upcoming_list> ScheduleModeList;
        private int resource;
        Context context;
        private LayoutInflater inflater;

        Schedule_class(Context context, int resource, List<Schedule_upcoming_list> objects) {
            super(context, resource, objects);
            ScheduleModeList = objects;
            this.context = context;
            this.resource = resource;
            inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
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

                holder.TVsubjectname = convertView.findViewById(R.id.sub_name_sched);
                holder.TVtypemenbers = convertView.findViewById(R.id.group_one_title);
                holder.TVschedule_des = convertView.findViewById(R.id.schedule_description);
                holder.TVprice_schedule = convertView.findViewById(R.id.price_schedule);
                holder.TVhours=convertView.findViewById(R.id.hours_sched);
                holder.TVmonth=convertView.findViewById(R.id.month_day_sched);
                holder.TVtime_sched=convertView.findViewById(R.id.time_sched);



                //     holder.TVstart_time = convertView.findViewById(R.id.hours_sched);

                convertView.setTag(holder);
            }//ino
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            Schedule_upcoming_list supl = ScheduleModeList.get(position);
            holder.TVsubjectname.setText(supl.getSubject());
            String type_group=supl.getType();
            if (type_group.equals("oneonone"))
            {
                type_group="1 on 1";
            }
            holder.TVtypemenbers.setText(type_group);
            holder.TVschedule_des.setText(supl.getDescription());
            holder.TVprice_schedule.setText(supl.getPrice());
            String Scompletestart=supl.getStart();
            String Scompleteend=supl.getEnd();

            String startdate=Scompletestart.substring(0,10);
            String starttime=Scompletestart.substring(11,19);
            String enddate=Scompleteend.substring(0,10);
            String endtime=Scompleteend.substring(11,19);
            String time_sched=Scompletestart.substring(11,16);
            String datestart=startdate+ " "+starttime;
            String dateend=enddate+ " "+endtime;
            int diff = DateTimeUtils.getDateDiff(datestart,dateend, DateTimeUnits.HOURS);
            diff=Math.abs(diff);
            if (diff>1)
            {
                holder.TVhours.setText(diff+" hours - ");


            }
            else
            {
                holder.TVhours.setText(diff+" hour - ");

            }
            holder.TVtime_sched.setText(time_sched+" | ");
            String monthformating=DateTimeUtils.formatWithPattern(startdate, "EEE, MMM dd");
            holder.TVmonth.setText(monthformating);
            //          holder.TVstart_time.setText(supl.getStart());


//        String strThatDay = holder.text_getingdate.getText().toString();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date d = null;
//        try {
//            d = formatter.parse(strThatDay);//catch exception
//        } catch (ParseException e) {
//
//            e.printStackTrace();
//        }
//        Calendar thatDay = Calendar.getInstance();
//        thatDay.setTime(d);
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        String formattedDate = df.format(c.getTime());
//        long diff = c.getTimeInMillis() - thatDay.getTimeInMillis(); //result in millis
//        days = diff / (24 * 60 );
//        holder.text_dispdate.setText("accepted before "+days+" days");


            return convertView;
        }

        class ViewHolder {
            private TextView TVsubjectname;
            private TextView TVtypemenbers;
            private TextView TVschedule_des;
            private TextView TVprice_schedule;
            private  TextView TVhours;
            private  TextView TVtime_sched;
            private  TextView TVmonth;

        }
    }



    @SuppressLint("StaticFieldLeak")
    public class ScheduleAsyncList extends AsyncTask<String, String, List<Schedule_upcoming_list>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            avi.show();
        }

        @Override
        protected List<Schedule_upcoming_list> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty("content-type","application/json");
                connection.setRequestProperty("x-tutor-app-id","tutor-app-android");
                connection.setRequestProperty("authorization","Bearer "+Stoken);
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("classes");

                List<Schedule_upcoming_list> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {

                    JSONObject finalObject = parentArray.getJSONObject(i);
                    //
                    Schedule_upcoming_list catego = new Schedule_upcoming_list();
                    catego.setSubject(finalObject.getString("subject"));
                    catego.setType(finalObject.getString("type"));
                    catego.setDescription(finalObject.getString("description"));
                    catego.setPrice(finalObject.getString("price"));

                    JSONObject schedule=finalObject.getJSONObject("schedule");

                    catego.setStart(schedule.getString("start"));
                    catego.setEnd(schedule.getString("end"));




                    milokilo.add(catego);



                }

                return milokilo;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<Schedule_upcoming_list> ScheduleMode) {
            super.onPostExecute(ScheduleMode);
            avi.hide();
            if ((ScheduleMode != null) && (ScheduleMode.size()>0)&& getActivity()!=null)
            {
                relativeLayoutdashbord.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                Schedule_class adapter = new Schedule_class(getActivity(), R.layout.schedule_list, ScheduleMode);
                LVtutordashboard.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            else {
                relativeLayoutdashbord.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
            }

        }
    }


    public void dashboard_twotr() {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_url_twotr.Profile_dashboard_student, new Response.Listener<String>() {

            public void onResponse(String response) {
                JSONObject jObj = null;
                try {
                    jObj = new JSONObject(response);
                    JSONArray parentArray = jObj.getJSONArray("classes");
                    JSONObject jsonObject = parentArray.getJSONObject(0);
                    String subject=jsonObject.getString("subject");
                    Sid=jsonObject.getString("_id");
                    String subject_type=jsonObject.getString("type");
                    if (subject_type.equals("oneonone"))
                    {
                        subject_type="1 on 1";
                    }

                    try {
                        JSONObject location=jsonObject.getJSONObject("location");
                        final   String lati = location.getString("lat");
                        final String longi=location.getString("lng");
                        imageViewloc.setVisibility(View.VISIBLE);
                        imageViewloc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Uri gmmIntentUri = Uri.parse("google.navigation:q="+lati+","+longi+"");
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);
                            }
                        });

                    } catch (JSONException e) {
                        imageViewnoloc.setVisibility(View.VISIBLE);

                        imageViewnoloc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), Addmaptutor.class);
                                startActivityForResult(intent, ADD_MAP_RES);
                            }
                        });
                    }

                    JSONObject schedule=jsonObject.getJSONObject("schedule");
                    String start=schedule.getString("start");

                    String startdate=start.substring(0,10);

                    String endtime=start.substring(11,16);
                    String endtimecomp=start.substring(11,19);

                    textViewsubjectname.setText(subject);

                    textViewstartdate.setText(startdate);
                    textViewstarttime.setText(endtime);

                    textViewsubjecttype.setText(subject_type);

                    Date date = new Date();
//
                    String dateend = startdate+" "+endtimecomp;
//// Get difference in milliseconds
                    int diff = DateTimeUtils.getDateDiff(date,dateend, DateTimeUnits.HOURS);
                    diff=Math.abs(diff);

                    int   hordiff=24-diff;


                    CGgauge.setValue(hordiff);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                headers.put("content-Type", "application/json");
                headers.put("x-tutor-app-id", "tutor-app-android");
                headers.put("authorization", "Bearer "+Stoken);

                return headers;

            }






        };

        requestQueue.add(stringRequest);

    }

    public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

        // The minimum amount of items to have below your current scroll position
        // before loading more.
        private int visibleThreshold = 1;
        // The current offset index of data you have loaded
        private int currentPage = 0;
        // The total number of items in the dataset after the last load
        private int previousTotalItemCount = 0;
        // True if we are still waiting for the last set of data to load.
        private boolean loading = true;
        // Sets the starting page index
        private int startingPageIndex = 0;

        public EndlessScrollListener() {
        }

        public EndlessScrollListener(int visibleThreshold) {
            this.visibleThreshold = visibleThreshold;
        }

        public EndlessScrollListener(int visibleThreshold, int startPage) {
            this.visibleThreshold = visibleThreshold;
            this.startingPageIndex = startPage;
            this.currentPage = startPage;
        }

        // This happens many times a second during a scroll, so be wary of the code you place here.
        // We are given a few useful parameters to help us work out if we need to load some more data,
        // but first we check if we are waiting for the previous load to finish`.
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // If the total item count is zero and the previous isn't, assume the
            // list is invalidated and should be reset back to initial state
            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = this.startingPageIndex;
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) {
                    this.loading = true;
                }
            }

            // If it’s still loading, we check to see if the dataset count has
            // changed, if so we conclude it has finished loading and update the current page
            // number and total item count.
            if (loading && (totalItemCount > previousTotalItemCount)) {
                loading = false;
                previousTotalItemCount = totalItemCount;
                currentPage++;
            }

            // If it isn’t currently loading, we check to see if we have breached
            // the visibleThreshold and need to reload more data.
            // If we do need to reload some more data, we execute onLoadMore to fetch the data.
            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                onLoadMore(currentPage + 1, totalItemCount);
                loading = true;
            }
        }

        // Defines the process for actually loading more data based on page
        public abstract void onLoadMore(int page, int totalItemsCount);

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // Don't take any action on changed

        }
    }
    public void update_schedule() {

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JSONObject jsonObjectall = new JSONObject();
            if (!Slati.isEmpty()&&!Slongi.isEmpty()) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("lat", Slati);
                jsonObject2.put("lng", Slongi);
                jsonObjectall.put("location", jsonObject2);
            }
            final String requestBody = jsonObjectall.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_url_twotr.Tutor_Schedule_update+Sid, new Response.Listener<String>() {

                public void onResponse(String response) {

                    startActivity(new Intent(getActivity(),HomePage.class));
                    getActivity().finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        try {
                            JSONObject jsonObject=new JSONObject(new String(networkResponse.data));
                            String message=jsonObject.getString("message");
                            Log.i("jiokoj",message);
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

                                        NetworkResponse networkResponse = error.networkResponse;
                                        if (networkResponse != null && networkResponse.data != null) {
                                            try {
                                                JSONObject jsonObject=new JSONObject(new String(networkResponse.data));
                                                String message=jsonObject.getString("message");
                                                Log.i("jiokoj",message);
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

                                                    Volley.newRequestQueue(getContext()).add(jsonRequest);


                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
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

                                Volley.newRequestQueue(getContext()).add(jsonRequest);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {



        if (requestCode == ADD_MAP_RES) {
            if (resultCode == RESULT_OK) {
                TinyDB tinydb = new TinyDB(getContext());
                Slati= tinydb.getString("latitude");
                Slongi=tinydb.getString("longitude");
                update_schedule();
            }

            //   startendarray.put(obj);
            //   }
            //Toast.makeText(context, "i got you", Toast.LENGTH_SHORT).show();
        }
    }
    
    
    
    

    
    
    
    
    
    
    
    
    
    public class Notification_class extends ArrayAdapter {
        private List<Notification_global_class> NotificationModeList;
        private int resource;
        Context context;
        private LayoutInflater inflater;

        Notification_class(Context context, int resource, List<Notification_global_class> objects) {
            super(context, resource, objects);
            NotificationModeList = objects;
            this.context = context;
            this.resource = resource;
            inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
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
                holder.TVnotify_subject=convertView.findViewById(R.id.notification_subject);
                holder.TVnotify_name=convertView.findViewById(R.id.notification_name);
                holder.TVnotify_status=convertView.findViewById(R.id.status_notification);
                holder.CIVnotify_iame=convertView.findViewById(R.id.notification_image);

                convertView.setTag(holder);
            }//ino
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            Notification_global_class ngc = NotificationModeList.get(position);
            holder.TVnotify_subject.setText("Subject : "+ngc.getSubject_name());
            holder.TVnotify_name.setText(ngc.getStud_firstName()+" "+ngc.getStud_lastName());
            String Status_record;
            if (ngc.getNotifi_isAccepted())
            {
                Status_record="Accepted";
                holder.TVnotify_status.setText("Status : "+Status_record);

            }
            else if (ngc.getNotifi_isConfirmed())
            {
                Status_record="Confirmed";
                holder.TVnotify_status.setText("Status : "+Status_record);

            }
            else if (ngc.getNotifi_isPending())
            {
                Status_record="Pending";
                holder.TVnotify_status.setText("Status : "+Status_record);

            }
            else
            {
                Status_record="Rejected";
                holder.TVnotify_status.setText("Status : "+Status_record);

            }

            Picasso
                    .with(context)
                    .load(Global_url_twotr.Image_Base_url+ngc.getProfile_Picture())
                    .fit()
                    .error(getResources().getDrawable(R.drawable.profile_image_tutor))
                    .centerCrop()
                    .into( holder.CIVnotify_iame);
            return convertView;
        }

        class ViewHolder {
            private CircleImageView CIVnotify_iame;
            private TextView TVnotify_name;
            private TextView TVnotify_subject;
            private TextView TVnotify_status;



        }
    }



    @SuppressLint("StaticFieldLeak")
    public class NotifiAsyncList extends AsyncTask<String, String, List<Notification_global_class>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<Notification_global_class> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                DataOutputStream printout;
                DataInputStream inputStream;
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput (true);
                connection.setDoOutput (true);
                connection.setUseCaches (false);
                connection.setRequestProperty("content-type","application/json");
                connection.setRequestProperty("x-tutor-app-id","tutor-app-android");
                connection.setRequestProperty("authorization", "Bearer "+Stoken);

                connection.setRequestMethod("POST");
                connection.connect();


                JSONObject auth=new JSONObject();

                auth.put("status", "");


                printout = new DataOutputStream(connection.getOutputStream ());
                printout.writeBytes(auth.toString());
                printout.flush ();
                printout.close ();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("notifications");

                List<Notification_global_class> milokilo = new ArrayList<>();

                for (int i = 0; i < parentArray.length(); i++) {

                    JSONObject finalObject = parentArray.getJSONObject(i);
                    //
                    Notification_global_class nofi_json = new Notification_global_class();
                    nofi_json.setClassId(finalObject.getString("classId"));
                    nofi_json.setIsActive(finalObject.getString("isActive"));
                    nofi_json.setSubjectId(finalObject.getString("subjectId"));
                    nofi_json.setSubject_name(finalObject.getString("subject"));
                    try {
                        nofi_json.setTermmsg(finalObject.getString("termsMsg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject student_details = finalObject.getJSONObject("tutor");
                    nofi_json.setStud_id(student_details.getString("_id"));
                    nofi_json.setStud_firstName(student_details.getString("firstName"));
                    nofi_json.setStud_lastName(student_details.getString("lastName"));
                    try {
                        JSONObject student_profile_image = student_details.getJSONObject("profilePicture");
                        nofi_json.setProfile_Picture(student_profile_image.getString("url"));
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject notification_details = finalObject.getJSONObject("notification");
                    nofi_json.setNotifi_id(notification_details.getString("_id"));
                    nofi_json.setNotifi_isConfirmed(notification_details.getBoolean("isConfirmed"));
                    nofi_json.setNotifi_isRejected(notification_details.getBoolean("isRejected"));
                    nofi_json.setNotifi_isAccepted(notification_details.getBoolean("isAccepted"));
                    nofi_json.setNotifi_isPending(notification_details.getBoolean("isPending"));
                    JSONArray jsonArray1 = notification_details.getJSONArray("schedules");

                    for (int j = 0; j < jsonArray1.length(); j++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(j);
                        nofi_json.setSched_start(jsonObject.getString("start"));
                        nofi_json.setSched_end(jsonObject.getString("end"));


                    }


                    milokilo.add(nofi_json);



                }

                return milokilo;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<Notification_global_class> NotificationMode) {
            super.onPostExecute(NotificationMode);

            if ((NotificationMode != null) && (NotificationMode.size()>0)&& getActivity()!=null)
            {
                IBnotification_view.setVisibility(View.VISIBLE);
                Notification_class adapter = new Notification_class(getActivity(), R.layout.notificationlist, NotificationMode);
                LVNotification_view.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                LVNotification_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String staus_notifi;
                        Notification_global_class notificla = NotificationMode.get(position);
                        if (notificla.getNotifi_isAccepted())
                        {
                            staus_notifi="Accepted";
                        }
                        else if (notificla.getNotifi_isConfirmed())
                        {
                            staus_notifi="Confirmed";
                        }
                        else if (notificla.getNotifi_isPending())
                        {
                            staus_notifi="Pending";
                        }
                        else
                        {
                            staus_notifi="Rejected";
                        }
                        Intent intent = new Intent(getActivity(), NotificationDetails.class);
                        intent.putExtra("stufname", notificla.getStud_firstName());
                        intent.putExtra("stulname", notificla.getStud_lastName());
                        intent.putExtra("stuimage", notificla.getProfile_Picture());
                        intent.putExtra("subject_name", notificla.getSubject_name());
                        intent.putExtra("subject_id", notificla.getSubjectId());
                        intent.putExtra("student_id",notificla.getStud_id());
                        intent.putExtra("notifi_id",notificla.getNotifi_id());
                        intent.putExtra("status_rec", staus_notifi);
                        intent.putExtra("start_time", notificla.getSched_start());
                        intent.putExtra("class_id",notificla.getClassId());
                        intent.putExtra("whoiam", "student");
                        intent.putExtra("termsmsg",notificla.getTermmsg());



                        startActivity(intent);
                    }
                });

            }

            else {
                IBnotification_view.setVisibility(View.GONE);
            }

        }
    }
}
