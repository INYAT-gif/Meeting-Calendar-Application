package se.inyat.data.impl;

import se.inyat.data.UserDao;
import se.inyat.exception.AuthenticationFailedException;
import se.inyat.exception.UserExpiredException;
import se.inyat.model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.inyat.data.UserDao;
import se.inyat.data.db.MeetingCalendarDBConnection;
import se.inyat.exception.AuthenticationFailedException;
import se.inyat.exception.MySQLException;
import se.inyat.exception.UserExpiredException;
import se.inyat.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final Logger log = LogManager.getLogger(UserDaoImpl.class);

    private Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User createUser(String username) {
        String query = "INSERT INTO USERS(USERNAME, _PASSWORD) VALUES(?,?)";
        log.info("Creating user: {} ", username);

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            User user = new User(username);
            user.newPassword();
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getHashedPassword());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                String errorMessage = "Creating user failed, no rows affected.";
                log.error(errorMessage);
                throw new MySQLException(errorMessage);
            }
            return user;
        } catch (SQLException e) {
            String errorMessage = "Error occurred while creating user: " + username;
            log.error(errorMessage);
            throw new MySQLException(errorMessage, e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String query = "SELECT * FROM USERS WHERE USERNAME = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String foundUsername = resultSet.getString("USERNAME");
                String foundPassword = resultSet.getString("_PASSWORD");
                boolean foundExpired = resultSet.getBoolean("EXPIRED");
                User user = new User(foundUsername, foundPassword, foundExpired);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new MySQLException("Error occurred while finding user by username: " + username, e);
        }
    }

    @Override
    public boolean authenticate(User user) throws AuthenticationFailedException, UserExpiredException { //  (admin - 123456)
        String query = "SELECT * FROM USERS WHERE USERNAME = ?";
        log.info("authenticate user: {}:", user.getUsername());

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {

            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                boolean isExpired = resultSet.getBoolean("EXPIRED");
                if (isExpired) {
                    log.warn("User is Expired. username: {}", user.getUsername());
                    throw new UserExpiredException("User is Expired. username: " + user.getUsername());
                }

                String hashedPassword = resultSet.getString("_PASSWORD");
                user.checkHash(hashedPassword);

            } else {
                log.warn("Authentication failed. Invalid credentials.");
                throw new AuthenticationFailedException("Authentication failed. Invalid credentials.");
            }

            return true;

        } catch (SQLException e) {
            log.error("Error occurred while authenticating user by username: {}", user.getUsername(), e);
            throw new MySQLException("Error occurred while authenticating user by username: " + user.getUsername(), e);
        }

    }
}