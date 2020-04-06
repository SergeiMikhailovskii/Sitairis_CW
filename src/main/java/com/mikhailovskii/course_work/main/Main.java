package com.mikhailovskii.course_work.main;

import com.mikhailovskii.course_work.bot.FootballQuizBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {

    public static void main(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new FootballQuizBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

}
