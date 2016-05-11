package com.rashata.jjamie.jibjib.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.rashata.jjamie.jibjib.R;
import com.rashata.jjamie.jibjib.manager.RESTAPIRetrofit;
import com.rashata.jjamie.jibjib.serializer.Token;
import com.rashata.jjamie.jibjib.serializer.User;
import com.rashata.jjamie.jibjib.util.MyHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    private Button btn_sign_in;
    private EditText edt_username;
    private EditText edt_password;
    private String token;
    private FrameLayout layout_loading;
    private SharedPreferences sharedPreferences;
    private CheckBox chk_remember_usr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindWidget();
    }


    public void bindWidget() {
        btn_sign_in = (Button) findViewById(R.id.btn_sign_in);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        layout_loading = (FrameLayout) findViewById(R.id.layout_loading);
        chk_remember_usr = (CheckBox) findViewById(R.id.chk_remember_usr);

        //set content
        sharedPreferences = getSharedPreferences("MY_PREFERENCE", Context.MODE_PRIVATE);
        edt_username.setText(sharedPreferences.getString("USERNAME", ""));
        chk_remember_usr.setChecked(sharedPreferences.getBoolean("REMEMBER", false));
        edt_username.setText("jjamie");
        edt_password.setText("Jamie16130");
    }


    public void clickSignIn(View v) {
        AsyncTask<Void, Void, Void> loginTask = new AsyncTask<Void, Void, Void>() {
            public String username;
            public String password;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                username = edt_username.getText().toString();
                password = edt_password.getText().toString();
                layout_loading.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {

//                String BASE_URL = "http://192.168.1.34:8000";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MyHelper.getInstance().getBaseUrl())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RESTAPIRetrofit restapiRetrofit = retrofit.create(RESTAPIRetrofit.class);
                User user = new User(username, password);
                Call<Token> call = restapiRetrofit.login(user);
                call.enqueue(new Callback<Token>() {

                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        Log.d(TAG, String.valueOf(response.body()));
                        if (response.body() == null) {
                            MyHelper.getInstance().showToast("username or password is wrong", LoginActivity.this);
                        } else {
                            token = "Token " + response.body().getToken();
                            openLoginActivity();

                        }
                        layout_loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        MyHelper.getInstance().showToast("Please connect to the internet", LoginActivity.this);
                        layout_loading.setVisibility(View.GONE);
                    }
                });
                return null;
            }
        };
        loginTask.execute();


    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, TabActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (chk_remember_usr.isChecked()) {
            editor.putString("USERNAME", edt_username.getText().toString());
        } else {
            editor.putString("USERNAME", "");
        }
        editor.putBoolean("REMEMBER", chk_remember_usr.isChecked());
        editor.commit();
    }

    //    private void runSplashPage(int i){
//        final ImageView splashImageView = (ImageView) findViewById(R.id.splashImage);
//        splashImageView.setVisibility(View.VISIBLE);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                splashImageView.setVisibility(View.GONE);
//            }
//        },i);
//    }
}
