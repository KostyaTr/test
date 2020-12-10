package repository.dao.impl;

import model.User;
import repository.converter.UserConverter;
import repository.file.FileRepository;
import repository.dao.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultUserRepository implements UserRepository {

    private final FileRepository fileRepository;
    private final List<User> users;

    public DefaultUserRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.users = fileRepository.readAll()
                .stream()
                .map(UserConverter::fromLine)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsers() {
        return this.users;
    }

    @Override
    public void save() {
        fileRepository.write(
                users.stream()
                        .map(UserConverter::fromUser)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<String> getUserByFullName(String fullName) {
        return null;
    }

    @Override
    public void updateUserInfo(User newUser, User oldUser) {
        if (!newUser.equals(oldUser)) {
            this.users.set(this.users.indexOf(oldUser), newUser);
        }
    }

    @Override
    public boolean addUser(User user) {
        return this.users.add(user);
    }

    @Override
    public boolean deleteUser(User user) {
        return this.users.remove(user);
    }
}
