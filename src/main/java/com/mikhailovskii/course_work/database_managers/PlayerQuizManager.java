package com.mikhailovskii.course_work.database_managers;

import com.mikhailovskii.course_work.entity.QuizQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerQuizManager {

    public List<QuizQuestion> getPlayers() throws SQLException {

        List<QuizQuestion> questions = new ArrayList<>();
        ResultSet resultSet = DatabaseManager.getStatementInstance().executeQuery("SELECT * FROM quiz_question");
        while (resultSet.next()) {
            questions.add(new QuizQuestion(resultSet.getString("question"),
                    new String[]{
                            resultSet.getString("first_answer"),
                            resultSet.getString("second_answer"),
                            resultSet.getString("third_answer"),
                            resultSet.getString("forth_answer")
                    },
                    resultSet.getInt("right_answer"),
                    resultSet.getInt("points")
            ));
        }
        return questions;

    }

}
