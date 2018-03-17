package com.twotr.twotr.tutorfiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
import com.twotr.twotr.globalpackfiles.TinyDB;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleSubjectSelect extends AppCompatActivity {

    SharedPreferences Shared_user_details;
    String Stoken;
    ListView listViewWithCheckBox;
    EditText serach_text;
    TextView textViewadd;
    Button Bdone;
    CheckBox cb;
    List<String> subjectnamelist;
    List<String> subjectnameid;
    Context context;
    AVLoadingIndicatorView avi;
    List<MultispinnerList> listViewItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_subject_select);
        listViewWithCheckBox = findViewById(R.id.listView);
        Shared_user_details=getSharedPreferences("user_detail_mode",0);
        Stoken=  Shared_user_details.getString("token", null);
        serach_text=findViewById(R.id.serach_subject);
        textViewadd=findViewById(R.id.add_subject_list);
        avi = findViewById(R.id.avi);
        avi.hide();
       // Bdone=findViewById(R.id.addsubject_button);
        context=this;
        subjectnamelist=new ArrayList<>();
        subjectnameid=new ArrayList<>();
        listViewItems = new ArrayList<MultispinnerList>();
        String usersubjectname="http://twotr.com:5040/api/userinfo/basic/profile";
        subject_name_list(usersubjectname);

//        listViewWithCheckBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//// make Toast when click
//                cb =  view.findViewById(R.id.checkBox);
//                cb.setChecked(!cb.isChecked());
//                Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_LONG).show();
//            }
//        });

//        Bdone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                TinyDB tinydb = new TinyDB(context);
//                tinydb.putListString("subjectnamelist", (ArrayList<String>) subjectnamelist);
//                tinydb.putListString("subjectnameid", (ArrayList<String>) subjectnameid);
//                tinydb.putString("subjecttype", "Subject");
//                Intent intent = new Intent();
//                setResult(RESULT_OK, intent);
//                finish();
//
//            }
//        });


        textViewadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newsub=serach_text.getText().toString().trim();
                user_subject(newsub);

            }
        });
        serach_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listViewItems.clear();
                String subcharname=  s.toString();
                subject_name_list("http://twotr.com:5040/api/subject/search?key="+subcharname);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




    }

    public class CustomListView extends ArrayAdapter {
        private List<MultispinnerList> ScheduleModeList;
        private int resource;
        Context context;
        private LayoutInflater inflater;

        CustomListView(Context context, int resource, List<MultispinnerList> objects) {
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

                holder.subjectname = (TextView)convertView.findViewById(R.id.subjectname);
                holder.subjectid = (TextView)convertView.findViewById(R.id.subjectid);

              //  holder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);

                //     holder.TVstart_time = convertView.findViewById(R.id.hours_sched);

                convertView.setTag(holder);
            }//ino
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            final MultispinnerList supl = ScheduleModeList.get(position);

            holder.subjectname.setText(supl.getMulsubject());
            holder.subjectid.setText(supl.getMulsubjectid());
holder.subjectname.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        subjectnamelist.add(supl.getMulsubject());
        subjectnameid.add(supl.getMulsubjectid());
        TinyDB tinydb = new TinyDB(context);
        tinydb.putListString("subjectnamelist", (ArrayList<String>) subjectnamelist);
        tinydb.putListString("subjectnameid", (ArrayList<String>) subjectnameid);
        tinydb.putString("subjecttype", "Subject");
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
});
//            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked){
//                        // add into arraylist
//                        subjectnamelist.add(supl.getMulsubject());
//                        subjectnameid.add(supl.getMulsubjectid());
//
//                    }else{
//                        // remove from arraylist
//                        subjectnamelist.remove(supl.getMulsubject());
//                        subjectnameid.remove(supl.getMulsubjectid());
//
//                    }
//
//                }
//            });

            return convertView;
        }

        class ViewHolder {

            TextView subjectname,subjectid;

         //   CheckBox checkBox;
            //  private TextView TVstart_time;


        }
    }



    public void subject_name_list(String usersubjecturl) {

        avi.show();

        RequestQueue requestQueueq = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, usersubjecturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("subjects");
                   // int totalrecords=jObj.getInt("totalRecords");
                    if (jsonArray.length()<1)
                    {
                        textViewadd.setVisibility(View.VISIBLE);
        //                Bdone.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
//                        Bdone.setVisibility(View.VISIBLE);
                        textViewadd.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonobject = jsonArray.getJSONObject(i);
                            String name = jsonobject.getString("title");
                            String id = jsonobject.getString("_id");
                            listViewItems.add(new MultispinnerList(name,id));

                        }
                    }

                    CustomListView adapter = new CustomListView(getBaseContext(), R.layout.single_subject_select, listViewItems);
                    listViewWithCheckBox.setAdapter(adapter);
                    avi.hide();
                    adapter.notifyDataSetChanged();
                    //    listViewWithCheckBox.setAdapter(new CustomListView(getBaseContext(), listViewItems));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                avi.hide();
     //           Toast.makeText(SingleSubjectSelect.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {

                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
              headers.put("Content-Type", "application/json");
                headers.put("x-tutor-app-id", "tutor-app-android");
                headers.put("Authorization", "Bearer "+Stoken);


                return headers;

            }



        };

        requestQueueq.add(stringRequest);
    }



    public void user_subject(String usersubject)
    {
        avi.show();
        try {

            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JSONObject jsonObjectall=new JSONObject();
            jsonObjectall.put("title",usersubject);

            final String requestBody = jsonObjectall.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_url_twotr.Profile_subject_create, new Response.Listener<String>() {

                public void onResponse(String response) {
                    JSONObject jObj = null;
                    try {
                        jObj = new JSONObject(response);
                        String title = jObj.getString("title");
                        String createdBy = jObj.getString("createdBy");
                        String id = jObj.getString("_id");
                        String createdAt = jObj.getString("createdAt");
                        String isApproved = jObj.getString("isApproved");

                        subjectnameid.add(id);
                        avi.hide();
                        TinyDB tinydb = new TinyDB(context);
                        tinydb.putString("subjecttype","UserSubject");
                        tinydb.putString("subjectnamei",title);
                        tinydb.putListString("subjectnameid", (ArrayList<String>) subjectnameid);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();

                        //Ssubjectid=id;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    avi.hide();
         //           Toast.makeText(context, "Please add subject again", Toast.LENGTH_SHORT).show();
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
