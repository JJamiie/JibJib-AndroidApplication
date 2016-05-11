package com.rashata.jjamie.jibjib.serializer;

import android.support.v7.widget.ThemedSpinnerAdapter;

import com.google.gson.annotations.SerializedName;
import com.rashata.jjamie.jibjib.util.MyHelper;

import java.util.List;

/**
 * Created by jjamierashata on 5/6/16 AD.
 */
public class UserProfile {

    @SerializedName("owner")
    String owner;

    @SerializedName("firstname")
    String firstname;

    @SerializedName("lastname")
    String lastname;

    @SerializedName("email")
    String email;

    @SerializedName("user_pic")
    String user_pic;

    @SerializedName("work")
    String work;

    @SerializedName("id")
    String id;

    @SerializedName("count_own_ans")
    String count_own_ans;

    @SerializedName("count_own_vote")
    String count_own_vote;

    @SerializedName("own_comment")
    List<Comment> own_comment;


    public UserProfile() {
        this.owner = "";
        this.firstname = "";
        this.lastname = "";
        this.email = "";
        this.user_pic = "";
        this.work = "";
        this.id = "";
        this.count_own_vote = "0";
        this.count_own_ans = "0";
    }

    public void setUserProfile(UserProfile userProfile) {
        this.owner = userProfile.getOwner();
        this.firstname = userProfile.getFirstname();
        this.lastname = userProfile.getLastname();
        this.email = userProfile.getEmail();
        this.user_pic = userProfile.getUser_pic();
        this.work = userProfile.getWork();
        this.id = userProfile.getId();
        this.count_own_ans = userProfile.getCount_own_ans();
        this.count_own_vote = userProfile.getCount_own_vote();
        this.own_comment = userProfile.getOwn_comment();
    }

    public UserProfile(String owner, String firstname, String lastname, String email, String user_pic, String work, String id) {
        this.owner = owner;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.user_pic = user_pic;
        this.work = work;
        this.id = id;
    }

    public UserProfile(String owner, String firstname, String lastname, String email, String user_pic, String work, String id, String count_own_ans, String count_own_vote, List<Comment> own_comment) {
        this.owner = owner;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.user_pic = user_pic;
        this.work = work;
        this.id = id;
        this.count_own_ans = count_own_ans;
        this.count_own_vote = count_own_vote;
        this.own_comment = own_comment;
    }

    public String getOwner() {
        return owner;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getUser_pic() {
        if (user_pic.contains("http")) {
            return user_pic;
        } else {
//            String BASE_URL = "http://192.168.1.34:800";
            return MyHelper.getInstance().getBaseUrl() + user_pic;
        }
    }

    public String getWork() {
        return work;
    }

    public String getId() {
        return id;
    }

    public String getCount_own_ans() {
        return count_own_ans;
    }

    public String getCount_own_vote() {
        return count_own_vote;
    }

    public List<Comment> getOwn_comment() {
        return own_comment;
    }
}
