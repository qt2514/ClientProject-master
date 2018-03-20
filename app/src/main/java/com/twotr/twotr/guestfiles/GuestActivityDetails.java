package com.twotr.twotr.guestfiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.picasso.Picasso;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
import com.twotr.twotr.studenttwotr.StudentBookings;
import com.twotr.twotr.studenttwotr.StudentDashboard;
import com.twotr.twotr.studenttwotr.StudentSearch;
import com.twotr.twotr.studenttwotr.StudentSettings;
import com.twotr.twotr.tutorfiles.ScheduleDetailPage;
import com.twotr.twotr.tutorfiles.Schedule_ShowMap;
import com.twotr.twotr.tutorfiles.Schedule_ShowSchedule;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class GuestActivityDetails extends AppCompatActivity {
    HorizontalCalendar horizontalCalendar;
    TimePickerDialog tpd;
    List<String> starttimeschednew;
    List<String> endtimeschednew;
    Context context;
    String Sstarttime,Sstarttime2,Sstarttime3,Sstarttime4,Sstarttime5,Sendtime,Sendtime2,Sendtime3,Sendtime4,Sendtime5;
    String checkstarttime,checkstarttime2,checkstarttime3,checkstarttime4,checkstarttime5,checkendtime,checkendtime2,checkendtime3
            ,checkendtime4,checkendtime5;
    RelativeLayout relativeLayout;
    TextView textView,textView2,textView3,textView4,textView5,textViewend,textViewend2,textViewend3,textViewend4,textViewend5,Tvprofname;
    LinearLayout linearLayout,linearLayout2,linearLayout3,linearLayout4,linearLayout5,linearLayouthead;
    String selectedDateStr ;
    String checkselectdate;
    String checkselectdate2;
    String checkselectdate3;
    String checkselectdate4;
    String checkselectdate5;
    ImageButton IB_back;
    String aac ;
    String bbc;
    String aac2;
    String bbc2;
    String aac3;
    String bbc3;
    String aac4 ;
    String bbc4;
    String aac5 ;
    String bbc5;
    String subjectname,subjecttype,subjecttime,Slati,Slongi,Susername,Screateid,Simageurl,checkevendate,reccoeventdate;
    Button Bmap_show;
CircleImageView profileimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_details);

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
        linearLayouthead=findViewById(R.id.linearLayout5);
Bmap_show=findViewById(R.id.map_show);
        IB_back=findViewById(R.id.back_ima_scedule);
Tvprofname=findViewById(R.id.prof_prof_name);
profileimage=findViewById(R.id.image_profile);
        starttimeschednew=new ArrayList<>();
        endtimeschednew=new ArrayList<>();
        context=GuestActivityDetails.this;
        final Intent intent = getIntent();
        Bundle Bintent = intent.getExtras();
        if(Bintent != null) {
            starttimeschednew= Bintent.getStringArrayList("starttime");
            endtimeschednew= Bintent.getStringArrayList("endtime");
            subjectname= Bintent.getString("subjectname");
            subjecttype= Bintent.getString("typesub");
            subjecttime= Bintent.getString("hrstot");
            Slati= Bintent.getString("latitude");
            Slongi= Bintent.getString("longitude");
Susername=Bintent.getString("createdbyname");
Screateid=Bintent.getString("createdby");
            Simageurl=Bintent.getString("imgurl");


        }
        Picasso
                .with(context)
                .load(Global_url_twotr.Image_Base_url+Simageurl)
                .fit()
                .error(getResources().getDrawable(R.drawable.profile_image_tutor))
                .centerCrop()
                .into( profileimage);
        linearLayouthead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcrea=new Intent(GuestActivityDetails.this,GuestProfile.class);
                intentcrea.putExtra("createdby",Screateid);
                startActivity(intentcrea);
            }
        });
Tvprofname.setText(Susername);
        IB_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bmap_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GuestActivityDetails.this, Schedule_ShowMap.class);
                intent.putExtra("latitude", Slati);
                intent.putExtra("longitude", Slongi);
                intent.putExtra("subjectname", subjectname);
                intent.putExtra("subjecttype", subjecttype);
                intent.putExtra("subjecthours", subjecttime);
                startActivity(intent);

