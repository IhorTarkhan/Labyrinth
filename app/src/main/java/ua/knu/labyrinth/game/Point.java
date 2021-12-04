package ua.knu.labyrinth.game;

public class Point {
    private Jump leftJump;
    private Jump rightJump;
    private Jump topJump;
    private Jump bottomJump;
    private String name;

    public Point(
            String name,
            Point leftPoint, boolean leftBorder,
            Point rightPoint, boolean rightBorder,
            Point topPoint, boolean topBorder,
            Point bottomPoint, boolean bottomBorder
    ) {
        this.name = name;

        Jump leftJump = new Jump(this, leftPoint, leftBorder);
        this.leftJump = leftJump;
        if (leftPoint != null) {
            leftPoint.rightJump = leftJump;
        }

        Jump rightJump = new Jump(this, rightPoint, rightBorder);
        this.rightJump = rightJump;
        if (rightPoint != null) {
            rightPoint.leftJump = rightJump;
        }

        Jump topJump = new Jump(this, topPoint, topBorder);
        this.topJump = topJump;
        if (topPoint != null) {
            topPoint.bottomJump = topJump;
        }

        Jump bottomJump = new Jump(this, bottomPoint, bottomBorder);
        this.bottomJump = bottomJump;
        if (bottomPoint != null) {
            bottomPoint.topJump = bottomJump;
        }
    }

    public static PointBuilder builder() {
        return new PointBuilder();
    }

    public Point getRight() {
        return rightJump.getOppositeTo(this);
    }

    public Point getBottom() {
        return bottomJump.getOppositeTo(this);
    }

    public Point getLeft() {
        return leftJump.getOppositeTo(this);
    }

    public Point getTop() {
        return topJump.getOppositeTo(this);
    }

    public boolean isBorderRight() {
        return rightJump.isBorder;
    }

    public boolean isBorderBottom() {
        return bottomJump.isBorder;
    }

    public boolean isBorderLeft() {
        return leftJump.isBorder;
    }

    public boolean isBorderTop() {
        return topJump.isBorder;
    }

    public boolean isBorderDirection(Direction direction) {
        boolean result;
        switch (direction) {
            case LEFT:
                result = isBorderLeft();
                break;
            case RIGHT:
                result = isBorderRight();
                break;
            case TOP:
                result = isBorderTop();
                break;
            case BOTTOM:
                result = isBorderBottom();
                break;
            default:
                result = true;
        }
        return result;
    }

    public void setBorderLeft(boolean border) {
        leftJump.setBorder(border);
    }

    public void setBorderRight(boolean border) {
        rightJump.setBorder(border);
    }

    public void setBorderTop(boolean border) {
        topJump.setBorder(border);
    }

    public void setBorderBottom(boolean border) {
        bottomJump.setBorder(border);
    }
}
