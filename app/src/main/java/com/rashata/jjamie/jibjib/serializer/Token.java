package com.rashata.jjamie.jibjib.serializer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jjamierashata on 5/6/16 AD.
 */
public class Token {

    @SerializedName("token")
    String token;
    public String getToken(){
        return token;
    }

}
