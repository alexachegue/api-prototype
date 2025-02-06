package com.csc340.demo;

public class Photo {
    private int id;
    private String author;
    private String url;

    public Photo(int id, String author, String url) {
        this.id = id;
        this.author = author;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
