package nl.benooms.advent_of_code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntUnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03 {

    public static final IntUnaryOperator charCodeToValue = chars -> chars >= 97 ? chars - 96 : chars - 38;

    public static void main(String[] args) throws Exception {
        assertEquals(157, part1("Day03_test.txt"));
        assertEquals(70, part2("Day03_test.txt"));
        System.out.println(part1("Day03.txt"));
        System.out.println(part2("Day03.txt"));
    }

    private static Integer part1(String input) throws IOException {
        var rucksacks = Files.readAllLines(Path.of("src/main/resources", input));
        return rucksacks.stream()
                .map(rucksack -> {
                    var compartment1 = rucksack.substring(0, rucksack.length()/2).chars();
                    var compartment2 = rucksack.substring(rucksack.length() / 2).chars().boxed().toList();
                    return compartment1
                            .distinct()
                            .filter(compartment2::contains)
                            .map(charCodeToValue)
                            .sum();
                }).reduce(Integer::sum).orElse(0);
    }

    private static Integer part2(String input) throws Exception {
        var rucksacks = Files.readAllLines(Path.of("src/main/resources", input));
        var chunks = createChunksOfThree(rucksacks);
        return chunks.stream().map(chunk-> {
            var elf1Content = chunk.get(0).chars();
            var elf2Content = chunk.get(1).chars().boxed().toList();
            var elf3Content = chunk.get(2).chars().boxed().toList();
            return elf1Content
                    .distinct()
                    .filter(c -> elf2Content.contains(c) && elf3Content.contains(c))
                    .map(charCodeToValue)
                    .sum();
        }).reduce(Integer::sum).orElse(0);
    }

    private static List<List<String>> createChunksOfThree(List<String> input) {
        var result = new ArrayList<List<String>>();
        var tmpList = new ArrayList<String>();
        for (String string : input) {
            tmpList.add(string);
            if (tmpList.size() == 3) {
                result.add(List.copyOf(tmpList));
                tmpList.clear();
            }
        }
        return result;
    }

}
