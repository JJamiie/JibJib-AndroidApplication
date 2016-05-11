package com.rashata.jjamie.jibjib.manager;

import com.rashata.jjamie.jibjib.serializer.Answer;
import com.rashata.jjamie.jibjib.serializer.Question;
import com.rashata.jjamie.jibjib.serializer.Token;
import com.rashata.jjamie.jibjib.serializer.User;
import com.rashata.jjamie.jibjib.serializer.UserProfile;
import com.rashata.jjamie.jibjib.serializer.Vote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by jjamierashata on 5/6/16 AD.
 */
public interface RESTAPIRetrofit {

    @POST("/api-token-auth/")
    Call<Token> login(@Body User user);

    @GET("/api/questions/")
    Call<List<Question>> getAllQuestions();

    @GET("/api/questions/own/")
    Call<List<Question>> getMyQuestions(@Header("Authorization") String token);

    @POST("/api/questions/")
    Call<Question> addQuestions(@Header("Authorization") String token, @Body Question question);

    @GET("/api/questions/{id}")
    Call<Question> getQuestion(@Path("id") String id, @Header("Authorization") String token);

    @GET("/api/answers/id/{id}")
    Call<List<Answer>> getAnswer(@Path("id") String id, @Header("Authorization") String token);

    @POST("/api/answers/")
    Call<Answer> postAnswer(@Header("Authorization") String token, @Body Answer answer);

    @GET("/api/userProfiles/own/")
    Call<List<UserProfile>> getUserProfile(@Header("Authorization") String token);

    @POST("/api/votes/")
    Call<Token> vote(@Header("Authorization") String token, @Body Vote vote);

}
