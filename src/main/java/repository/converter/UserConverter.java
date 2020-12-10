package repository.converter;

import model.User;

public class UserConverter {
    public static User fromLine(String line) {
        String [] split = line.split(",");
        return new User(
                split[0],
                split[1],
                split[2],
                RolesConverter.fromLine(split[3]),
                PhonesConverter.fromLine(split[4])
        );
    }

    public static String fromUser(User user){
        return user.getFirstName() + "," + user.getLastName() + "," + user.getMail() + ","
                + RolesConverter.fromRole(user.getRoles()) + ","
                + PhonesConverter.fromPhonesList(user.getPhoneNumbers());
    }
}
