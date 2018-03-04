package com.twotr.twotr.tutorfiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.TinyDB;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class ScheduleStart extends AppCompatActivity  implements TimePickerDialog.OnTimeSetListener{

    HorizontalCalendar horizontalCalendar;
    TextView TVsub_name;
    TimePickerDialog tpd;
    LinearLayout linearLayout2,linearLayout3,linearLayout4,linearLayout5;
    TextView textViewstart,textViewend,textViewstart2,textViewend2,textViewstart3,textViewend3,textViewstart4,textViewend4,textViewstart5,textViewend5;
    String starttimetpd;
    int clickcount=0;
    TextView textViewaddmore,textViewcancel;
    ImageButton imageButton, imageButtonclose2,imageButtonclose3,imageButtonclose4,imageButtonclose5,IB_back;
    List<String> starttime;
    List<String> starttime2;
    List<String> starttime3;
    List<String> starttime4;
    List<String> starttime5;
    List<String> endtime;
    List<String> endtime2;
    List<String> endtime3;
    List<String> endtime4;
    List<String> endtime5;
    String selectedDateStr;
Button buttonaddtime;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_start);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        final Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 0);
        Calendar now = Calendar.getInstance();
         tpd = TimePickerDialog.newInstance(
                ScheduleStart.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                 now.get(Calendar.SECOND),
                 true
        );
        tpd.setThemeDark(true);
        tpd.vibrate(true);

context=ScheduleStart.this;

        TVsub_name=findViewById(R.id.subject_name);
textViewstart=findViewById(R.id.start_time_slot);

        textViewstart2=findViewById(R.id.start_time_slot2);
        textViewstart3=findViewById(R.id.start_time_slot3);
        textViewstart4=findViewById(R.id.start_time_slot4);
        textViewstart5=findViewById(R.id.start_time_slot5);
        textViewend=findViewById(R.id.end_time_slot);
        textViewend2=findViewById(R.id.end_time_slot2);
        textViewend3=findViewById(R.id.end_time_slot3);
        textViewend4=findViewById(R.id.end_time_slot4);
        textViewend5=findViewById(R.id.end_time_slot5);

        linearLayout2=findViewById(R.id.linlay2);
        linearLayout3=findViewById(R.id.linlay3);
        linearLayout4=findViewById(R.id.linlay4);
        linearLayout5=findViewById(R.id.linlay5);
        IB_back=findViewById(R.id.back_ima_scedule);
        IB_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textViewaddmore=findViewById(R.id.add_more_schedule);
        textViewcancel=findViewById(R.id.cancel_schedule);

buttonaddtime=findViewById(R.id.add_time_slots);

        imageButtonclose2=findViewById(R.id.time_slot_close2);
        imageButtonclose3=findViewById(R.id.time_slot_close3);
        imageButtonclose4=findViewById(R.id.time_slot_close4);
        imageButtonclose5=findViewById(R.id.time_slot_close5);

imageButton=findViewById(R.id.time_slot_close);
        starttime=new ArrayList<>();
         starttime2=new ArrayList<>();
      starttime3=new ArrayList<>();
       starttime4=new ArrayList<>();
        starttime5=new ArrayList<>();
         endtime=new ArrayList<>();
         endtime2=new ArrayList<>();
         endtime3=new ArrayList<>();
       endtime4=new ArrayList<>();
        endtime5=new ArrayList<>();
        final Calendar defaultSelectedDate = Calendar.getInstance();
        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(6)
                .defaultSelectedDate(defaultSelectedDate)
                .build();
selectedDateStr=DateFormat.format("yyyy-MM-dd", defaultSelectedDate).toString();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                 selectedDateStr = DateFormat.format("yyyy-MM-dd", date).toString();

            }
        });
        textViewstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpd.setTitle("Start Time");
                tpd.show(getFragmentManager(), "timestart");
                starttimetpd="1";
            }
        });

        textViewend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpd.setTitle("End Time");
                tpd.show(getFragmentManager(), "timeend");
                starttimetpd="6";
            }
        });

        textViewstart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starttimetpd="2";
                tpd.setTitle("Start Time");
                tpd.show(getFragmentManager(), "start2");
            }
        });
      textViewend2.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              starttimetpd="7";
              tpd.setTitle("End Time");
              tpd.show(getFragmentManager(), "end2");
          }
      });

      textViewstart3.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              starttimetpd="3";
              tpd.setTitle("End Time");
              tpd.show(getFragmentManager(), "start3");
          }
      });
      textViewend3.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              starttimetpd="8";
              tpd.setTitle("End Time");
              tpd.show(getFragmentManager(), "end3");
          }
      });

      textViewstart4.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              starttimetpd="4";
              tpd.setTitle("Start Time");
              tpd.show(getFragmentManager(), "start4");
          }
      });

      textViewend4.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              starttimetpd="9";
              tpd.setTitle("End Time");
              tpd.show(getFragmentManager(), "end4");

          }
      });

      textViewstart5.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              starttimetpd="5";
              tpd.setTitle("Start Time");
              tpd.show(getFragmentManager(), "start5");
          }
      });
      textViewend5.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              starttimetpd="10";
              tpd.setTitle("End Time");
              tpd.show(getFragmentManager(), "end5");

          }
      });






      imageButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              textViewstart.setText("");
              textViewend.setText("");
          }
      });


