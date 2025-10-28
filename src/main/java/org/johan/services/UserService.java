package org.johan.services;

import org.johan.models.User;
import java.util.*;

/**
 * Service class for managing users.
 * Provides CRUD operations and stores users in memory for demonstration.
 */
public class UserService {

    private final List<User> users = new ArrayList<>();

    public UserService() {
        // Preload sample users
        users.add(new User("Rafael Torres", "rafael@mail.com"));
        users.add(new User("Sofía Jiménez", "sofia@mail.com"));
        users.add(new User("Carlos Méndez", "carlos@mail.com"));
    }

    /** Retrieve all users **/
    public List<User> getAllUsers() {
        return users;
    }

    /** Retrieve a user by ID **/
    public User getUserById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /** Create and add a new user **/
    public User addUser(User newUser) {
        users.add(newUser);
        return newUser;
    }

    /** Delete a user by ID **/
    public boolean deleteUserById(String id) {
        return users.removeIf(u -> u.getId().equals(id));
    }

    /** Update a user's information **/
    public User updateUser(String id, User updatedUser) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                return user;
            }
        }
        return null;
    }
}