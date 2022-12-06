package nl.benooms.advent_of_code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02 {

    private static final int DRAW = 3;
    private static final int WIN = 6;
    private static final int LOSE = 0;

    private static final Function<String, GameInput> opponentInputMapper = round -> GameInput.getByOpponentCode(round.substring(0,1));
    private static final Function<String, GameInput> playerInputMapper = round -> GameInput.getByPlayerCode(round.substring(2));
    public static void main(String[] args) throws Exception {
        assertEquals(15, part1("Day02_test"));
        assertEquals(12, part2("Day02_test"));
        System.out.println(part1("Day02"));
        System.out.println(part2("Day02"));
    }

    private static Integer part1(String input) throws IOException {
        var rounds = Files.readAllLines(Path.of("src/main/resources/Day02_test.txt"));
        return rounds.stream()
                .map(round -> {
                    var opponentFace = opponentInputMapper.apply(round);
                    var playerFace = playerInputMapper.apply(round);
                    return calculateScore(opponentFace, playerFace);
                })
                .reduce(Integer::sum).orElse(0);
    }

    private static Integer part2(String input) throws Exception {
        var rounds = Files.readAllLines(Path.of("src/main/resources/Day02_test.txt"));
        return rounds.stream()
                .map(round -> {
                    var opponentFace = opponentInputMapper.apply(round);
                    GameInput playerFace;
                    try {
                        playerFace = getFaceToPlay(opponentFace, round.substring(2));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return calculateScore(opponentFace, playerFace);
                })
                .reduce(Integer::sum).orElse(0);
    }

    enum GameInput {
        ROCK("A", "X", 1),
        PAPER("B", "Y", 2),
        SCISSOR("C", "Z", 3);
        private final String opponentCode;
        private final String playerCode;
        private final int faceValue;

        GameInput(String opponentCode, String playerCode, int faceValue) {
            this.opponentCode = opponentCode;
            this.playerCode = playerCode;
            this.faceValue = faceValue;
        }

        public static GameInput getByOpponentCode(String code) {
            for (GameInput gameInput : GameInput.values()) {
                if (gameInput.opponentCode.equals(code)) return gameInput;
            }
            throw new NoSuchElementException("Game input with code "+code+" not found");
        }
        public static GameInput getByPlayerCode(String code) {
            for (GameInput gameInput : GameInput.values()) {
                if (gameInput.playerCode.equals(code)) return gameInput;
            }
            throw new NoSuchElementException("Game input with code "+code+" not found");
        }
        public int getFaceValue() {
            return faceValue;
        }
    }

    private static Integer calculateScore(GameInput opponent, GameInput player) {
        var gameScore = switch (opponent) {
                    case ROCK -> switch (player) {
                        case ROCK -> DRAW;
                        case PAPER -> WIN;
                        case SCISSOR -> LOSE;
                    };
                    case PAPER -> switch (player) {
                        case ROCK -> LOSE;
                        case PAPER -> DRAW;
                        case SCISSOR -> WIN;
                    };
                    case SCISSOR -> switch (player) {
                        case ROCK -> WIN;
                        case PAPER -> LOSE;
                        case SCISSOR -> DRAW;
                    };
        };
        return gameScore + player.getFaceValue();
    }

    private static GameInput getFaceToPlay(GameInput opponent, String strategy) throws Exception {
        return switch (opponent) {
            case ROCK -> switch(strategy) {
                case "X" -> GameInput.SCISSOR;
                case "Y" -> GameInput.ROCK;
                case "Z" -> GameInput.PAPER;
                default -> throw new Exception("Strategy not found");
            };
            case PAPER -> switch(strategy) {
                case "X" -> GameInput.ROCK;
                case "Y" -> GameInput.PAPER;
                case "Z" -> GameInput.SCISSOR;
                default -> throw new Exception("Strategy not found");
            };
            case SCISSOR -> switch(strategy) {
                case "X" -> GameInput.PAPER;
                case "Y" -> GameInput.SCISSOR;
                case "Z" -> GameInput.ROCK;
                default -> throw new Exception("Strategy not found");
            };
        };
    }
}
