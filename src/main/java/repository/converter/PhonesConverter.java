package repository.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PhonesConverter {
    public static List<String> fromLine(String line) {
        String [] split = line.split(";");
        List<String> phones = new ArrayList<>(3);
        phones.addAll(Arrays.asList(split));
        return phones;
    }

    public static String fromPhonesList(List<String> phones) {
        return phones
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(";"));
    }
}
