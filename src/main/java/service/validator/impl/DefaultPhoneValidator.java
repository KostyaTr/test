package service.validator.impl;

import service.validator.PhoneValidator;

public class DefaultPhoneValidator implements PhoneValidator {
    @Override
    public boolean validate(String phone) {
        return phone.matches("375[0-9]{2}\\s[0-9]{7}");
    }
}
