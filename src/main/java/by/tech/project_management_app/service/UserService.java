package by.tech.project_management_app.service;

import java.util.List;

import by.tech.project_management_app.entities.User;

public interface UserService {

    List<User> getAll();

    User findById(int id) throws ServiceException;

    User create(User user) throws ServiceException;

    void edit(User user) throws ServiceException;

    void delete(int id) throws ServiceException;

}