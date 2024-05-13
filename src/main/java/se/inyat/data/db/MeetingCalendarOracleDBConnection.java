package se.inyat.data.db;

import se.inyat.exception.DBConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MeetingCalendarOracleDBConnection {

    private static final String DB_NAME = "meeting_calendar_database";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root"; // 1234

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (SQLException e) {
            throw new DBConnectionException("Failed to connect to DB (" + DB_NAME + ")", e);
        }
    }

}