package com.twotr.twotr.tutorfiles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.thunder413.datetimeutils.DateTimeUnits;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
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

public class SubscriptionActivity extends AppCompatActivity {
    AVLoadingIndicatorView avi;
    SharedPreferences Shared_user_details;
    public String Stoken;
    ListView Lsubs_list;
    List<String> gradelevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        Lsubs_list=findViewById(R.id.subs_list);
        avi=findViewById(R.id.avi);
        Shared_user_details=getSharedPreferences("user_detail_mode",0);
        Stoken=  Shared_user_details.getString("token", null);
        new ScheduleAsyncList().execute(Global_url_twotr.Tutor_subscription+"page=1&size=20");
    }



    public class Schedule_class extends ArrayAdapter {
        private List<Subscriptionlisttutor> ScheduleModeList;
        private int resource;
        Context context;
        private LayoutInflater inflater;

        Schedule_class(Context context, int resource, List<Subscriptionlisttutor> objects) {
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

                holder.TVtitle = convertView.findViewById(R.id.title_subs);

                holder.TVdescription = convertView.findViewById(R.id.descrip_subs);
                holder.TVprice = convertView.findViewById(R.id.price_subs);
                holder.IMagesubs = convertView.findViewById(R.id.image_subs);




                //     holder.TVstart_time = convertView.findViewById(R.id.hours_sched);

                convertView.setTag(holder);
            }//ino
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            Subscriptionlisttutor supl = ScheduleModeList.get(position);

holder.TVtitle.setText(supl.getTitle());


            holder.TVdescription.setText(supl.getDescription());
            holder.TVprice.setText(supl.getPaymentType()+"-"+supl.getPrice());
            holder.TVtitle.setText(supl.getTitle());
            Picasso
                    .with(context)
                    .load(Global_url_twotr.Image_Base_url+supl.getUrl())
                    .fit()
                    .centerCrop()
                    .into( holder.IMagesubs);
            return convertView;
        }

        class ViewHolder {
            private TextView TVtitle,TVdescription,TVprice;
            private ImageView IMagesubs;


        }
    }



    @SuppressLint("StaticFieldLeak")
    public class ScheduleAsyncList extends AsyncTask<String, String, List<Subscriptionlisttutor>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            avi.show();
        }

        @Override
        protected List<Subscriptionlisttutor> doInBackground(String... params) {
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
                JSONArray parentArray = parentObject.getJSONArray("subscriptions");

                List<Subscriptionlisttutor> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {

                    JSONObject finalObject = parentArray.getJSONObject(i);
                    //
                    Subscriptionlisttutor catego = new Subscriptionlisttutor();
                    catego.set_id(finalObject.getString("_id"));
                    catego.setTitle(finalObject.getString("title"));
                    catego.setDescription(finalObject.getString("description"));
                    catego.setPrice(finalObject.getString("price"));
                    catego.setPaymentType(finalObject.getString("paymentType"));
                    catego.setStart(finalObject.getString("start"));
                    catego.setEnd(finalObject.getString("end"));
                    catego.setCreatedBy(finalObject.getString("createdBy"));
                    catego.setLastModifiedAt(finalObject.getString("LastModifiedAt"));
                    catego.setAstModifiedBy(finalObject.getString("LastModifiedBy"));
                    catego.setCreatedAt(finalObject.getString("createdAt"));

                    try {
                        JSONObject schedule=finalObject.getJSONObject("bannerPicture");
                        catego.setUrl(schedule.getString("url"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayList<String> gradelevelst =new ArrayList<>();
                    JSONArray jsonArray = finalObject.getJSONArray("gradeLevel");
                    for (int j = 0; j < jsonArray.length(); j++) {

                        gradelevelst.add(String.valueOf(jsonArray.get(j)));
                    }

                    catego.setGradeLevel(gradelevelst);
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
        protected void onPostExecute(final List<Subscriptionlisttutor> ScheduleMode) {
            super.onPostExecute(ScheduleMode);
            avi.hide();
            if ((ScheduleMode != null) && (ScheduleMode.size()>0))
            {
               
                Schedule_class adapter = new Schedule_class(getApplicationContext(), R.layout.subscriptionviewlt, ScheduleMode);
                Lsubs_list.setAdapter(adapter);

                Lsubs_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Subscriptionlisttutor subscriptionlisttutor = ScheduleMode.get(position);
                        Intent intent = new Intent(SubscriptionActivity.this, SubscriptionDetail.class);
                        intent.putExtra("imagemain", subscriptionlisttutor.getUrl());
                        intent.putExtra("title_main", subscriptionlisttutor.getTitle());
                        intent.putExtra("description", subscriptionlisttutor.getDescription());
                        intent.putExtra("price", subscriptionlisttutor.getPrice());
                        intent.putExtra("paymenttype", subscriptionlisttutor.getPaymentType());
                        intent.putStringArrayListExtra("gradelevel", subscriptionlisttutor.getGradeLevel());

                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();

            }

//            else {
//                relativeLayoutdashbord.setVisibility(View.INVISIBLE);
//                imageView.setVisibility(View.VISIBLE);
//            }

        }
    }
}
