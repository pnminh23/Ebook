package com.example.ebook_pnminh.model;

import android.net.Uri;

public class Books {
    private int favorites, pageIntro;
    private String name, img, author, id, category, sub, pdf;
    private String timestamp;

    public Books() {
    }

    public Books(int favorites, int pageIntro, String name, String img, String author, String id, String category, String sub, String pdf, String timestamp) {
        this.favorites = favorites;
        this.pageIntro = pageIntro;
        this.name = name;
        this.img = img;
        this.author = author;
        this.id = id;
        this.category = category;
        this.sub = sub;
        this.pdf = pdf;
        this.timestamp = timestamp;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public int getPageIntro() {
        return pageIntro;
    }

    public void setPageIntro(int pageIntro) {
        this.pageIntro = pageIntro;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
