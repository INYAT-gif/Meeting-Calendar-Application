package se.inyat.model;

import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private int calendarId;
    private String calendarTitle;
    private String calendarUsername;
    private List<Meeting> meetings;

    public Calendar(String title, String username) {
        this(title);
        this.calendarUsername = username;
    }

    public Calendar(String title) {
        this.calendarTitle = title;
    }

    public Calendar(int id, String title, String username, List<Meeting> meetings) {
        this(title, username);
        this.calendarId = id;
        this.meetings = meetings;
    }

    public Calendar(int calendarId, String calendarUsername, String title) {
    }

    public int getId() {
        return calendarId;
    }

    public String getTitle() {
        return calendarTitle;
    }

    public String getUsername() {
        return calendarUsername;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void addMeeting(Meeting meeting) {
        if (meetings == null) meetings = new ArrayList<>();
        meetings.add(meeting);
    }

    public void removeMeeting(Meeting meeting) {
        if (meetings == null) throw new IllegalArgumentException("Meeting list is null.");
        if (meeting == null) throw new IllegalArgumentException("Meeting data is null.");
        meetings.remove(meeting);
    }

    public String calendarInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Calendar info: ").append("\n");
        stringBuilder.append("id: ").append(calendarId).append("\n");
        stringBuilder.append("Title: ").append(calendarTitle).append("\n");
        stringBuilder.append("Username: ").append(calendarUsername).append("\n");
        return stringBuilder.toString();
    }
}