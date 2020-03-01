package com.example.dilangpinoy.objects;

public class Trivia {

    private String text;
    private String imagePath;


    public Trivia(String text, String imagePath) {
        this.text = text;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
