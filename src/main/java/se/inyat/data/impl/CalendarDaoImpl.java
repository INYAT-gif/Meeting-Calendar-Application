package se.inyat.data.impl;

import se.inyat.data.CalendarDao;
import se.inyat.exception.MySQLException;
import se.inyat.model.Calendar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            Calendar Calendar = new Calendar(CalendarTitle, CalendarUsername);
            preparedStatement.setString(1, CalendarTitle);
            preparedStatement.setString(2, CalendarUsername);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new MySQLException("Creating calendar failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int calendarId = generatedKeys.getInt(1); //
                    return new Calendar(calendarId, CalendarTitle, CalendarUsername);
                } else {
                    throw new MySQLException("Creating calendar failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new MySQLException("Creating calendar failed: " + CalendarTitle);
        }
    }


    @Override
    public Optional<Calendar> findById(int id) {
        String selectQuery = "SELECT * FROM calendars WHERE id = ?";
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    String username = resultSet.getString("username");
                    String title = resultSet.getString("title");
                    return Optional.of(new Calendar(id, username, title));
                }
            }
        } catch (SQLException e) {
            String errorMessage = "Error occurred while finding a calendar by ID " + id;
            throw new MySQLException(errorMessage, e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Calendar> findByUsername(String username) {
        String selectQuery = "SELECT * FROM calendars WHERE username = ?";
        List<Calendar> calendars = new ArrayList<>();
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)
        ) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String title = resultSet.getString("title");
                        calendars.add(new Calendar(id, username, title));
                    }

    }
    } catch (SQLException e) {
        String errorMessage = "Error occurred while finding a calendar by username " + username;
        throw new MySQLException(errorMessage, e);
    }
        return calendars;
    }
        @Override
    public Optional<Calendar> findByTitle(String title) {
        String selectQuery = "SELECT * FROM calendars WHERE title = ?";
        Calendar calendar = null;
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, title);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    calendar = new Calendar(id, username, title);
                }
            }
        } catch (SQLException e) {
            String errorMessage = "Error occurred while finding a calendar by title " + title;
            throw new MySQLException(errorMessage, e);
        }

        return Optional.ofNullable(calendar);
    }

    @Override
    public boolean deleteCalendar(int id) {
        String deleteQuery = "DELETE FROM calendars WHERE id = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)
        ) {
            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            String errorMessage = "Error occurred while deleting a calendar by ID " + id;
            throw new MySQLException(errorMessage, e);
        }

    }
}

