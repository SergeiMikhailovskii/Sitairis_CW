package com.mikhailovskii.course_work.entity;

public class QuestionInfo extends BaseEntity {

    private String info;
    private String image;

    public QuestionInfo(String info, String image) {
        this.info = info;
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public String getImage() {
        return image;
    }

}