imageButtonclose2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        linearLayout2.setVisibility(View.GONE);

    }
});

        imageButtonclose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout3.setVisibility(View.GONE);

            }
        });
        imageButtonclose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout4.setVisibility(View.GONE);

            }
        });
        imageButtonclose5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout5.setVisibility(View.GONE);

            }
        });
        textViewaddmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickcount=clickcount+1;
                if (clickcount==1)
                {
                    linearLayout2.setVisibility(View.VISIBLE);

                }
                if (clickcount==2)
                {
                    linearLayout3.setVisibility(View.VISIBLE);
                }
                if (clickcount==3)
                {
                    linearLayout4.setVisibility(View.VISIBLE);
                }
                if (clickcount==4)
                {
                    clickcount=0;
                    linearLayout5.setVisibility(View.VISIBLE);
                }
            }
        });

 textViewcancel.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         linearLayout2.setVisibility(View.GONE);
         linearLayout3.setVisibility(View.GONE);
         linearLayout4.setVisibility(View.GONE);
         linearLayout5.setVisibility(View.GONE);

     }
 });



 buttonaddtime.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {

   String stringsart=textViewstart.getText().toString();
         String stringsart2=textViewstart2.getText().toString();
         String stringsart3=textViewstart3.getText().toString();
         String stringsart4=textViewstart4.getText().toString();
         String stringsart5=textViewstart5.getText().toString();
         String stringend=textViewend.getText().toString();
         String stringend2=textViewend2.getText().toString();
         String stringend3=textViewend3.getText().toString();
         String stringend4=textViewend4.getText().toString();
         String stringend5=textViewend5.getText().toString();

if (!(stringend.isEmpty())) {
    starttime.add(selectedDateStr + "T" + stringsart + "+03:00");
    endtime.add(selectedDateStr + "T" + stringend + "+03:00");

}
         if (!(stringend2.isEmpty())) {
             starttime.add(selectedDateStr + "T" + stringsart2 + "+03:00");
             endtime.add(selectedDateStr + "T" + stringend2 + "+03:00");
         }
         if (!(stringend3.isEmpty())) {
             starttime.add(selectedDateStr + "T" + stringsart3 + "+03:00");
             endtime.add(selectedDateStr + "T" + stringend3 + "+03:00");

         }
         if (!(stringend4.isEmpty())) {
             starttime.add(selectedDateStr + "T" + stringsart4 + "+03:00");
             endtime.add(selectedDateStr + "T" + stringend4 + "+03:00");
         }
         if (!(stringend5.isEmpty())) {
             starttime.add(selectedDateStr + "T" + stringsart5 + "+03:00");
             endtime.add(selectedDateStr + "T" + stringend5 + "+03:00");
         }

         TinyDB tinydb = new TinyDB(context);
         tinydb.putListString("starttime", (ArrayList<String>) starttime);
         tinydb.putListString("endtime", (ArrayList<String>) endtime);
       onBackPressed();

     }
 });

        Intent intent = getIntent();
        Bundle Bintent = intent.getExtras();
        if(Bintent != null) {
         String   subname = (String) Bintent.get("sub_name");
            TVsub_name.setText(subname);
        }
    }






    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hoursday,minuteofday,secondsofday;
        if(hourOfDay<9)
        {
 hoursday="0"+hourOfDay;
        }
        else
        {
            hoursday= Integer.toString( hourOfDay );
        }
        if(minute<9)
        {
            minuteofday="0"+minute;
        }
        else
        {
            minuteofday= Integer.toString( minute );
        }
        if (second<9)
        {
            secondsofday="0"+second;

        }
        else {
            secondsofday= Integer.toString( second );

        }
        String time = hoursday+":"+minuteofday+":"+secondsofday;

      if (starttimetpd.equals("1"))
      {


        textViewstart.setText(time);

      }

        if (starttimetpd.equals("2"))
        {
            textViewstart2.setText(time);
        }
        if (starttimetpd.equals("3"))
        {
            textViewstart3.setText(time);
        }
        if (starttimetpd.equals("4"))
        {
            textViewstart4.setText(time);
        }
        if (starttimetpd.equals("5"))
        {
            textViewstart5.setText(time);
        }

        if (starttimetpd.equals("6"))
        {
            textViewend.setText(time);
        }
        if (starttimetpd.equals("7"))
        {
            textViewend2.setText(time);
        }
        if (starttimetpd.equals("8"))
        {
            textViewend3.setText(time);
        }
        if (starttimetpd.equals("9"))
        {
            textViewend4.setText(time);
        }
        if (starttimetpd.matches("10"))
        {
            textViewend5.setText(time);
        }

   }



}
