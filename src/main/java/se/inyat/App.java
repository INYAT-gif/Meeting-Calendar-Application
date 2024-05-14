package se.inyat;

import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import se.inyat.controller.CalendarController;
import se.inyat.data.CalendarDao;
import se.inyat.data.UserDao;
import se.inyat.data.db.MeetingCalendarDBConnection;
import se.inyat.data.db.MeetingCalendarOracleDBConnection;
import se.inyat.data.impl.CalendarDaoImpl;
import se.inyat.data.impl.UserDaoImpl;
import se.inyat.exception.AuthenticationFailedException;
import se.inyat.exception.CalendarExceptionHandler;
import se.inyat.exception.UserExpiredException;
import se.inyat.model.User;
import se.inyat.view.CalendarConsoleUI;
import se.inyat.view.CalendarView;

import java.sql.Connection;
import java.util.Optional;

public class App {

    public static void main(String[] args) {

        System.setProperty("log4j.configurationFile", "log4j2.properties");

        Connection mysqlConnection = MeetingCalendarDBConnection.getConnection();
        CalendarView view = new CalendarConsoleUI();
        UserDao userDao = new UserDaoImpl(mysqlConnection);
        userDao.createUser("admin");
        System.out.println("userInfo = " + userDao.createUser("admin"));
        CalendarDao calendarDao = new CalendarDaoImpl(mysqlConnection);


        Optional<User> userOptional = userDao.findByUsername("admin");
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User = " + user);
            try {
                user.checkHash("123456");
            } catch (AuthenticationFailedException e) {
                throw new RuntimeException(e);
            }
        }
        //check authenticate(user) method in main class
        try {
            userDao.authenticate(new User("admin", "123456"));
            System.out.println("You are logged in ");
        } catch (AuthenticationFailedException e){
                System.out.println("User is Expired. username: " + e);
        } catch (UserExpiredException e) {
            throw new RuntimeException(e);
        }
    }

     //   CalendarController controller = new CalendarController(view, userDao, calendarDao);
       // controller.run();
    }

