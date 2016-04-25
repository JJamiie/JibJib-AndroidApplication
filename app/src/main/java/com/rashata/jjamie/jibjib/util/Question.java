package com.rashata.jjamie.jibjib.util;

import java.util.Date;

/**
 * Created by JJamie on 4/10/16 AD.
 */
public class Question {
    public static final int TYPE_BLUE = 1;
    public static final int TYPE_PINK = 2;

    int type;
    String to_language;
    String from_language;
    int number_of_answers;
    int number_of_views;
    int number_of_votes;
    String item_title;
    String item_description;
    Date time_created;
    String name_user_created;

    public Question(int type, String to_language, String from_language, int number_of_answers, int number_of_views,
                    int number_of_votes, String item_title, String item_description, Date time_created, String name_user_created) {
        this.type = type;
        this.to_language = to_language;
        this.from_language = from_language;
        this.number_of_answers = number_of_answers;
        this.number_of_views = number_of_views;
        this.number_of_votes = number_of_votes;
        this.item_title = item_title;
        this.item_description = item_description;
        this.time_created = time_created;
        this.name_user_created = name_user_created;
    }
}
