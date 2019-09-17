package net.spark.myapplication.models;

import android.util.Log;

import com.google.gson.Gson;

import net.spark.myapplication.network.Api;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionList extends BaseObservable{

    private ArrayList<String> categories = new ArrayList<>();

    private MutableLiveData<ArrayList<String>> categoriesList = new MutableLiveData<>();

    private ArrayList<Question> questions = new ArrayList<>();

    private MutableLiveData<ArrayList<Question>> questionsList = new MutableLiveData<>();


    public MutableLiveData<ArrayList<Question>> getQuestionsList() {
        return questionsList;
    }


    public MutableLiveData<ArrayList<String>> getCategoryList() {
        return categoriesList;
    }


    private  MutableLiveData<SaveDataResponse> saveDataResp = new MutableLiveData<>();

    public MutableLiveData<SaveDataResponse> getSaveDataResp() {
        return saveDataResp;
    }


    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void saveDataToServer(String userId, QuestionList questionList){

        Callback<SaveDataResponse> callback = new Callback<SaveDataResponse>() {
            @Override
            public void onResponse(Call<SaveDataResponse> call, Response<SaveDataResponse> response) {

                Gson gson = new Gson();
                String responseBody = gson.toJson(response.body());
                Log.d("resp",""+responseBody);
                saveDataResp.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SaveDataResponse> call, Throwable t) {
                Log.e("Test", t.getMessage(), t);
            }
        };

        SaveDataRequest saveDataRequest = new SaveDataRequest();
        saveDataRequest.setUserId(userId);

        QuestionListToServer questionListToServer = new QuestionListToServer();
        questionListToServer.setCategories(questionList.getCategories());
        questionListToServer.setQuestions(questionList.getQuestions());

        saveDataRequest.setDataToSave(questionListToServer);

        Api.getApi().saveQuestionResult(saveDataRequest).enqueue(callback);

    }

    public void fetchQuestionList() {
        Callback<QuestionList> callback = new Callback<QuestionList>() {
            @Override
            public void onResponse(Call<QuestionList> call, Response<QuestionList> response) {

                Gson gson = new Gson();
                String responseBody = gson.toJson(response.body());
                Log.d("resp",""+responseBody);
                QuestionList questionListMine = response.body();
                questionsList.setValue(questionListMine.questions);
                categoriesList.setValue(questionListMine.categories);

            }

            @Override
            public void onFailure(Call<QuestionList> call, Throwable t) {
                Log.e("Test", t.getMessage(), t);
            }
        };

        Api.getApi().getQuestionsForQuiz().enqueue(callback);
    }

}

