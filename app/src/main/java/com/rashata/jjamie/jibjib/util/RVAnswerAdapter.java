package com.rashata.jjamie.jibjib.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rashata.jjamie.jibjib.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by JJamie on 4/10/16 AD.
 */
public class RVAnswerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_ANSWER = 2;
    private static final int VIEW_TYPE_POST_ANSWER = 3;
    Context context;
    ArrayList<Answer> answers;

    public RVAnswerAdapter(ArrayList<Answer> answers, Context context) {
        this.answers = answers;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return VIEW_TYPE_HEADER;
            case 4:
                return VIEW_TYPE_POST_ANSWER;
            default:
                return VIEW_TYPE_ANSWER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                View v_header = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_answer_header, viewGroup, false);
                HeaderViewHolder hvh = new HeaderViewHolder(v_header);
                return hvh;
            case VIEW_TYPE_POST_ANSWER:
                View v_post = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_answer_your_ans, viewGroup, false);
                PostViewHolder pvh = new PostViewHolder(v_post);
                return pvh;
            default:
                View v_answer = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_answer_listview, viewGroup, false);
                AnswerViewHolder avh = new AnswerViewHolder(v_answer);
                return avh;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder qvh, int position) {
        switch (position) {
            case 0:
                break;
            case 4:
                break;
            default:
                AnswerViewHolder avh = (AnswerViewHolder) qvh;
                Glide.with(context).load(R.drawable.profile)
                        .bitmapTransform(new CropCircleTransformation(context))
                        .into((avh.img_answer_profile_pic));
                break;
        }

        /*
        switch (questions.get(position).type) {
            case Question.TYPE_BLUE:
                qvh.linearLyt_item_header.setBackgroundResource(R.color.blueSky);
                qvh.linearLyt_item_footer.setBackgroundResource(R.color.blueSkyDark);
            case Question.TYPE_PINK:
                qvh.linearLyt_item_header.setBackgroundResource(R.color.pink);
                qvh.linearLyt_item_footer.setBackgroundResource(R.color.pinkDark);
        }
        qvh.txt_to_language.setText(questions.get(position).to_language);
        qvh.txt_from_language.setText(questions.get(position).from_language);
        qvh.txt_number_of_answers.setText(questions.get(position).number_of_answers);
        qvh.txt_number_of_views.setText(questions.get(position).number_of_views);
        qvh.txt_number_of_votes.setText(questions.get(position).number_of_votes);
        qvh.txt_item_title.setText(questions.get(position).item_title);
        qvh.txt_item_description.setText(questions.get(position).item_description);
        qvh.txt_time_created.setText(questions.get(position).time_created.toString());
        qvh.txt_name_user_created.setText(questions.get(position).name_user_created.toString());
        */
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {
        TextView txt_answer_name;
        TextView txt_answer_content;
        TextView txt_answer_number_of_votes;
        Button btn_answer_plus_vote;
        Button btn_answer_minus_vote;
        ImageView img_answer_profile_pic;


        AnswerViewHolder(View itemView) {
            super(itemView);
            txt_answer_name = (TextView) itemView.findViewById(R.id.txt_answer_name);
            txt_answer_content = (TextView) itemView.findViewById(R.id.txt_answer_content);
            txt_answer_number_of_votes = (TextView) itemView.findViewById(R.id.txt_answer_number_of_votes);
            btn_answer_plus_vote = (Button) itemView.findViewById(R.id.btn_answer_plus_vote);
            btn_answer_minus_vote = (Button) itemView.findViewById(R.id.btn_answer_minus_vote);
            img_answer_profile_pic = (ImageView) itemView.findViewById(R.id.img_answer_profile_pic);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        HeaderViewHolder(View itemView) {
            super(itemView);

        }
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        PostViewHolder(View itemView) {
            super(itemView);

        }
    }
}
