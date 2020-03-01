package com.example.dilangpinoy.objects;

import java.util.List;

public class AncientLevel {
    private String question;
    private List<AncientImage> images;

    public AncientLevel(String question, List<AncientImage> images) {
        this.question = question;
        this.images = images;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AncientImage> getImages() {
        return images;
    }

    public void setImages(List<AncientImage> images) {
        this.images = images;
    }
}
