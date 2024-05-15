package se.inyat.data;


import se.inyat.model.Calendar;

import java.util.Collection;
import java.util.Optional;

public interface CalendarDao { //CRUD Operation

    Calendar createCalendar(String title, String username);

    Optional<Calendar> findById(int id);

    Collection<Calendar> findByUsername(String username);

    Optional<Calendar> findByTitle(String title);

    boolean deleteCalendar(int id);
}