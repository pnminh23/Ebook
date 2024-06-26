package com.example.ebook_pnminh.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebook_pnminh.BookActivity;
import com.example.ebook_pnminh.databinding.BookItemBinding;
import com.example.ebook_pnminh.model.Books;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.HolderPopular> {
    private Context context;
    private List<Books> listBooks;

    public BookAdapter(Context context, List<Books> listBooks) {
        this.context = context;
        this.listBooks = listBooks;
    }

    public BookAdapter(List<Books> listBooks) {
        this.listBooks = listBooks;
    }



    @NonNull
    @Override
    public HolderPopular onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout using ViewBinding
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BookItemBinding binding = BookItemBinding.inflate(inflater, parent, false);
        return new HolderPopular(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPopular holder, int position) {
        // Get data
        Books book = listBooks.get(position);
        String bookId = book.getId();
        String name = book.getName();
        String img = book.getImg();

        // Bind data
        holder.binding.namebook.setText(name);
        Picasso.get().load(img).into(holder.binding.imageView);
        holder.binding.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra("bookId",bookId);
                context.startActivity(intent);

            }
        });
    }
//    public void updateBooks(List<Books> newBooks) {
//        this.listBooks = newBooks;
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    public static class HolderPopular extends RecyclerView.ViewHolder {
        BookItemBinding binding;

        public HolderPopular(BookItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
