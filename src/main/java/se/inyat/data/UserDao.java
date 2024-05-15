package se.inyat.data;

import se.inyat.exception.AuthenticationFailedException;
import se.inyat.exception.AuthenticationFailedException;
import se.inyat.exception.UserExpiredException;
import se.inyat.model.User;

import java.util.Optional;

public interface UserDao {
    User createUser(String username);

    Optional<User> findByUsername(String username);

    boolean authenticate(User user) throws UserExpiredException, AuthenticationFailedException;

    // add more if needed
}