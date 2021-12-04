package ua.knu.labyrinth.game;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private Point rootTopLeft;
    Integer size;

    public Map(Point rootTopLeft) {
        this.rootTopLeft = rootTopLeft;
        this.size = 1;
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

    private void accessibleTops(Point current, List<Point> connected){

        Point left = current.getLeft();
        Point right = current.getRight();
        Point bottom = current.getBottom();
        Point top = current.getTop();

        if (!current.isBorderLeft() && left != null){
            if (!connected.contains(left)){
                connected.add(left);
                accessibleTops(left, connected);
            }
        }
        if (!current.isBorderRight() && right != null){
            if (!connected.contains(right)){
                connected.add(right);
                accessibleTops(right, connected);
            }
        }
        if (!current.isBorderBottom() && bottom != null){
            if (!connected.contains(bottom)){
                connected.add(bottom);
                accessibleTops(bottom, connected);
            }
        }
        if (!current.isBorderTop() && top != null){
            if (!connected.contains(top)){
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

    private Point getPoint(int xPosition, int yPosition)
    {
        Point result = rootTopLeft;
        for (int x = 0; x < xPosition; x++) {
            result = result.getRight();
        }
        for (int y = 0; y < yPosition; y++) {
            result = result.getBottom();
        }
        return result;
    }

    private boolean createBorder(Point point, String direction){
        switch (direction){
            case "left":
                if (point.isBorderLeft()) {
                    return false;
                } else {
                    point.setBorderLeft(true);
                    return true;
                }
            case "right":
                if (point.isBorderRight()) {
                    return false;
                } else {
                    point.setBorderRight(true);
                    return true;
                }
            case "top":
                if (point.isBorderTop()) {
                    return false;
                } else {
                    point.setBorderTop(true);
                    return true;
                }
            case "bottom":
                if (point.isBorderBottom()) {
                    return false;
                } else {
                    point.setBorderBottom(true);
                    return true;
                }
            default:
                return false;
        }
    }

    private boolean deleteBorder(Point point, String direction){
        switch (direction){
            case "left":
                if (point.isBorderLeft()) {
                    return false;
                } else {
                    point.setBorderLeft(false);
                    return true;
                }
            case "right":
                if (point.isBorderRight()) {
                    return false;
                } else {
                    point.setBorderRight(false);
                    return true;
                }
            case "top":
                if (point.isBorderTop()) {
                    return false;
                } else {
                    point.setBorderTop(false);
                    return true;
                }
            case "bottom":
                if (point.isBorderBottom()) {
                    return false;
                } else {
                    point.setBorderBottom(false);
                    return true;
                }
            default:
                return false;
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