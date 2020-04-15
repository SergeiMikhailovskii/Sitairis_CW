package com.mikhailovskii.course_work.database_managers;

import com.mikhailovskii.course_work.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckUsersScoreManager {

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

}
