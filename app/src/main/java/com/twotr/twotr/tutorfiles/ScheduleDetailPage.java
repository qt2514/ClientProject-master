package com.twotr.twotr.tutorfiles;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.twotr.twotr.R;

public class ScheduleDetailPage extends AppCompatActivity {
TextView TVsubject_name,TVtypesubject,TVprice_amount,TVhrscal,TVstudentcount;
EditText ETsched_desc;
ImageButton IBedit_sched,IBpre_post,IB_back;
LinearLayout linear_layout;
String Slati,Slongi,type_subject,subname,hrschmon,Sstudentcount;
Button But_showmap,But_showschedule;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail_page);
        IBedit_sched=findViewById(R.id.edit_desc_sched);
        TVsubject_name=findViewById(R.id.subject_name);
        TVtypesubject=findViewById(R.id.type_detail_subject);
        ETsched_desc=findViewById(R.id.schedule_descripti);
        TVprice_amount=findViewById(R.id.price_amount);
        IBpre_post=findViewById(R.id.add_pre_post);
        TVstudentcount=findViewById(R.id.stu_count_sched);
        linear_layout=findViewById(R.id.post_pre_linear);
        TVhrscal=findViewById(R.id.hours_sched);
        But_showmap=findViewById(R.id.schedule_map);
        But_showschedule=findViewById(R.id.schedule_show);
        IB_back=findViewById(R.id.back_ima_scedule);
        IB_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        But_showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

    Intent intent = new Intent(ScheduleDetailPage.this, Schedule_ShowMap.class);
    intent.putExtra("latitude", Slati);
    intent.putExtra("longitude", Slongi);
    intent.putExtra("subjectname", subname);
    intent.putExtra("subjecttype", type_subject);
    intent.putExtra("subjecthours", hrschmon);
    startActivity(intent);

//                Toast.makeText(ScheduleDetailPage.this, "Coming Soon", Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(ScheduleDetailPage.this,Schedule_ShowMap.class));
            }
        });
        But_showschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ScheduleDetailPage.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                //   startActivity(new Intent(ScheduleDetailPage.this,Schedule_ShowSchedule.class));
            }
        });
        ETsched_desc.setEnabled(false);
        Intent intent = getIntent();
        Bundle Bintent = intent.getExtras();
        if(Bintent != null)
        {
             subname = (String) Bintent.get("subject_name");
             type_subject = (String) Bintent.get("type_subject");
            Slati=(String) Bintent.get("latitude");
            Slongi=(String) Bintent.get("longitude");
            Sstudentcount=(String) Bintent.get("studentscount");
            assert type_subject != null;
            if (type_subject.equals("oneonone"))
            {
                type_subject="1 to 1";
            }
            String sched_descr = (String) Bintent.get("schedule_description");
            String sched_price = (String) Bintent.get("schedule_price");
             hrschmon = (String) Bintent.get("hrschmon");
            TVhrscal.setText(hrschmon);
            TVprice_amount.setText(sched_price);
            TVtypesubject.setText(" "+ type_subject);
            TVsubject_name.setText(subname);
            ETsched_desc.setText(sched_descr);
            TVstudentcount.setText(Sstudentcount);
            if (Slati==null)
            {
                But_showmap.setVisibility(View.GONE);
            }
            else
            {
                But_showmap.setVisibility(View.VISIBLE);
            }
        }
        IBedit_sched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ETsched_desc.setEnabled(true);
             //   IBedit_sched.setBackgroundResource(R.drawable.save_schedule_edit);

            }
        });
        IBpre_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
linear_layout.setVisibility(View.VISIBLE);
            }
        });
    }

}
