package se.inyat.data.impl;

import se.inyat.data.CalendarDao;
import se.inyat.exception.MySQLException;
import se.inyat.model.Calendar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CalendarDaoImpl implements CalendarDao {
    private Connection connection;

    public CalendarDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Calendar createCalendar(String CalendarTitle, String CalendarUsername) {
        String query = "INSERT INTO calendars (CalendarTitle, CalendarUsername) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            Calendar Calendar = new Calendar(CalendarTitle, CalendarUsername);
            preparedStatement.setString(1, CalendarTitle);
            preparedStatement.setString(2, CalendarUsername);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new MySQLException("Creating calendar failed, no rows affected.");
            }
            return Calendar;
        } catch (SQLException e) {
            throw new MySQLException("Creating calendar failed: " + CalendarTitle);
        }
    }


    @Override
    public Optional<Calendar> findByCalendarId(int CalendarId) {
        return Optional.empty();
    }

    @Override
    public Collection<Calendar> findCalendarsByCalendarUsername(String CalendarUsername) {
        return List.of();
    }

    @Override
    public Optional<Calendar> findByCalendarTitle(String CalendarTitle) {
        return Optional.empty();
    }

    @Override
    public boolean deleteCalendar(int CalendarId) {
        return false;
    }
}

