package service.input.impl;

import model.Role;
import model.User;
import repository.dao.UserRepository;
import service.input.InputWithBufferedReader;
import service.validator.EmailValidator;
import service.validator.PhoneValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultInputWithBufferedReader implements InputWithBufferedReader {

    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    private final PhoneValidator phoneValidator;

    public DefaultInputWithBufferedReader(UserRepository userRepository, EmailValidator emailValidator,
                                          PhoneValidator phoneValidator) {

        this.userRepository = userRepository;
        this.emailValidator = emailValidator;
        this.phoneValidator = phoneValidator;
    }

    @Override
    public void run() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("1. Get all users");
            System.out.println("2. Get user");
            System.out.println("3. Update user info");
            System.out.println("4. Add user");
            System.out.println("5. Delete user");
            System.out.println("6. Exit");
            System.out.print("Please select an item from 1 to 6: ");
            switch (reader.readLine()) {
                case "1":
                    getAllUsers();
                    break;
                case "2":
                    getUser(reader);
                    break;
                case "3":
                    updateUserInfo(reader);
                    break;
                case "4":
                    addUser(reader);
                    break;
                case "5":
                    deleteUser(reader);
                    break;
                case "6":
                    exit();
                default:
                    System.out.println("Incorrect Input");
            }
        }
    }

    @Override
    public void getAllUsers() {
        userRepository.getAllUsers()
                .forEach(user -> System.out.println(user.toString()));
    }

    @Override
    public void getUser(BufferedReader reader) throws IOException {
        System.out.print("Please enter user's full name (first name + last name): ");
        getUserByFullName(reader.readLine())
                .forEach(user -> System.out.println(user.toString()));
    }

    @Override
    public boolean updateUserInfo(BufferedReader reader) throws IOException {
        System.out.print("Please enter the full name of the user you want to update (first name + last name): ");
        List<User> matchingUsers = getUserByFullName(reader.readLine());
        if (matchingUsers.size() == 0) {
            System.out.println("No user was found with this name was found");
            return false;
        } else {
            if (matchingUsers.size() == 1) {
                return update(reader, matchingUsers.get(0));
            } else {
                System.out.println("Several users with this name were found");
                matchingUsers.forEach(user -> System.out.println(user.toString()));
                while (true){
                    System.out.print("Please select the user you want to update (1,2,3..): ");
                    int answer;
                    try {
                        answer = Integer.parseInt(reader.readLine());
                    } catch (NumberFormatException e){
                        System.out.println("Please enter a number");
                        continue;
                    }
                    if (answer >= 1 && answer <= matchingUsers.size()) {
                        return update(reader, matchingUsers.get(answer - 1));
                    } else {
                        System.out.println("Please enter a valid number");
                    }
                }
            }
        }
    }

    @Override
    public boolean addUser(BufferedReader reader) throws IOException {
        User user = new User();
        System.out.print("Enter user's first name: ");
        user.setFirstName(reader.readLine());
        System.out.print("Enter user's last name: ");
        user.setLastName(reader.readLine());

        System.out.print("Enter user's mail: ");
        user.setMail(getMail(reader));
        user.setRoles(getRoles(reader));

        System.out.print("Enter user's phone number: ");
        user.setPhoneNumbers(getPhoneNumbers(reader));

        return userRepository.addUser(user);
    }

    @Override
    public boolean deleteUser(BufferedReader reader) throws IOException {
        System.out.print("Please enter the full name of the user you want to delete (first name + last name): ");
        List<User> matchingUsers = getUserByFullName(reader.readLine());
        if (matchingUsers.size() == 0) {
            System.out.println("No user was found with this name was found");
            return false;
        } else {
            if (matchingUsers.size() == 1) {
                return confirmDelete(reader, matchingUsers.get(0));
            } else {
                System.out.println("Several users with this name were found");
                matchingUsers.forEach(user -> System.out.println(user.toString()));
                while (true) {
                    System.out.print("Please select the user you want to delete (1,2,3..): ");
                    int answer;
                    try {
                        answer = Integer.parseInt(reader.readLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a number");
                        continue;
                    }
                    if (answer >= 1 && answer <= matchingUsers.size()) {
                        return confirmDelete(reader, matchingUsers.get(answer - 1));
                    } else {
                        System.out.println("Please enter a valid number");
                    }
                }
            }
        }
    }

    @Override
    public void exit() {
        userRepository.save();
        System.exit(0);
    }

    private Role getRoles(BufferedReader reader) throws IOException {
        Role.FirstLevel firstLevelRole = null;
        Role.SecondLevel secondLevelRole = null;
        Role.ThirdLevel thirdLevelRole = null;
        boolean done = false;
        System.out.println("Choose role level");
        while (!done) {
            System.out.println("1. First Level " + Arrays.toString(Role.FirstLevel.values()));
            System.out.println("2. Second Level " + Arrays.toString(Role.SecondLevel.values()));
            System.out.println("3. Third Level " + Arrays.toString(Role.ThirdLevel.values()));
            System.out.println("4. Done");
            System.out.print("Choose option: ");
            switch (reader.readLine()) {
                case "1":
                    if (firstLevelRole != null) {
                        System.out.println("You have already entered first level role");
                        break;
                    }
                    Arrays.stream(Role.FirstLevel.values())
                            .forEach(firstLevel -> System.out.println(firstLevel.name() + " -- " + firstLevel.ordinal()));
                    System.out.print("Choose role: ");
                    while (firstLevelRole == null) {
                        try {
                            firstLevelRole = Role.FirstLevel.values()[Integer.parseInt(reader.readLine())];
                        } catch (NumberFormatException ex) {
                            System.out.println("Enter a number");
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            System.out.println("Enter a valid number");
                        }
                    }
                    break;
                case "2":
                    if (secondLevelRole != null) {
                        System.out.println("You have already entered second level role");
                        break;
                    }
                    Arrays.stream(Role.SecondLevel.values())
                            .forEach(secondLevel -> System.out.println(secondLevel.name() + " -- " + secondLevel.ordinal()));
                    System.out.print("Choose role: ");
                    while (secondLevelRole == null) {
                        try {
                            secondLevelRole = Role.SecondLevel.values()[Integer.parseInt(reader.readLine())];
                        } catch (NumberFormatException ex) {
                            System.out.println("Enter a number");
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            System.out.println("Enter a valid number");
                        }
                    }
                    break;
                case "3":
                    if (firstLevelRole != null || secondLevelRole != null) {
                        System.out.println("You have already entered first level or second level role");
                        break;
                    }
                    Arrays.stream(Role.ThirdLevel.values())
                            .forEach(thirdLevel -> System.out.println(thirdLevel.name() + " -- " + thirdLevel.ordinal()));
                    System.out.print("Choose role: ");
                    while (thirdLevelRole == null) {
                        try {
                            thirdLevelRole = Role.ThirdLevel.values()[Integer.parseInt(reader.readLine())];
                        } catch (NumberFormatException ex) {
                            System.out.println("Enter a number");
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            System.out.println("Enter a valid number");
                        }
                    }
                    done = true;
                    break;
                case "4":
                    if (firstLevelRole == null && secondLevelRole == null && thirdLevelRole == null) {
                        System.out.println("You have not chosen any role");
                    } else {
                        done = true;
                    }
                    break;
                default:
                    System.out.println("Incorrect input");
            }
        }
        if (thirdLevelRole != null) {
            return new Role(thirdLevelRole);
        } else {
            return new Role(firstLevelRole, secondLevelRole);
        }
    }

    private List<String> getPhoneNumbers(BufferedReader reader) throws IOException {
        List<String> phoneNumbers = new ArrayList<>(3);
        phoneNumbers.add(getPhoneNumber(reader));
        for (int i = 0; i < 2; i++) {
            System.out.print("Do you want to add another phone number? (y/n) ");
            switch (reader.readLine()) {
                case "y":
                    System.out.print("Enter another user's phone number: ");
                    phoneNumbers.add(getPhoneNumber(reader));
                    break;
                case "n":
                    i = 2; // to exit loop
                    break;
                default:
                    System.out.println("Incorrect input");
                    i--;
            }
        }
        return phoneNumbers;
    }

    private String getPhoneNumber(BufferedReader reader) throws IOException {
        String phoneNumber;
        while (true) {
            phoneNumber = reader.readLine();
            if (phoneValidator.validate(phoneNumber)) {
                break;
            }
            System.out.print("Incorrect input. Example: 37500 1234567. Please enter again: ");
        }
        return phoneNumber;
    }

    private String getMail(BufferedReader reader) throws IOException {
        String mail;
        while (true) {
            mail = reader.readLine();
            if (emailValidator.validate(mail)) {
                break;
            }
            System.out.print("Incorrect input. Example: any@email.com. Please enter again: ");
        }
        return mail;
    }

    private List<User> getUserByFullName(String fullName) {
        return userRepository.getAllUsers()
                .stream()
                .filter(user -> (user.getFirstName() + " " + user.getLastName()).equals(fullName))
                .collect(Collectors.toList());
    }

    private boolean confirmDelete(BufferedReader reader, User user) throws IOException {
        System.out.println(user.toString());
        while (true) {
            System.out.print("You want to delete this user? (y/n): ");
            switch (reader.readLine()) {
                case "y":
                    return userRepository.deleteUser(user);
                case "n":
                    return false;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }

    private boolean update(BufferedReader reader, User user) throws IOException {
        User newUser = user;
        while (true) {
            System.out.println(newUser.toString());
            System.out.println("1. Change user's first name");
            System.out.println("2. Change user's last name");
            System.out.println("3. Change user's mail");
            System.out.println("4. Change user's roles");
            System.out.println("5. Change user's phone numbers");
            System.out.println("6. Finish update");
            System.out.println("7. Cancel update");
            System.out.print("Please select an item from 1 to 7: ");
            switch (reader.readLine()) {
                case "1":
                    System.out.print("Enter user's new first name: ");
                    newUser.setFirstName(reader.readLine());
                    break;
                case "2":
                    System.out.print("Enter user's new last name: ");
                    newUser.setLastName(reader.readLine());
                    break;
                case "3":
                    System.out.print("Enter user's new mail (Example: any@email.com): ");
                    newUser.setMail(getMail(reader));
                    break;
                case "4":
                    user.setRoles(getRoles(reader));
                    break;
                case "5":
                    System.out.print("Enter user's new phone number: ");
                    user.setPhoneNumbers(getPhoneNumbers(reader));
                    break;
                case "6":
                    userRepository.updateUserInfo(newUser, user);
                    return true;
                case "7":
                    return false;
            }
        }
    }
}
