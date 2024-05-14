package se.inyat.data;


import se.inyat.model.Calendar;

import java.util.Collection;
import java.util.Optional;

public interface CalendarDao { //CRUD Operation

    Calendar createCalendar(String CalendarTitle, String CalendarUsername);

    Optional<Calendar> findByCalendarId(int CalendarId);

    Collection<Calendar> findCalendarsByCalendarUsername(String CalendarUsername);

    Optional<Calendar> findByCalendarTitle(String CalendarTitle);

    boolean deleteCalendar(int CalendarId);
}