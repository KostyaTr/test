package service.factory;

import repository.dao.impl.DefaultUserRepository;
import repository.file.impl.DefaultFileRepository;
import service.input.Input;
import service.input.impl.DefaultInputWithBufferedReader;
import service.validator.impl.DefaultEmailValidator;
import service.validator.impl.DefaultPhoneValidator;

public class DefaultApplicationInput extends ApplicationInput{

    @Override
    public Input getInput(String fileName) {
        return new DefaultInputWithBufferedReader(
                new DefaultUserRepository(new DefaultFileRepository(fileName)),
                new DefaultEmailValidator(),
                new DefaultPhoneValidator()
                );
    }
}
