package com.mikhailovskii.course_work.database_managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class DatabaseManager {

    private static Statement statement;

    static Statement getStatementInstance() {
        if (statement == null) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Sitairis?useUnicode=true&serverTimezone=UTC", "root", "12345");
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return statement;
    }

}
