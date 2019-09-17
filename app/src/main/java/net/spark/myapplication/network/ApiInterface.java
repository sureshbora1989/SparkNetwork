package net.spark.myapplication.network;

import net.spark.myapplication.models.QuestionList;
import net.spark.myapplication.models.SaveDataRequest;
import net.spark.myapplication.models.SaveDataResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {


    @GET("getQuestionsForQuiz")
    Call<QuestionList> getQuestionsForQuiz();



    @POST("saveQuestionResult")
    Call<SaveDataResponse> saveQuestionResult(@Body SaveDataRequest saveDataRequest);

}
