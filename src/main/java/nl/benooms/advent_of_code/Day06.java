package nl.benooms.advent_of_code;

import nl.benooms.advent_of_code.utils.FileReader;

import java.io.IOException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06 {

    public static void main(String[] args) throws Exception {
        assertEquals(7, part1("Day06_test.txt"));
        assertEquals(19, part2("Day06_test.txt"));
        System.out.println(part1("Day06.txt"));
        System.out.println(part2("Day06.txt"));
    }

    private static Integer part1(String input) throws IOException {
        var text = FileReader.getText(input);
        for(int i =0; i < text.length(); i++) {
            var string = text.substring(i, i+4);
            var set = new HashSet<>(string.chars().boxed().toList());
            if (set.size() == 4) {
                return i + 4;
            }
        }
        return 0;
    }

    private static Integer part2(String input) throws IOException {
        var text = FileReader.getText(input);
        for(int i =0; i < text.length(); i++) {
            var string = text.substring(i, i+14);
            var set = new HashSet<>(string.chars().boxed().toList());
            if (set.size() == 14) {
                return i + 14;
            }
        }
        return 0;
    }
}
