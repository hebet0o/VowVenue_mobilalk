package com.example.vowvenue;

public class Venue {
    private String name;
    private String city;
    private int capacity;
    private String imageUrl;

    // Üres konstruktor a Firestore-hoz
    public Venue() {
    }

    public Venue(String name, String city, int capacity, String imageUrl) {
        this.name = name;
        this.city = city;
        this.capacity = capacity;
        this.imageUrl = imageUrl;
    }

    // Getterek és setterek
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
