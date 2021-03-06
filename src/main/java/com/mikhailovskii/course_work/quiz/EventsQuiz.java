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

public class EventsQuiz implements Quiz {

    private int currentQuestion;
    private List<QuizQuestion> eventsQuiz;
    private UserFlow userFlow = new UserFlow();
    private QuizManager quizManager = new QuizManager();

    public EventsQuiz() {
        try {
            eventsQuiz = quizManager.getQuizQuestions(Commands.EVENT);
        } catch (SQLException e) {
            e.printStackTrace();
            eventsQuiz = new ArrayList<>();
        }
    }

    @Override
    public QuizAnswerResponse handleAnswer(String answer, long chatId, long userId) {
        SendMessage sendMessage = new SendMessage();
        QuizQuestion question = eventsQuiz.get(currentQuestion);

        if (answer.equals(question.getAnswers()[question.getRightAnswer()])) {
            userFlow.addPointsToUser(question.getPoints(), userId);
            sendMessage.setChatId(chatId)
                    .setText("Верный ответ. Вы заработали " + question.getPoints() + " очков!")
                    .setReplyMarkup(Keyboard.getQuizQuestionReplyKeyboard(
                            question.getAnswers()[0],
                            question.getAnswers()[1],
                            question.getAnswers()[2],
                            question.getAnswers()[3],
                            Commands.LEAVE_EVENTS_QUIZ
                    ));
        } else {
            sendMessage.setChatId(chatId)
                    .setText("Неверный ответ")
                    .setReplyMarkup(Keyboard.getQuizQuestionReplyKeyboard(
                            question.getAnswers()[0],
                            question.getAnswers()[1],
                            question.getAnswers()[2],
                            question.getAnswers()[3],
                            Commands.LEAVE_EVENTS_QUIZ
                    ));
        }

        QuestionInfo info = null;
        try {
            info = quizManager.getQuestionInfo(question.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        QuizAnswerResponse response = new QuizAnswerResponse(question.getId(), sendMessage, info);

        currentQuestion++;

        Preferences.userRoot().putInt(State.CURRENT_EVENTS_QUIZ_QUESTION, currentQuestion);

        return response;
    }

    @Override
    public SendMessage getQuestion(long chatId) {
        currentQuestion = Preferences.userRoot().getInt(State.CURRENT_EVENTS_QUIZ_QUESTION, 0);
        if (currentQuestion >= eventsQuiz.size()) {
            currentQuestion = 0;
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId)
                .setText(eventsQuiz.get(currentQuestion).getQuestion())
                .setReplyMarkup(Keyboard.getQuizQuestionReplyKeyboard(
                        eventsQuiz.get(currentQuestion).getAnswers()[0],
                        eventsQuiz.get(currentQuestion).getAnswers()[1],
                        eventsQuiz.get(currentQuestion).getAnswers()[2],
                        eventsQuiz.get(currentQuestion).getAnswers()[3],
                        Commands.LEAVE_EVENTS_QUIZ
                ));

        return sendMessage;
    }

    @Override
    public SendMessage stopQuiz(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId)
                .setText("Вы закончили викторину о событиях. Возвращайтесь скорее!")
                .setReplyMarkup(Keyboard.getMainMenuKeyboard());
        return sendMessage;
    }

}
