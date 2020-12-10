package service.input;

import java.io.BufferedReader;
import java.io.IOException;

public interface InputWithBufferedReader extends Input{
    boolean addUser(BufferedReader reader) throws IOException;

    void getUser(BufferedReader reader) throws IOException;

    void getAllUsers();

    boolean updateUserInfo(BufferedReader reader) throws IOException;

    boolean deleteUser(BufferedReader reader) throws IOException;

    void exit();
}
