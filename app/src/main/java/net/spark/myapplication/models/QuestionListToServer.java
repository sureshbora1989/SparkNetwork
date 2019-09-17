package net.spark.myapplication.models;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;

public class QuestionListToServer extends BaseObservable{

    private ArrayList<String> categories = new ArrayList<>();

   private ArrayList<Question> questions = new ArrayList<>();

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
}

