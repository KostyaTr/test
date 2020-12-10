package service.validator;

public interface EmailValidator extends Validator {
    boolean validate(String email);
}
