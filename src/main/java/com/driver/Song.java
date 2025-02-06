package com.driver;

public class Song {
    private String title;
    private int length;
    private int likes;
    private Artist artist;

    // Default constructor
    public Song() {
    }

    // Constructor with title and length
    public Song(String title, int length) {
        this.title = title;
        this.length = length;
    }

    // Constructor with title, length, and artist
    public Song(String title, int length, Artist artist) {
        this.title = title;
        this.length = length;
        this.artist = artist;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
