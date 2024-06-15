package com.example.ebook_pnminh.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ebook_pnminh.Adapter.AdapterPopularFragment;
import com.example.ebook_pnminh.R;
import com.example.ebook_pnminh.databinding.FragmentPopularBinding;
import com.example.ebook_pnminh.model.Books;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PopularFragment extends Fragment {

    private ArrayList<Books> booksPopular;
    private AdapterPopularFragment adapterPopular;
    FragmentPopularBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPopularBinding.inflate(inflater, container, false);
        loadPopular();


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //vch thêm mỗi dòng này
        binding.recycelView.setLayoutManager(new GridLayoutManager(getContext(),3));
    }

    private void loadPopular() {
        booksPopular = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Book");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Firebase", "onDataChange called");
                booksPopular.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Books book = ds.getValue(Books.class);
                    if (book != null) {
                        booksPopular.add(book);
                        Log.d("Firebase", "Book added: " + book.getName());
                    } else {
                        Log.d("Firebase", "Book is null");
                    }
                }
                adapterPopular = new AdapterPopularFragment(getContext(), booksPopular);
                binding.recycelView.setAdapter(adapterPopular);
                Log.d("Firebase", "Adapter set with " + booksPopular.size() + " items");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });
    }

}