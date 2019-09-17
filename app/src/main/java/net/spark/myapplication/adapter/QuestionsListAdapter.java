package net.spark.myapplication.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import net.spark.myapplication.BR;
import net.spark.myapplication.models.Question;
import net.spark.myapplication.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionsListAdapter extends RecyclerView.Adapter<QuestionsListAdapter.QuestionViewHolder>{

    private int layoutId;
    private ArrayList<Question> questions;
    private MainActivityViewModel viewModel;

    public QuestionsListAdapter(@LayoutRes int layoutId, MainActivityViewModel viewModel) {
        this.layoutId = layoutId;
        this.viewModel = viewModel;
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    @Override
    public int getItemCount() {
        return questions == null ? 0 : questions.size();
    }




    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    public void setCategoryList(ArrayList<Question> questions) {
        this.questions = questions;
    }


    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new QuestionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder categoryViewHolder, int position) {
        categoryViewHolder.bind(viewModel, position);
    }


    class QuestionViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        QuestionViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MainActivityViewModel viewModel, Integer position) {
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }

    }


}
