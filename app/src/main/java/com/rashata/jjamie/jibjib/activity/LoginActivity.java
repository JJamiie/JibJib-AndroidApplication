package com.rashata.jjamie.jibjib.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.rashata.jjamie.jibjib.R;
import com.rashata.jjamie.jibjib.util.HTTPHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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
        AsyncTask<Void, Void, String> loginTask = new AsyncTask<Void, Void, String>() {
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
            protected String doInBackground(Void... params) {
                HTTPHelper httpHelper = new HTTPHelper();
                HashMap<String, String> param = new HashMap<>();
                param.put("username", username);
                param.put("password", password);
                return httpHelper.POST("http://10.201.136.154:8000/api-token-auth/", param);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    token = jsonObject.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                openLoginActivity();
                layout_loading.setVisibility(View.GONE);

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
