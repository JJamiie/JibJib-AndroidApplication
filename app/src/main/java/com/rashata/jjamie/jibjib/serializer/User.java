package com.rashata.jjamie.jibjib.serializer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jjamierashata on 5/6/16 AD.
 */
public class User {

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
