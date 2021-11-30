package ua.knu.labyrinth.game;

public class Point {
    public Jump leftJump;
    public Jump rightJump;
    public Jump topJump;
    public Jump bottomJump;
    public String name;

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
}
