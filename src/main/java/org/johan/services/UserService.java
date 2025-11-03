package org.johan.services;

import java.util.ArrayList;
import java.util.List;

import org.johan.models.User;

// Nivel C2: Separamos la "lógica de negocio" del controlador.
// Este servicio FINGE ser la base de datos.
public class UserService {

    // Nuestra base de datos Falsa
    private List<User> users = new ArrayList<>();

    public UserService() {
        // Datos de prueba para el Sprint 1
        users.add(new User("1", "Rafael", "rafael@coleccionista.com"));
        users.add(new User("2", "Ramón", "ramon@eventos.com"));
        users.add(new User("3", "Sofía", "sofia@experta.com"));
    }

    public List<User> getAllUsers() {
        return this.users;
    }

    public User getUserById(String id) {
        // Usamos Java Streams (funcionalidad avanzada C1/C2)
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null); // (Manejaremos el error 404 en Sprint 2)
    }

    public User createUser(User user) {
        // En una BD real, el ID sería autogenerado
        users.add(user);
        return user;
    }

    public User updateUser(String id, User updatedUserData) {
        User userToUpdate = getUserById(id);
        if (userToUpdate != null) {
            userToUpdate.setName(updatedUserData.getName());
            userToUpdate.setEmail(updatedUserData.getEmail());
            return userToUpdate;
        }
        return null;
    }

    public boolean deleteUser(String id) {
        return users.removeIf(user -> user.getId().equals(id));
    }
    
    public boolean userExists(String id) {
        return users.stream().anyMatch(user -> user.getId().equals(id));
    }
}