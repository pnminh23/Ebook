package com.example.ebook_pnminh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebook_pnminh.Home.PopularFragment;
import com.example.ebook_pnminh.R;
import com.example.ebook_pnminh.databinding.BookItemBinding;
import com.example.ebook_pnminh.model.Books;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterPopularFragment extends FirebaseRecyclerAdapter<Books,AdapterPopularFragment.ViewHolder> {
    BookItemBinding binding;
    public AdapterPopularFragment(@NonNull FirebaseRecyclerOptions<Books> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterPopularFragment.ViewHolder viewHolder, int i, @NonNull Books books) {

        viewHolder.textView.setText(books.getName());
        Picasso.get().load(books.getImg()).into(viewHolder.imageView);


    }

    @NonNull
    @Override
    public AdapterPopularFragment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,parent,false));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = binding.imageView;
            textView = binding.namebook;
        }
    }
}
