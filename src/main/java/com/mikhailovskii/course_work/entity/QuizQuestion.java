package com.mikhailovskii.course_work.entity;

public class QuizQuestion extends BaseEntity {

    private int id;
    private String question;
    private int rightAnswer;
    private int points;
    private String[] answers;

    public QuizQuestion(int id, String question, String[] answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public int getId() {
        return id;
    }
}
