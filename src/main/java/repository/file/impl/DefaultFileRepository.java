package repository.file.impl;

import repository.file.FileRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultFileRepository implements FileRepository {

    private final String fileName;

    public DefaultFileRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<String> readAll() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            bufferedReader.readLine(); // headers
            String line = bufferedReader.readLine();
            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    //            bufferedWriter.write("first_name,second_name,mail,roles,phones"); // headers
    @Override
    public void write(List<String> lines) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write("first_name,second_name,mail,roles,phones"); // headers
            bufferedWriter.newLine();
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
