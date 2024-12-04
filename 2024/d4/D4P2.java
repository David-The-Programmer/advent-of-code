import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.lang.StringBuilder;

public class D4P2 {

  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      List<List<String>> grid = new ArrayList<>();
      while(scanner.hasNextLine()) {
        grid.add(Arrays.stream(scanner.nextLine().split("")).toList());
      }
      Point[] checks = new Point[]{
        new Point(-1, -1), // top left
        new Point(1, 1), // bottom right
        new Point(1, -1), // top right
        new Point(-1, 1), // bottom left
      };
      int numTimesXmasAppears = 0;
      for(int r = 0; r < grid.size(); r++) {
        for(int c = 0; c < grid.get(0).size(); c++) {
          if(!grid.get(r).get(c).equals("A")) {
            continue;
          }
          if(r == 0 || r == grid.size() - 1) {
            continue;
          }
          if(c == 0 || c == grid.get(0).size() - 1) {
            continue;
          }
          String topLeft = grid.get(r - 1).get(c - 1);
          String bottomRight = grid.get(r + 1).get(c + 1);
          String topRight = grid.get(r - 1).get(c + 1);
          String bottomLeft = grid.get(r + 1).get(c - 1);
          String s1 = topLeft + "A" + bottomRight;
          String s2 = topRight + "A" + bottomLeft;

          if((s1.equals("MAS") || D4P2.reverse(s1).equals("MAS")) && (s2.equals("MAS") || D4P2.reverse(s2).equals("MAS"))) {
            numTimesXmasAppears++;
          }
        }
      }
      System.out.println("Number of times XMAS is printed " + numTimesXmasAppears);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

  public static String reverse(String s) {
    return new StringBuilder().append(s).reverse().toString();
  }
}
