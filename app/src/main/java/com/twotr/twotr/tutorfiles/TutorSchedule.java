package com.twotr.twotr.tutorfiles;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.github.thunder413.datetimeutils.DateTimeUnits;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.google.gson.Gson;
import com.twotr.twotr.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorSchedule extends Fragment {
    AVLoadingIndicatorView avi;

private Button Bhistory,Bupcoming;
SwipeMenuListView LVschedule;
private  String schedule_list_url;
    SharedPreferences Shared_user_details;
    public String Stoken;
TextView nodatatextview;
    GifImageView underser_gif;
    public static TutorSchedule newInstance() {
        return new TutorSchedule();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view= inflater.inflate(R.layout.fragment_tutor_schedule, container, false);
        Shared_user_details=this.getActivity().getSharedPreferences("user_detail_mode",0);
        Stoken=  Shared_user_details.getString("token", null);
     Bhistory=view.findViewById(R.id.history_button);
     Bupcoming=view.findViewById(R.id.upcoming_button);
     underser_gif=view.findViewById(R.id.nodatagif);
     nodatatextview=view.findViewById(R.id.nodatatext);
LVschedule=view.findViewById(R.id.schedule_list);
        avi=view.findViewById(R.id.avi);
         schedule_list_url = "https://api.twotr.com/api/class/upcoming?page=1&size=10" ;
        new ScheduleAsyncList().execute(schedule_list_url);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem more_sched = new SwipeMenuItem(
                        getContext());
                // set item background
                more_sched.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                more_sched.setWidth(180);
                // set item title

                more_sched.setTitle("More");
                more_sched.setIcon(R.drawable.ic_more_horiz_black_24dp);
                // set item title fontsize
                more_sched.setTitleSize(12);
                // set item title font color
                more_sched.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(more_sched);

                // create "delete" item
                SwipeMenuItem review_sched = new SwipeMenuItem(
                        getContext());
                // set item background
                review_sched.setBackground(new ColorDrawable(Color.rgb(0x62, 0xCD,
                        0xEC)));
                // set item width
                review_sched.setWidth(180);
                review_sched.setTitle("Chat");
                review_sched.setTitleSize(12);
                // set item title font color
                review_sched.setTitleColor(Color.WHITE);
                // set a icon
                review_sched.setIcon(R.drawable.chat_list);
                // add to menu
                menu.addMenuItem(review_sched);
            }
        };

        LVschedule.setMenuCreator(creator);
     Bhistory.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             Bhistory.setBackgroundResource(R.drawable.tab_selected);
             Bupcoming.setBackgroundResource(R.drawable.tab_unselected_right);
             Bupcoming.setTextColor(getResources().getColor(R.color.mdtp_white));
             Bhistory.setTextColor(getResources().getColor(R.color.black));

             schedule_list_url = "https://api.twotr.com/api/class/history?page=1&size=10" ;
             new ScheduleAsyncList().execute(schedule_list_url);
             SwipeMenuCreator creator = new SwipeMenuCreator() {

                 @Override
                 public void create(SwipeMenu menu) {
                     // create "open" item
                     SwipeMenuItem more_sched = new SwipeMenuItem(
                             getContext());
                     // set item background
                     more_sched.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                             0xCE)));
                     // set item width
                     more_sched.setWidth(180);
                     // set item title

                     more_sched.setTitle("More");
                     more_sched.setIcon(R.drawable.ic_more_horiz_black_24dp);
                     // set item title fontsize
                     more_sched.setTitleSize(12);
                     // set item title font color
                     more_sched.setTitleColor(Color.WHITE);
                     // add to menu
                     menu.addMenuItem(more_sched);

                     // create "delete" item
                     SwipeMenuItem review_sched = new SwipeMenuItem(
                             getContext());
                     // set item background
                     review_sched.setBackground(new ColorDrawable(Color.rgb(0x62, 0xCD,
                             0xEC)));
                     // set item width
                     review_sched.setWidth(180);
                     review_sched.setTitle("Review");
                     review_sched.setTitleSize(12);
                     // set item title font color
                     review_sched.setTitleColor(Color.WHITE);
                     // set a icon
                     review_sched.setIcon(R.drawable.review_list);
                     // add to menu
                     menu.addMenuItem(review_sched);
                 }
             };

             LVschedule.setMenuCreator(creator);
         }
     });
     Bupcoming.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Bupcoming.setBackgroundResource(R.drawable.tab_selected_right);
             Bhistory.setBackgroundResource(R.drawable.tab_unselected);
             Bupcoming.setTextColor(getResources().getColor(R.color.black));
             Bhistory.setTextColor(getResources().getColor(R.color.mdtp_white));
