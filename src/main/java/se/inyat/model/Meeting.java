package se.inyat.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Meeting {
    private int id;
    private String title;
    private LocalDateTime startTime; // 2024-05-13 10:00
    private LocalDateTime endTime; // endTime cannot be before startTime 2024-05-13 09:00
    private String description;
    private Calendar calendar;


    public Meeting(String title, LocalDateTime startTime, LocalDateTime endTime, String description) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public Meeting(String title, LocalDateTime startTime, LocalDateTime endTime, String description, Calendar calendar) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.calendar = calendar;
    }

    public Meeting(int id, String title, LocalDateTime startTime, LocalDateTime endTime, String description, Calendar calendar) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.calendar = calendar;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String meetingInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Meeting info: ").append("\n");
        stringBuilder.append("id: ").append(id).append("\n");
        stringBuilder.append("Title: ").append(title).append("\n");
        stringBuilder.append("Start time: ").append(startTime).append("\n");
        stringBuilder.append("End time: ").append(endTime).append("\n");
        stringBuilder.append("Description: ").append(description).append("\n");
        return stringBuilder.toString();
    }

    private void timeValidation() {
        if (this.endTime.isBefore(this.startTime)) {
            throw new IllegalArgumentException("End time must be after the start time");
        }
        if (this.startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start time must be in the future.");
        }
    }


}