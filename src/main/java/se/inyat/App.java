package se.inyat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.inyat.controller.CalendarController;
import se.inyat.data.CalendarDao;
import se.inyat.data.UserDao;
import se.inyat.data.db.MeetingCalendarDBConnection;
import se.inyat.data.impl.CalendarDaoImpl;
import se.inyat.data.impl.UserDaoImpl;
import se.inyat.view.CalendarConsoleUI;


import java.sql.Connection;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

        Connection connection = MeetingCalendarDBConnection.getConnection();
        // CalendarView view = new CalendarConsoleUI();
        UserDao userDao = new UserDaoImpl(connection);
        CalendarDao calendarDao = new CalendarDaoImpl(connection);
       // CalendarController controller = new CalendarController(view, userDao, calendarDao);
      //  controller.run();

    }
}