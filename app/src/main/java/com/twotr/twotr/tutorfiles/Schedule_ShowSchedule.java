package com.twotr.twotr.tutorfiles;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.TinyDB;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class Schedule_ShowSchedule extends AppCompatActivity {
    HorizontalCalendar horizontalCalendar;
    TimePickerDialog tpd;
    List<String> starttimeschednew;
    List<String> endtimeschednew;
    Context context;
    String Sstarttime,Sstarttime2,Sstarttime3,Sstarttime4,Sstarttime5,Sendtime,Sendtime2,Sendtime3,Sendtime4,Sendtime5;
    String checkstarttime,checkstarttime2,checkstarttime3,checkstarttime4,checkstarttime5,checkendtime,checkendtime2,checkendtime3
            ,checkendtime4,checkendtime5;
RelativeLayout relativeLayout;
    TextView textView,textView2,textView3,textView4,textView5,textViewend,textViewend2,textViewend3,textViewend4,textViewend5;
    LinearLayout linearLayout,linearLayout2,linearLayout3,linearLayout4,linearLayout5;
    int e,s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__show_schedule);
        linearLayout=findViewById(R.id.linlay1);
        linearLayout2=findViewById(R.id.linlay2);
        linearLayout3=findViewById(R.id.linlay3);
        linearLayout4=findViewById(R.id.linlay4);
        linearLayout5=findViewById(R.id.linlay5);

        relativeLayout=findViewById(R.id.relaymast);

        textView=findViewById(R.id.starttime1);
        textView2=findViewById(R.id.starttime2);
        textView3=findViewById(R.id.starttime3);
        textView4=findViewById(R.id.starttime4);
        textView5=findViewById(R.id.starttime5);
        textViewend=findViewById(R.id.endtime1);
        textViewend2=findViewById(R.id.endtime2);
        textViewend3=findViewById(R.id.endtime3);
        textViewend4=findViewById(R.id.endtime4);
        textViewend5=findViewById(R.id.endtime5);


        starttimeschednew=new ArrayList<>();
        endtimeschednew=new ArrayList<>();
        context=Schedule_ShowSchedule.this;
        TinyDB tinydb = new TinyDB(context);
        starttimeschednew=   tinydb.getListString("starttimeschednew");
        endtimeschednew=   tinydb.getListString("endtimeschednew" );