//                Toast.makeText(ScheduleDetailPage.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(ScheduleDetailPage.this,Schedule_ShowMap.class));//
            }
        });

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 0);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, 25);
        final Calendar defaultSelectedDate = Calendar.getInstance();
        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .defaultSelectedDate(defaultSelectedDate)
                .addEvents(new CalendarEventsPredicate() {
                    @Override
                    public List<CalendarEvent> events(Calendar date) {

                        List<CalendarEvent> events = new ArrayList<>();

                        checkevendate=DateFormat.format("MM-dd-yyyy", date).toString();

                        Sstarttime = starttimeschednew.get(0);
                        reccoeventdate = DateTimeUtils.formatWithPattern(Sstarttime, "MM-dd-yyyy");
                        if (checkevendate.matches(reccoeventdate)) {

                            events.add(new CalendarEvent(getResources().getColor(R.color.colorPrimaryDark), "event"));

                        }


                        return events;
                    }
                })
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                SimpleDateFormat inFormat = new SimpleDateFormat("HH:mm");
                SimpleDateFormat outFormat = new SimpleDateFormat("hh:mm aa");
                relativeLayout.setVisibility(View.INVISIBLE);
                selectedDateStr = DateFormat.format("MM-dd-yyyy", date).toString();
                if (starttimeschednew.size()==1)
                {
                    Sstarttime = starttimeschednew.get(0);
                    Sendtime = endtimeschednew.get(0);
                    checkselectdate = DateTimeUtils.formatWithPattern(Sstarttime, "MM-dd-yyyy");
                    if (checkselectdate.matches(selectedDateStr)) {



                        checkstarttime=Sstarttime.substring(11,16);
                        checkendtime=Sendtime.substring(11,16);
                        try {
                            aac = outFormat.format(inFormat.parse(checkstarttime));
                            bbc = outFormat.format(inFormat.parse(checkendtime));

                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        relativeLayout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                        textView.setText(aac);
                        textViewend.setText(bbc);
                    }
                    else
                    {
                        relativeLayout.setVisibility(View.INVISIBLE);

                    }

//                    if (!Slongi.isEmpty())
//                    {
//                        Bmap_show.setVisibility(View.VISIBLE);
//                    }
//                    else
//                    {
//                        Bmap_show.setVisibility(View.GONE);
//                    }



                }

                else if (starttimeschednew.size()==2)
                {
                    Sstarttime = starttimeschednew.get(0);
                    Sstarttime2 = starttimeschednew.get(1);
                    Sendtime = endtimeschednew.get(0);
                    Sendtime2 = endtimeschednew.get(1);
                    checkselectdate = DateTimeUtils.formatWithPattern(Sstarttime, "MM-dd-yyyy");

                    checkselectdate2 = DateTimeUtils.formatWithPattern(Sstarttime2, "MM-dd-yyyy");

                    if (checkselectdate.matches(selectedDateStr)) {



                        checkstarttime=Sstarttime.substring(11,16);
                        checkendtime=Sendtime.substring(11,16);
                        checkstarttime2=Sstarttime2.substring(11,16);
                        checkendtime2=Sendtime2.substring(11,16);

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
                        relativeLayout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout2.setVisibility(View.VISIBLE);
                        textView.setText(aac);
                        textViewend.setText(bbc);
                        textView2.setText(aac2);
                        textViewend2.setText(bbc2);
                    }
                    else
                    {
                        relativeLayout.setVisibility(View.INVISIBLE);

                    }
//                    if (!Slongi.isEmpty())
//                    {
//                        Bmap_show.setVisibility(View.VISIBLE);
//                    }
//                    else
//                    {
//                        Bmap_show.setVisibility(View.GONE);
//                    }



                }
                else if(starttimeschednew.size()==3)
                {
                    Sstarttime = starttimeschednew.get(0);
                    Sstarttime2 = starttimeschednew.get(1);
                    Sendtime = endtimeschednew.get(0);
                    Sendtime2 = endtimeschednew.get(1);
                    Sstarttime3 = starttimeschednew.get(2);
                    Sendtime3 = endtimeschednew.get(2);

                    checkselectdate = DateTimeUtils.formatWithPattern(Sstarttime, "MM-dd-yyyy");

                    checkselectdate2 = DateTimeUtils.formatWithPattern(Sstarttime2, "MM-dd-yyyy");

                    checkselectdate3 = DateTimeUtils.formatWithPattern(Sstarttime3, "MM-dd-yyyy");

                    if (checkselectdate.matches(selectedDateStr)) {



                        checkstarttime=Sstarttime.substring(11,16);
                        checkendtime=Sendtime.substring(11,16);
                        checkstarttime2=Sstarttime2.substring(11,16);
                        checkendtime2=Sendtime2.substring(11,16);
                        checkstarttime3=Sstarttime3.substring(11,16);
                        checkendtime3=Sendtime3.substring(11,16);

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
                        relativeLayout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout2.setVisibility(View.VISIBLE);
                        linearLayout3.setVisibility(View.VISIBLE);
                        textView.setText(aac);
                        textViewend.setText(bbc);
                        textView2.setText(aac2);
                        textViewend2.setText(bbc2);
                        textView3.setText(aac3);
                        textViewend3.setText(bbc3);
                    }
                    else
                    {
                        relativeLayout.setVisibility(View.INVISIBLE);

                    }
//                    if (!Slongi.isEmpty())
//                    {
//                        Bmap_show.setVisibility(View.VISIBLE);
//                    }
//                    else
//                    {
//                        Bmap_show.setVisibility(View.GONE);
//                    }


                }
                else if (starttimeschednew.size()==4)
                {
                    Sstarttime = starttimeschednew.get(0);
                    Sstarttime2 = starttimeschednew.get(1);
                    Sendtime = endtimeschednew.get(0);
                    Sendtime2 = endtimeschednew.get(1);
                    Sstarttime3 = starttimeschednew.get(2);
                    Sendtime3 = endtimeschednew.get(2);
                    Sstarttime4 = starttimeschednew.get(3);
                    Sendtime4 = endtimeschednew.get(3);

                    checkselectdate = DateTimeUtils.formatWithPattern(Sstarttime, "MM-dd-yyyy");

                    checkselectdate2 = DateTimeUtils.formatWithPattern(Sstarttime2, "MM-dd-yyyy");

                    checkselectdate3 = DateTimeUtils.formatWithPattern(Sstarttime3, "MM-dd-yyyy");

                    checkselectdate4 = DateTimeUtils.formatWithPattern(Sstarttime4, "MM-dd-yyyy");

                    if (checkselectdate.matches(selectedDateStr)) {



                        checkstarttime=Sstarttime.substring(11,16);
                        checkendtime=Sendtime.substring(11,16);
                        checkstarttime2=Sstarttime2.substring(11,16);
                        checkendtime2=Sendtime2.substring(11,16);
                        checkstarttime3=Sstarttime3.substring(11,16);
                        checkendtime3=Sendtime3.substring(11,16);
                        checkstarttime4=Sstarttime4.substring(11,16);
                        checkendtime4=Sendtime4.substring(11,16);

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
                        relativeLayout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout2.setVisibility(View.VISIBLE);
                        linearLayout3.setVisibility(View.VISIBLE);
                        linearLayout4.setVisibility(View.VISIBLE);
                        textView.setText(aac);
                        textViewend.setText(bbc);
                        textView2.setText(aac2);
                        textViewend2.setText(bbc2);
                        textView3.setText(aac3);
                        textViewend3.setText(bbc3);
                        textView4.setText(aac4);
                        textViewend4.setText(bbc4);
                    }
                    else
                    {
                        relativeLayout.setVisibility(View.INVISIBLE);

                    }

//                    if (!Slongi.isEmpty())
//                    {
//                        Bmap_show.setVisibility(View.VISIBLE);
//                    }
//                    else
//                    {
//                        Bmap_show.setVisibility(View.GONE);
//                    }


                }
                else if (starttimeschednew.size()==5)
                {
                    Sstarttime = starttimeschednew.get(0);
                    Sstarttime2 = starttimeschednew.get(1);
                    Sendtime = endtimeschednew.get(0);
                    Sendtime2 = endtimeschednew.get(1);
                    Sstarttime3 = starttimeschednew.get(2);
                    Sendtime3 = endtimeschednew.get(2);
                    Sstarttime4 = starttimeschednew.get(3);
                    Sendtime4 = endtimeschednew.get(3);
                    Sstarttime5 = starttimeschednew.get(4);
                    Sendtime5 = endtimeschednew.get(4);

                    checkselectdate = DateTimeUtils.formatWithPattern(Sstarttime, "MM-dd-yyyy");
                    checkselectdate2 = DateTimeUtils.formatWithPattern(Sstarttime2, "MM-dd-yyyy");
                    checkselectdate3 = DateTimeUtils.formatWithPattern(Sstarttime3, "MM-dd-yyyy");
                    checkselectdate4 = DateTimeUtils.formatWithPattern(Sstarttime4, "MM-dd-yyyy");
                    checkselectdate5 = DateTimeUtils.formatWithPattern(Sstarttime5, "MM-dd-yyyy");
                    if (checkselectdate.matches(selectedDateStr)) {



                        checkstarttime=Sstarttime.substring(11,16);
                        checkendtime=Sendtime.substring(11,16);
                        checkstarttime2=Sstarttime2.substring(11,16);
                        checkendtime2=Sendtime2.substring(11,16);
                        checkstarttime3=Sstarttime3.substring(11,16);
                        checkendtime3=Sendtime3.substring(11,16);
                        checkstarttime4=Sstarttime4.substring(11,16);
                        checkendtime4=Sendtime4.substring(11,16);
                        checkstarttime5=Sstarttime5.substring(11,16);
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
                        relativeLayout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout2.setVisibility(View.VISIBLE);
                        linearLayout3.setVisibility(View.VISIBLE);
                        linearLayout4.setVisibility(View.VISIBLE);
                        linearLayout5.setVisibility(View.VISIBLE);
                        textView.setText(aac);
                        textViewend.setText(bbc);
                        textView2.setText(aac2);
                        textViewend2.setText(bbc2);
                        textView3.setText(aac3);
                        textViewend3.setText(bbc3);
                        textView4.setText(aac4);
                        textViewend4.setText(bbc4);
                        textView5.setText(aac5);
                        textViewend5.setText(bbc5);
                    }
                    else
                    {
                        relativeLayout.setVisibility(View.INVISIBLE);

                    }
//                    if (!Slongi.isEmpty())
//                    {
//                        Bmap_show.setVisibility(View.VISIBLE);
//                    }
//                    else
//                    {
//                        Bmap_show.setVisibility(View.GONE);
//                    }
//

                }
                else
                {
                    Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

}
