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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.github.thunder413.datetimeutils.DateTimeUnits;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.google.gson.Gson;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorSchedule extends Fragment {
    AVLoadingIndicatorView avi;

private Button Bhistory,Bupcoming;
SwipeMenuListView LVschedule,LVschedule_history;
private  String schedule_list_url,schedule_list_url_history;
    SharedPreferences Shared_user_details;
    public String Stoken;
TextView nodatatextview;
    SwipyRefreshLayout swipyRefreshLayout,swipyRefreshLayout_history;
    ProgressBar footer;
    Schedule_history_class adapter_history;
    Schedule_class adaptersa;
    GifImageView underser_gif;
    String[] upcomingmore = {
            "Reschedule",
            "Delete"

    };
    String[] historymore = {
            "Edit",
            "Delete"

    };
    List<Schedule_history_list> milokilo;
    String navtab_list="upcoming";

    SwipeMenuCreator swipeMenuCreatorhostory;
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
LVschedule_history=view.findViewById(R.id.schedule_list_history);
        avi=view.findViewById(R.id.avi);
        footer = new ProgressBar(getContext());
        LVschedule.addFooterView(footer);
        LVschedule_history.addFooterView(footer);
        swipyRefreshLayout=view.findViewById(R.id.swipyrefreshlayout);
        swipyRefreshLayout_history=view.findViewById(R.id.swipyrefreshlayout_history);

        schedule_list_url = "http://twotr.com:5040/api/class/upcoming?page=1&size=10" ;
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
navtab_list="history";
LVschedule.setVisibility(View.INVISIBLE);
LVschedule_history.setVisibility(View.VISIBLE);
             Bhistory.setBackgroundResource(R.drawable.tab_selected);
             swipyRefreshLayout.setVisibility(View.INVISIBLE);
             swipyRefreshLayout_history.setVisibility(View.VISIBLE);
             Bupcoming.setBackgroundResource(R.drawable.tab_unselected_right);
             Bupcoming.setTextColor(getResources().getColor(R.color.mdtp_white));
             Bhistory.setTextColor(getResources().getColor(R.color.black));
             schedule_list_url_history = "http://twotr.com:5040/api/class/history?page=1&size=10" ;
             new ScheduleAsyncListHistory().execute(schedule_list_url_history);

         }
     });
     Bupcoming.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             navtab_list="upcoming";
             LVschedule_history.setVisibility(View.INVISIBLE);
             LVschedule.setVisibility(View.VISIBLE);
             swipyRefreshLayout.setVisibility(View.VISIBLE);
             swipyRefreshLayout_history.setVisibility(View.INVISIBLE);
             Bupcoming.setBackgroundResource(R.drawable.tab_selected_right);
             Bhistory.setBackgroundResource(R.drawable.tab_unselected);
             Bupcoming.setTextColor(getResources().getColor(R.color.black));
             Bhistory.setTextColor(getResources().getColor(R.color.mdtp_white));
