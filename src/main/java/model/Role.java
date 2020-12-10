package model;

public class Role {
    public enum FirstLevel {
        USER,
        CUSTOMER
    }
    public enum SecondLevel {
        PROVIDER,
        ADMIN
    }
    public enum ThirdLevel {
        SUPER_ADMIN
    }

    private FirstLevel firstLevel;
    private SecondLevel secondLevel;
    private ThirdLevel thirdLevel;

    public Role(FirstLevel firstLevel, SecondLevel secondLevel) {
        this.firstLevel = firstLevel;
        this.secondLevel = secondLevel;
    }

    public Role(ThirdLevel thirdLevel) {
        this.thirdLevel = thirdLevel;
    }

    public FirstLevel getFirstLevel() {
        return firstLevel;
    }

    public SecondLevel getSecondLevel() {
        return secondLevel;
    }

    public ThirdLevel getThirdLevel() {
        return thirdLevel;
    }

    @Override
    public String toString() {
        return "Role{" +
                "firstLevel=" + firstLevel +
                ", secondLevel=" + secondLevel +
                ", thirdLevel=" + thirdLevel +
                '}';
    }
}
