package com.rashata.jjamie.jibjib.serializer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JJamie on 4/10/16 AD.
 */
public class Answer {

    @SerializedName("owner")
    String owner;

    @SerializedName("question")
    String question;

    @SerializedName("content")
    String content;

    @SerializedName("userprofile")
    UserProfile userprofile;

    @SerializedName("id")
    String id;

    @SerializedName("vote")
    List<Vote> vote;


    public Answer(String owner, String question, String content, String id, UserProfile userProfile, List<Vote> vote) {
        this.owner = owner;
        this.question = question;
        this.content = content;
        this.userprofile = userProfile;
        this.id = id;
        this.vote = vote;
    }

    public Answer(String question, String content) {
        this.question = question;
        this.content = content;
    }

    public String getOwner() {
        return owner;
    }

    public String getQuestion() {
        return question;
    }

    public String getContent() {
        return content;
    }

    public UserProfile getUserprofile() {
        return userprofile;
    }

    public String getName() {
        return getUserprofile().getOwner();
    }

    public List<Vote> getVote() {
        return vote;
    }

    public int getSumVote() {
        int sum = 0;
        for (int i = 0; i < vote.size(); i++) {
            sum += Integer.parseInt(vote.get(i).getScore());
        }
        return sum;
    }
}
