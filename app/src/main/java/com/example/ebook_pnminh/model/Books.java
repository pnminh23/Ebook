package com.example.ebook_pnminh.model;

import android.net.Uri;

public class Books {
    private int id,favorites;
    private String name,img,author,category,sub;

    public Books() {
    }

    public Books(int id, int favorites, String name, String img, String author, String category, String sub) {
        this.id = id;
        this.favorites = favorites;
        this.name = name;
        this.img = img;
        this.author = author;
        this.category = category;
        this.sub = sub;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
