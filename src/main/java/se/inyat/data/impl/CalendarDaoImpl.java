package se.inyat.data.impl;

import se.inyat.data.CalendarDao;
import se.inyat.exception.MySQLException;
import se.inyat.model.Calendar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public class CalendarDaoImpl implements CalendarDao {
    private Connection connection;
    public CalendarDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Calendar createCalendar(String title, String username) {
        //step1: define query || "select * from calendar where title = ? and username = ?"
        //step2: prepared statement || PreparedStatement ps = connection.prepareStatement(query);
        //step3: resultSet to store result || ResultSet rs = ps.executeQuery();
        //step4: executeQuery
        //step5: check the result set || if(rs.next())
        //step6: return the result
        //step7:

        String query = "INSERT INTO calendar (title, username) VALUES (?, ?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ) {
            Calendar calendar = new Calendar(title, username);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, username);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating calendar failed, no rows affected.");
            }
            return calendar;
        } catch (SQLException e) {
            throw new MySQLException("Error creating calendar" + e.getMessage());
        }
            }

    @Override
    public Optional<Calendar> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Collection<Calendar> findCalendarsByUsername(String username) {
        return null;
    }

    @Override
    public Optional<Calendar> findByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public boolean deleteCalendar(int id) {
        return false;
    }
}
