package com.mikhailovskii.course_work.database_managers;

import com.mikhailovskii.course_work.entity.QuestionInfo;
import com.mikhailovskii.course_work.entity.QuizQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizManager {

    public List<QuizQuestion> getQuizQuestions(String type) throws SQLException {
        List<QuizQuestion> questions = new ArrayList<>();
        ResultSet resultSet = DatabaseManager.getStatementInstance().executeQuery("SELECT * FROM quiz_question WHERE quiz_type='"+type+"'");
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

        for (QuizQuestion question : questions) {
            resultSet = DatabaseManager.getStatementInstance().executeQuery("SELECT * FROM quiz_question_values WHERE id=" + question.getId());
            resultSet.next();
            question.setPoints(resultSet.getInt("points"));
            question.setRightAnswer(resultSet.getInt("right_answer"));
        }

        return questions;
    }

    public QuestionInfo getQuestionInfo(int id) throws SQLException {
        ResultSet resultSet = DatabaseManager.getStatementInstance().executeQuery("SELECT  * FROM quiz_question_info WHERE id=" + id);
        resultSet.next();
        return new QuestionInfo(resultSet.getString("info"), resultSet.getString("image"));
    }

}
