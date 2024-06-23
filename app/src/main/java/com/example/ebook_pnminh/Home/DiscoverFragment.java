package com.example.ebook_pnminh.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ebook_pnminh.Adapter.BookAdapter;
import com.example.ebook_pnminh.Adapter.CategoryAdapter;
import com.example.ebook_pnminh.BookActivity;
import com.example.ebook_pnminh.R;
import com.example.ebook_pnminh.databinding.FragmentDiscoverBinding;
import com.example.ebook_pnminh.model.Books;
import com.example.ebook_pnminh.model.Category;
import com.example.ebook_pnminh.model.SlideItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DiscoverFragment extends Fragment {
    ImageSlider imageSlider;
    View view;

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    BookAdapter bookAdapter;
    private List<Books> listBooks;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discover, container, false);
        imageSlider = view.findViewById(R.id.image_slider);
        loadBookSlide();
        recyclerView = view.findViewById(R.id.rcv_favorites);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadBookFavorites();





        return view;
    }

    private void loadBookFavorites() {
//        List<Category> list = getListCategory();
//        categoryAdapter = new CategoryAdapter(getContext(),list);
//        recyclerView.setAdapter(categoryAdapter);
        listBooks = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Book");
        Query query = reference.orderByChild("favorites");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Firebase", "onDataChange called");
                listBooks.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Books books = ds.getValue(Books.class);
                    if (books != null) {
                        listBooks.add(0,books);

                        Log.d("Firebase", "Book added: " + books.getName());

                    }
                    else {
                        Log.d("Firebase", "Book is null");
                    }

                }
                bookAdapter = new BookAdapter(getContext(),listBooks);
                recyclerView.setAdapter(bookAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

//    private List<Category> getListCategory() {
//        List<Category> list = new ArrayList<>();
//        list.add(new Category("Văn học nước ngoài",getBookFirebase("Văn học nước ngoài")));
//        list.add(new Category("Tuổi học trò",getBookFirebase("Tuổi học trò")));
////        Log.d("getlistCategory", "List added: " + list.get(0));
////        Category category = list.get(0);
////        Log.d("getlistCategory", "List added: " + getBookFirebase("Văn học nước ngoài"));
//
//        return list;
//    }
//    private List<Books> getBookFirebase(String name) {
//        listBooks  = new ArrayList<>();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Book");
//        Query query = reference.orderByChild("category").equalTo(name);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("Firebase", "onDataChange called");
//                listBooks.clear();
//                for (DataSnapshot ds : snapshot.getChildren()){
//                    Books books = ds.getValue(Books.class);
//                    if (books != null) {
//                        listBooks.add(books);
//                        Log.d("Firebase", "Book added: " + books.getName());
//                    }
//                    else {
//                        Log.d("Firebase", "Book is null");
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//        Log.d("getlistCategory", "List added: " + listBooks);
//        return listBooks;
//    }


    private void loadBookSlide() {
        final List<SlideItem> remoteImage = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Book")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            String id = ds.child("id").getValue().toString();
                            SlideModel slideModel = new SlideModel(
                                    ds.child("img").getValue().toString(),
                                    ds.child("name").getValue().toString(),
                                    ScaleTypes.CENTER_INSIDE
                            );
                            remoteImage.add(new SlideItem(id, slideModel));

                        }
                        List<SlideModel> slideModels = new ArrayList<>();
                        for (SlideItem item : remoteImage) {
                            slideModels.add(item.getSlideModel());
                        }
                        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_INSIDE);

                        // Set item click listener
                        imageSlider.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onItemSelected(int i) {
                                String bookId = remoteImage.get(i).getId();
                                Intent intent = new Intent(getContext(), BookActivity.class);
                                intent.putExtra("bookId",bookId);
                                startActivity(intent);
                            }

                            @Override
                            public void doubleClick(int i) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }




}