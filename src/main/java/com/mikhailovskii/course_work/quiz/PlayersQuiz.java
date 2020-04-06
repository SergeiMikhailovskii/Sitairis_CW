package com.mikhailovskii.course_work.quiz;

import com.mikhailovskii.course_work.entity.QuizQuestion;
import com.mikhailovskii.course_work.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class PlayersQuiz {

    private int currentQuestion = 0;

    private QuizQuestion[] playersQuiz = new QuizQuestion[]{
            new QuizQuestion("Question 1", "A", "B", "C", "D"),
            new QuizQuestion("Question 2", "A", "B", "C", "D"),
            new QuizQuestion("Question 3", "A", "B", "C", "D"),
            new QuizQuestion("Question 4", "A", "B", "C", "D"),
            new QuizQuestion("Question 5", "A", "B", "C", "D"),
            new QuizQuestion("Question 6", "A", "B", "C", "D"),
            new QuizQuestion("Question 7", "A", "B", "C", "D"),
            new QuizQuestion("Question 8", "A", "B", "C", "D"),
            new QuizQuestion("Question 9", "A", "B", "C", "D"),
            new QuizQuestion("Question 10", "A", "B", "C", "D"),
    };

    public SendMessage startQuiz(Message receivedMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(receivedMessage.getChatId())
                .setText(playersQuiz[currentQuestion].getQuestion())
                .setReplyMarkup(Keyboard.getQuizQuestionReplyKeyboard(
                        playersQuiz[currentQuestion].getFirstAnswer(),
                        playersQuiz[currentQuestion].getSecondAnswer(),
                        playersQuiz[currentQuestion].getThirdAnswer(),
                        playersQuiz[currentQuestion].getForthAnswer()
                ));
        currentQuestion++;
        return sendMessage;
    }

}
