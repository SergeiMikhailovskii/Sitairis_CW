package com.mikhailovskii.course_work.entity;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class QuizAnswerResponse extends BaseEntity {

    private int questionId;
    private SendMessage message;
    private QuestionInfo info;

    public QuizAnswerResponse(int questionId, SendMessage message, QuestionInfo info) {
        this.questionId = questionId;
        this.message = message;
        this.info = info;
    }

    public int getQuestionId() {
        return questionId;
    }

    public SendMessage getMessage() {
        return message;
    }

    public QuestionInfo getInfo() {
        return info;
    }

}
