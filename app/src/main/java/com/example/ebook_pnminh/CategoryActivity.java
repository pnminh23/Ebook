package com.example.ebook_pnminh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.ebook_pnminh.Adapter.BookAdapter;
import com.example.ebook_pnminh.databinding.ActivityCategoryBinding;
import com.example.ebook_pnminh.model.Books;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding binding;
    List<Books> listBooks;
    BookAdapter bookAdapter;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        binding.nameBook.setText(category);

        loadCategory(category);
        binding.rcvCategory.setLayoutManager(new GridLayoutManager(CategoryActivity.this,3));
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

    }

    private void loadCategory(String category) {
        listBooks = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Book");
        Query query = reference.orderByChild("category").equalTo(category);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Firebasecategor", "onDataChange called");
                listBooks.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Books books = ds.getValue(Books.class);
                    if (books != null) {
                        listBooks.add(books);

                        Log.d("Firebasecategor", "Book added: " + books.getName());

                    }
                    else {
                        Log.d("Firebasecategor", "Book is null");
                    }
                    bookAdapter = new BookAdapter(CategoryActivity.this,listBooks);
                    binding.rcvCategory.setAdapter(bookAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}