//upcominglist(upcoming_url);

              schedule_list_url = "https://api.twotr.com/api/class/upcoming?page=1&size=10" ;
             new ScheduleAsyncList().execute(schedule_list_url);
             SwipeMenuCreator creator = new SwipeMenuCreator() {

                 @Override
                 public void create(SwipeMenu menu) {
                     // create "open" item
                     SwipeMenuItem more_sched = new SwipeMenuItem(
                             getContext());
                     // set item background
                     more_sched.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                             0xCE)));
                     // set item width
                     more_sched.setWidth(180);
                     // set item title

                     more_sched.setTitle("More");
                     more_sched.setIcon(R.drawable.ic_more_horiz_black_24dp);
                     // set item title fontsize
                     more_sched.setTitleSize(12);
                     // set item title font color
                     more_sched.setTitleColor(Color.WHITE);
                     // add to menu
                     menu.addMenuItem(more_sched);

                     // create "delete" item
                     SwipeMenuItem review_sched = new SwipeMenuItem(
                             getContext());
                     // set item background
                     review_sched.setBackground(new ColorDrawable(Color.rgb(0x62, 0xCD,
                             0xEC)));
                     // set
                     review_sched.setWidth(180);
                     review_sched.setTitle("Chat");
                     review_sched.setTitleSize(12);
                     // set item title font color
                     review_sched.setTitleColor(Color.WHITE);
                     // set a icon
                     review_sched.setIcon(R.drawable.chat_list);
                     // add to menu
                     menu.addMenuItem(review_sched);
                 }
             };

             LVschedule.setMenuCreator(creator);
         }
     });

     return view;
    }


//    public void upcominglist(String upcoming_url)
//    {
//        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
//
//        StringRequest postRequest = new StringRequest(Request.Method.GET, upcoming_url,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.d("Response", response);
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                        Log.d("ERROR","error => "+error.toString());
//                    }
//                }
//        ) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("content-type", "application/json");
//                params.put("x-tutor-app-id", "tutor-app-ios");
//                params.put("authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVhM2MxZDY0MTE5OTBkMmMyNDRiNDI4OCIsInJvbGVzIjpbInR1dG9yIl0sImlhdCI6MTUxNzE1MTI4OCwiZXhwIjoxNTE4NDQ3Mjg4LCJqdGkiOiIxIn0.efviEAlmLRkBev5Ib4N35XaCZyzXinc1KJYgv9KM_Yo");
//
//                return params;
//            }
//        };
//        queue.add(postRequest);
  //  }
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
        final Schedule_class.ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
            holder = new Schedule_class.ViewHolder();

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
            holder = (Schedule_class.ViewHolder) convertView.getTag();
        }
        Schedule_upcoming_list supl = ScheduleModeList.get(position);
        holder.TVsubjectname.setText(supl.getSubject());
        String type_group=supl.getType();
        if (type_group.equals("oneonone"))
        {
            type_group="1 to 1";
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
        holder.TVhours.setText(diff+" hours - ");
        holder.TVtime_sched.setText(time_sched+" | ");
        String monthformating=DateTimeUtils.formatWithPattern(startdate, "EEE, MMM dd");
        holder.TVmonth.setText(monthformating);
        // holder.TVstart_time.setText(supl.getStart());


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
      //  private TextView TVstart_time;


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

                    JSONArray jsonArray1 = finalObject.getJSONArray("schedules");
                    for (int j = 0; j < jsonArray1.length(); j++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(j);
                        catego.setStart(jsonObject.getString("start"));
                        catego.setEnd(jsonObject.getString("end"));
                      //  ListSubject.add(Skind);
                    }



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
            if ((ScheduleMode != null) && (ScheduleMode.size()>0))
            {
                LVschedule.setVisibility(View.VISIBLE);
                nodatatextview.setVisibility(View.INVISIBLE);
                underser_gif.setVisibility(View.INVISIBLE);
                Schedule_class adapter = new Schedule_class(getActivity(), R.layout.schedule_list, ScheduleMode);
                LVschedule.setAdapter(adapter);
                LVschedule.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Schedule_upcoming_list schedule_upcoming_list = ScheduleMode.get(position);
                        Intent intent = new Intent(getActivity(),ScheduleDetailPage.class);
                        intent.putExtra("subject_name",schedule_upcoming_list.getSubject());
                        intent.putExtra("type_subject",schedule_upcoming_list.getType());
                        intent.putExtra("schedule_description",schedule_upcoming_list.getDescription());
                  intent.putExtra("schedule_price",schedule_upcoming_list.getPrice());
                        String Scompletestart=schedule_upcoming_list.getStart();
                        String Scompleteend=schedule_upcoming_list.getEnd();

                        String startdate=Scompletestart.substring(0,10);
                        String starttime=Scompletestart.substring(11,19);
                        String enddate=Scompleteend.substring(0,10);
                        String endtime=Scompleteend.substring(11,19);
                        String time_sched=Scompletestart.substring(11,16);
                        String datestart=startdate+ " "+starttime;
                        String dateend=enddate+ " "+endtime;
                        int diff = DateTimeUtils.getDateDiff(datestart,dateend, DateTimeUnits.HOURS);
                        diff=Math.abs(diff);
                       String shours=diff+" hours - ";
                        String stimsc= time_sched+" | ";
                        String smonth= DateTimeUtils.formatWithPattern(startdate, " MMMM dd");
                        intent.putExtra("hrschmon",shours+stimsc+smonth);
                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();

            }
            else
            {
                LVschedule.setVisibility(View.INVISIBLE);
                nodatatextview.setVisibility(View.VISIBLE);
                underser_gif.setVisibility(View.VISIBLE);
            }

            }
        }
    

}
