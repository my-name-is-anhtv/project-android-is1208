package com.example.myproject_is1208.course;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myproject_is1208.MainActivity;
import com.example.myproject_is1208.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BiologicalFragment extends Fragment {


    public BiologicalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("biological");
        return inflater.inflate(R.layout.fragment_biological, container, false);
    }

}
