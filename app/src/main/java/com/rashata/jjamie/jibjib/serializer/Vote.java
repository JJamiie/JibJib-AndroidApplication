package com.rashata.jjamie.jibjib.serializer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jjamierashata on 5/6/16 AD.
 */
public class Vote {

    @SerializedName("owner")
    String owner;

    @SerializedName("answer")
    String answer;


    @SerializedName("score")
    String score;

    public Vote(String owner, String answer, String score) {
        this.owner = owner;
        this.answer = answer;
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public String getAnswer() {
        return answer;
    }

    public String getOwner() {
        return owner;
    }
}
