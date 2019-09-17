package net.spark.myapplication.viewmodel;


import android.app.Application;
import android.view.View;

import net.spark.myapplication.R;
import net.spark.myapplication.adapter.CategoryListAdapter;
import net.spark.myapplication.adapter.QuestionsListAdapter;
import net.spark.myapplication.adapter.QuestionsOptionAdapter;
import net.spark.myapplication.callbacks.MainActivityNavigator;
import net.spark.myapplication.models.Question;
import net.spark.myapplication.models.QuestionList;
import net.spark.myapplication.models.SaveDataResponse;
import net.spark.myapplication.utils.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel {

    public ObservableInt loading;
    public ObservableInt showEmpty;


    public ObservableInt loadingQuestions;
    public ObservableInt showEmptyQuestions;

    private ArrayList<String> categoryList = new ArrayList<>();

    private ArrayList<Question> questionList = new ArrayList<>();
    private ArrayList<Question> questionListOrignal = new ArrayList<>();

    private CategoryListAdapter adapter;
    private QuestionsListAdapter questionsListAdapter;

    private QuestionList questions;

    public MutableLiveData<String> selectedCategory;

    private MainActivityNavigator mainActivityNavigator;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void setNavigatorCallback(MainActivityNavigator callback) {
        this.mainActivityNavigator = callback;
    }

    public String getCategoryName(Integer index) {
        if (categoryList != null &&
                index != null &&
                categoryList.size() > index) {
            return Utils.formatString(categoryList.get(index));
        }
        return null;
    }

    public void onOptionSelected(int adapterPosition, int optionPosition, QuestionsOptionAdapter adapter) {

        questionList.get(adapterPosition)
                .getQuestion_type().setSelectedIndex(optionPosition);
        adapter.setQuestionsOptions(questionList.get(adapterPosition)
                .getQuestion_type());
        adapter.notifyDataSetChanged();
    }

    public String getQuestionIndex(Integer index) {
        if (questionList != null &&
                index != null &&
                questionList.size() > index) {
            return "Q." + (index + 1);
        }
        return null;
    }

    public String getQuestionLabel(Integer index) {
        if (questionList != null &&
                index != null &&
                questionList.size() > index) {
            return questionList.get(index).getQuestion();
        }
        return null;
    }


    public MutableLiveData<String> getSelectedCategory() {
        return selectedCategory;
    }

    public void onItemClick(Integer index) {
        String category = getCategoryName(index);
        selectedCategory.setValue(category);


        if (mainActivityNavigator != null) {
            mainActivityNavigator.onCategorySelected();
        }
        filterQuestionBasedOnCategory(category);
        this.questionsListAdapter.setCategoryList(questionList);
        this.questionsListAdapter.notifyDataSetChanged();


    }

    private void filterQuestionBasedOnCategory(String categoryName) {
        questionList = new ArrayList<>();
        for (int i = 0; i < questionListOrignal.size(); i++) {
            if (questionListOrignal.get(i).getCategory().equalsIgnoreCase(categoryName)) {
                questionList.add(questionListOrignal.get(i));
            }
        }
    }

    public String getSeletedCategory() {
        if(selectedCategory!=null){
            return selectedCategory.getValue();
        }else{
            return "";
        }

    }

    public void onSubmitAnswer() {
        questions.saveDataToServer("suresh" + selectedCategory, questions);
    }


    public void init() {
        questions = new QuestionList();
        selectedCategory = new MutableLiveData<>();
        adapter = new CategoryListAdapter(R.layout.category_item, this);
        questionsListAdapter = new QuestionsListAdapter((R.layout.question_item), this);

        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);


        loadingQuestions = new ObservableInt(View.GONE);
        showEmptyQuestions = new ObservableInt(View.GONE);

    }

    public void fetchQuestions() {
        if (questions != null) {
            questions.fetchQuestionList();
        }
    }

    public MutableLiveData<ArrayList<Question>> getQuestionList() {
        return questions.getQuestionsList();
    }


    public MutableLiveData<ArrayList<String>> getCategoryList() {
        return questions.getCategoryList();
    }

    public MutableLiveData<SaveDataResponse> saveDataResponse() {
        return questions.getSaveDataResp();
    }


    public QuestionsOptionAdapter questionsOptionAdapter(int position) {

        QuestionsOptionAdapter questionsOptionAdapter =
                new QuestionsOptionAdapter((R.layout.question_options), this);
        questionsOptionAdapter.setQuestionsOptions(questionList.get(position).getQuestion_type());
        questionsOptionAdapter.setQuestionIndex(position);
        return questionsOptionAdapter;
    }

    public String getOptionLabel(int adapterPosition, int position) {

        ArrayList<String> questionOptions =
                questionList.get(adapterPosition).getQuestion_type().getOptions();
        return Utils.getAlphabeticIndex(position) + " - " + Utils.formatString(questionOptions.get(position));
    }


    public QuestionsListAdapter getQuestionsListAdapter() {
        return questionsListAdapter;
    }

    public void setQuestionInQuestionAdapter(ArrayList<Question> questionList) {
        this.questionList = questionList;
        this.questionListOrignal = questionList;
    }


    public CategoryListAdapter getAdapter() {
        return adapter;
    }

    public void setCategoryListInAdapter(ArrayList<String> categoryList) {
        this.categoryList = categoryList;
        this.adapter.setCategoryList(categoryList);
        this.adapter.notifyDataSetChanged();
    }


}
