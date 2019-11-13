package com.example.myproject_is1208.question;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.myproject_is1208.MainActivity;
import com.example.myproject_is1208.R;
import com.example.myproject_is1208.score.ScoreController;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    ListView lvQuestion;
    ScoreController question;
    QuestionAdapter adapter;
    EditText edtSearch;
    ImageButton imageButton;
    String subject = null;
    public void begin(){
        lvQuestion = getActivity().findViewById(R.id.lvQuestion);
        edtSearch = getActivity().findViewById(R.id.edtSearch);
        imageButton = getActivity().findViewById(R.id.imgSubject);
        question = new ScoreController(getActivity());
        listCursor(question.getSearchQuestion(subject,edtSearch.getText().toString()));
    }
    public void listCursor(Cursor cursor){
        adapter = new QuestionAdapter(getActivity(),cursor,true);
        lvQuestion.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Search");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        begin();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listCursor(question.getSearchQuestion(subject,edtSearch.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });
    }
    public void showMenu(View view){
        PopupMenu popupMenu = new PopupMenu(getActivity(),view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.QuesAll:
                        subject = "";
                        listCursor(question.getQuestionBySubject(subject));
                        break;
                    case R.id.QuesMath:
                        subject = "Math";
                        listCursor(question.getQuestionBySubject(subject));
                        break;
                    case R.id.QuesPhysical:
                        subject = "Physical";
                        listCursor(question.getQuestionBySubject(subject));
                        break;
                }
                return false;
            }
        });
        popupMenu.inflate(R.menu.menu_question);
        setForceShowIcon(popupMenu);
        popupMenu.show();
    }

    //Hien thi icon  pop up
    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
