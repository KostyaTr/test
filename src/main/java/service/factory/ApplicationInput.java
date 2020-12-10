package service.factory;

import service.input.Input;

public abstract class ApplicationInput {
    public abstract Input getInput(String fileName);
}
