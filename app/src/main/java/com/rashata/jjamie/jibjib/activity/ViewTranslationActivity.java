package com.rashata.jjamie.jibjib.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rashata.jjamie.jibjib.R;
import com.rashata.jjamie.jibjib.util.Answer;
import com.rashata.jjamie.jibjib.util.RVAnswerAdapter;
import com.rashata.jjamie.jibjib.util.RVQuestionAdapter;

import java.util.ArrayList;

public class ViewTranslationActivity extends Activity implements AppCompatCallback {
    private AppCompatDelegate delegate;
    private Toolbar mToolbar;
    RecyclerView recyclerView_answers;
    private ArrayList<Answer> answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create the delegate
        delegate = AppCompatDelegate.create(this, this);

        //call the onCreate() of the AppCompatDelegate
        delegate.onCreate(savedInstanceState);

        //use the delegate to inflate the layout
        delegate.setContentView(R.layout.activity_view_translation);

        //add the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        delegate.setSupportActionBar(toolbar);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        delegate.setSupportActionBar(mToolbar);
        delegate.getSupportActionBar().setTitle("View Translation");
        delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView_answers = (RecyclerView) findViewById(R.id.recycle_view_answer);
        recyclerView_answers.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView_answers.setLayoutManager(llm);

        answers = new ArrayList<>();
        RVAnswerAdapter rv_adapter = new RVAnswerAdapter(answers,this);
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
}
