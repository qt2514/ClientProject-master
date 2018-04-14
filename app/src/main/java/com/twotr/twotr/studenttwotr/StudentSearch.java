package com.twotr.twotr.studenttwotr;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
import com.twotr.twotr.tutorfiles.NotificationDetails;
import com.twotr.twotr.tutorfiles.Notification_global_class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        LVNotification_view=view.findViewById(R.id.notification_listview);
        IBnotification_close=view.findViewById(R.id.close_notification);
        notificationpanel=view.findViewById(R.id.notification_masterpanel);
        IBnotification_view=view.findViewById(R.id.notification_dashboard_load);
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
