package com.twotr.twotr.studenttwotr;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twotr.twotr.R;
import com.twotr.twotr.tutorfiles.TutorDashboard;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentBookings extends Fragment {


    public StudentBookings() {
        // Required empty public constructor
    }
    public static StudentBookings newInstance() {
        StudentBookings fragment= new StudentBookings();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_bookings, container, false);
    }

}
