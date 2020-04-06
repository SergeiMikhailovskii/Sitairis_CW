package com.mikhailovskii.course_work.bot;

import com.mikhailovskii.course_work.constants.Commands;
import com.mikhailovskii.course_work.constants.State;
import com.mikhailovskii.course_work.keyboard.Keyboard;
import com.mikhailovskii.course_work.quiz.PlayersQuiz;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.prefs.Preferences;

public class FootballQuizBot extends TelegramLongPollingBot {
    private PlayersQuiz playersQuiz = new PlayersQuiz();

    @Override
    public void onUpdateReceived(Update update) {
        Message receivedMessage;
        String state = Preferences.userRoot().node(getClass().getName()).get(State.CURRENT_STATE, State.INITIAL_STATE);

        if (update.hasMessage() && update.getMessage().hasText()) {
            receivedMessage = update.getMessage();
        } else {
            return;
        }

        if (state.equals(State.INITIAL_STATE)) {
            handleInitialStateCommand(receivedMessage);
        } else if (state.equals(State.MAIN_MENU_STATE)) {
            handleMainMenuStateCommand(receivedMessage);
        } else if (state.equals(State.PLAYER_QUIZ_STATE)) {
            handlePlayerQuizStateCommand(receivedMessage);
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

    private void handleInitialStateCommand(Message command) {
        if (command.getText().equals(Commands.START)) {
            Preferences.userRoot().node(getClass().getName()).put(State.CURRENT_STATE, State.MAIN_MENU_STATE);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(command.getChatId())
                    .setText("You started the bot")
                    .setReplyMarkup(Keyboard.getMainMenuKeyboard());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMainMenuStateCommand(Message receivedMessage) {
        if (receivedMessage.getText().equals(Commands.PLAYERS_QUIZ)) {
            Preferences.userRoot().node(getClass().getName()).put(State.CURRENT_STATE, State.PLAYER_QUIZ_STATE);
            SendMessage message = playersQuiz.startQuiz(receivedMessage.getChatId());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlePlayerQuizStateCommand(Message receivedMessage) {
        try {
            execute(playersQuiz.handleAnswer(receivedMessage.getText(), receivedMessage.getChatId()));
            execute(playersQuiz.startQuiz(receivedMessage.getChatId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
