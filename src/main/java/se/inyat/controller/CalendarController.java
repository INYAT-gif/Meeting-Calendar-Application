package se.inyat.controller;

import se.inyat.data.UserDao;
import se.inyat.exception.CalendarExceptionHandler;
import se.inyat.model.Calendar;
import se.inyat.model.User;
import se.inyat.view.CalendarView;
import se.inyat.data.CalendarDao;

public class CalendarController {//act as a breach between model and view in controller.java
    //all this functionality and logic are going to be implemented here

    // dependencies
    //did we forget public void run() {
    // displayMenu(); accessing view package. use view.displayMenu(); functionaly
    //simality we used controller where we injected all the dependencies
    //
    //public CalendarController(CalendarView view, UserDao userDao, CalendarDao calendarDao) {
      //  this.view = view;
       // this.userDao = userDao;
       // this.calendarDao = calendarDao;
    // }

    private CalendarView view;//inject it as a dependency we need constructor similar to MeetingDao we used Connection connection;
    private UserDao userDao;//dependency injection plus add it to constructor
    private CalendarDao calendarDao;

    // fields
    private boolean isLoggedIn;//
    private String username;//going to need it in other methods

    public CalendarController(CalendarView view, UserDao userDao, CalendarDao calendarDao) {
        this.view = view;
        this.userDao = userDao;
        this.calendarDao = calendarDao;
    }


    public void run() {

        while (true) {//infinite loop
            view.displayMenu();//display menu
            int choice = getUserChoice();//get user choice has to be int

            switch (choice) {//switch case to check if the choice wsa for ex. 0 then do this and so on
                case 0:
                    register();
                    System.out.println();
                    break;
                case 1:
                    login();//call login(); method
                    System.out.println();
                    break;
                case 2:
                    createCalendar();
                    System.out.println();
                    break;
                case 3:
                    //todo: call meeting method
                    System.out.println();

                    break;
                case 4:
                    //todo: call delete calendar method
                    System.out.println();

                    break;
                case 5:
                    //todo: call display calendar method
                    System.out.println();

                    break;
                case 6:
                    isLoggedIn = false;
                    view.displayMessage("You are logged out. Goodbye " + username);
                    System.out.println();
                    break;
                case 7:
                    System.exit(0);
                    System.out.println();
                    break;

                default:
                    view.displayWarningMessage("Invalid choice. Please select a valid option.");
                    System.out.println();
            }

        }

    }


    private int getUserChoice() {
        String operationType = view.promoteString();
        int choice = -1;//define outside the try block to use in catch block if it is not a number it will go to catch block
        //and throw exception and go to catch block and display error message to user
        try {
            choice = Integer.parseInt(operationType);//convert String to int
        } catch (NumberFormatException e) {//catch exception if it is not a number it will throw exception and go to catch block
            view.displayErrorMessage("Invalid input. Please enter a number.");//display error message to user to enter number
        }
        return choice;//
    }


    private void register() {
        view.displayMessage("Enter you username:");//display message to user to enter username for registration
        String username = view.promoteString();//promoteUserForm it takes username and password
        // but we don't need password so use promoteStirng becusse username is a String and store it in String variable of username
        User registeredUser = userDao.createUser(username); //dependency injection
        view.displayUser(registeredUser);// call registeredUser method
        // show username and password to user because password is auto_generated
    }

    private void login() {
        User user = view.promoteUserForm();//need to ask User to enter username and password by using
        // promoteUserForm. Store it is user object and pass it to authenticate method
        try {
            isLoggedIn = userDao.authenticate(user);//pass user object to authenticate method//make isLoggedIn variable global,
            // so we can use it in other methods
            username = user.getUsername();//store username in username variable because it is used in other methods
            view.displaySuccessMessage("Login successful. Welcome " + username);
        } catch (Exception e) {//customExceptionHandler.java
            CalendarExceptionHandler.handelException(e);
        }

    }


    private void createCalendar() {//why public void createCalendar()

        if (!isLoggedIn) {//access isLoggedIn variable which we created in login method
            view.displayWarningMessage("You need to login first.");
            return;
        }

        String calendarTitle = view.promoteCalendarForm();//promoteCalendarForm method is in CalendarView.java
        Calendar createdCalendar = calendarDao.createCalendar(calendarTitle, username);//dependency injection store it in createdCalendar
        view.displaySuccessMessage("Calendar created successfully. ;)");//
        view.displayCalendar(createdCalendar);//displayCalendar method is in CalendarView.java

    }


}