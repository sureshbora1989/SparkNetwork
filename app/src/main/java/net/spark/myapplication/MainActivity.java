package net.spark.myapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import net.spark.myapplication.callbacks.MainActivityNavigator;
import net.spark.myapplication.models.Question;
import net.spark.myapplication.models.SaveDataResponse;
import net.spark.myapplication.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity implements MainActivityNavigator {

    private MainActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBindings(savedInstanceState);
    }


    private void initBindings(Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        viewModel.setNavigatorCallback(this);

        setupListUpdate();

    }


    private void setupListUpdate() {
        viewModel.loading.set(View.GONE);
        viewModel.fetchQuestions();

        viewModel.getCategoryList().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> categoryList) {
                viewModel.loading.set(View.GONE);
                if (categoryList.size() == 0) {
                    viewModel.showEmpty.set(View.VISIBLE);
                } else {
                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setCategoryListInAdapter(categoryList);
                }
            }
        });

        viewModel.getQuestionList().observe(this, new Observer<ArrayList<Question>>() {
            @Override
            public void onChanged(ArrayList<Question> questions) {
                viewModel.loadingQuestions.set(View.GONE);
                if (questions.size() == 0) {
                    viewModel.showEmptyQuestions.set(View.VISIBLE);
                } else {
                    viewModel.showEmptyQuestions.set(View.GONE);
                    viewModel.setQuestionInQuestionAdapter(questions);
                }
            }
        });

        viewModel.saveDataResponse().observe(this, new Observer<SaveDataResponse>() {
            @Override
            public void onChanged(SaveDataResponse saveDataResponse) {
                if(saveDataResponse == null || saveDataResponse.getMessage().equalsIgnoreCase("error")){

                    Toast.makeText(MainActivity.this, "Server Error Try Again", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });

        setupListClick();
    }

    @Override
    public void onCategorySelected() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_frame,new QuestionsFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setupListClick() {
        viewModel.getSelectedCategory().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String category) {
                if (category != null) {
                    Toast.makeText(MainActivity.this, "You selected a " +category, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