//upcominglist(upcoming_url);

              schedule_list_url = "http://twotr.com:5040/api/class/upcoming?page=1&size=10" ;
             new ScheduleAsyncList().execute(schedule_list_url);


         }
     });
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                schedule_list_url = "http://twotr.com:5040/api/class/upcoming?page=1&size=10" ;
                new ScheduleAsyncList().execute(schedule_list_url);
                swipyRefreshLayout.setRefreshing(false);
            }
        });
        swipyRefreshLayout_history.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                schedule_list_url_history = "http://twotr.com:5040/api/class/history?page=1&size=10" ;
                new ScheduleAsyncListHistory().execute(schedule_list_url_history);

                swipyRefreshLayout_history.setRefreshing(false);
            }
        });

        LVschedule_history.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {


                schedule_list_url_history = "http://twotr.com:5040/api/class/history?page="+page+"&size=10" ;
                new ScheduleAsyncListHistoryadd().execute(schedule_list_url_history);

            }

        });
        LVschedule_history.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {


                schedule_list_url = "http://twotr.com:5040/api/class/upcoming?page="+page+"&size=10" ;
                new ScheduleAsyncListadd().execute(schedule_list_url);



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


    @SuppressLint("SetTextI18n")
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
            holder.TVhours.setText(diff+" hours - ");

        }
        holder.TVtime_sched.setText(time_sched+" | ");
        String monthformating=DateTimeUtils.formatWithPattern(startdate, "EEE, MMM dd");
        holder.TVmonth.setText(monthformating);

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

    LVschedule.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
            switch (index) {
                case 0:
                    new MaterialDialog.Builder(getActivity())
                            .title("More")
                            .items(upcomingmore)
                            .icon(getResources().getDrawable(R.drawable.ic_more_horiz_black_24dp))
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {


                                }
                            })
                            .show();
                    break;
                case 1:
                    Toast.makeText(getActivity(), "Chat "+position, Toast.LENGTH_SHORT).show();
                    break;
            }


            return false;
        }
    });




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
catego.setStudentsCount(finalObject.getString("studentsCount"));
catego.setMinPrice(finalObject.getString("minPrice"));
catego.set_id(finalObject.getString("_id"));
                    JSONArray jsonArray1 = finalObject.getJSONArray("schedules");
                    for (int j = 0; j < jsonArray1.length(); j++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(j);
                        catego.setStart(jsonObject.getString("start"));
                        catego.setEnd(jsonObject.getString("end"));

                        //  ListSubject.add(Skind);
                    }

                    try {
                        JSONObject jlocati=finalObject.getJSONObject("location");
                        catego.setLat(jlocati.getString("lat"));
                        catego.setLng(jlocati.getString("lng"));
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                 adaptersa = new Schedule_class(getActivity(), R.layout.schedule_list, ScheduleMode);
                LVschedule.setAdapter(adaptersa);
                if (navtab_list.equals("upcoming")) {
                    Log.i("checksch",navtab_list);
                    LVschedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Schedule_upcoming_list schedule_upcoming_list = ScheduleMode.get(position);
                            Intent intent = new Intent(getActivity(), ScheduleDetailPage.class);
                            intent.putExtra("subject_name", schedule_upcoming_list.getSubject());
                            intent.putExtra("type_subject", schedule_upcoming_list.getType());
                            intent.putExtra("schedule_description", schedule_upcoming_list.getDescription());
                            intent.putExtra("latitude", schedule_upcoming_list.getLat());
                            intent.putExtra("longitude", schedule_upcoming_list.getLng());
                            intent.putExtra("schedule_price", schedule_upcoming_list.getPrice());
                            intent.putExtra("studentscount", schedule_upcoming_list.getStudentsCount());
                            intent.putExtra("minprice", schedule_upcoming_list.getMinPrice());
                            intent.putExtra("cateid",schedule_upcoming_list.get_id());
                            String Scompletestart = schedule_upcoming_list.getStart();
                            String Scompleteend = schedule_upcoming_list.getEnd();

                            String startdate = Scompletestart.substring(0, 10);
                            String starttime = Scompletestart.substring(11, 19);
                            String enddate = Scompleteend.substring(0, 10);
                            String endtime = Scompleteend.substring(11, 19);
                            String time_sched = Scompletestart.substring(11, 16);
                            String datestart = startdate + " " + starttime;
                            String dateend = enddate + " " + endtime;
                            int diff = DateTimeUtils.getDateDiff(datestart, dateend, DateTimeUnits.HOURS);
                            diff = Math.abs(diff);
                            String shours = diff + " hours - ";
                            String stimsc = time_sched + " | ";
                            String smonth = DateTimeUtils.formatWithPattern(startdate, " MMMM dd");
                            intent.putExtra("hrschmon", shours + stimsc + smonth);
                            startActivity(intent);
                        }
                    });
                    adaptersa.notifyDataSetChanged();
                    LVschedule.removeFooterView(footer);

                }


            }
            else
            {
                LVschedule.setVisibility(View.INVISIBLE);
                nodatatextview.setVisibility(View.VISIBLE);
                underser_gif.setVisibility(View.VISIBLE);
            }

            }
        }



    public class Schedule_history_class extends ArrayAdapter {
        private List<Schedule_history_list> ScheduleModeList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        Schedule_history_class(Context context, int resource, List<Schedule_history_list> objects) {
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
        @SuppressLint("SetTextI18n")
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
            Schedule_history_list supl = ScheduleModeList.get(position);
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
            holder.TVhours.setText(diff+" hours - ");
            holder.TVtime_sched.setText(time_sched+" | ");
            String monthformating=DateTimeUtils.formatWithPattern(startdate, "EEE, MMM dd");
            holder.TVmonth.setText(monthformating);

                 swipeMenuCreatorhostory = new SwipeMenuCreator() {
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
            LVschedule_history.setMenuCreator(swipeMenuCreatorhostory);
            LVschedule_history.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(int position, SwipeMenu menu, final int index) {
                        final Schedule_history_list schedule_history_list = milokilo.get(position);

                        switch (index) {
                            case 0:
                                new MaterialDialog.Builder(getActivity())
                                        .title("More")
                                        .items(historymore)
                                        .icon(getResources().getDrawable(R.drawable.ic_more_horiz_black_24dp))
                                        .itemsCallback(new MaterialDialog.ListCallback() {
                                            @Override
                                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                                switch (which)
                                                {
                                                    case 0:


                                                        break;
                                                    case 1:
                                                        String id=schedule_history_list.get_id();
                                                        delete_mylist(id);
                                                        break;
                                                }


                                            }
                                        })
                                        .show();
                                break;
                            case 1:
                              //  Toast.makeText(getActivity(), "Chat "+position, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });


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
    public class ScheduleAsyncListHistory extends AsyncTask<String, String, List<Schedule_history_list>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            avi.show();
        }
        @Override
        protected List<Schedule_history_list> doInBackground(String... params) {
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
           milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    //
                    Schedule_history_list catego = new Schedule_history_list();
                    catego.setSubject(finalObject.getString("subject"));
                    catego.setType(finalObject.getString("type"));
                    catego.setDescription(finalObject.getString("description"));
                    catego.setPrice(finalObject.getString("price"));
                    catego.setStudentsCount(finalObject.getString("studentsCount"));
                    catego.setMinPrice(finalObject.getString("minPrice"));
                    catego.set_id(finalObject.getString("_id"));

                    JSONArray jsonArray1 = finalObject.getJSONArray("schedules");
                    for (int j = 0; j < jsonArray1.length(); j++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(j);
                        catego.setStart(jsonObject.getString("start"));
                        catego.setEnd(jsonObject.getString("end"));

                    }
                    try {
                        JSONObject jlocati=finalObject.getJSONObject("location");
                        catego.setLat(jlocati.getString("lat"));
                        catego.setLng(jlocati.getString("lng"));
                    } catch (JSONException e) {
                        e.printStackTrace();
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
        protected void onPostExecute(final List<Schedule_history_list> Schedule_Mode_Class) {
            super.onPostExecute(Schedule_Mode_Class);
            avi.hide();
            if ((Schedule_Mode_Class != null) && (Schedule_Mode_Class.size()>0))
            {
                LVschedule_history.setVisibility(View.VISIBLE);
                nodatatextview.setVisibility(View.INVISIBLE);
                underser_gif.setVisibility(View.INVISIBLE);
                 adapter_history = new Schedule_history_class(getActivity(), R.layout.schedule_list, Schedule_Mode_Class);
                LVschedule_history.setAdapter(adapter_history);

                adapter_history.notifyDataSetChanged();
                LVschedule_history.removeFooterView(footer);

            }
            else
            {
                LVschedule_history.setVisibility(View.INVISIBLE);
                nodatatextview.setVisibility(View.VISIBLE);
                underser_gif.setVisibility(View.VISIBLE);
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class ScheduleAsyncListHistoryadd extends AsyncTask<String, String, List<Schedule_history_list>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            avi.show();
        }
        @Override
        protected List<Schedule_history_list> doInBackground(String... params) {
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
                milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    //
                    Schedule_history_list catego = new Schedule_history_list();
                    catego.setSubject(finalObject.getString("subject"));
                    catego.setType(finalObject.getString("type"));
                    catego.setDescription(finalObject.getString("description"));
                    catego.setPrice(finalObject.getString("price"));
                    catego.setStudentsCount(finalObject.getString("studentsCount"));
                    catego.setMinPrice(finalObject.getString("minPrice"));
                    catego.set_id(finalObject.getString("_id"));

                    JSONArray jsonArray1 = finalObject.getJSONArray("schedules");
                    for (int j = 0; j < jsonArray1.length(); j++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(j);
                        catego.setStart(jsonObject.getString("start"));
                        catego.setEnd(jsonObject.getString("end"));

                    }
                    try {
                        JSONObject jlocati=finalObject.getJSONObject("location");
                        catego.setLat(jlocati.getString("lat"));
                        catego.setLng(jlocati.getString("lng"));
                    } catch (JSONException e) {
                        e.printStackTrace();
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
        protected void onPostExecute(final List<Schedule_history_list> Schedule_Mode_Class) {
            super.onPostExecute(Schedule_Mode_Class);
            avi.hide();
            if ((Schedule_Mode_Class != null) && (Schedule_Mode_Class.size()>0))
            {

         //       adapter_history = new Schedule_history_class(getActivity(), R.layout.schedule_list, Schedule_Mode_Class);
          adapter_history.addAll(Schedule_Mode_Class);
          adapter_history.notifyDataSetChanged();
                LVschedule_history.removeFooterView(footer);


            }
            else
            {
                LVschedule_history.removeFooterView(footer);
                avi.hide();
            }

        }
    }


    @SuppressLint("StaticFieldLeak")
    public class ScheduleAsyncListadd extends AsyncTask<String, String, List<Schedule_upcoming_list>> {
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
                    catego.setStudentsCount(finalObject.getString("studentsCount"));
                    catego.setMinPrice(finalObject.getString("minPrice"));
                    catego.set_id(finalObject.getString("_id"));
                    JSONArray jsonArray1 = finalObject.getJSONArray("schedules");
                    for (int j = 0; j < jsonArray1.length(); j++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(j);
                        catego.setStart(jsonObject.getString("start"));
                        catego.setEnd(jsonObject.getString("end"));

                        //  ListSubject.add(Skind);
                    }

                    try {
                        JSONObject jlocati=finalObject.getJSONObject("location");
                        catego.setLat(jlocati.getString("lat"));
                        catego.setLng(jlocati.getString("lng"));
                    } catch (JSONException e) {
                        e.printStackTrace();
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
//                LVschedule.setVisibility(View.VISIBLE);
//                nodatatextview.setVisibility(View.INVISIBLE);
//                underser_gif.setVisibility(View.INVISIBLE);
//                adaptersa = new Schedule_class(getActivity(), R.layout.schedule_list, ScheduleMode);
//                LVschedule.setAdapter(adaptersa);
adaptersa.addAll(ScheduleMode);
                    adaptersa.notifyDataSetChanged();
                }

            else
            {
                LVschedule.removeFooterView(footer);
                avi.hide();
            }
            }


        }










    public void delete_mylist(String id)
{

    try {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject jsonObjectall = new JSONObject();
        jsonObjectall.put("null", "sd");
        final String requestBody = jsonObjectall.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Global_url_twotr.Tutor_schedule_delete+id, new Response.Listener<String>() {

            public void onResponse(String response) {
                schedule_list_url_history = "http://twotr.com:5040/api/class/history?page=1&size=10" ;
                new ScheduleAsyncListHistory().execute(schedule_list_url_history);
               adapter_history.notifyDataSetChanged();
             //   startActivity(new Intent(getActivity(),HomePage.class));
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.contentContainer, fragment, fragment.getClass().getSimpleName())
//                            .commit();

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
        // but first we check if we are waiting for the previous load to finish.
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

}
