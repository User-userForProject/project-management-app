package by.tech.project_management_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.tech.project_management_app.entities.User;
import by.tech.project_management_app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            checkIfUserExists(id);
        } catch (ServiceException ex) {
            throw new ServiceException("User is not found");
        }
        userRepository.deleteUser(id);
    }

    @Override
    public User create(User user) {
        // not implemented
        return null;
    }

    @Override
    public void edit(User user) {
        //not implemented
    }

    @Override
    public User findById(int id) throws ServiceException {
        try {
            return userRepository.findUserById(id);
        } catch (Exception ex) {
            throw new ServiceException("User is not found");
        }
    }

    private void checkIfUserExists(int id) throws ServiceException {
        int countProject = userRepository.countUserId(id);
        if (countProject == 0) {
            throw new ServiceException();
        }
    }
}