package com.rashata.jjamie.jibjib.util;

import java.util.Date;

/**
 * Created by JJamie on 4/10/16 AD.
 */
public class Question {
    public static final int TYPE_BLUE = 1;
    public static final int TYPE_PINK = 2;

    public int type;
    public String id;
    public String to_lang;
    public String from_lang;
    public String title;
    public String content;
    public String owner;

    public Date time_created;
    public int number_of_answers;
    public int number_of_views;
    public int number_of_votes;



}
