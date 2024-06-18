package com.example.ebook_pnminh.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebook_pnminh.databinding.BookItemBinding;
import com.example.ebook_pnminh.databinding.CategoryListItemBinding;
import com.example.ebook_pnminh.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> listCategory;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public CategoryAdapter(Context context, List<Category> listCategory) {
        this.context = context;
        this.listCategory = listCategory;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CategoryListItemBinding binding = CategoryListItemBinding.inflate(inflater, parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        Category category = listCategory.get(position);
        if(category == null){
            return;
        }
        holder.binding.tvCateegory.setText(category.getNameCategory());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.binding.rcvCategory.setLayoutManager(linearLayoutManager);
        BookAdapter bookAdapter = new BookAdapter(category.getBooks());

        holder.binding.rcvCategory.setAdapter(bookAdapter);
    }

    @Override
    public int getItemCount() {
        if (listCategory != null){
            return listCategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        CategoryListItemBinding binding;
        public CategoryViewHolder(CategoryListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
