package se.inyat.controller;

import se.inyat.data.UserDao;
import se.inyat.exception.CalendarExceptionHandler;
import se.inyat.model.Calendar;
import se.inyat.model.User;
import se.inyat.view.CalendarView;
import se.inyat.data.CalendarDao;

public class CalendarController {

    // dependencies
    private CalendarView view;
    private UserDao userDao;
    private CalendarDao calendarDao;

    // fields
    private boolean isLoggedIn;
    private String username;

    public CalendarController() {
        this.view = view;
        this.userDao = userDao;
        this.calendarDao = calendarDao;
    }

    public void run() {

        while (true) {
            view.displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 0:
                    register();
                    break;
                case 1:
                    login();
                    break;
                case 2:
                    createCalendar();
                    break;
                case 3:
                    //todo: call meeting method
                    break;
                case 4:
                    //todo: call delete calendar method
                    break;
                case 5:
                    //todo: call display calendar method
                    break;
                case 6:
                    isLoggedIn = false;
                    break;
                case 7:
                    System.exit(0);
                    break;

                default:
                    view.displayWarningMessage("Invalid choice. Please select a valid option.");
            }

        }

    }


    private int getUserChoice() {
        String operationType = view.promoteString();
        int choice = -1;
        try {
            choice = Integer.parseInt(operationType);
        } catch (NumberFormatException e) {
            view.displayErrorMessage("Invalid input. Please enter a number.");
        }
        return choice;
    }


    private void register() {
        view.displayMessage("Enter you username:");
        String username = view.promoteString();
        User registeredUser = userDao.createUser(username);
        view.displayUser(registeredUser);
    }

    private void login() {
        User user = view.promoteUserForm();
        try {
            isLoggedIn = userDao.authenticate(user);
            username = user.getUsername();
            view.displaySuccessMessage("Login successful.");
        } catch (Exception e) {
            CalendarExceptionHandler.handelException(e);
        }

    }


    private void createCalendar() {
        if (!isLoggedIn) {
            view.displayWarningMessage("You need to login first.");
            return;
        }

        String calendarTitle = view.promoteCalendarForm();
        Calendar createdCalendar = calendarDao.createCalendar(calendarTitle, username);
        view.displaySuccessMessage("Calendar created successfully. ;)");
        view.displayCalendar(createdCalendar);

    }


}