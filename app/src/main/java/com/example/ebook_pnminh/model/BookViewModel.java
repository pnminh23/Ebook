package com.example.ebook_pnminh.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookViewModel extends ViewModel {
    private final MutableLiveData<List<Books>> favoriteBooks;

    public BookViewModel() {
        favoriteBooks = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Books>> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void addBookToFavorites(Books book) {
        List<Books> currentList = favoriteBooks.getValue();
        if (currentList != null) {
            currentList.add(book);
            favoriteBooks.setValue(currentList);
        }
    }

    public void removeBookFromFavorites(Books book) {
        List<Books> currentList = favoriteBooks.getValue();
        if (currentList != null) {
            currentList.remove(book);
            favoriteBooks.setValue(currentList);
        }
    }

}
