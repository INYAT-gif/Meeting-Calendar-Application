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

        try {
            UserDao userDao = new UserDaoImpl(MeetingCalendarDBConnection.getConnection());
            try {
                userDao.authenticate(new User("admin", "$2a$10$32WnO6/dfSpGggA8IYPhu.Uqca/Jjrbl1ZiEZAFd.WWV3wwhNGZQa"));
                System.out.println("you are logged in");

            } catch (Exception e) {
                CalendarExceptionHandler.handelException(e);
            }

        } catch (Exception e) {
            CalendarExceptionHandler.handelException(e);
        }
    }
}



