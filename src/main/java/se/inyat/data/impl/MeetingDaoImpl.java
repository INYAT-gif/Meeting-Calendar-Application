package se.inyat.data.impl;

import se.inyat.data.MeetingDao;
import se.inyat.exception.DBConnectionException;
import se.inyat.exception.MySQLException;
import se.inyat.model.Calendar;
import se.inyat.model.Meeting;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MeetingDaoImpl implements MeetingDao {

    private Connection connection;

    public MeetingDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Meeting createMeeting(Meeting meeting) {
        String insertQuery = "INSERT INTO meetings (title, start_time, end_time, _description, calendar_id) VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, meeting.getTitle());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(meeting.getStartTime()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(meeting.getEndTime()));
            preparedStatement.setString(4, meeting.getDescription());
            preparedStatement.setInt(5, meeting.getCalendar().getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                String errorMessage = "Creating meeting failed, no rows affected.";
                throw new MySQLException(errorMessage);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int meetingId = generatedKeys.getInt(1);
                    //meeting.setId(meetingId); // create setId to set meeting id and reuse the meeting data
                    new Meeting(meetingId, meeting.getTitle(), meeting.getStartTime(), meeting.getEndTime(), meeting.getDescription(), meeting.getCalendar());
                    return meeting;
                } else {
                    String errorMessage = "Creating meeting failed, no ID obtained.";
                    throw new MySQLException(errorMessage);
                }
            }
        } catch (SQLException e) {
            String errorMessage = "Error occurred while creating a meeting";
            throw new MySQLException(errorMessage, e);
        }
    }

    @Override
    public Optional<Meeting> findById(int id) {
        String selectQuery = "SELECT m.*, mc.username as username, mc.title as calendarTitle FROM meetings m inner join meeting_calendars mc on m.calendar_id = mc.id WHERE m.id = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int meetingId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                Timestamp startTime = resultSet.getTimestamp("start_time");
                Timestamp endTime = resultSet.getTimestamp("end_time");
                String description = resultSet.getString("_description");
                int calendarId = resultSet.getInt("calendar_id");
                String calendarUsername = resultSet.getString("username");
                String calendarTitle = resultSet.getString("calendarTitle");

                LocalDateTime startDateTime = startTime.toLocalDateTime();
                LocalDateTime endDateTime = endTime.toLocalDateTime();

                return Optional.of(new Meeting(meetingId, title, startDateTime, endDateTime, description, new Calendar(calendarId, calendarUsername, title)));

            }
        } catch (SQLException e) {
            String errorMessage = "Error occurred while finding a meeting by ID " + id;
            throw new MySQLException(errorMessage, e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Meeting> findAllMeetingsByCalendarId(int calendarId) {
        return null;
    }

    /**  @Override
    public Collection<Meeting> findAllMeetingsByCalendarId(int calendarId) {
        List<Meeting> meetings = new ArrayList<>();
        String selectQuery = "SELECT * FROM meetings WHERE calendar_id = ?";

        try (

                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

        ) {
            preparedStatement.setInt(1, calendarId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    int meetingId = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    Timestamp startTime = resultSet.getTimestamp("start_time");
                    Timestamp endTime = resultSet.getTimestamp("end_time");
                    String description = resultSet.getString("_description");
                    Calendar calendar = null;

                    LocalDateTime startDateTime = startTime.toLocalDateTime();
                    LocalDateTime endDateTime = endTime.toLocalDateTime();


                    Meeting meeting = new Meeting( meetingId, title, startTime, endTime, description );

                    meetings.add(meeting);
                }
            }


        } catch (SQLException e) {
            String errorMessage = "Error occurred while retrieving all meetings";
            throw new MySQLException(errorMessage, e);
        }
        return meetings;
    }
*/
    @Override
    public boolean deleteMeeting(int meetingId) {
        // todo: needs completion
        return false;
    }
}