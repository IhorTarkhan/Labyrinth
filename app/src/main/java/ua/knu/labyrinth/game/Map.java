package ua.knu.labyrinth.game;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private Point rootTopLeft;

    public Map(Point rootTopLeft) {
        this.rootTopLeft = rootTopLeft;
    }

    public static void generateMap(int size){
        Point rightBottom = Point.builder().build("a" + size + size);

        Point currentRowPoint = rightBottom;

        for (int i = 0; i < size; i++) {
            Point nextLeft = Point.builder()
                    .right(currentRowPoint, false)
                    .build("a");
            currentRowPoint = nextLeft;
        }

        Point currentColumnPoint = rightBottom;
        for (int i = 0; i < size; i++) {
            Point nextTop = Point.builder()
                    .bottom(currentColumnPoint, false)
                    .build("a");
            currentColumnPoint = nextTop;
        }
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
1 5 6 7
2 4 5 3
3 3 2 3
4 3 6 3
*/