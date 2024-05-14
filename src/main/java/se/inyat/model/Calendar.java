package se.inyat.model;

import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private int calenderId;
    private String calenderTitle;
    private String calenderUsername;
    private List<Meeting> meetings;

    public Calendar(String title, String username) {
        this(title);
        this.calenderUsername = username;
    }

    public Calendar(String title) {
        this.calenderTitle = title;
    }

    public Calendar(int id, String title, String username, List<Meeting> meetings) {
        this(title, username);
        this.calenderId = id;
        this.meetings = meetings;
    }

    public Calendar(int calendarId, String calendarUsername, String title) {
    }

    public int getId() {
        return calenderId;
    }

    public String getTitle() {
        return calenderTitle;
    }

    public String getUsername() {
        return calenderUsername;
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
        stringBuilder.append("id: ").append(calenderId).append("\n");
        stringBuilder.append("Title: ").append(calenderTitle).append("\n");
        stringBuilder.append("Username: ").append(calenderUsername).append("\n");
        return stringBuilder.toString();
    }
}