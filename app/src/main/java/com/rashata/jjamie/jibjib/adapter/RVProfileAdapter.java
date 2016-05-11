package com.rashata.jjamie.jibjib.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rashata.jjamie.jibjib.R;
import com.rashata.jjamie.jibjib.manager.RESTAPIRetrofit;
import com.rashata.jjamie.jibjib.serializer.Answer;
import com.rashata.jjamie.jibjib.serializer.Comment;
import com.rashata.jjamie.jibjib.serializer.UserProfile;
import com.rashata.jjamie.jibjib.util.MyHelper;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JJamie on 4/10/16 AD.
 */
public class RVProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_COMMENT = 2;
    private static final String TAG = "RVProfileAdapter";
    private Context context;
    private UserProfile userProfile;
    private String token;
    private ArrayList<Comment> comments;

    public RVProfileAdapter(Context context, String token) {
        this.context = context;
        this.token = token;
        comments = new ArrayList<Comment>();
        userProfile = new UserProfile();
        getProfile();
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
                Glide.with(context).load(userProfile.getUser_pic()).bitmapTransform(new CropCircleTransformation(context))
                        .into((hvh.img_profile));
                hvh.txt_profile_name.setText(userProfile.getOwner());
                hvh.txt_profile_study.setText(userProfile.getWork());
                hvh.txt_number_of_answers.setText(userProfile.getCount_own_ans());
                hvh.txt_number_of_votes.setText(userProfile.getCount_own_vote());
                hvh.txt_number_of_comments.setText(comments.size() + "");
                break;
            default:
                CommentViewHolder cvh = (CommentViewHolder) qvh;
                Glide.with(context).load(comments.get(position - 1).getUserprofile().getUser_pic())
                        .into((cvh.img_comment_profile_pic));
                cvh.txt_comment_name.setText(comments.get(position - 1).getCommenter());
                cvh.txt_comment_content.setText(comments.get(position - 1).getContent());
                break;

        }
    }

    @Override
    public int getItemCount() {
        return comments.size() + 1;
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
        private TextView txt_profile_name;
        private TextView txt_profile_study;
        private TextView txt_number_of_answers;
        private TextView txt_number_of_comments;
        private TextView txt_number_of_votes;

        HeaderViewHolder(View itemView) {
            super(itemView);
            img_profile = (ImageView) itemView.findViewById(R.id.img_profile_picture);
            txt_profile_name = (TextView) itemView.findViewById(R.id.txt_profile_name);
            txt_profile_study = (TextView) itemView.findViewById(R.id.txt_profile_study);
            txt_number_of_answers = (TextView) itemView.findViewById(R.id.txt_number_of_answers);
            txt_number_of_comments = (TextView) itemView.findViewById(R.id.txt_number_of_comments);
            txt_number_of_votes = (TextView) itemView.findViewById(R.id.txt_number_of_votes);
        }
    }

    public void getProfile() {
        AsyncTask<Void, Void, Void> getProfileTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
//                String BASE_URL = "http://192.168.1.34:8000";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MyHelper.getInstance().getBaseUrl())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RESTAPIRetrofit restapiRetrofit = retrofit.create(RESTAPIRetrofit.class);
                Call<List<UserProfile>> call = restapiRetrofit.getUserProfile(token);
                call.enqueue(new Callback<List<UserProfile>>() {
                    @Override
                    public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                        userProfile.setUserProfile(response.body().get(0));
                        comments.clear();
                        comments.addAll(userProfile.getOwn_comment());
                        getAdapter().notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                    }
                });
                return null;
            }
        };
        getProfileTask.execute();

    }

    public RecyclerView.Adapter getAdapter() {
        return this;
    }
}
