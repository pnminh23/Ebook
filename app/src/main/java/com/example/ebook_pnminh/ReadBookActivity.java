package com.example.ebook_pnminh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ebook_pnminh.databinding.ActivityReadBookBinding;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReadBookActivity extends AppCompatActivity {
    ActivityReadBookBinding binding;
    String bookId;
    private OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadBookBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");
        client = new OkHttpClient();
        loadBook();
        setContentView(binding.getRoot());


    }

    private void loadBook() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Book");
        reference.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = ""+snapshot.child("name").getValue();
                String pdf = ""+snapshot.child("pdf").getValue();
                displayPdfFromUrl(pdf);
                Log.d("Firebase2", "Book added: " + ""+snapshot.child("pdf").getValue());

                binding.nameBook.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void displayPdfFromUrl(String pdf){
        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(pdf);


        reference.getBytes(50000000).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                binding.pdfView.fromBytes(bytes).swipeHorizontal(false).onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        int currentPage = (page+1);
                        binding.pageCount.setText(currentPage+"/"+pageCount);
                    }
                }).onError(new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(ReadBookActivity.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }).onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int page, Throwable t) {
                        Toast.makeText(ReadBookActivity.this,"Error on page"+page+" "+t.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }).load();
            }
        });
        Log.d("Firebaserefernce", "Book added: " + reference.getBytes(50000000));
    }
}