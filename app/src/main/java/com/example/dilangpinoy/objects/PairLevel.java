package com.example.dilangpinoy.objects;

import java.util.List;

public class PairLevel {

    private List<Pair> pairs;

    public PairLevel(List<Pair> pairs) {
        this.pairs = pairs;
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }
}
