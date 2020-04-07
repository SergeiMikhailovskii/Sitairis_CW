package com.mikhailovskii.course_work.quiz;

import com.mikhailovskii.course_work.constants.State;
import com.mikhailovskii.course_work.entity.QuizQuestion;
import com.mikhailovskii.course_work.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.prefs.Preferences;

public class PlayersQuiz {

    private int currentQuestion;

    private QuizQuestion[] playersQuiz = new QuizQuestion[]{
            new QuizQuestion("Question 1", new String[]{"A", "B", "C", "D"}, 0),
            new QuizQuestion("Question 2", new String[]{"A", "B", "C", "D"}, 0),
            new QuizQuestion("Question 3", new String[]{"A", "B", "C", "D"}, 0),
            new QuizQuestion("Question 4", new String[]{"A", "B", "C", "D"}, 0),
            new QuizQuestion("Question 5", new String[]{"A", "B", "C", "D"}, 0),
            new QuizQuestion("Question 6", new String[]{"A", "B", "C", "D"}, 0),
            new QuizQuestion("Question 7", new String[]{"A", "B", "C", "D"}, 0),
            new QuizQuestion("Question 8", new String[]{"A", "B", "C", "D"}, 0),
            new QuizQuestion("Question 9", new String[]{"A", "B", "C", "D"}, 0),
            new QuizQuestion("Question 10", new String[]{"A", "B", "C", "D"}, 0),
    };

    public SendMessage startQuiz(long chatId) {
        currentQuestion = Preferences.userRoot().node(getClass().getName()).getInt(State.CURRENT_PLAYERS_QUIZ_QUESTION, 0);
        if (currentQuestion >= playersQuiz.length) {
            currentQuestion = 0;
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId)
                .setText(playersQuiz[currentQuestion].getQuestion())
                .setReplyMarkup(Keyboard.getQuizQuestionReplyKeyboard(
                        playersQuiz[currentQuestion].getAnswers()[0],
                        playersQuiz[currentQuestion].getAnswers()[1],
                        playersQuiz[currentQuestion].getAnswers()[2],
                        playersQuiz[currentQuestion].getAnswers()[3]
                ));
        currentQuestion++;

        // TODO remove if app will crash here
        new Thread(new Runnable() {
            @Override
            public void run() {
                Preferences.userRoot().node(getClass().getName()).putInt(State.CURRENT_PLAYERS_QUIZ_QUESTION, currentQuestion);
            }
        }).start();

        return sendMessage;
    }

    public SendMessage handleAnswer(String answer, long chatId) {
        if (answer.equals(playersQuiz[currentQuestion].getAnswers()[playersQuiz[currentQuestion].getRightAnswer()])) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId)
                    .setText("Right answer")
                    .setReplyMarkup(Keyboard.getQuizQuestionReplyKeyboard(
                            playersQuiz[currentQuestion].getAnswers()[0],
                            playersQuiz[currentQuestion].getAnswers()[1],
                            playersQuiz[currentQuestion].getAnswers()[2],
                            playersQuiz[currentQuestion].getAnswers()[3]
                    ));
            return sendMessage;
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId)
                    .setText("Wrong answer")
                    .setReplyMarkup(Keyboard.getQuizQuestionReplyKeyboard(
                            playersQuiz[currentQuestion].getAnswers()[0],
                            playersQuiz[currentQuestion].getAnswers()[1],
                            playersQuiz[currentQuestion].getAnswers()[2],
                            playersQuiz[currentQuestion].getAnswers()[3]
                    ));
            return sendMessage;
        }
    }

    public SendMessage stopQuiz(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId)
                .setText("You finished the quiz. Feel free to come back at any time")
                .setReplyMarkup(Keyboard.getMainMenuKeyboard());
        return sendMessage;
    }

}
