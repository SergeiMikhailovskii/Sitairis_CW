package com.mikhailovskii.course_work.bot;

import com.mikhailovskii.course_work.keyboard.Keyboard;
import com.mikhailovskii.course_work.quiz.PlayersQuiz;
import com.mikhailovskii.course_work.commands.Commands;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class FootballQuizBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals(Commands.START)) {
                message.setChatId(update.getMessage().getChatId())
                        .setText("Welcome")
                        .setReplyMarkup(Keyboard.getMainMenuKeyboard());
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (update.getMessage().getText().equals(Commands.PLAYERS_QUIZ)) {
                PlayersQuiz playersQuiz = new PlayersQuiz();
                message = playersQuiz.startQuiz(update.getMessage());
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "FootballQuizCourseWorkBot";
    }

    @Override
    public String getBotToken() {
        return "856507021:AAHucrT_sB9Q6c1wd-Bzf32GX9sUo7q5vQA";
    }

}
