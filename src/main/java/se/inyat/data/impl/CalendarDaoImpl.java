package se.inyat.data.impl;

import se.inyat.data.CalendarDao;
import se.inyat.model.Calendar;

import java.sql.Connection;
import java.util.Collection;
import java.util.Optional;

public class CalendarDaoImpl implements CalendarDao {
    private Connection connection;
    public CalendarDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Calendar createCalendar(String title, String username) {
        return null;
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
