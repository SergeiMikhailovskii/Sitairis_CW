package com.mikhailovskii.course_work.quiz;

import com.mikhailovskii.course_work.constants.State;
import com.mikhailovskii.course_work.database_managers.PlayerQuizManager;
import com.mikhailovskii.course_work.entity.QuizQuestion;
import com.mikhailovskii.course_work.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class PlayersQuiz {

    private int currentQuestion;
    private List<QuizQuestion> playersQuiz;

    public PlayersQuiz() {
        try {
            playersQuiz = new PlayerQuizManager().getPlayers();
        } catch (Exception e) {
            e.printStackTrace();
            playersQuiz = new ArrayList<>();
        }

    }

    public SendMessage getQuestion(long chatId) {
        currentQuestion = Preferences.userRoot().node(getClass().getName()).getInt(State.CURRENT_PLAYERS_QUIZ_QUESTION, 0);
        if (currentQuestion >= playersQuiz.size()) {
            currentQuestion = 0;
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId)
                .setText(playersQuiz.get(currentQuestion).getQuestion())
                .setReplyMarkup(Keyboard.getQuizQuestionReplyKeyboard(
                        playersQuiz.get(currentQuestion).getAnswers()[0],
                        playersQuiz.get(currentQuestion).getAnswers()[1],
                        playersQuiz.get(currentQuestion).getAnswers()[2],
                        playersQuiz.get(currentQuestion).getAnswers()[3]
                ));

        return sendMessage;
    }

    public SendMessage handleAnswer(String answer, long chatId) {
        SendMessage sendMessage = new SendMessage();
        if (answer.equals(playersQuiz.get(currentQuestion).getAnswers()[playersQuiz.get(currentQuestion).getRightAnswer()])) {
            sendMessage.setChatId(chatId)
                    .setText("Right answer")
                    .setReplyMarkup(Keyboard.getQuizQuestionReplyKeyboard(
                            playersQuiz.get(currentQuestion).getAnswers()[0],
                            playersQuiz.get(currentQuestion).getAnswers()[1],
                            playersQuiz.get(currentQuestion).getAnswers()[2],
                            playersQuiz.get(currentQuestion).getAnswers()[3]
                    ));
        } else {
            sendMessage.setChatId(chatId)
                    .setText("Wrong answer")
                    .setReplyMarkup(Keyboard.getQuizQuestionReplyKeyboard(
                            playersQuiz.get(currentQuestion).getAnswers()[0],
                            playersQuiz.get(currentQuestion).getAnswers()[1],
                            playersQuiz.get(currentQuestion).getAnswers()[2],
                            playersQuiz.get(currentQuestion).getAnswers()[3]
                    ));
        }
        currentQuestion++;

        Preferences.userRoot().node(getClass().getName()).putInt(State.CURRENT_PLAYERS_QUIZ_QUESTION, currentQuestion);

        return sendMessage;
    }

    public SendMessage stopQuiz(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId)
                .setText("You finished the quiz. Feel free to come back at any time")
                .setReplyMarkup(Keyboard.getMainMenuKeyboard());
        return sendMessage;
    }

}
