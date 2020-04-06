package com.mikhailovskii.course_work.entity;

public class QuizQuestion extends BaseEntity {

    private String question;
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;
    private String forthAnswer;

    public QuizQuestion(String question, String firstAnswer, String secondAnswer, String thirdAnswer, String forthAnswer) {
        this.question = question;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
        this.forthAnswer = forthAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public String getForthAnswer() {
        return forthAnswer;
    }

    public void setForthAnswer(String forthAnswer) {
        this.forthAnswer = forthAnswer;
    }

}
