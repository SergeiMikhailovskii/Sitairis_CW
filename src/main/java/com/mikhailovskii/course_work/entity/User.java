package com.mikhailovskii.course_work.entity;

import java.util.Formatter;

public class User extends BaseEntity {

    private int userId;
    private String userName;
    private int score;

    public User(int userId, String userName, int score) {
        this.userId = userId;
        this.userName = userName;
        this.score = score;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder);
        formatter.format("%12d %20s %4d\n", userId, userName, score);
        return builder.toString();
    }

}
