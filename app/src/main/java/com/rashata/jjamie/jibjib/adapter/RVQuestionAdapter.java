package com.rashata.jjamie.jibjib.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rashata.jjamie.jibjib.R;
import com.rashata.jjamie.jibjib.activity.ViewTranslationActivity;
import com.rashata.jjamie.jibjib.serializer.Question;

import java.util.ArrayList;

/**
 * Created by JJamie on 4/10/16 AD.
 */
public class RVQuestionAdapter extends RecyclerView.Adapter<RVQuestionAdapter.QuestionViewHolder> {
    private static final String TAG = "RVQuestionAdapter";
    ArrayList<Question> questions;
    Context mContext;
    String token;

    public RVQuestionAdapter(ArrayList<Question> questions, Context context, String token) {
        this.questions = questions;
        this.mContext = context;
        this.token = token;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_question_en_th, viewGroup, false);
        QuestionViewHolder qvh = new QuestionViewHolder(v);
        return qvh;
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder qvh, final int position) {
        /*
        switch (questions.get(position).type) {
            case Question.TYPE_BLUE:
                qvh.linearLyt_item_header.setBackgroundResource(R.color.blueSky);
                qvh.linearLyt_item_footer.setBackgroundResource(R.color.blueSkyDark);
            case Question.TYPE_PINK:
                qvh.linearLyt_item_header.setBackgroundResource(R.color.pink);
                qvh.linearLyt_item_footer.setBackgroundResource(R.color.pinkDark);
        }
        */
        qvh.txt_to_language.setText(changeToAbbreviation(questions.get(position).getTo_lang()));


        qvh.txt_from_language.setText(changeToAbbreviation(questions.get(position).getFrom_lang()));
        qvh.txt_number_of_answers.setText(questions.get(position).getCount_ans());
//        qvh.txt_number_of_views.setText(questions.get(position).number_of_views);
        qvh.txt_number_of_votes.setText(questions.get(position).getCount_vote());
        qvh.txt_item_title.setText(questions.get(position).getTitle());
        qvh.txt_item_description.setText(questions.get(position).getContent());
        qvh.txt_time_created.setText(questions.get(position).get_date());
        qvh.txt_name_user_created.setText(questions.get(position).getOwner());

        qvh.rel_item_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ViewTranslationActivity.class);
                intent.putExtra("idQuestion", questions.get(position).getId());
                intent.putExtra("token", token);
                mContext.startActivity(intent);
            }
        });
    }

    public String changeToAbbreviation(String lang) {
        switch (lang) {
            case "Thai":
                return "TH";
            case "English":
                return "EN";
            case "Chinese":
                return "CH";
            default:
                return "";
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLyt_item_header;
        LinearLayout linearLyt_item_footer;
        TextView txt_to_language;
        TextView txt_from_language;
        TextView txt_number_of_answers;
        TextView txt_number_of_views;
        TextView txt_number_of_votes;
        TextView txt_item_title;
        TextView txt_item_description;
        TextView txt_time_created;
        TextView txt_name_user_created;
        RelativeLayout rel_item_que;

        QuestionViewHolder(View itemView) {
            super(itemView);
            linearLyt_item_header = (LinearLayout) itemView.findViewById(R.id.linearLyt_item_header);
            linearLyt_item_footer = (LinearLayout) itemView.findViewById(R.id.linearLyt_item_footer);
            txt_to_language = (TextView) itemView.findViewById(R.id.txt_to_language);
            txt_from_language = (TextView) itemView.findViewById(R.id.txt_from_language);
            txt_number_of_answers = (TextView) itemView.findViewById(R.id.txt_number_of_answers);
            txt_number_of_views = (TextView) itemView.findViewById(R.id.txt_number_of_views);
            txt_number_of_votes = (TextView) itemView.findViewById(R.id.txt_number_of_votes);
            txt_item_title = (TextView) itemView.findViewById(R.id.txt_item_title);
            txt_item_description = (TextView) itemView.findViewById(R.id.txt_item_description);
            txt_time_created = (TextView) itemView.findViewById(R.id.txt_time_created);
            txt_name_user_created = (TextView) itemView.findViewById(R.id.txt_name_user_created);
            rel_item_que = (RelativeLayout) itemView.findViewById(R.id.rel_item_question);
        }
    }
}
