package com.rashata.jjamie.jibjib.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rashata.jjamie.jibjib.R;
import com.rashata.jjamie.jibjib.manager.RESTAPIRetrofit;
import com.rashata.jjamie.jibjib.serializer.Question;
import com.rashata.jjamie.jibjib.util.MyHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateTranslation extends Activity implements AppCompatCallback {
    private static final String TAG = "CreateTranslation";
    private AppCompatDelegate delegate;
    private Toolbar mToolbar;
    private Question question;
    private RelativeLayout rel_from;
    private RelativeLayout rel_to;
    private TextView txt_from;
    private TextView txt_to;
    private TextView edt_translation_title;
    private TextView edt_content_create;
    private FrameLayout layout_loading;
    private String token;
    private ArrayAdapter<String> arrayAdapterFrom;
    private ArrayAdapter<String> arrayAdapterTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create the delegate
        delegate = AppCompatDelegate.create(this, this);

        //call the onCreate() of the AppCompatDelegate
        delegate.onCreate(savedInstanceState);

        //use the delegate to inflate the layout
        delegate.setContentView(R.layout.activity_create_translation);

        //add the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        delegate.setSupportActionBar(toolbar);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        delegate.setSupportActionBar(mToolbar);
        delegate.getSupportActionBar().setTitle("Create Translation");
        delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        setDialogAdapter();
        bindWidget();
    }

    public void setDialogAdapter() {
        arrayAdapterFrom = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        arrayAdapterFrom.add("Thai");
        arrayAdapterFrom.add("English");
        arrayAdapterFrom.add("Chinese");
        arrayAdapterTo = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        arrayAdapterTo.add("Thai");
        arrayAdapterTo.add("English");
        arrayAdapterTo.add("Chinese");
    }

    public void bindWidget() {
        rel_from = (RelativeLayout) findViewById(R.id.rel_from_create);
        rel_to = (RelativeLayout) findViewById(R.id.rel_to_create);
        txt_from = (TextView) findViewById(R.id.txt_from_create);
        txt_to = (TextView) findViewById(R.id.txt_to_create);
        edt_translation_title = (TextView) findViewById(R.id.edt_translation_title_create);
        edt_content_create = (TextView) findViewById(R.id.edt_content_create);
        layout_loading = (FrameLayout) findViewById(R.id.layout_loading);
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

    public void showFromDialog(View v) {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setAdapter(
                arrayAdapterFrom,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapterFrom.getItem(which);
                        txt_from.setText(strName);
                        setDialogAdapter();
                        arrayAdapterTo.remove(strName);
                    }
                });
        builderSingle.show();
    }

    public void showToDialog(View v) {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setAdapter(
                arrayAdapterTo,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapterTo.getItem(which);
                        txt_to.setText(strName);
                        setDialogAdapter();
                        arrayAdapterFrom.remove(strName);
                    }
                });
        builderSingle.show();
    }


    public void createTranslation(View v) {
        final String title = edt_translation_title.getText().toString();
        final String content = edt_content_create.getText().toString();
        final String from_lang = txt_from.getText().toString();
        final String to_lang = txt_to.getText().toString();
        layout_loading.setVisibility(View.VISIBLE);

        if (title.equals("") || content.equals("") || from_lang.equals("") || to_lang.equals("")) {
            MyHelper.getInstance().showToast("Please fill in all information", CreateTranslation.this);
            layout_loading.setVisibility(View.GONE);
            return;
        }
        AsyncTask<Void, Void, Void> loginTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

//                String BASE_URL = "http://192.168.1.34:8000";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MyHelper.getInstance().getBaseUrl())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RESTAPIRetrofit restapiRetrofit = retrofit.create(RESTAPIRetrofit.class);
                Question question = new Question(title, content, from_lang, to_lang);
                Call<Question> call = restapiRetrofit.addQuestions(token, question);
                call.enqueue(new Callback<Question>() {

                    @Override
                    public void onResponse(Call<Question> call, Response<Question> response) {
                        layout_loading.setVisibility(View.GONE);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Question> call, Throwable t) {

                    }
                });
                return null;
            }
        };
        loginTask.execute();
    }


}
