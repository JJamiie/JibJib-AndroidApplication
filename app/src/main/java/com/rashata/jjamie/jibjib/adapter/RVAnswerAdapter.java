package com.rashata.jjamie.jibjib.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rashata.jjamie.jibjib.R;
import com.rashata.jjamie.jibjib.manager.RESTAPIRetrofit;
import com.rashata.jjamie.jibjib.serializer.Answer;
import com.rashata.jjamie.jibjib.serializer.Question;
import com.rashata.jjamie.jibjib.serializer.Vote;
import com.rashata.jjamie.jibjib.util.MyHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JJamie on 4/10/16 AD.
 */
public class RVAnswerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_ANSWER = 2;
    private static final int VIEW_TYPE_POST_ANSWER = 3;
    private static final String TAG = "RVAnswerAdapter";
    private Context context;
    private ArrayList<Answer> answers;
    private Question question;
    private String token;
    private RecyclerView recyclerView_answers;

    public RVAnswerAdapter(Context context, Question question, String token, RecyclerView recyclerView_answers) {
        this.context = context;
        this.question = question;
        this.token = token;
        this.recyclerView_answers = recyclerView_answers;
        answers = new ArrayList<Answer>();
        getAnswer();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else if (position == answers.size() + 1) {
            return VIEW_TYPE_POST_ANSWER;
        } else {
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
        if (position == 0) {
            HeaderViewHolder hvh = (HeaderViewHolder) qvh;
            hvh.txt_view_question_from.setText(question.getFrom_lang());
            hvh.txt_view_question_to.setText(question.getTo_lang());
            hvh.txt_view_question_content.setText(question.getContent());
            hvh.txt_view_question_num_ans.setText(answers.size() + " answers");
        } else if (position == answers.size() + 1) {
            final PostViewHolder pvh = (PostViewHolder) qvh;
            pvh.btn_post_your_answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postAnswer(pvh.edt_type_your_answer);
                }
            });
        } else {
            AnswerViewHolder avh = (AnswerViewHolder) qvh;
            Answer ans = answers.get(position - 1);
            avh.txt_answer_content.setText(ans.getContent());
            avh.txt_answer_number_of_votes.setText(ans.getSumVote() + "");
            avh.txt_answer_name.setText(ans.getName());
            String chkVote = checkVote(ans.getVote());
            if (chkVote.equals("1")) {
                avh.btn_answer_plus_vote.setPressed(true);
                avh.btn_answer_minus_vote.setPressed(false);
            } else if (chkVote.equals("-1")) {
                avh.btn_answer_plus_vote.setPressed(false);
                avh.btn_answer_minus_vote.setPressed(true);
            } else {
                avh.btn_answer_plus_vote.setPressed(false);
                avh.btn_answer_minus_vote.setPressed(false);
            }

            avh.btn_answer_plus_vote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            avh.btn_answer_minus_vote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            Glide.with(context).load(ans.getUserprofile().getUser_pic()).into((avh.img_answer_profile_pic));

        }

    }

    public String checkVote(List<Vote> votes) {
        for (int i = 0; i < votes.size(); i++) {
            if (votes.get(i).getOwner().equals(question.getOwner())) {
                return votes.get(i).getScore();
            }
        }
        return "0";
    }

    @Override
    public int getItemCount() {
        return answers.size() + 2;
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
        TextView txt_view_question_from;
        TextView txt_view_question_content;
        TextView txt_view_question_to;
        TextView txt_view_question_num_ans;

        HeaderViewHolder(View itemView) {
            super(itemView);
            txt_view_question_from = (TextView) itemView.findViewById(R.id.txt_view_question_from);
            txt_view_question_content = (TextView) itemView.findViewById(R.id.txt_view_question_content);
            txt_view_question_to = (TextView) itemView.findViewById(R.id.txt_view_question_to);
            txt_view_question_num_ans = (TextView) itemView.findViewById(R.id.txt_view_question_num_ans);
        }
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        EditText edt_type_your_answer;
        Button btn_post_your_answer;

        PostViewHolder(View itemView) {
            super(itemView);
            edt_type_your_answer = (EditText) itemView.findViewById(R.id.edt_type_your_answer);
            btn_post_your_answer = (Button) itemView.findViewById(R.id.btn_post_your_answer);
        }
    }

    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    public void postAnswer(final EditText edt_content) {
        AsyncTask<Void, Void, Void> postAnswerTask = new AsyncTask<Void, Void, Void>() {
            String content;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                content = edt_content.getText().toString();
            }

            @Override
            protected Void doInBackground(Void... params) {

//                String BASE_URL = "http://192.168.1.34:8000";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MyHelper.getInstance().getBaseUrl())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RESTAPIRetrofit restapiRetrofit = retrofit.create(RESTAPIRetrofit.class);
                Answer answer = new Answer(question.getId(), content);
                Call<Answer> call = restapiRetrofit.postAnswer(token, answer);
                call.enqueue(new Callback<Answer>() {

                    @Override
                    public void onResponse(Call<Answer> call, Response<Answer> response) {
                        getAnswer();
                        edt_content.setText("");
                    }

                    @Override
                    public void onFailure(Call<Answer> call, Throwable t) {

                    }
                });
                return null;
            }
        };
        postAnswerTask.execute();
    }


    public void getAnswer() {
        AsyncTask<Void, Void, Void> getAnswerTask = new AsyncTask<Void, Void, Void>() {

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
                Call<List<Answer>> call = restapiRetrofit.getAnswer(question.getId(), token);
                call.enqueue(new Callback<List<Answer>>() {
                    @Override
                    public void onResponse(Call<List<Answer>> call, Response<List<Answer>> response) {
                        answers.clear();
                        answers.addAll(response.body());
                        getAdapter().notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Answer>> call, Throwable t) {

                    }
                });
                return null;
            }
        };
        getAnswerTask.execute();

    }
}
