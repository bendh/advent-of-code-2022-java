package nl.benooms.advent_of_code.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReader {

    private static final String INPUT_PATH = "src/main/resources";
    public static List<String> getLines(String input) throws IOException {
        var path = Path.of( INPUT_PATH, input);
        return Files.readAllLines(path);
    }

    public static String getText(String input) throws IOException {
        var path = Path.of( INPUT_PATH, input);
        return Files.readString(path);
    }
}
