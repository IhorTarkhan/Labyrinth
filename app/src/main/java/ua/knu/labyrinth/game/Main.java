package ua.knu.labyrinth.game;

import java.util.List;

public class Main {
    public static void main() {
        System.out.println("-----------------tut-----------------");
        Point c3 = Point.builder().build("c3");

        Point c2 = Point.builder()
                .right(c3, false)
                .build("c2");

        Point c1 = Point.builder()
                .right(c2, true)
                .build("c1");

        Point b3 = Point.builder()
                .bottom(c3, true)
                .build("b3");

        Point b2 = Point.builder()
                .bottom(c2, false)
                .right(b3, true)
                .build("b2");

        Point b1 = Point.builder()
                .bottom(c1, false)
                .right(b2, false)
                .build("b1");

        Point a3 = Point.builder()
                .bottom(b3, false)
                .build("a3");

        Point a2 = Point.builder()
                .bottom(b2, false)
                .right(a3, false)
                .build("a2");

        Point a1 = Point.builder()
                .bottom(b1, true)
                .right(a2, true)
                .build("a1");

        Map map = new Map(a1);

        List<List<Point>> matrix = map.getMatrix();

        for (int i = 0; i < matrix.size(); i++) {
            List<Point> row = matrix.get(i);
            for (int j = 0; j < row.size(); j++) {
                Point point = row.get(j);
                System.out.print(point.name);
                if (point.rightJump.isBorder) {
                    System.out.print(" b ");
                } else {
                    System.out.print(" e ");
                }
            }
            System.out.println();
        }

        System.out.println("-----------------tam-----------------");
    }
}

/*

a1 - a2   a3
--
b1   b2 - b3
          --
c1 - c2   c3

*/