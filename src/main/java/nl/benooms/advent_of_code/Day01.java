package nl.benooms.advent_of_code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01 {
    public static void main(String[] args) throws IOException {
        assertEquals(24000, part1("Day01_test"));
        assertEquals(45000, part2("Day01_test"));
        System.out.println(part1("Day01"));
        System.out.println(part2("Day01"));
    }

    private static Integer part1(String input) throws IOException {
        var suppliesPerElf = Files.readString(Path.of("src/main/resources/Day01_test.txt")).split("\n\n");
        return Arrays.stream(suppliesPerElf)
                .map( supplies -> Arrays.stream(supplies.split("\n"))
                        .mapToInt(Integer::parseInt).sum())
                .max(Comparator.naturalOrder()).orElseGet(() -> 0);
    }

    private static Integer part2(String input) throws IOException {
        var suppliesPerElf = Files.readString(Path.of("src/main/resources/Day01_test.txt")).split("\n\n");
        var caloriesPerElf = Arrays.stream(suppliesPerElf)
                            .map( supplies -> Arrays.stream(supplies.split("\n"))
                            .mapToInt(Integer::parseInt).sum()).sorted().toList();
        return caloriesPerElf.subList(caloriesPerElf.size()-3, caloriesPerElf.size()).stream()
                .reduce((acc, value) -> acc+value).orElseGet(() -> 0);
    }
}
