package com.mikhailovskii.course_work.quiz;

import com.mikhailovskii.course_work.entity.QuizAnswerResponse;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Quiz {

    QuizAnswerResponse handleAnswer(String answer, long chatId, long userId);

    SendMessage getQuestion(long chatId);

    SendMessage stopQuiz(long chatId);

}
