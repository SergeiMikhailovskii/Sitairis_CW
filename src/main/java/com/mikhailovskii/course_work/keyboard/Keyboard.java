package com.mikhailovskii.course_work.keyboard;

import com.mikhailovskii.course_work.constants.Commands;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {

    private static ReplyKeyboardMarkup initKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getMainMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = initKeyboard();

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(Commands.PLAYERS_QUIZ);
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add(Commands.EVENTS_QUIZ);
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add(Commands.CHECK_SCORE);
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getQuizQuestionReplyKeyboard(String firstAnswer, String secondAnswer, String thirdAnswer, String forthAnswer, String leave) {
        ReplyKeyboardMarkup replyKeyboardMarkup = initKeyboard();

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(firstAnswer);
        row.add(secondAnswer);
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add(thirdAnswer);
        row.add(forthAnswer);
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add(leave);
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getSaveFactDialogKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = initKeyboard();

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Yes, this fact is interesting");
        row.add("No, thanks");
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);

        return replyKeyboardMarkup;
    }


}
