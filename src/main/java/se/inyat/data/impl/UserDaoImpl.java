package se.inyat.data.impl;

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

    private Connection connection;//static field, global variable in class fields

    public UserDaoImpl(Connection connection) {//constructor shows dependency on connection object
        this.connection = connection;//depencency injection, one class depends on another class diffrenct from Maven
    }

    @Override
    public User createUser(String username) {
        String query = "INSERT INTO USERS(USERNAME, _PASSWORD) VALUES(?,?)"; //insert new row in users table
        log.info("Creating user: {} ", username);//log user creation to console

        try (
                //Connection connection = MeetingCalendarDBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);//BECAUSE WE HAVE PARAMETERS
        ) {
            User user = new User(username);//create user object using username, password will be randomly generated
            user.newPassword();
            preparedStatement.setString(1, user.getUsername());//set username
            preparedStatement.setString(2, user.getHashedPassword());//set password
            int affectedRows = preparedStatement.executeUpdate();//executeUpdate because we are inserting/modifying a row
            if (affectedRows == 0) {//check if insert was successful
                String errorMessage = "Creating user failed, no rows affected.";
                log.error(errorMessage);//log error if insert was not successful
                throw new MySQLException(errorMessage);
            }
            return user;//if it was not null return user
        } catch (SQLException e) {
            String errorMessage = "Error occurred while creating user: " + username;
            log.error(errorMessage);
            throw new MySQLException(errorMessage, e);//("Error occurred while creating user" + username);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String query = "SELECT * FROM USERS WHERE USERNAME = ?";//find user by username

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);//prepare statement
        ) {

            preparedStatement.setString(1, username);//set @Param || parameter username
            ResultSet resultSet = preparedStatement.executeQuery();//executeQuery returnType: ResultSet

            if (resultSet.next()) {//check if(resultSet.next()) has value then we can access username and _password
                String foundUsername = resultSet.getString("USERNAME");//get username and assign it to Java object
                String foundPassword = resultSet.getString("_PASSWORD");//name of column is _PASSWORD
                boolean foundExpired = resultSet.getBoolean("EXPIRED");//foundExpired column is boolean and assign it to Java object
                User user = new User(foundUsername, foundPassword, foundExpired);//create user object using username, password and foundExpired
                return Optional.of(user);//put it in Optional to check it if it is empty
            } else {//if resultSet does not exist return empty and returnType is Optional so cannot return null
                return Optional.empty();//return Optional
            }

        } catch (SQLException e) {//e is the error code for SQLException or MySQLException in this case
            throw new MySQLException("Error occurred while finding user by username: " + username, e);
        }
    }

    @Override
    public boolean authenticate(User user) throws AuthenticationFailedException, UserExpiredException { //  (admin - 123456)
        String query = "SELECT * FROM USERS WHERE USERNAME = ?";//STEP1: DEFINE QUERY || SELECT * FROM USERS WHERE USERNAME = ?
        log.info("authenticate user: {}:", user.getUsername());
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);//STEP2: PREPARE STATEMENT
        ) {
            preparedStatement.setString(1, user.getUsername());//STEP3: WHERE DO I GET THE VALUE FROM MY PARAM: user.getUsername()
            ResultSet resultSet = preparedStatement.executeQuery();//STEP4: EXECUTE QUERY
            if (resultSet.next()) {//STEP5: check if user exists|| checking the resultSet if it has value if our result set exists
                boolean isExpired = resultSet.getBoolean("EXPIRED");//STEP6: check if user is expired
                if (isExpired) {
                    log.warn("User is Expired. username: {}", user.getUsername());
                    throw new UserExpiredException("User is Expired. username: " + user.getUsername());//IF 'resultSet' was null
                }
                String hashedPassword = resultSet.getString("_PASSWORD");
                user.checkHash(hashedPassword);
            } else {
                log.warn("Authentication failed. Invalid credentials.");
                throw new AuthenticationFailedException("Authentication failed. Invalid credentials.");
            }
            return true;//step9: if we don't throw any exception then return true
        } catch (SQLException e) {
            log.error("Error occurred while authenticating user by username: {}", user.getUsername(), e);
            throw new MySQLException("Error occurred while authenticating user by username: " + user.getUsername(), e);
            //if there was something wrong with executing query then throw MySQLException
        }
    }
}