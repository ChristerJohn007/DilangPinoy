package com.example.dilangpinoy.objects;

import java.util.List;

public class PictionaryLevel {
    private String question;
    private List<PictionaryImage> images;

    public PictionaryLevel(String question, List<PictionaryImage> images) {
        this.question = question;
        this.images = images;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<PictionaryImage> getImages() {
        return images;
    }

    public void setImages(List<PictionaryImage> images) {
        this.images = images;
    }
}
