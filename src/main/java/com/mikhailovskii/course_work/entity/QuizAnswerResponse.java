package com.mikhailovskii.course_work.entity;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class QuizAnswerResponse extends BaseEntity {

    private SendMessage message;
    private QuestionInfo info;

    public QuizAnswerResponse(SendMessage message, QuestionInfo info) {
        this.message = message;
        this.info = info;
    }

    public SendMessage getMessage() {
        return message;
    }

    public QuestionInfo getInfo() {
        return info;
    }

}
