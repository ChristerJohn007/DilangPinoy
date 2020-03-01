package com.example.dilangpinoy.objects;

public class PictionaryImage {

    private String imagePath;
    private String imageText;

    public PictionaryImage(String imagePath, String imageText) {
        this.imagePath = imagePath;
        this.imageText = imageText;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageText() {
        return imageText;
    }

    public void setImageText(String imageText) {
        this.imageText = imageText;
    }
}
