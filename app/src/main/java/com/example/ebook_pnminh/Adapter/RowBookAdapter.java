package com.example.ebook_pnminh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebook_pnminh.BookActivity;
import com.example.ebook_pnminh.Filter.FilterBook;
import com.example.ebook_pnminh.databinding.BookItemBinding;
import com.example.ebook_pnminh.databinding.RowBookItemBinding;
import com.example.ebook_pnminh.model.Books;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RowBookAdapter extends RecyclerView.Adapter<RowBookAdapter.HolderRowBook> implements Filterable{
    Context context;
    public List<Books> listBook, filterlist;
    private FilterBook filter;

    public RowBookAdapter(Context context, List<Books> listBook) {
        this.context = context;
        this.listBook = listBook;
        this.filterlist = listBook;
    }

    @NonNull
    @Override
    public RowBookAdapter.HolderRowBook onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowBookItemBinding binding = RowBookItemBinding.inflate(inflater, parent, false);
        return new RowBookAdapter.HolderRowBook(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RowBookAdapter.HolderRowBook holder, int position) {
        Books book = listBook.get(position);
        String bookId = String.valueOf(book.getId());
        String name = book.getName();
        String img = book.getImg();
        String author = book.getAuthor();
        holder.binding.tvNameBook.setText(name);
        holder.binding.tvAuthor.setText(author);
        Picasso.get().load(img).into(holder.binding.imgBook);
        holder.binding.imgBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra("bookId",bookId);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listBook.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new FilterBook(filterlist,this);
        }
        return filter;
    }


    public class HolderRowBook extends RecyclerView.ViewHolder {
        RowBookItemBinding binding;
        public HolderRowBook(RowBookItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
