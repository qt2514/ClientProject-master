package com.twotr.twotr.tutorfiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.github.thunder413.datetimeutils.DateTimeUnits;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
import com.twotr.twotr.globalpackfiles.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class TutorMaterialUpload extends AppCompatActivity {
 ListView listViewupload;
 Button Badditem,Bdone;
    ArrayList<String> preuplo=new ArrayList<String>();
     Schedule_class adapter;
     int i=1;
     Context context;
  String asda;
    SharedPreferences Shared_user_details;
    public String Stoken;
    private ArrayList<String> docPaths = new ArrayList<>();
    String prepos,classid;
    ImageButton IB_back;
    ArrayList<String> prename,preurl,preid;
    ArrayList<String> posname,posurl,posid;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_material_upload);
        Shared_user_details=getSharedPreferences("user_detail_mode",0);
        Stoken=  Shared_user_details.getString("token", null);
        listViewupload=findViewById(R.id.listcheck);
        Badditem=findViewById(R.id.addBtn);
        context=TutorMaterialUpload.this;
        IB_back=findViewById(R.id.back_ima_scedule);
textView=findViewById(R.id.classtype);
Bdone=findViewById(R.id.doneclass);
        prename= new ArrayList<>();
        preurl= new ArrayList<>();
        preid= new ArrayList<>();
        posname= new ArrayList<>();
        posurl= new ArrayList<>();
        posid= new ArrayList<>();


        Badditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//i++;
//                preuplo.add("Pick Your File :"+String.valueOf(i));
//
//                listViewupload.setAdapter(adapter);
                new ChooserDialog().with(TutorMaterialUpload.this)
                        .withStartFile(asda)

                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {

                                  uploadBitmap(path,pathFile);
                            }
                        })
                        .build()
                        .show();

            }
        });

        final Intent intent = getIntent();
        Bundle Bintent = intent.getExtras();
        if(Bintent != null) {
            classid = (String) Bintent.get("calid");
            prepos=(String) Bintent.get("classtype");
          String prepostest=(String) Bintent.get("classtypetest");
          if (prepos.equals("pre/"))
          {
              prename= Bintent.getStringArrayList("prename");
              preurl= Bintent.getStringArrayList("preurl");
              preid= Bintent.getStringArrayList("preid");
              Log.i("prename",prename.toString());

                  preuplo.addAll(prename);

          }
           else
          {
              posname= Bintent.getStringArrayList("posname");
              posurl= Bintent.getStringArrayList("posurl");
              posid= Bintent.getStringArrayList("posid");
              Log.i("posname",posname.toString());
              preuplo.addAll(posname);
          }


            textView.setText(prepostest);

        }



        adapter = new Schedule_class(TutorMaterialUpload.this, R.layout.fileuploadlistplay, preuplo);
        listViewupload.setAdapter(adapter);


        IB_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

                holder.TVtitle = convertView.findViewById(R.id.textview_join);
                holder.IBclose = convertView.findViewById(R.id.close_file);
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

            holder.IBclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   preuplo.remove(preupl);
                    adapter.notifyDataSetChanged();

                }
            });

           holder.TVtitle.setText(preupl);

           holder.TVtitle.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
//                   new ChooserDialog().with(TutorMaterialUpload.this)
//                           .withStartFile(asda)
//
//                           .withChosenListener(new ChooserDialog.Result() {
//                               @Override
//                               public void onChoosePath(String path, File pathFile) {
//                                   holder.TVtitle.setText(path);
//                                //   uploadBitmap(path,pathFile);
//                               }
//                           })
//                           .build()
//                           .show();
               }
           });

            return convertView;
        }

        class ViewHolder {
            private ImageButton IBclose;
            private TextView TVtitle;


        }
    }

    private void uploadBitmap(final String path, final File filepa) {

        Map<String, String> myheader = new HashMap<String, String>();

        myheader.put("x-tutor-app-id","tutor-app-android");
        myheader.put("Authorization","Bearer "+Stoken);
//        myheader.put("content-type", "application/json");


        //our custom volley request
        final VolleyDocuup volleyMultipartRequest = new VolleyDocuup(Request.Method.POST, Global_url_twotr.Tutor_material_upload+prepos+classid, myheader,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Log.i("myresult", "onResponse: " + obj);
//                            Log.d(TAG, "onResponse: " + obj.toString());
                            Toast.makeText(context, "File Uploded Succesfully", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("myresult", "onError: " + error);
                        Toast.makeText(context, "File Format Not Supported", Toast.LENGTH_SHORT).show();

                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tags", "files");
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                    params.put("files" + path, new DataPart(path, filepa));


                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }
}