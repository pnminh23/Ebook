package com.example.ebook_pnminh;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReadBookActivity extends AppCompatActivity {
    ActivityReadBookBinding binding;
    String bookId;
    private OkHttpClient client;
    int pageCount;
    int currentPage=0;
    int savePage=1;

    String uid = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadBookBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");
        Log.d("bookId", "onCreate: " + bookId);
        client = new OkHttpClient();
        loadBook();
        setContentView(binding.getRoot());
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchDialog();
            }
        });
        binding.btnSavePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkPage();


            }
        });
        binding.btnNextIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextIntro(bookId);
            }
        });

    }

    private void nextIntro(String bookId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Book").child(bookId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String pageIntro = ""+snapshot.child("pageIntro").getValue();
                int nextPage = Integer.parseInt(pageIntro)-1;
                if(nextPage!=0){
                    binding.pdfView.jumpTo(nextPage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void checkPage(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BookSavePage");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("savePage", "onDataChange: accset");
                String savePageKey = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("savePage", "onDataChange: for");
                    String currentUid = ""+snapshot.child("uid").getValue();
                    String currentBookId = ""+snapshot.child("bookId").getValue();
                    String currentPageString = ""+snapshot.child("savePage").getValue();
                    Log.d("savePage", "onDataChange: " + currentPageString);

                    savePage= Integer.parseInt(currentPageString);

                    Log.d("savePage", "onDataChange: " + savePage);
                    if (currentUid.equals(uid) && currentBookId.equals(bookId)) {
                        savePageKey = snapshot.getKey();
                        break;
                    }


                }if(savePageKey != null){
                     updateSavePage(savePageKey);
                    Log.d("bookId", "Đã có sách: " );


                }
                else{
                    currentPage = binding.pdfView.getCurrentPage();
                    addSavePageToUid(uid,bookId,currentPage);
                    Log.d("bookId", "chưa có sách: " );

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void updateSavePage( String savePageKey) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BookSavePage").child(savePageKey);
        ref.child("savePage").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int newcurrentPage = binding.pdfView.getCurrentPage()+1;
                ref.child("savePage").setValue(newcurrentPage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void jumpPage() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BookSavePage");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("savePage", "onDataChange: accset");
                String savePageKey = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("savePage", "onDataChange: for");
                    String currentUid = ""+snapshot.child("uid").getValue();
                    String currentBookId = ""+snapshot.child("bookId").getValue();
                    String currentPageString = ""+snapshot.child("savePage").getValue();
                    Log.d("savePage", "onDataChange: " + currentPageString);

                    savePage= Integer.parseInt(currentPageString);

                    Log.d("savePage", "onDataChange: " + savePage);
                    if (currentUid.equals(uid) && currentBookId.equals(bookId)) {

                        binding.pdfView.jumpTo(savePage-1);
                        break;
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void addSavePageToUid(String uid, String bookId, int currentPage) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BookSavePage");
        DatabaseReference newRef = ref.push();
        String newKey = newRef.getKey(); // Lấy khóa mới được tạo
        int tmp = currentPage +1;
        // Tạo HashMap để lưu dữ liệu
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("bookId", bookId);
        hashMap.put("savePage",tmp);
        ref.child(String.valueOf(newKey)).setValue(hashMap);
        // Lấy số lượng khóa hiện tại


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
                        ReadBookActivity.this.pageCount = pageCount;
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
                jumpPage();
            }
        });
        Log.d("Firebaserefernce", "Book added: " + reference.getBytes(50000000));
    }
    private void showSearchDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_dialog_search);
        dialog.setTitle("Search Page");

        EditText pageNumberEditText = dialog.findViewById(R.id.pageNumberEditText);
        Button searchButton = dialog.findViewById(R.id.dialogSearchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pageNumberStr = pageNumberEditText.getText().toString();
                if (!pageNumberStr.isEmpty()) {
                    int pageNumber = Integer.parseInt(pageNumberStr);
                    if (pageNumber > 0 && pageNumber <= pageCount) {
                        binding.pdfView.jumpTo(pageNumber -1);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(ReadBookActivity.this, "Invalid page number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        dialog.show();
    }
}

