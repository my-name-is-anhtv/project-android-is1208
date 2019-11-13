package com.example.myproject_is1208.slide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.myproject_is1208.R;
import com.example.myproject_is1208.question.MyDBHelper;
import com.example.myproject_is1208.question.Question;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ScreenSlideActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static int NUM_PAGES = 8;
    String subject;
    int num_exam;
    TextView tvTimer;
    TextView ViewPoint;
    CounterClass timer;
    int totalTimer;
    public int checkAns = 0;
    String test = "";
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;
    private  ArrayList<Question> questionArrayList;
    MyDBHelper myDBHelper;
    TextView tvTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());
        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        num_exam = intent.getIntExtra("num_exam",0);
        test = intent.getStringExtra("test");

        questionArrayList = new ArrayList<Question>();
            myDBHelper = new MyDBHelper(this, "csdl1.db",null,10);

            String strSQL = "SELECT * FROM question WHERE subject='"+subject+"' ORDER BY random()";
            Cursor cursor = myDBHelper.selectData(strSQL);
            cursor.moveToFirst();
            String a = cursor.getString(0);
            do {
                Question item;
                item= new Question(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getInt(7),cursor.getString(8),cursor.getString(9),"");
                questionArrayList.add(item);
            }while (cursor.moveToNext());
        //Click for check the point
        ViewPoint = findViewById(R.id.tvScore);
        ViewPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intentViewPoint = new Intent(ScreenSlideActivity.this , TestDoneActivity.class);
                intentViewPoint.putExtra("questionArrayList", questionArrayList);
                startActivity(intentViewPoint);
            }
        });

        tvTest  = findViewById(R.id.tvKiemTra);
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
        totalTimer = 10;//change time
        timer = new CounterClass(totalTimer * 60 * 1000,1000);
        tvTimer = findViewById(R.id.tvTimer);
        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        timer.start();

    }

    public void exitDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(ScreenSlideActivity.this);
        builder.setIcon(R.drawable.exit);
        builder.setTitle("Information Message");
        builder.setMessage("Do you wanna to quit???");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer.cancel();
                finish();
            }
        });
        builder.show();
    }

    public ArrayList<Question> getData() {
        return questionArrayList;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            exitDialog();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Send the position of page to fragment
            return ScreenSlidePageFragment.create(position,checkAns);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

    public void checkAnswer(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.check_answer_dialog);
        dialog.setTitle("List of answers");
        CheckAnswerAdapter answerAdapter = new CheckAnswerAdapter(questionArrayList,this);
        GridView gvListQuestion = dialog.findViewById(R.id.gridViewQuestion);
        gvListQuestion.setAdapter(answerAdapter);
        gvListQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                mPager.setCurrentItem(position);
                dialog.dismiss();
            }
        });
        Button btnCancel, btnFinish;
        btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnFinish = (Button) dialog.findViewById(R.id.btnFinish);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                result();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void result(){
        checkAns = 1;
        if(mPager.getCurrentItem() >= 5 ) mPager.setCurrentItem(mPager.getCurrentItem() - 4);
        else if(mPager.getCurrentItem() <= 5) mPager.setCurrentItem(mPager.getCurrentItem() + 4);
        ViewPoint.setVisibility(View.VISIBLE);
        tvTest.setVisibility(View.GONE);
    }
    //time to test
    public class CounterClass extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */


        //millisInFuture: 60*1000
        //countDownInterval:  1000
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String countTime = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            tvTimer.setText(countTime); //SetText cho textview hiện thị thời gian.
        }

        @Override
        public void onFinish() {
            tvTimer.setText("00:00");  //SetText cho textview hiện thị thời gian.
            //result();
        }
    }
}
