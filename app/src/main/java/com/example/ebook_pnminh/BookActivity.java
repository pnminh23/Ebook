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

import com.example.ebook_pnminh.Adapter.BookAdapter;
import com.example.ebook_pnminh.databinding.ActivityBookBinding;
import com.example.ebook_pnminh.model.Books;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    private ActivityBookBinding binding;
    String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");
        loadBookDetail();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        binding.btnReadNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, ReadBookActivity.class);
                intent.putExtra("bookId",bookId);
                startActivity(intent);
            }
        });

    }

    private void loadBookDetail() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Book");
        reference.child(""+bookId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // get data
                String name = ""+snapshot.child("name").getValue();
                String author = ""+snapshot.child("author").getValue();
                String favorites = ""+snapshot.child("favorites").getValue();
                String img = ""+snapshot.child("img").getValue();
                String sub = ""+snapshot.child("sub").getValue();
//                Log.d("Firebase1", "Book added: " + ""+snapshot.child("name").getValue());
                // set data
                binding.txtTitle.setText(name);
                binding.txtAuthor.setText(author);
                binding.txtDescription.setText(sub);
                Picasso.get().load(img).into(binding.imgBookCover);
                binding.txtFavoriteCount.setText(favorites);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}