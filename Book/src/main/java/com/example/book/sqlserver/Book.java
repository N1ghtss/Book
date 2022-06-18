package com.example.book.sqlserver;

public class Book {
    String name;
    String author;
    String id;
    String location;

    public Book(String id, String name, String author, String location) {
        this.name = name;
        this.author = author;
        this.id = id;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getLocation() {
        return location;
    }
}


