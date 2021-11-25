package by.tech.project_management_app.repository;

import java.util.List;

import by.tech.project_management_app.entities.User;

public interface UserRepository {
    User findByUsername(String userName);
    List<User> findAll();
    void deleteUser(int id);
    User findUserById(int id);
    int countUserId(int id);
}