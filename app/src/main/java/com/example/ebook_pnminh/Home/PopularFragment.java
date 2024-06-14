package com.example.ebook_pnminh.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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

    private View popularView;
    private ArrayList<Books> booksPopular;
    private AdapterPopularFragment adapterPopular;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        popularView = inflater.inflate(R.layout.fragment_popular, container, false);

        FirebaseRecyclerOptions<Books> options = new FirebaseRecyclerOptions.Builder<Books>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Book"),Books.class)
                .build();
        adapterPopular = new AdapterPopularFragment(options);
        recyclerView = popularView.findViewById(R.id.recycelView);
        recyclerView.setAdapter(adapterPopular);
        return popularView;
    }

    @Override
    public void onStart() {
        adapterPopular.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        adapterPopular.startListening();

        super.onStop();
    }
}