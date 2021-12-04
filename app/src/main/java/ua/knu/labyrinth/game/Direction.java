package ua.knu.labyrinth.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Direction {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT;

    private static final List<Direction> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Direction randomDirection(List<Direction> directions) {
        while (true) {
            Direction direction = VALUES.get(RANDOM.nextInt(SIZE));
            if (!directions.contains(direction)) {
                return direction;
            }
        }
    }
}
