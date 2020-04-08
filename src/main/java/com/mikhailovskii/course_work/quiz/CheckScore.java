package com.mikhailovskii.course_work.quiz;

import com.mikhailovskii.course_work.database_managers.CheckUsersScoreManager;
import com.mikhailovskii.course_work.entity.User;
import com.mikhailovskii.course_work.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckScore {

    public SendMessage getUsersScore(long chatId) {
        StringBuilder result = new StringBuilder();
        List<User> users;

        try {
            users = new CheckUsersScoreManager().getUsersScore();
        } catch (SQLException e) {
            e.printStackTrace();
            users = new ArrayList<>();
        }

        for (User user : users) {
            result.append(user.toString());
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId)
                .setText(result.toString())
                .setReplyMarkup(Keyboard.getMainMenuKeyboard());

        return sendMessage;
    }

}
