package service.validator.impl;

import service.validator.EmailValidator;

public class DefaultEmailValidator implements EmailValidator {

    @Override
    public boolean validate(String email) {
        return email.matches("^.+@.+\\..+$");
    }
}
