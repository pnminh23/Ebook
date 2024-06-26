package com.example.ebook_pnminh.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ebook_pnminh.Adapter.BookAdapter;
import com.example.ebook_pnminh.R;
import com.example.ebook_pnminh.SearchActivity;

import com.example.ebook_pnminh.databinding.FragmentLibraryBinding;
import com.example.ebook_pnminh.databinding.FragmentPopularBinding;
import com.example.ebook_pnminh.model.Books;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class LibraryFragment extends Fragment {
    FragmentLibraryBinding binding;
    private BookAdapter bookAdapter;
    private List<Books> listBooks;
    String uid = FirebaseAuth.getInstance().getUid();
    List<String> bookIds ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        binding.recycelViewFvr.setLayoutManager(new GridLayoutManager(getContext(),3));
        listBooks = new ArrayList<>();
        bookAdapter = new BookAdapter(getContext(), listBooks);

        binding.recycelViewFvr.setAdapter(bookAdapter);
        loadFavoritesBook();
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();

    }

    private void loadFavoritesBook() {
        bookIds = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BookFavorites");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookIds.clear();
                for (DataSnapshot sp : snapshot.getChildren()) {
                    String currentUid = sp.child("uid").getValue(String.class);
                    if (currentUid != null && currentUid.equals(uid)) {
                        String bookId = sp.child("bookId").getValue(String.class);
                        if (bookId != null) {
                            bookIds.add(bookId);
                        }
                    }
                }

                fetchBooksFromIds(bookIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchBooksFromIds(List<String> bookIds) {
        Log.d("Library", "fetchBooksFromIds: "+ bookIds);
        DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference("Book"); // Giả sử bạn có một node "Books"
        listBooks.clear(); // Xóa danh sách sách hiện tại trước khi thêm sách mới
        bookAdapter.notifyDataSetChanged();
        for (String bookId : bookIds) {
            booksRef.child(""+bookId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    Books book = dataSnapshot.getValue(Books.class);
                    if (book != null) {
                        Log.d("Library", "fetchBooksFromIds: "+ book.getName());
                        listBooks.add(book);
                        bookAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Lỗi lấy dữ liệu sách", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}