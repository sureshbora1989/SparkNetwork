package net.spark.myapplication.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import net.spark.myapplication.BR;
import net.spark.myapplication.R;
import net.spark.myapplication.models.QuestionType;
import net.spark.myapplication.viewmodel.MainActivityViewModel;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionsOptionAdapter extends RecyclerView.Adapter<QuestionsOptionAdapter.QuestionOptionsViewHolder>{

    private int layoutId;
    private QuestionType questionsOptions;
    private MainActivityViewModel viewModel;
    private int currentIndex = 0;

    public QuestionsOptionAdapter(@LayoutRes int layoutId, MainActivityViewModel viewModel) {
        this.layoutId = layoutId;
        this.viewModel = viewModel;
    }

    public void setQuestionIndex(int questionIndex){
        currentIndex = questionIndex;
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    @Override
    public int getItemCount() {
        return questionsOptions == null ? 0 : questionsOptions.getOptions().size();
    }



    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    public void setQuestionsOptions(QuestionType questions) {
        this.questionsOptions = questions;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public QuestionOptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new QuestionOptionsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionOptionsViewHolder categoryViewHolder, int position) {
        categoryViewHolder.bind(viewModel, position);
        setItemSelected(categoryViewHolder.binding.getRoot(),position);
    }

    /**
     * Set Item selected if Selected by user
     * @param view
     */
    void setItemSelected(View view,int pos){
        if(questionsOptions.getSelectedIndex() == pos){
            ((FrameLayout) view.findViewById(R.id.fl_question_option)).setSelected(true);
        }else{
            ((FrameLayout) view.findViewById(R.id.fl_question_option)).setSelected(false);
        }

    }

    class QuestionOptionsViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        QuestionOptionsViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MainActivityViewModel viewModel, Integer position) {
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.setVariable(BR.adapterPosition, currentIndex);
            binding.setVariable(BR.adapter, QuestionsOptionAdapter.this);
            binding.executePendingBindings();
        }

    }


}
