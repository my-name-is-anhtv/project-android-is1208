package com.example.myproject_is1208.course;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.myproject_is1208.MainActivity;
import com.example.myproject_is1208.R;
import com.example.myproject_is1208.slide.ScreenSlideActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MathFragment extends Fragment {

    ExamAdapter examAdapter;
    GridView gvExam;
    ArrayList<Exam> arrExam = new ArrayList<Exam>();
    public MathFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Math");
        return inflater.inflate(R.layout.fragment_math, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gvExam = (GridView) getActivity().findViewById(R.id.gvSubject);
        arrExam.add(new Exam("Exam 1"));
        arrExam.add(new Exam("Exam 2"));
        arrExam.add(new Exam("Exam 3"));
        arrExam.add(new Exam("Exam 4"));
        arrExam.add(new Exam("Exam 5"));
        arrExam.add(new Exam("Exam 6"));
        arrExam.add(new Exam("Exam 7"));

        examAdapter = new ExamAdapter(getActivity() , arrExam);
        gvExam.setAdapter(examAdapter);
        gvExam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ScreenSlideActivity.class);
                intent.putExtra("num_exam",position + 1);
                intent.putExtra("subject","Math");
                intent.putExtra("test","yes");
                startActivity(intent);
            }
        });



    }
}
