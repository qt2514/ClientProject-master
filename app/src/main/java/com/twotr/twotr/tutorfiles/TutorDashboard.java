package com.twotr.twotr.tutorfiles;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
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

import pl.pawelkleczkowski.customgauge.CustomGauge;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorDashboard extends Fragment {
    AVLoadingIndicatorView avi;
    ListView LVtutordashboard;
    SharedPreferences Shared_user_details;
    public String Stoken;
ImageView imageView;
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
   CGgauge=view.findViewById(R.id.gauge2);
        avi=view.findViewById(R.id.avi);
        LVtutordashboard=view.findViewById(R.id.dashboard_list);
        new ScheduleAsyncList().execute(Global_url_twotr.Profile_dashboard);

   CGgauge.setValue(60);

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


                //     holder.TVstart_time = convertView.findViewById(R.id.hours_sched);

                convertView.setTag(holder);
            }//ino
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            Schedule_upcoming_list supl = ScheduleModeList.get(position);
            holder.TVsubjectname.setText(supl.getSubject());
            String type_group=supl.getType();
            holder.TVtypemenbers.setText(type_group);
            holder.TVschedule_des.setText(supl.getDescription());
            holder.TVprice_schedule.setText(supl.getPrice());


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
                    Schedule_upcoming_list catego = gson.fromJson(finalObject.toString(), Schedule_upcoming_list.class);
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
                imageView.setVisibility(View.INVISIBLE);
                Schedule_class adapter = new Schedule_class(getActivity(), R.layout.schedule_list, ScheduleMode);
                LVtutordashboard.setAdapter(adapter);
//                LVtutordashboard.setOnItemClickListener( new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Schedule_upcoming_list schedule_upcoming_list = ScheduleMode.get(position);
//                        Intent intent = new Intent(getActivity(),ScheduleDetailPage.class);
//                        intent.putExtra("subject_name",schedule_upcoming_list.getSubject());
//                        intent.putExtra("type_subject",schedule_upcoming_list.getSubject());
//                        intent.putExtra("schedule_description",schedule_upcoming_list.getDescription());
//                        intent.putExtra("schedule_price",schedule_upcoming_list.getPrice());
//
//                        startActivity(intent);
//                    }
//                });
                adapter.notifyDataSetChanged();

            }

            else {
                imageView.setVisibility(View.VISIBLE);
            }

        }
    }




}
