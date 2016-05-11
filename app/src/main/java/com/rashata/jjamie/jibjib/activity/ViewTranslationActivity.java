package com.rashata.jjamie.jibjib.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.rashata.jjamie.jibjib.R;
import com.rashata.jjamie.jibjib.manager.RESTAPIRetrofit;
import com.rashata.jjamie.jibjib.adapter.RVAnswerAdapter;
import com.rashata.jjamie.jibjib.serializer.Question;
import com.rashata.jjamie.jibjib.util.MyHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewTranslationActivity extends Activity implements AppCompatCallback {
    private static final String TAG = "ViewTranslationActivity";
    private AppCompatDelegate delegate;
    private Toolbar mToolbar;
    RecyclerView recyclerView_answers;
    private String token;
    private String idQuestion;
    Question question;
    private RVAnswerAdapter rv_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create the delegate
        delegate = AppCompatDelegate.create(this, this);

        //call the onCreate() of the AppCompatDelegate
        delegate.onCreate(savedInstanceState);

        //use the delegate to inflate the layout
        delegate.setContentView(R.layout.activity_view_translation);

        Intent intent = getIntent();
        idQuestion = intent.getStringExtra("idQuestion");
        token = intent.getStringExtra("token");
        getQuestion();

    }

    public void bindWidget() {
        //add the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        delegate.setSupportActionBar(toolbar);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        delegate.setSupportActionBar(mToolbar);
        delegate.getSupportActionBar().setTitle(question.getTitle());
        delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView_answers = (RecyclerView) findViewById(R.id.recycle_view_answer);
        recyclerView_answers.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView_answers.setLayoutManager(llm);
        rv_adapter = new RVAnswerAdapter(this, question, token, recyclerView_answers);
        recyclerView_answers.setAdapter(rv_adapter);
    }


    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getQuestion() {
        AsyncTask<Void, Void, Void> getQuestionTask = new AsyncTask<Void, Void, Void>() {

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
                Call<Question> call = restapiRetrofit.getQuestion(idQuestion, token);
                call.enqueue(new Callback<Question>() {
                    @Override
                    public void onResponse(Call<Question> call, Response<Question> response) {
                        question = response.body();
                        Log.d(TAG, question.toString());
                        bindWidget();

                    }

                    @Override
                    public void onFailure(Call<Question> call, Throwable t) {

                    }
                });
                return null;
            }
        };
        getQuestionTask.execute();
    }

}
