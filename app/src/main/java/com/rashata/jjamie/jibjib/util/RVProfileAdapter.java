package com.rashata.jjamie.jibjib.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rashata.jjamie.jibjib.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by JJamie on 4/10/16 AD.
 */
public class RVProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_COMMENT = 2;
    public Context context;

    ArrayList<Comment> comments;

    public RVProfileAdapter(ArrayList<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return VIEW_TYPE_HEADER;
            default:
                return VIEW_TYPE_COMMENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                View v_header = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_profile_header, viewGroup, false);
                HeaderViewHolder hvh = new HeaderViewHolder(v_header);
                return hvh;
            default:
                View v_comment = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment_listview, viewGroup, false);
                CommentViewHolder qvh = new CommentViewHolder(v_comment);
                return qvh;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder qvh, int position) {
        switch (position) {
            case 0:
                HeaderViewHolder hvh = (HeaderViewHolder) qvh;
                Glide.with(context).load(R.drawable.profile)
                        .bitmapTransform(new CropCircleTransformation(context))
                        .into((hvh.img_profile));
                break;
            default:
                CommentViewHolder cvh = (CommentViewHolder) qvh;
                Glide.with(context).load(R.drawable.profile)
                        .bitmapTransform(new CropCircleTransformation(context))
                        .into((cvh.img_comment_profile_pic));
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

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView img_comment_profile_pic;
        TextView txt_comment_name;
        TextView txt_comment_content;

        CommentViewHolder(View itemView) {
            super(itemView);
            img_comment_profile_pic = (ImageView) itemView.findViewById(R.id.img_comment_profile_pic);
            txt_comment_name = (TextView) itemView.findViewById(R.id.txt_comment_name);
            txt_comment_content = (TextView) itemView.findViewById(R.id.txt_comment_content);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_profile;

        HeaderViewHolder(View itemView) {
            super(itemView);
            img_profile = (ImageView) itemView.findViewById(R.id.img_profile_picture);
        }
    }

}
