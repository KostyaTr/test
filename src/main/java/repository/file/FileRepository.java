package repository.file;

import java.util.List;

public interface FileRepository {
    List<String> readAll();

    void write(List<String> lines);
}
