package com.twotr.twotr.tutorfiles;


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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.github.thunder413.datetimeutils.DateTimeUnits;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.google.gson.Gson;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
import com.twotr.twotr.globalpackfiles.TinyDB;
import com.twotr.twotr.guestfiles.GuestControlBoard;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
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

import pl.pawelkleczkowski.customgauge.CustomGauge;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorDashboard extends Fragment {
    AVLoadingIndicatorView avi;
    ListView LVtutordashboard;
    SharedPreferences Shared_user_details;
    public String Stoken;
    String Slati,Slongi,Sid;
ImageView imageView,imageViewloc,imageViewnoloc;
TextView textViewstartdate,textViewstarttime,textViewsubjectname,textViewsubjecttype;
RelativeLayout relativeLayoutdashbord;
    SwipyRefreshLayout swipyRefreshLayout;
    private static final int ADD_MAP_RES = 4;

    ProgressBar footer;
    private CustomGauge CGgauge;
    public static TutorDashboard newInstance() {
        TutorDashboard fragment= new TutorDashboard();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tutor_dashboard, container, false);
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
        avi=view.findViewById(R.id.avi);

        LVtutordashboard=view.findViewById(R.id.dashboard_list);
//        footer = new ProgressBar(getContext());
//        LVtutordashboard.addFooterView(footer);
        swipyRefreshLayout=view.findViewById(R.id.swipyrefreshlayout);

        new ScheduleAsyncList().execute(Global_url_twotr.Profile_dashboard);

        LVtutordashboard.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                new ScheduleAsyncList().execute(Global_url_twotr.Profile_dashboard);


            }

        });

        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                new ScheduleAsyncList().execute(Global_url_twotr.Profile_dashboard);
                swipyRefreshLayout.setRefreshing(false);
            }
        });
     dashboard_twotr();
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


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_url_twotr.Profile_dashboard, new Response.Listener<String>() {

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
}
