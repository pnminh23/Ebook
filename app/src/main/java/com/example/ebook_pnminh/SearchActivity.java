package com.example.ebook_pnminh;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ebook_pnminh.Adapter.BookAdapter;
import com.example.ebook_pnminh.Adapter.RowBookAdapter;
import com.example.ebook_pnminh.databinding.ActivitySearchBinding;
import com.example.ebook_pnminh.model.Books;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    RowBookAdapter rowBookAdapter;
    private List<Books> listbooks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadbook();
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rowBookAdapter.getFilter().filter(s);
                binding.rcvListSearch.setAdapter(rowBookAdapter);
                binding.rcvListSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadbook() {
        listbooks = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Book");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("Firebase", "onDataChange called");
                listbooks.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Books book = ds.getValue(Books.class);
                    if (book != null) {
                        listbooks.add(book);
//                        Log.d("Firebase", "Book added: " + book.getName());
                    }
//                    else {
//                        Log.d("Firebase", "Book is null");
//                    }
                }
                rowBookAdapter = new RowBookAdapter(SearchActivity.this, listbooks);

//                Log.d("Firebase", "Adapter set with " + booksPopular.size() + " items");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });
    }
}