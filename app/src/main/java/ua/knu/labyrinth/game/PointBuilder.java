package ua.knu.labyrinth.game;

public class PointBuilder {
    private Point rightPoint = null;
    private boolean rightBorder = true;
    private Point bottomPoint = null;
    private boolean bottomBorder = true;

    public PointBuilder right(Point point, boolean border) {
        this.rightPoint = point;
        this.rightBorder = border;
        return this;
    }

    public PointBuilder bottom(Point point, boolean border) {
        this.bottomPoint = point;
        this.bottomBorder = border;
        return this;
    }

    public Point build(String name) {
        return new Point(name, null, true, this.rightPoint, this.rightBorder, null, true, this.bottomPoint, this.bottomBorder);
    }
}
