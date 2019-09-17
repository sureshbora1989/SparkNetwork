package net.spark.myapplication.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import net.spark.myapplication.BR;
import net.spark.myapplication.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>{

    private int layoutId;
    private ArrayList<String> category;
    private MainActivityViewModel viewModel;

    public CategoryListAdapter(@LayoutRes int layoutId, MainActivityViewModel viewModel) {
        this.layoutId = layoutId;
        this.viewModel = viewModel;
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    @Override
    public int getItemCount() {
        return category == null ? 0 : category.size();
    }



    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    public void setCategoryList(ArrayList<String> category) {
        this.category = category;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int position) {
        categoryViewHolder.bind(viewModel, position);
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        CategoryViewHolder(ViewDataBinding binding) {
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
