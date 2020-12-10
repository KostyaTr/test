package repository.converter;

import model.Role;

public class RolesConverter {
    public static Role fromLine(String line) {
        String [] split = line.split(";");
        if(split.length > 1) {
            Role.FirstLevel firstLevel = split[0].equals("null") ? null : Role.FirstLevel.valueOf(split[0]);
            Role.SecondLevel secondLevel = split[1].equals("null") ? null : Role.SecondLevel.valueOf(split[1]);
            return new Role(firstLevel, secondLevel);
        } else {
            return new Role(Role.ThirdLevel.valueOf(split[0]));
        }
    }

    public static String fromRole(Role role) {
        if (role.getThirdLevel() == null) {
            return role.getFirstLevel() + ";" + role.getSecondLevel();
        } else {
            return role.getThirdLevel().toString();
        }
    }
}
