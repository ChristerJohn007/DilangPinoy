package com.example.dilangpinoy.objects;

import androidx.annotation.NonNull;

public class LeaderboardItem implements Comparable<LeaderboardItem>{

    private String name;
    private long score;

    private int position;

    public LeaderboardItem() {
    }

    public LeaderboardItem(String name, long score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int compareTo(LeaderboardItem leaderboardItem) {
        return this.getScore() > leaderboardItem.getScore() ? -1 : (this.getScore() < leaderboardItem.getScore()) ? 1 : 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "Position:"+getPosition()+" Name:"+getName()+" Score:"+getScore();
    }
}
