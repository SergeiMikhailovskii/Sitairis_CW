package com.mikhailovskii.course_work.quiz;

import com.mikhailovskii.course_work.entity.QuizQuestion;
import com.mikhailovskii.course_work.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class PlayersQuiz {

    private int currentQuestion = 0;

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

}
