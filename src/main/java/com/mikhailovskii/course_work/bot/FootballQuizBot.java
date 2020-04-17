package com.mikhailovskii.course_work.bot;

import com.mikhailovskii.course_work.constants.Commands;
import com.mikhailovskii.course_work.constants.State;
import com.mikhailovskii.course_work.entity.QuizAnswerResponse;
import com.mikhailovskii.course_work.entity.User;
import com.mikhailovskii.course_work.keyboard.Keyboard;
import com.mikhailovskii.course_work.quiz.EventsQuiz;
import com.mikhailovskii.course_work.quiz.PlayersQuiz;
import com.mikhailovskii.course_work.quiz.UserFlow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.prefs.Preferences;

public class FootballQuizBot extends TelegramLongPollingBot {
    private PlayersQuiz playersQuiz = new PlayersQuiz();
    private EventsQuiz eventsQuiz = new EventsQuiz();
    private UserFlow userFlow = new UserFlow();

    @Override
    public void onUpdateReceived(Update update) {
        // For cleaning preferences data
//        try {
//            Preferences.userRoot().node(getClass().getName()).clear();
//        } catch (BackingStoreException e) {
//            e.printStackTrace();
//        }

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
        } else if (state.equals(State.EVENTS_QUIZ_STATE)) {
            handleEventsQuizStateCommand(receivedMessage);
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
            userFlow.addUserToDB(new User(command.getFrom().getId(), command.getFrom().getUserName(), 0));
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
        switch (receivedMessage.getText()) {
            case Commands.PLAYERS_QUIZ: {
                Preferences.userRoot().node(getClass().getName()).put(State.CURRENT_STATE, State.PLAYER_QUIZ_STATE);
                SendMessage message = playersQuiz.getQuestion(receivedMessage.getChatId());
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            }
            case Commands.EVENTS_QUIZ: {
                Preferences.userRoot().node(getClass().getName()).put(State.CURRENT_STATE, State.EVENTS_QUIZ_STATE);
                SendMessage message = eventsQuiz.getQuestion(receivedMessage.getChatId());
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            }
            case Commands.CHECK_SCORE: {
                SendMessage message = userFlow.getUsersScore(receivedMessage.getChatId());
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void handlePlayerQuizStateCommand(Message receivedMessage) {
        try {
            if (receivedMessage.getText().equals(Commands.LEAVE_PLAYERS_QUIZ)) {
                Preferences.userRoot().node(getClass().getName()).put(State.CURRENT_STATE, State.MAIN_MENU_STATE);
                execute(playersQuiz.stopQuiz(receivedMessage.getChatId()));
            } else {
                QuizAnswerResponse response = playersQuiz.handleAnswer(receivedMessage.getText(), receivedMessage.getChatId(), receivedMessage.getFrom().getId());
                execute(new SendMessage().setChatId(receivedMessage.getChatId()).setText(response.getInfo().getInfo()));

                try {
                    execute(new SendPhoto().setChatId(receivedMessage.getChatId()).setPhoto(response.getInfo().getImage()));
                } catch (TelegramApiRequestException e) {
                    e.printStackTrace();
                }

                execute(response.getMessage());
                execute(playersQuiz.getQuestion(receivedMessage.getChatId()));
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleEventsQuizStateCommand(Message receivedMessage) {
        try {
            if (receivedMessage.getText().equals(Commands.LEAVE_EVENTS_QUIZ)) {
                Preferences.userRoot().node(getClass().getName()).put(State.CURRENT_STATE, State.MAIN_MENU_STATE);
                execute(eventsQuiz.stopQuiz(receivedMessage.getChatId()));
            } else {
                QuizAnswerResponse response = eventsQuiz.handleAnswer(receivedMessage.getText(), receivedMessage.getChatId(), receivedMessage.getFrom().getId());
                execute(new SendMessage().setChatId(receivedMessage.getChatId()).setText(response.getInfo().getInfo()));

                try {
                    execute(new SendPhoto().setChatId(receivedMessage.getChatId()).setPhoto(response.getInfo().getImage()));
                } catch (TelegramApiRequestException e) {
                    e.printStackTrace();
                }

                execute(response.getMessage());
                execute(eventsQuiz.getQuestion(receivedMessage.getChatId()));
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
