package repository.dao;

import model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();

    void save();

    List<String> getUserByFullName(String fullName);

    void updateUserInfo(User newUser, User oldUser);

    boolean addUser(User user);

    boolean deleteUser(User user);
}
