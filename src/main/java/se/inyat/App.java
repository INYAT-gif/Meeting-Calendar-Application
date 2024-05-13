package se.inyat;

import se.inyat.controller.CalendarController;
import se.inyat.data.CalendarDao;
import se.inyat.data.UserDao;
import se.inyat.data.db.MeetingCalendarDBConnection;
import se.inyat.data.impl.CalendarDaoImpl;
import se.inyat.data.impl.UserDaoImpl;
import se.inyat.view.CalendarConsoleUI;
import se.inyat.view.CalendarView;

import java.sql.Connection;

public class App {

    public static void main(String[] args) {

        System.setProperty("log4j.configurationFile", "log4j2.properties");

        Connection mysqlConnection = MeetingCalendarDBConnection.getConnection();
        CalendarView view = new CalendarConsoleUI();
        UserDao userDao = new UserDaoImpl(mysqlConnection);
        CalendarDao calendarDao = new CalendarDaoImpl(mysqlConnection);

        CalendarController controller = new CalendarController(view, userDao, calendarDao);
        controller.run();

    }

}