package com.example.ebook_pnminh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.example.ebook_pnminh.databinding.ActivityBookBinding;
import com.example.ebook_pnminh.model.BookViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class BookActivity extends AppCompatActivity {
    private ActivityBookBinding binding;
    String bookId = "";
    String uid = FirebaseAuth.getInstance().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");
        loadBookDetail();
        defaultButonFavorites();
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
        int indexBookFavorites = 1;
        binding.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBookFavorites(uid,bookId);


            }
        });
//        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
    }

    private void defaultButonFavorites() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BookFavorites");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String favoriteKey = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String currentUid = snapshot.child("uid").getValue(String.class);
                    String currentBookId = snapshot.child("bookId").getValue(String.class);
                    if (currentUid.equals(uid) && currentBookId.equals(bookId)) {
                        favoriteKey = snapshot.getKey();;
                        break;
                    }
                }

                if (favoriteKey != null) {
                    binding.btnFavorite.setImageResource(R.drawable.img);


                } else {
                    binding.btnFavorite.setImageResource(R.drawable.img_3);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BookActivity.this, "Lỗi kiểm tra dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void checkBookFavorites(String uid, String bookId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BookFavorites");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String favoriteKey = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String currentUid = snapshot.child("uid").getValue(String.class);
                    String currentBookId = snapshot.child("bookId").getValue(String.class);
                    if (currentUid.equals(uid) && currentBookId.equals(bookId)) {
                        favoriteKey = snapshot.getKey();
                        break;
                    }
                }

                if (favoriteKey != null) {
                    binding.btnFavorite.setImageResource(R.drawable.img_3);

                    deleteBookFavorite(favoriteKey);
                    Toast.makeText(BookActivity.this, "Sách đã có trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    binding.btnFavorite.setImageResource(R.drawable.img);

                    addBookFavorites(uid, bookId);
                    Toast.makeText(BookActivity.this, "Sách chưa có trong danh sách yêu thích", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BookActivity.this, "Lỗi kiểm tra dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateAddFavorites() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Book").child(bookId);
        reference.child("favorites").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long currentCount = 0;
                if (snapshot.exists()) {
                    currentCount = snapshot.getValue(Long.class);
                }
                long newCount = currentCount + 1;
                reference.child("favorites").setValue(newCount).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.txtFavoriteCount.setText(""+newCount);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void updateDeleteFavorites() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Book").child(bookId);
        reference.child("favorites").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long currentCount = 0;
                if (snapshot.exists()) {
                    currentCount = snapshot.getValue(Long.class);
                }
                long newCount = currentCount - 1;
                reference.child("favorites").setValue(newCount).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.txtFavoriteCount.setText(""+newCount);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addBookFavorites(String uid, String bookId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BookFavorites");
        DatabaseReference newRef = ref.push();
        String newKey = newRef.getKey(); // Lấy khóa mới được tạo
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("bookId", bookId);

        // Thêm dữ liệu vào Firebase với khóa mới
        ref.child(String.valueOf(newKey)).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                updateAddFavorites();
                Toast.makeText(BookActivity.this, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookActivity.this, "Thêm sách thất bại", Toast.LENGTH_SHORT).show();
            }
        });



    }
    private void deleteBookFavorite(String favoriteKey) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BookFavorites").child(favoriteKey);

        ref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                updateDeleteFavorites();
                Toast.makeText(BookActivity.this, "Xóa sách khỏi danh sách yêu thích thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookActivity.this, "Xóa sách khỏi danh sách yêu thích thất bại", Toast.LENGTH_SHORT).show();
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
                String timestamp = ""+snapshot.child("timestamp").getValue();
//                Log.d("Firebase1", "Book added: " + ""+snapshot.child("name").getValue());
                // set data
                binding.txtTitle.setText(name);
                binding.txtAuthor.setText(author);
                binding.txtDescription.setText(sub);
                Picasso.get().load(img).into(binding.imgBookCover);
                binding.txtFavoriteCount.setText(favorites);
                binding.txtTime.setText(timestamp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}