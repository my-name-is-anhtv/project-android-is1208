package com.example.myproject_is1208.slide;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject_is1208.MainActivity;
import com.example.myproject_is1208.R;
import com.example.myproject_is1208.question.Question;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScreenSlidePageFragment extends Fragment {

    private  int mPageNumber;
    private ArrayList<Question> questionArrayList;
    private TextView tvNum , tvQuestion;
    private RadioGroup radioGroup;
    private RadioButton radioButtonA , radioButtonB , radioButtonC , radioButtonD;
    public int checks;//check true answer

    public ScreenSlidePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        tvNum = rootView.findViewById(R.id.tvNum);
        tvQuestion = rootView.findViewById(R.id.tvQuestion);
        radioButtonA = rootView.findViewById(R.id.radA);
        radioButtonB = rootView.findViewById(R.id.radB);
        radioButtonC = rootView.findViewById(R.id.radC);
        radioButtonD = rootView.findViewById(R.id.radD);
        radioGroup = rootView.findViewById(R.id.radGroup);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenSlideActivity slideActivity = (ScreenSlideActivity)getActivity();
        questionArrayList = new ArrayList<Question>();
        questionArrayList = slideActivity.getData();
        mPageNumber = getArguments().getInt("page");
        checks = getArguments().getInt("checkAns");
    }

    public static ScreenSlidePageFragment create(int pageNumber, int checkAns){
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt("page" , pageNumber);
        args.putInt("checkAns",checkAns);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvNum.setText("Question " + (mPageNumber + 1));
        tvQuestion.setText(questionArrayList.get(mPageNumber).getQuestion());
        radioButtonA.setText(getItem(mPageNumber).getAns_a());
        radioButtonB.setText(getItem(mPageNumber).getAns_b());
        radioButtonC.setText(getItem(mPageNumber).getAns_c());
        radioButtonD.setText(getItem(mPageNumber).getAns_d());
        if(checks != 0){
            radioButtonA.setClickable(false);
            radioButtonB.setClickable(false);
            radioButtonC.setClickable(false);
            radioButtonD.setClickable(false);
            checkTrueAns(getItem(mPageNumber).getResult());
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                getItem(mPageNumber).choice_id = i;
                getItem(mPageNumber).setQuestionAnswer(getChoice(i));
            }
        });
    }
    public Question getItem(int i){
        return questionArrayList.get(i);
    }
    //get index of radio to a,b,c,d
    private String getChoice(int id){
        if(id == R.id.radA){
            return "A";
        } else if(id == R.id.radB){
            return "B";
        } else if(id == R.id.radC){
            return "C";
        } else if(id == R.id.radD){
            return "D";
        } else{
            return "";
        }
    }
    //check true answer, set background for them
    private void checkTrueAns(String ans){
        if(ans.equals("A") == true){
            radioButtonA.setBackgroundColor(Color.RED);
        } else if(ans.equals("B") == true){
            radioButtonB.setBackgroundColor(Color.RED);
        } else if(ans.equals("C") == true){
            radioButtonC.setBackgroundColor(Color.RED);
        } else if(ans.equals("D") == true){
            radioButtonD.setBackgroundColor(Color.RED);
        }
    }
}
