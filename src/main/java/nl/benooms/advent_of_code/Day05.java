package nl.benooms.advent_of_code;

import nl.benooms.advent_of_code.utils.FileReader;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05 {

    public static void main(String[] args) throws Exception {
        assertEquals("CMZ", part1("Day05_test.txt"));
        assertEquals("MCD", part2("Day05_test.txt"));
        System.out.println(part1("Day05.txt"));
        System.out.println(part2("Day05.txt"));
    }

    private static String part1(String input) throws IOException {
        var lines = FileReader.getLines(input);
        var supplyStacks = readInitialSupplyStacks(lines);
        var craneCommands = lines.subList(lines.indexOf("")+1, lines.size());
        var commandRegex = Pattern.compile("^move\\s+(\\d+)\\s+from\\s+(\\d)+\\s+to\\s+(\\d)+");
        craneCommands.forEach(command -> {
            var matcher = commandRegex.matcher(command);
            if (matcher.find()) {
                var count = Integer.parseInt(matcher.group(1));
                var from = Integer.parseInt(matcher.group(2))-1;
                var to = Integer.parseInt(matcher.group(3))-1;
                IntStream.rangeClosed(1, count).forEach(value -> {
                    var supply = supplyStacks.get(from).removeFirst();
                    supplyStacks.get(to).addFirst(supply);
                });
            }
        });
        return getTopSuplies(supplyStacks);
    }

    private static String part2(String input) throws IOException {
        var lines = FileReader.getLines(input);
        var supplyStacks = readInitialSupplyStacks(lines);
        var craneCommands = lines.subList(lines.indexOf("")+1, lines.size());
        var commandRegex = Pattern.compile("^move\\s+(\\d+)\\s+from\\s+(\\d)+\\s+to\\s+(\\d)+");
        craneCommands.forEach(command -> {
            var matcher = commandRegex.matcher(command);
            if (matcher.find()) {
                var count = Integer.parseInt(matcher.group(1));
                var from = Integer.parseInt(matcher.group(2))-1;
                var to = Integer.parseInt(matcher.group(3))-1;
                var suppliesToMove = new ArrayDeque<String>();
                IntStream.rangeClosed(1, count).forEach(value -> suppliesToMove.add(supplyStacks.get(from).removeFirst()));
                while(!suppliesToMove.isEmpty()) supplyStacks.get(to).addFirst(suppliesToMove.removeLast());
            }
        });
        return getTopSuplies(supplyStacks);
    }

    private static String getTopSuplies(List<ArrayDeque<String>> supplyStacks) {
        return supplyStacks.stream()
                .map(stack -> {
                    if (!stack.isEmpty()) {
                        return stack.removeFirst();
                    } else {
                        return "";
                    }
                })
                .reduce((o, o2) -> o + o2)
                .orElse("");
    }

    private static List<ArrayDeque<String>> readInitialSupplyStacks(List<String> lines) {
        List<ArrayDeque<String>> supplyStacks = new ArrayList<>();
        // Get stack data
        var stackData = lines.subList(0, lines.indexOf(""));
        // Create list of stacks
        var stackCountData = stackData.get(stackData.size()-1);
        var stackCountRegex = Pattern.compile("^[\\s*\\d+]*(\\d+)\\s*$");
        var matcher = stackCountRegex.matcher(stackCountData);
        if (matcher.find()) {
            var lastStackNumber = Integer.parseInt(matcher.group(1));
            IntStream.rangeClosed(1, lastStackNumber).forEach(value -> supplyStacks.add(new ArrayDeque<>()));
        }
        // Fill stacks with initial supplies
        var supplyData = stackData.subList(0, stackData.size()-1);
        supplyData.forEach(line -> {
            for(int i=0;i<line.length();i=i+4) {
                if (i == 0) {
                    var subString = line.substring(0, i+4);
                    if (subString.startsWith("[")) {
                        supplyStacks.get(i).add(subString.substring(1,2));
                    }
                } else {
                    var subString = line.substring(i, i+4>= line.length()? line.length()-1 : i+4);
                    if (subString.startsWith("[")) {
                        supplyStacks.get(i/4).add(subString.substring(1,2));
                    }
                }
            }
        });
        return supplyStacks;
    }
}
