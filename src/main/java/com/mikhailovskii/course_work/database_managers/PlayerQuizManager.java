package com.mikhailovskii.course_work.database_managers;

import com.mikhailovskii.course_work.entity.QuestionInfo;
import com.mikhailovskii.course_work.entity.QuizQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerQuizManager {

    public List<QuizQuestion> getPlayers() throws SQLException {
        int currentQuestion = 0;

        List<QuizQuestion> questions = new ArrayList<>();
        ResultSet resultSet = DatabaseManager.getStatementInstance().executeQuery("SELECT * FROM quiz_question");
        while (resultSet.next()) {
            questions.add(new QuizQuestion(
                    resultSet.getInt("id"),
                    resultSet.getString("question"),
                    new String[]{
                            resultSet.getString("first_answer"),
                            resultSet.getString("second_answer"),
                            resultSet.getString("third_answer"),
                            resultSet.getString("forth_answer")
                    }
            ));
        }

        resultSet = DatabaseManager.getStatementInstance().executeQuery("SELECT * FROM quiz_question_values");
        while (resultSet.next()) {
            questions.get(currentQuestion).setRightAnswer(resultSet.getInt("right_answer"));
            questions.get(currentQuestion).setPoints(resultSet.getInt("points"));
            currentQuestion++;
        }

        return questions;

    }

    public QuestionInfo getQuestionInfo(int id) throws SQLException {
        ResultSet resultSet = DatabaseManager.getStatementInstance().executeQuery("SELECT  * FROM quiz_question_info WHERE id=" + id);
        resultSet.next();
        return new QuestionInfo(resultSet.getString("info"), resultSet.getString("image"));
    }

}
