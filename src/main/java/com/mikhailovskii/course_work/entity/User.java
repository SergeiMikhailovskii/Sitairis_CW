package com.mikhailovskii.course_work.entity;

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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return userId + " " + userName + " " + score + "\n";
    }

}
