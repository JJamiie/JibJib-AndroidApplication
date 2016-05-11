package com.rashata.jjamie.jibjib.serializer;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by JJamie on 4/10/16 AD.
 */
public class Question {

    @SerializedName("owner")
    String owner;

    @SerializedName("title")
    String title;

    @SerializedName("content")
    String content;

    @SerializedName("from_lang")
    String from_lang;

    @SerializedName("to_lang")
    String to_lang;

    @SerializedName("id")
    String id;

    @SerializedName("count_ans")
    String count_ans;

    @SerializedName("count_vote")
    String count_vote;

    @SerializedName("created_at")
    String created_at;

    public Question(String owner, String title, String content, String from_lang, String to_lang, String id, String count_ans, String count_vote, String created_at) {
        this.owner = owner;
        this.title = title;
        this.content = content;
        this.from_lang = from_lang;
        this.to_lang = to_lang;
        this.id = id;
        this.count_ans = count_ans;
        this.count_vote = count_vote;
        this.created_at = created_at;
    }

    public Question(String title, String content, String from_lang, String to_lang) {
        this.title = title;
        this.content = content;
        this.from_lang = from_lang;
        this.to_lang = to_lang;
    }

    public String getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getFrom_lang() {
        return from_lang;
    }

    public String getTo_lang() {
        return to_lang;
    }

    public String getId() {
        return id;
    }

    public String getCount_ans() {
        return count_ans;
    }

    public String getCount_vote() {
        return count_vote;
    }
    public String get_date() {
        return created_at;
    }

    public static final int TYPE_BLUE = 1;
    public static final int TYPE_PINK = 2;
    public int number_of_views;
    public int type;


}
