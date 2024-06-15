package com.example.ebook_pnminh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebook_pnminh.R;
import com.example.ebook_pnminh.databinding.BookItemBinding;
import com.example.ebook_pnminh.model.Books;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterPopularFragment extends RecyclerView.Adapter<AdapterPopularFragment.HolderPopular> {
    private Context context;
    private ArrayList<Books> listBooks;

    public AdapterPopularFragment(Context context, ArrayList<Books> listBooks) {
        this.context = context;
        this.listBooks = listBooks;
    }

    @NonNull
    @Override
    public HolderPopular onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout using ViewBinding
        LayoutInflater inflater = LayoutInflater.from(context);
        BookItemBinding binding = BookItemBinding.inflate(inflater, parent, false);
        return new HolderPopular(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPopular holder, int position) {
        // Get data
        Books book = listBooks.get(position);
        String name = book.getName();
        String img = book.getImg();

        // Bind data
        holder.binding.namebook.setText(name);
        Picasso.get().load(img).into(holder.binding.imageView);
    }

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
