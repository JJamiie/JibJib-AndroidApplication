package com.rashata.jjamie.jibjib.serializer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JJamie on 4/10/16 AD.
 */
public class Comment {

    @SerializedName("owner")
    String owner;

    @SerializedName("commenter")
    String commenter;

    @SerializedName("content")
    String content;

    @SerializedName("id")
    String id;


    @SerializedName("userprofile")
    UserProfile userprofile;

    public Comment(String owner, String commenter, String content, String id) {
        this.owner = owner;
        this.commenter = commenter;
        this.content = content;
        this.id = id;
    }

    public Comment(String owner, String commenter, String content, String id, UserProfile userprofile) {
        this.owner = owner;
        this.commenter = commenter;
        this.content = content;
        this.id = id;
        this.userprofile = userprofile;
    }

    public UserProfile getUserprofile() {
        return userprofile;
    }

    public String getOwner() {
        return owner;
    }

    public String getCommenter() {
        return commenter;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }
}