Log.i("strat",starttimeschednew.toString());
        Log.i("endrat",endtimeschednew.toString());



        Sstarttime = starttimeschednew.get(0);
        Sstarttime2 = starttimeschednew.get(1);
        Sstarttime3 = starttimeschednew.get(2);
        Sstarttime4 = starttimeschednew.get(3);
        Sstarttime5 = starttimeschednew.get(4);

            Sendtime = endtimeschednew.get(0);
            Sendtime2 = endtimeschednew.get(1);
            Sendtime3 = endtimeschednew.get(2);
            Sendtime4 = endtimeschednew.get(3);
            Sendtime5 = endtimeschednew.get(4);

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 0);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, 25);
        final Calendar defaultSelectedDate = Calendar.getInstance();
        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(6)
                       .defaultSelectedDate(defaultSelectedDate)

                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                String selectedDateStr = DateFormat.format("MM-dd-yyyy", date).toString();
                Log.i("selectdate",selectedDateStr);
                String checkselectdate= DateTimeUtils.formatWithPattern(Sstarttime, "MM-dd-yyyy");
                Log.i("checkselectdate",checkselectdate);

                String checkselectdate2= DateTimeUtils.formatWithPattern(Sstarttime2, "MM-dd-yyyy");
                Log.i("checkselectdate2",checkselectdate2);

                String checkselectdate3= DateTimeUtils.formatWithPattern(Sstarttime3, "MM-dd-yyyy");
                Log.i("checkselectdate3",checkselectdate3);

                String checkselectdate4= DateTimeUtils.formatWithPattern(Sstarttime4, "MM-dd-yyyy");
                Log.i("checkselectdate4",checkselectdate4);

                String checkselectdate5= DateTimeUtils.formatWithPattern(Sstarttime5, "MM-dd-yyyy");
                Log.i("checkselectdate5",checkselectdate5);

                String aac = null;
                String bbc=null;
                String aac2 = null;
                String bbc2=null;
                String aac3 = null;
                String bbc3=null;
                String aac4 = null;
                String bbc4=null;
                String aac5 = null;
                String bbc5=null;
                SimpleDateFormat inFormat = new SimpleDateFormat("HH:mm");
                SimpleDateFormat outFormat = new SimpleDateFormat("hh:mm aa");

                 checkstarttime=Sstarttime.substring(11,16);
                checkstarttime2=Sstarttime2.substring(11,16);
                checkstarttime3=Sstarttime3.substring(11,16);
                checkstarttime4=Sstarttime4.substring(11,16);
                checkstarttime5=Sstarttime5.substring(11,16);
                checkendtime=Sendtime.substring(11,16);
                checkendtime2=Sendtime2.substring(11,16);
                checkendtime3=Sendtime3.substring(11,16);
                checkendtime4=Sendtime4.substring(11,16);
                checkendtime5=Sendtime5.substring(11,16);
                try {
                    aac = outFormat.format(inFormat.parse(checkstarttime));
                    bbc = outFormat.format(inFormat.parse(checkendtime));

                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                try {
                    aac2 = outFormat.format(inFormat.parse(checkstarttime2));
                    bbc2 = outFormat.format(inFormat.parse(checkendtime2));

                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                try {
                    aac3 = outFormat.format(inFormat.parse(checkstarttime3));
                    bbc3 = outFormat.format(inFormat.parse(checkendtime3));

                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                try {
                    aac4 = outFormat.format(inFormat.parse(checkstarttime4));
                    bbc4 = outFormat.format(inFormat.parse(checkendtime4));

                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                try {
                    aac5 = outFormat.format(inFormat.parse(checkstarttime5));
                    bbc5 = outFormat.format(inFormat.parse(checkendtime5));

                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                if (checkselectdate.matches(selectedDateStr))
{
    relativeLayout.setVisibility(View.VISIBLE);
    linearLayout2.setVisibility(View.GONE);
    linearLayout3.setVisibility(View.GONE);
    linearLayout4.setVisibility(View.GONE);
    linearLayout5.setVisibility(View.GONE);
    textView.setText(aac);
   textViewend.setText(bbc);
}
else
    {

                    relativeLayout.setVisibility(View.INVISIBLE);

                }


                if (checkselectdate2.matches(selectedDateStr))
                {
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout2.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);

                    linearLayout3.setVisibility(View.GONE);
                    linearLayout4.setVisibility(View.GONE);
                    linearLayout5.setVisibility(View.GONE);
                    textView2.setText(aac2);
                    textViewend2.setText(bbc2);
                }
                if (checkselectdate3.matches(selectedDateStr))
                {
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout3.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.GONE);

                    linearLayout4.setVisibility(View.GONE);
                    linearLayout5.setVisibility(View.GONE);
                    textView3.setText(aac3);
                    textViewend3.setText(bbc3);
                }
                if (checkselectdate4.matches(selectedDateStr))
                {
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.GONE);
                    linearLayout3.setVisibility(View.GONE);
                    linearLayout4.setVisibility(View.VISIBLE);
                    linearLayout5.setVisibility(View.GONE);
                    textView4.setText(aac4);
                    textViewend4.setText(bbc4);
                }
                if (checkselectdate5.matches(selectedDateStr))
                {
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.GONE);
                    linearLayout3.setVisibility(View.GONE);
                    linearLayout4.setVisibility(View.GONE);

                    linearLayout5.setVisibility(View.VISIBLE);
                    textView5.setText(aac5);
                    textViewend5.setText(bbc5);
                }

                //do something
            }
        });

    }

}
