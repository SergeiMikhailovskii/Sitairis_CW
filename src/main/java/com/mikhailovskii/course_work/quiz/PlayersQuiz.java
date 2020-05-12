package com.mikhailovskii.course_work.quiz;

import com.mikhailovskii.course_work.constants.Commands;
import com.mikhailovskii.course_work.constants.State;
import com.mikhailovskii.course_work.database_managers.QuizManager;
import com.mikhailovskii.course_work.entity.QuestionInfo;
import com.mikhailovskii.course_work.entity.QuizAnswerResponse;
import com.mikhailovskii.course_work.entity.QuizQuestion;
import com.mikhailovskii.course_work.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class PlayersQuiz implements Quiz {

    private int currentQuestion;
    private List<QuizQuestion> playersQuiz;
    private UserFlow userFlow = new UserFlow();
    private QuizManager quizManager = new QuizManager();

    public PlayersQuiz() {
        try {
            playersQuiz = quizManager.getQuizQuestions(Commands.PLAYER);
        } catch (Exception e) {
            e.printStackTrace();
            playersQuiz = new ArrayList<>();
        }

    }

    @Override
    public SendMessage getQuestion(long chatId) {
        currentQuestion = Preferences.userRoot().getInt(State.CURRENT_PLAYERS_QUIZ_QUESTION, 0);
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
                        playersQuiz.get(currentQuestion).getAnswers()[3],
                        Commands.LEAVE_PLAYERS_QUIZ
                ));

        return sendMessage;
    }

    @Override
    public QuizAnswerResponse handleAnswer(String answer, long chatId, long userId) {
        SendMessage sendMessage = new SendMessage();
        if (answer.equals(playersQuiz.get(currentQuestion).getAnswers()[playersQuiz.get(currentQuestion).getRightAnswer()])) {
            userFlow.addPointsToUser(playersQuiz.get(currentQuestion).getPoints(), userId);
            sendMessage.setChatId(chatId)
                    .setText("Верный ответ. Вы заработали " + playersQuiz.get(currentQuestion).getPoints() + " очков!")
                    .setReplyMarkup(Keyboard.getQuizQuestionReplyKeyboard(
                            playersQuiz.get(currentQuestion).getAnswers()[0],
                            playersQuiz.get(currentQuestion).getAnswers()[1],
                            playersQuiz.get(currentQuestion).getAnswers()[2],
                            playersQuiz.get(currentQuestion).getAnswers()[3],
                            Commands.LEAVE_PLAYERS_QUIZ
                    ));
        } else {
            sendMessage.setChatId(chatId)
                    .setText("Неверный ответ")
                    .setReplyMarkup(Keyboard.getQuizQuestionReplyKeyboard(
                            playersQuiz.get(currentQuestion).getAnswers()[0],
                            playersQuiz.get(currentQuestion).getAnswers()[1],
                            playersQuiz.get(currentQuestion).getAnswers()[2],
                            playersQuiz.get(currentQuestion).getAnswers()[3],
                            Commands.LEAVE_PLAYERS_QUIZ
                    ));
        }

        QuestionInfo info = null;
        try {
            info = quizManager.getQuestionInfo(playersQuiz.get(currentQuestion).getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        QuizAnswerResponse response = new QuizAnswerResponse(playersQuiz.get(currentQuestion).getId(), sendMessage, info);

        currentQuestion++;

        Preferences.userRoot().putInt(State.CURRENT_PLAYERS_QUIZ_QUESTION, currentQuestion);

        return response;
    }

    @Override
    public SendMessage stopQuiz(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId)
                .setText("Вы закончили викторину об игроках. Возвращайтесь скорее!")
                .setReplyMarkup(Keyboard.getMainMenuKeyboard());
        return sendMessage;
    }

}
