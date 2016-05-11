package com.rashata.jjamie.jibjib.util;

import android.app.Activity;
import android.widget.Toast;


/**
 * Created by jjamierashata on 5/7/16 AD.
 */
public class MyHelper {
    private static MyHelper instance;


    public static MyHelper getInstance() {
        if (instance == null)
            instance = new MyHelper();
        return instance;
    }

    private MyHelper() {
    }

    public void showToast(final String message, final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public String getBaseUrl(){
        return "http://128.199.141.51:8000";
    }
}

