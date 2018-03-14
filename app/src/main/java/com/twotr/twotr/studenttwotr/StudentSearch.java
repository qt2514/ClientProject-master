package com.twotr.twotr.studenttwotr;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twotr.twotr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentSearch extends Fragment {


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
        return inflater.inflate(R.layout.fragment_student_search, container, false);
    }

}
