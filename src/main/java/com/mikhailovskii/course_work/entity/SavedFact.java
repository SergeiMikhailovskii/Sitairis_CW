package com.mikhailovskii.course_work.entity;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public class SavedFact extends BaseEntity {

    private SendMessage info;
    private SendPhoto image;

    public SavedFact(SendMessage info, SendPhoto image) {
        this.info = info;
        this.image = image;
    }

    public SendMessage getInfo() {
        return info;
    }

    public SendPhoto getImage() {
        return image;
    }

}
