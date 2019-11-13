package com.example.myproject_is1208.slide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject_is1208.R;
import com.example.myproject_is1208.question.Question;
import com.example.myproject_is1208.score.ScoreController;

import java.util.ArrayList;

public class TestDoneActivity extends AppCompatActivity {
    int numNoAnswer = 0;
    int numTrue = 0;
    int numFalse = 0;
    TextView tvTrue,tvFalse,tvNoAnswer,tvTotalPoint;
    Button btnSaveScore, btnExit;
    ArrayList<Question> questionArrayList = new ArrayList<Question>();
    ScoreController scoreController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_done);
        Intent intent = getIntent();
        scoreController = new ScoreController(this);
        questionArrayList = (ArrayList<Question>) intent.getExtras().getSerializable("questionArrayList");
        begin();
        checkResult();
        tvFalse.setText("" + numFalse);
        tvTrue.setText( "" + numTrue);
        tvNoAnswer.setText( "" + numNoAnswer);
        tvTotalPoint.setText("" + numTrue);
        btnSaveScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(TestDoneActivity.this);
                LayoutInflater inflater=TestDoneActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.allert_dialog_save_score,null);
                builder.setView(view);

                final EditText edtName= (EditText) view.findViewById(R.id.edtName);
                final EditText edtRoom= (EditText) view.findViewById(R.id.edtRoom);
                TextView tvScore= (TextView) view.findViewById(R.id.tvScore);
                final int numTotal= numTrue*10;
                tvScore.setText(numTotal+" điểm");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name= edtName.getText().toString();
                        String room= edtRoom.getText().toString();
                        scoreController.insertScore(name,numTotal,room);
                        Toast.makeText(TestDoneActivity.this, "Lưu điểm thành công!",Toast.LENGTH_LONG).show();
                        finish();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog b= builder.create();
                b.show();
            }
        });
    }



    //Click On Exit Button
    public void btnExitButtonOnClick(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(TestDoneActivity.this);
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

            }
        });
        builder.show();
    }

    public void  begin(){
        tvTrue = findViewById(R.id.tvTrue);
        tvFalse = findViewById(R.id.tvFalse);
        tvNoAnswer = findViewById(R.id.tvNotAnswer);
        tvTotalPoint = findViewById(R.id.tvTotalPoint);
        btnSaveScore=(Button)findViewById(R.id.btnSavePoint);
        btnExit=(Button)findViewById(R.id.btnExit);

    }


    public void checkResult() {
        for (int i = 0 ; i < questionArrayList.size() ; i ++){
            if(questionArrayList.get(i).getQuestionAnswer().equals("")){
                numNoAnswer ++;
            }else  if (questionArrayList.get(i).getResult().equals(questionArrayList.get(i).getQuestionAnswer())){
                numTrue ++;
            }else {
                numFalse ++;
            }
        }
    }
}
