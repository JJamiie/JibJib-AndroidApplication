package com.rashata.jjamie.jibjib.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.rashata.jjamie.jibjib.R;

public class LoginActivity extends Activity {
    private Button btn_sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindWidget();
    }


    public void clickSignIn(View v) {
        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);
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

    public void bindWidget() {
        btn_sign_in = (Button) findViewById(R.id.btn_sign_in);

    }
}
