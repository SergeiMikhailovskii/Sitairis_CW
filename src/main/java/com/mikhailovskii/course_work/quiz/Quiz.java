package com.mikhailovskii.course_work.quiz;

import com.mikhailovskii.course_work.constants.State;
import com.mikhailovskii.course_work.entity.QuizAnswerResponse;
import com.mikhailovskii.course_work.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.prefs.Preferences;

public interface Quiz {

    QuizAnswerResponse handleAnswer(String answer, long chatId, long userId);

    SendMessage getQuestion(long chatId);

    SendMessage stopQuiz(long chatId);

    default SendMessage saveFactDialog(String state, int factId, long chatId) {
        SendMessage saveFactDialog = new SendMessage();

        Preferences.userRoot().put(State.CURRENT_STATE, State.SAVING_FACT_STATE);
        Preferences.userRoot().put(State.PREVIOUS_STATE, state);

        saveFactDialog.setChatId(chatId)
                .setText("Если этот факт интересен для вас, то вы можете его сохранить.")
                .setReplyMarkup(Keyboard.getSaveFactDialogKeyboard());

        return saveFactDialog;
    }

}
