package com.example.myproject_is1208.slide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myproject_is1208.R;
import com.example.myproject_is1208.question.Question;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CheckAnswerAdapter extends BaseAdapter {

    ArrayList listData;
    LayoutInflater layoutInflater;

    public CheckAnswerAdapter(ArrayList listData, Context context) {
        this.listData = listData;
        layoutInflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent) {
        Question question = (Question) getItem(position);
        ViewHolder viewHolder;
        if( convertView == null ){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_gridview_list_answer, null);
            viewHolder.tvAnswer = (TextView) convertView.findViewById(R.id.tvAnswer);
            viewHolder.tvNumAnswer = (TextView) convertView.findViewById(R.id.tvNumAnswer);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int i = position + 1;
        viewHolder.tvNumAnswer.setText("Question " + i + " :");
        viewHolder.tvAnswer.setText(question.getQuestionAnswer());
        //21p: 21
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder{
        TextView tvNumAnswer , tvAnswer;

    }
}
