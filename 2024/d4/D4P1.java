import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.util.HashMap;
import java.util.Arrays;

public class D4P1 {

  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      List<List<String>> grid = new ArrayList<>();
      while(scanner.hasNextLine()) {
        grid.add(Arrays.stream(scanner.nextLine().split("")).toList());
      }
      Point[] checks = new Point[]{
        new Point(-1, 0),
        new Point(1, 0),
        new Point(0, -1),
        new Point(0, 1),
        new Point(-1, -1),
        new Point(1, -1),
        new Point(-1, 1),
        new Point(1, 1),
      };
      int numTimesXmasAppears = 0;
      for(int r = 0; r < grid.size(); r++) {
        for(int c = 0; c < grid.get(0).size(); c++) {
          if(!grid.get(r).get(c).equals("X")) {
            continue;
          }
          String[] chars = new String[]{"M", "A", "S"};
          for(Point pt: checks) {
            int matchCount = 0;
            int x = c;
            int y = r;
            for(int i = 0; i < 3; i++) {
              x += pt.getX();
              y += pt.getY();
              if(x < 0 || x > grid.get(0).size() - 1) {
                break;
              }
              if(y < 0 || y > grid.size() - 1) {
                break;
              }
              if(!grid.get(y).get(x).equals(chars[i])) {
                break;
              }
              matchCount++;
            }
            if(matchCount == 3) {
              numTimesXmasAppears++;
            }
          }
        }
      }
      System.out.println("Number of times XMAS is printed " + numTimesXmasAppears);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }
}
