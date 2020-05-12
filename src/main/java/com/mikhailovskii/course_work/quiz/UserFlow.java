package com.mikhailovskii.course_work.quiz;

import com.mikhailovskii.course_work.database_managers.UserManager;
import com.mikhailovskii.course_work.entity.QuestionInfo;
import com.mikhailovskii.course_work.entity.SavedFact;
import com.mikhailovskii.course_work.entity.User;
import com.mikhailovskii.course_work.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserFlow {

    private UserManager userManager = new UserManager();

    public SendMessage getUsersScore(long chatId) {
        StringBuilder result = new StringBuilder();
        List<User> users;

        try {
            users = userManager.getUsersScore();
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

    public void addUserToDB(User user) {
        try {
            if (!userManager.isUserInDB(user.getUserId())) {
                userManager.addUserToDB(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void addPointsToUser(int pointsAmount, long userId) {
        try {
            if (userManager.isUserInDB(userId)) {
                userManager.addPointsToUser(pointsAmount, userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveFact(int questionId, long userId) {
        try {
            if (!userManager.didUserSaveFact(questionId, userId)) {
                userManager.saveFact(questionId, userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SavedFact> getSavedFacts(long userId, long chatId) {
        try {
            List<QuestionInfo> facts = userManager.getSavedFacts(userId);
            List<SavedFact> savedFactMessages = new ArrayList<>();
            facts.forEach(fact -> {
                SendMessage info = new SendMessage().setChatId(chatId).setText(fact.getInfo());
                SendPhoto photo = new SendPhoto().setChatId(chatId).setPhoto(fact.getImage());
                savedFactMessages.add(new SavedFact(info, photo));
            });
            return savedFactMessages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void resetScore(long userId) {
        try {
            userManager.resetScore(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(long userId) {
        try {
            userManager.deleteUser(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
