package nl.benooms.advent_of_code;

import nl.benooms.advent_of_code.utils.FileReader;

import java.io.IOException;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04 {

    private static IntStream getRangeFrom(String range) {
        var from = Integer.parseInt(range.split("-")[0]);
        var to = Integer.parseInt(range.split("-")[1]) + 1;
        return IntStream.range(from, to);
    }

    public static void main(String[] args) throws Exception {
        assertEquals(2, part1("Day04_test.txt"));
        assertEquals(4, part2("Day04_test.txt"));
        System.out.println(part1("Day04.txt"));
        System.out.println(part2("Day04.txt"));
    }

    private static Integer part1(String input) throws IOException {
        var lines = FileReader.getLines(input);
        return lines.stream().map(line -> {
            var ranges = line.split(",");
            var firstRange = getRangeFrom(ranges[0]).boxed().toList();
            var secondRange = getRangeFrom(ranges[1]).boxed().toList();
            if (firstRange.containsAll(secondRange) || secondRange.containsAll(firstRange)) {
                return 1;
            } else {
                return 0;
            }
        }).reduce(Integer::sum).orElse(0);
    }

    private static Integer part2(String input) throws Exception {
        var lines = FileReader.getLines(input);
        return lines.stream().map(line -> {
            var ranges = line.split(",");
            var firstRange = getRangeFrom(ranges[0]).boxed().toList();
            var secondRange = getRangeFrom(ranges[1]).boxed().toList();
            if (firstRange.stream().anyMatch(secondRange::contains)) {
                return 1;
            } else {
                return 0;
            }
        }).reduce(Integer::sum).orElse(0);
    }
}
