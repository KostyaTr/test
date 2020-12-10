import service.factory.ApplicationInput;
import service.factory.DefaultApplicationInput;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        ApplicationInput applicationInput = new DefaultApplicationInput();
        try {
            applicationInput.getInput("users.csv").run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
