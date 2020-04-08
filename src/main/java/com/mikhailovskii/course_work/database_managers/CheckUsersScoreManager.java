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

}
