package com.example.ebook_pnminh.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ebook_pnminh.Adapter.BookAdapter;
import com.example.ebook_pnminh.R;
import com.example.ebook_pnminh.databinding.FragmentNewBinding;
import com.example.ebook_pnminh.databinding.FragmentPopularBinding;
import com.example.ebook_pnminh.model.Books;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class NewFragment extends Fragment {
    private List<Books> listBooks;
    private BookAdapter adapterPopular;
    FragmentNewBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewBinding.inflate(inflater, container, false);
        loadNewBooks();
        binding.recycelViewNew.setLayoutManager(new GridLayoutManager(getContext(),3));

        return binding.getRoot();
    }

    private void loadNewBooks() {
        listBooks = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Book");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBooks.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Books book = ds.getValue(Books.class);
                    if (book != null) {

                        listBooks.add(book);
                    }
                }

                // Sắp xếp danh sách booksPopular theo thứ tự giảm dần của timestamp
                Collections.sort(listBooks, new Comparator<Books>() {
                    @Override
                    public int compare(Books o1, Books o2) {
                        // Sắp xếp theo thứ tự giảm dần của timestamp
                        return o2.getTimestamp().compareTo(o1.getTimestamp());
                    }
                });

                // Tạo adapter mới với danh sách đã sắp xếp và cài đặt adapter cho RecyclerView
                adapterPopular = new BookAdapter(getContext(), listBooks);
                binding.recycelViewNew.setAdapter(adapterPopular);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("new", "Error: " + error.getMessage());
            }
        });
    }
}