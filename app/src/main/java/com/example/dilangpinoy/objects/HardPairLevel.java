package com.example.dilangpinoy.objects;

import java.util.List;

public class HardPairLevel {

    private List<HardPair> pairs;

    public HardPairLevel(List<HardPair> pairs) {
        this.pairs = pairs;
    }

    public List<HardPair> getPairs() {
        return pairs;
    }

    public void setPairs(List<HardPair> pairs) {
        this.pairs = pairs;
    }
}
