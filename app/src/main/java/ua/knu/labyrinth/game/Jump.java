package ua.knu.labyrinth.game;

public class Jump {
    Point p1;
    Point p2;
    boolean isBorder;

    public Jump(Point p1, Point p2, boolean isBorder) {
        this.p1 = p1;
        this.p2 = p2;
        this.isBorder = isBorder;
    }

    public Point getOppositeTo(Point p) {
        if (p == p1) {
            return p2;
        }
        if (p == p2) {
            return p1;
        }
        throw new IllegalArgumentException();
    }

    public void setBorder(boolean border) {
        isBorder = border;
    }
}
