package com.mikhailovskii.course_work.database_managers;

import com.mikhailovskii.course_work.entity.QuestionInfo;
import com.mikhailovskii.course_work.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    public List<User> getUsersScore() throws SQLException {
        List<User> users = new ArrayList<>();
        ResultSet resultSet = DatabaseManager.getStatementInstance().executeQuery("SELECT * FROM users");

        while (resultSet.next()) {
            users.add(new User(
                    resultSet.getInt("id"),
                    resultSet.getString("user_name"),
                    resultSet.getInt("score")
            ));
        }

        return users;
    }

    public void addUserToDB(User user) throws SQLException {
        DatabaseManager.getStatementInstance().executeUpdate(
                "INSERT INTO users VALUE (" + user.getUserId() + ",'" + user.getUserName() + "'," + user.getScore() + ")"
        );
    }

    public boolean isUserInDB(long id) throws SQLException {
        ResultSet resultSet = DatabaseManager.getStatementInstance().executeQuery("SELECT * FROM users WHERE id=" + id);
        return resultSet.next();
    }

    public void addPointsToUser(int amount, long id) throws SQLException {
        ResultSet resultSet = DatabaseManager.getStatementInstance().executeQuery("SELECT * FROM users WHERE id=" + id);
        resultSet.next();
        int score = resultSet.getInt("score");
        DatabaseManager.getStatementInstance().executeUpdate("UPDATE users SET score=" + (score + amount) + " WHERE id=" + id);
    }

    public boolean didUserSaveFact(int questionId, long userId) throws SQLException {
        ResultSet resultSet = DatabaseManager.getStatementInstance().executeQuery("SELECT * FROM saved_fact WHERE user_id=" + userId + " AND quiz_question_info_id=" + questionId);
        return resultSet.next();
    }

    public void saveFact(int questionId, long userId) throws SQLException {
        DatabaseManager.getStatementInstance().executeUpdate(
                "INSERT INTO saved_fact (user_id, quiz_question_info_id) VALUE (" + userId + ", " + questionId + ")"
        );
    }

    public List<QuestionInfo> getSavedFacts(long userId) throws SQLException {
        ResultSet resultSet = DatabaseManager.getStatementInstance().executeQuery("SELECT quiz_question_info.id, quiz_question_info.info, quiz_question_info.image, saved_fact.user_id, saved_fact.quiz_question_info_id FROM quiz_question_info INNER JOIN saved_fact on quiz_question_info.id = saved_fact.quiz_question_info_id WHERE saved_fact.user_id=" + userId);

        List<QuestionInfo> savedFacts = new ArrayList<>();

        while (resultSet.next()) {
            savedFacts.add(new QuestionInfo(resultSet.getString("quiz_question_info.info"), resultSet.getString("quiz_question_info.image")));
        }

        return savedFacts;
    }

    public void resetScore(long userId) throws SQLException {
        DatabaseManager.getStatementInstance().executeUpdate("UPDATE users SET score=0 WHERE id=" + userId);
    }

    public void deleteUser(long userId) throws SQLException {
        DatabaseManager.getStatementInstance().executeUpdate("DELETE FROM saved_fact WHERE user_id="+userId);
        DatabaseManager.getStatementInstance().executeUpdate("DELETE FROM users WHERE id=" + userId);
    }

}
