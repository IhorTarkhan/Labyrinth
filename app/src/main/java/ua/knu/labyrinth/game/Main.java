package ua.knu.labyrinth.game;

public class Main {
    public static void main() {
        Map.generateMap(10);

        /*
        List<List<Point>> matrix = map.getMatrix();

        for (int i = 0; i < matrix.size(); i++) {
            List<Point> row = matrix.get(i);
            for (int j = 0; j < row.size(); j++) {
                Point point = row.get(j);
                if (point.isBorderRight()) {
                    System.out.print(" b ");
                } else {
                    System.out.print(" e ");
                }
            }
            System.out.println();
        }*/

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