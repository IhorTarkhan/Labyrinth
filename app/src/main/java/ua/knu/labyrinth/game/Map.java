package ua.knu.labyrinth.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private final Point rootTopLeft;
    int size;

    public Map(Point rootTopLeft, int size) {
        this.rootTopLeft = rootTopLeft;
        this.size = size;
    }

    public static Map generateMap(int size) {
        Point rightBottom = Point.builder().build();

        Point currentRow = rightBottom;
        for (int i = 0; i < size - 1; i++) {
            currentRow = Point.builder()
                    .right(currentRow, false)
                    .build();
        }

        Point currentColumn = rightBottom;
        for (int i = 0; i < size - 1; i++) {
            currentColumn = Point.builder()
                    .bottom(currentColumn, false)
                    .build();
        }

        currentColumn = rightBottom.getLeft();
        for (int i = size - 1; i > 0; i--) {
            Point currentBottom = currentColumn;
            Point currentRight = currentColumn.getRight().getTop();
            for (int j = size - 1; j > 0; j--) {
                Point current = Point.builder()
                        .bottom(currentBottom, false)
                        .right(currentRight, false)
                        .build();
                currentBottom = currentBottom.getTop();
                currentRight = currentRight.getTop();
                if (i == 1 && j == 1) {
                    return new Map(current, size);
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

    public Point getPoint(int xPosition, int yPosition) {
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
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j <= i; j++) {
                createBorder(getPoint(i, j), Direction.RIGHT);
                createBorder(getPoint(j, i), Direction.BOTTOM);
            }
            int position = random.nextInt(i + 1);
            boolean isX = random.nextBoolean();
            if (isX) {
                deleteBorder(getPoint(i, position), Direction.RIGHT);
            } else {
                deleteBorder(getPoint(position, i), Direction.BOTTOM);
            }
        }
    }

    private boolean generateBorderSuccess(Point iterator) {
        List<Direction> directions = new ArrayList<>();
        while (directions.size() < 4) {
            Direction direction = Direction.randomDirection(directions);
            if (!iterator.isBorderDirection(direction)) {
                createBorder(iterator, direction);
                if (allTopsAreConnected()) {
                    return true;
                } else {
                    deleteBorder(iterator, direction);
                }
            }
            directions.add(direction);
        }
        return false;
    }

    public void generateBordersMedium() {

        Point leftTopIterator = this.rootTopLeft;
        Point rightTopIterator = leftTopIterator;
        while (rightTopIterator.getRight() != null) {
            rightTopIterator = rightTopIterator.getRight();
        }
        Point leftBottomIterator = leftTopIterator;
        while (leftBottomIterator.getBottom() != null) {
            leftBottomIterator = leftBottomIterator.getBottom();
        }
        Point rightBottomIterator = rightTopIterator;
        while (rightBottomIterator.getBottom() != null) {
            rightBottomIterator = rightBottomIterator.getBottom();
        }
        int numberOfBorders = (size - 1) * (size - 1);
        int currentNumberOfBorders = 0;
        Random random = new Random();

        if (random.nextBoolean()) {
            Point iterator = leftTopIterator;
            while (iterator != null) {
                if (generateBorderSuccess(iterator)) {
                    currentNumberOfBorders++;
                    if (currentNumberOfBorders == numberOfBorders) {
                        return;
                    }
                }
                iterator = iterator.getRight();
            }
        } else {
            Point iterator = rightTopIterator;
            while (iterator != null) {
                if (generateBorderSuccess(iterator)) {
                    currentNumberOfBorders++;
                    if (currentNumberOfBorders == numberOfBorders) {
                        return;
                    }
                }
                iterator = iterator.getLeft();
            }
        }
        if (random.nextBoolean()){
            Point iterator = leftBottomIterator;
            while (iterator != null) {
                if (generateBorderSuccess(iterator)) {
                    currentNumberOfBorders++;
                    if (currentNumberOfBorders == numberOfBorders) {
                        return;
                    }
                }
                iterator = iterator.getRight();
            }
        } else {
            Point iterator = rightBottomIterator;
            while (iterator != null) {
                if (generateBorderSuccess(iterator)) {
                    currentNumberOfBorders++;
                    if (currentNumberOfBorders == numberOfBorders) {
                        return;
                    }
                }
                iterator = iterator.getLeft();
            }
        }
        if (random.nextBoolean()){
            Point iterator = leftTopIterator;
            while (iterator != null) {
                if (generateBorderSuccess(iterator)) {
                    currentNumberOfBorders++;
                    if (currentNumberOfBorders == numberOfBorders) {
                        return;
                    }
                }
                iterator = iterator.getBottom();
            }
        } else {
            Point iterator = leftBottomIterator;
            while (iterator != null) {
                if (generateBorderSuccess(iterator)) {
                    currentNumberOfBorders++;
                    if (currentNumberOfBorders == numberOfBorders) {
                        return;
                    }
                }
                iterator = iterator.getTop();
            }
        }
        if (random.nextBoolean()){
            Point iterator = rightTopIterator;
            while (iterator != null) {
                if (generateBorderSuccess(iterator)) {
                    currentNumberOfBorders++;
                    if (currentNumberOfBorders == numberOfBorders) {
                        return;
                    }
                }
                iterator = iterator.getBottom();
            }
        } else {
            Point iterator = rightBottomIterator;
            while (iterator != null) {
                if (generateBorderSuccess(iterator)) {
                    currentNumberOfBorders++;
                    if (currentNumberOfBorders == numberOfBorders) {
                        return;
                    }
                }
                iterator = iterator.getTop();
            }
        }

        Point rowIterator = rootTopLeft.getBottom().getRight();
        while (rowIterator != null) {
            Point iterator = rowIterator;
            if (random.nextBoolean()) {
                while (iterator != null) {
                    if (generateBorderSuccess(iterator)) {
                        currentNumberOfBorders++;
                        if (currentNumberOfBorders == numberOfBorders) {
                            return;
                        }
                    }
                    iterator = iterator.getRight();
                }
            } else {
                while (iterator.getRight() != null) {
                    iterator = iterator.getRight();
                }
                while (iterator != null) {
                    if (generateBorderSuccess(iterator)) {
                        currentNumberOfBorders++;
                        if (currentNumberOfBorders == numberOfBorders) {
                            return;
                        }
                    }
                    iterator = iterator.getLeft();
                }
                rowIterator = rowIterator.getBottom();
            }
        }
    }

    public void generateBordersHard(){
        int numberOfBorders = (size - 1) * (size - 1);
        int currentNumberOfBorders = 0;
        int maxFailedCreating = size *size;
        int failedCreating = 0;
        Random random = new Random();
        while (failedCreating < maxFailedCreating){
            int xPosition = random.nextInt()%size;
            int yPosition = random.nextInt()%size;
            Point point = getPoint(xPosition,yPosition);
            if (generateBorderSuccess(point)){
                currentNumberOfBorders++;
                if (currentNumberOfBorders == numberOfBorders) {
                    return;
                }
            }else{
                failedCreating++;
            }
        }
        if (currentNumberOfBorders < numberOfBorders){
            generateBordersMedium();
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
