package com.example.ebook_pnminh.model;

import java.util.List;

public class Category {
    private String nameCategory;
    private List<Books> Books;

    public Category() {
    }

    public Category(String nameCategory, List<Books> books) {
        this.nameCategory = nameCategory;
        this.Books = books;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<Books> getBooks() {
        return Books;
    }

    public void setBooks(List<Books> books) {
        Books = books;
    }
}
