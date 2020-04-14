package com.mikhailovskii.course_work.entity;

public class QuizQuestion extends BaseEntity {

    private String question;
    private int rightAnswer;
    private String[] answers;

    public QuizQuestion(String question, String[] answers, int rightAnswer) {
        this.question = question;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
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

}
