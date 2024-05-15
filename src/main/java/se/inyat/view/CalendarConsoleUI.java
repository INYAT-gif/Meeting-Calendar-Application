package se.inyat.view;

import se.inyat.model.Calendar;
import se.inyat.model.Meeting;
import se.inyat.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class CalendarConsoleUI implements CalendarView { //interact with user take data and pass it to CalendarDaoImpl.java

    //did not implement all the methods because they are not needed for the consoleUI?

    @Override
    public void displayUser(User user) {

        System.out.println(user.userInfo());//get userInfo() from User class
        System.out.println("------------------------");
    }

    @Override
    public void displayCalendar(Calendar calendar) {
        System.out.println(calendar.calendarInfo());
        System.out.println("------------------------");
    }

    @Override
    public void displayMeetings(List<Meeting> meetings) {
        //check isEmpty() or not
        if (meetings.isEmpty()) {//check if there are any meetings in the calendar
            System.out.println("No meetings in this calendar.");
        } else {
            System.out.println("Meetings in thi calendar: ");
            meetings.forEach(meeting -> {//access for.Each element in the list and print meeting info using lambda expression
                System.out.println(meeting.meetingInfo());
                System.out.println("------------------------");
            });
        }
    }

    @Override
    public String promoteString() {
        Scanner scanner = new Scanner(System.in);
        String inputParam = scanner.nextLine();//get input from user
        return inputParam;//return String or inputParam
    }

    @Override
    public Meeting promoteMeetingForm() {
        //which data do we need from User same as when we want to create a meeting for example calendar? not yet may be later?
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a title:");
        String title = scanner.nextLine();

        System.out.println("Start Date & Time (yyyy-MM-dd HH:mm): ");//template for my DateTimeFormatter ofPattern("yyyy-MM-dd HH:mm")
        String start = scanner.nextLine();//get input from user and assign it to String start
        System.out.println("End Date & Time (yyyy-MM-dd HH:mm): ");
        String end = scanner.nextLine();

        System.out.println("Description:");
        String desc = scanner.nextLine();

        //we are getting String from the user, so we need to converting it to LocalDateTime object using DateTimeFormatter
        //before return new Meeting object passing(title, startDateTime, endDateTime, desc);
        LocalDateTime startDateTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        //store the LocalDateTime.parse() object in LocalDateTime startDateTime
        LocalDateTime endDateTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return new Meeting(title, startDateTime, endDateTime, desc);
    }

    @Override
    public String promoteCalendarForm() {
        //only title because username we already know and calendar will be created with username and title
        // as parameters in CalendarDaoImpl.java
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a title: ");
        String title = scanner.nextLine();
        return title;
    }

    @Override
    public User promoteUserForm() {
        //providing data for authentication() method in UserDaoImpl.java
        //ask user for username and password using Scanner
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a username: ");
        String username = scanner.nextLine();//access username and assign it to String username
        System.out.println("Enter a password: ");
        String password = scanner.nextLine();
        return new User(username, password);//return User object by creating a new User object by passing username and password parameters
    }

}