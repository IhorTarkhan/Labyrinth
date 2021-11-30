package ua.knu.labyrinth.game;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private final Point rootTopLeft;

    public Map(Point rootTopLeft) {
        this.rootTopLeft = rootTopLeft;
    }

    public static Map generateMap(int size) {
        Point rightBottom = Point.builder().build("a" + size + size);

        Point currentRow = rightBottom;
        for (int i = 0; i < size - 1; i++) {
            currentRow = Point.builder()
                    .right(currentRow, false)
                    .build("a");
        }

        Point currentColumn = rightBottom;
        for (int i = 0; i < size - 1; i++) {
            currentColumn = Point.builder()
                    .bottom(currentColumn, false)
                    .build("a");
        }

        currentColumn = rightBottom.getLeft();
        for (int i = size - 1; i > 0; i--) {
            Point currentBottom = currentColumn;
            Point currentRight = currentColumn.getRight().getTop();
            for (int j = size - 1; j > 0; j--) {
                Point current = Point.builder()
                        .bottom(currentBottom, false)
                        .right(currentRight, false)
                        .build("a");
                currentBottom = currentBottom.getTop();
                currentRight = currentRight.getTop();
                if (i == 1 && j == 1){
                    return new Map(current);
                }
            }
            currentColumn = currentColumn.getLeft();
        }
        throw new IllegalArgumentException();
    }

    public List<List<Point>> getMatrix() {
        List<List<Point>> result = new ArrayList<>();

        Point leftIterator = this.rootTopLeft;
        while (leftIterator != null) {
            List<Point> row = new ArrayList<>();
            result.add(row);
            Point iterator = leftIterator;
            row.add(iterator);
            while (iterator.getRight() != null) {
                row.add(iterator.getRight());
                iterator = iterator.getRight();
            }
            leftIterator = leftIterator.getBottom();
        }
        return result;
    }
}

/*
      7
      3
      3
4 3 6 3
*/