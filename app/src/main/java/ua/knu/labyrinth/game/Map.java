package ua.knu.labyrinth.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private final Point rootTopLeft;
    Integer size;

    public Map(Point rootTopLeft) {
        this.rootTopLeft = rootTopLeft;
        this.size = 1;
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

    private void accessibleTops(Point current, List<Point> connected) {

        Point left = current.getLeft();
        Point right = current.getRight();
        Point bottom = current.getBottom();
        Point top = current.getTop();

        if (!current.isBorderLeft() && left != null) {
            if (!connected.contains(left)) {
                connected.add(left);
                accessibleTops(left, connected);
            }
        }
        if (!current.isBorderRight() && right != null) {
            if (!connected.contains(right)) {
                connected.add(right);
                accessibleTops(right, connected);
            }
        }
        if (!current.isBorderBottom() && bottom != null) {
            if (!connected.contains(bottom)) {
                connected.add(bottom);
                accessibleTops(bottom, connected);
            }
        }
        if (!current.isBorderTop() && top != null) {
            if (!connected.contains(top)) {
                connected.add(top);
                accessibleTops(top, connected);
            }
        }
    }

    private boolean allTopsAreConnected() {
        List<Point> connectedTops = new ArrayList<>();
        accessibleTops(rootTopLeft, connectedTops);
        return connectedTops.size() == size * size;
    }

    private Point getPoint(int xPosition, int yPosition) {
        Point result = rootTopLeft;
        for (int x = 0; x < xPosition; x++) {
            result = result.getRight();
        }
        for (int y = 0; y < yPosition; y++) {
            result = result.getBottom();
        }
        return result;
    }

    private void createBorder(Point point, Direction direction) {
        switch (direction) {
            case LEFT:
                point.setBorderLeft(true);
                break;
            case RIGHT:
                point.setBorderRight(true);
                break;
            case TOP:
                point.setBorderTop(true);
                break;
            case BOTTOM:
                point.setBorderBottom(true);
                break;
        }
    }

    private void deleteBorder(Point point, Direction direction) {
        switch (direction) {
            case LEFT:
                point.setBorderLeft(false);
                break;
            case RIGHT:
                point.setBorderRight(false);
                break;
            case TOP:
                point.setBorderTop(false);
                break;
            case BOTTOM:
                point.setBorderBottom(false);
                break;
        }
    }

    public void generateBordersEasy() {
        Random random = new Random();
        for (int i = 1; i < size; i++) {
            for (int j = 1; j <= i; j++) {
                createBorder(getPoint(i, j), Direction.RIGHT);
                createBorder(getPoint(j, i), Direction.BOTTOM);
            }
            int position = random.nextInt(i + 1);
            boolean isX = random.nextBoolean();
            if (isX) {
                deleteBorder(getPoint(position, i), Direction.RIGHT);
            } else {
                deleteBorder(getPoint(i, position), Direction.BOTTOM);
            }
        }
    }
/*
    public void generateBorderHard() {
        Point rowIterator = this.rootTopLeft;
        int numberOfBorders = (size - 1) * (size - 1);
        int currentNumberOfBorder = 0;
        while (rowIterator != null) {
            Point iterator = rowIterator;
            while (iterator.getRight() != null) {
                iterator = iterator.getRight();
            }
            rowIterator = rowIterator.getBottom();
        }
    }*/

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