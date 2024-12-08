import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class D8P2 {
  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      List<List<String>> grid = new ArrayList<>();
      while(scanner.hasNextLine()) {
        grid.add(Arrays.asList(scanner.nextLine().split("")));
      }
      List<Pair<String, List<Integer>>> antennas = new ArrayList<>();
      for (int r = 0; r < grid.size(); r++) {
        for (int c = 0; c < grid.get(0).size(); c++) {
          String cell = grid.get(r).get(c);
          if(D8P1.isAlphaNumeric(cell.charAt(0))) {
            antennas.add(new Pair<>(cell, List.of(c, r)));
          }
        }
      }
      HashMap<Integer, List<Integer>> antiNodePos = new HashMap<>();
      int uniqueNumAntiNodes = 0;
      for(Pair<String, List<Integer>> a : antennas) {
        String v1 = a.getFirst();
        int x1 = a.getSecond().get(0);
        int y1 = a.getSecond().get(1);
        for(Pair<String, List<Integer>> b : antennas) {
          String v2 = b.getFirst();
          int x2 = b.getSecond().get(0);
          int y2 = b.getSecond().get(1);
          if(x1 == x2 && y1 == y2) {
            continue;
          }
          if(!v1.equals(v2)) {
            continue;
          }
          List<List<Integer>> antennasInLine = List.of(List.of(x1, y1), List.of(x2, y2));
          List<List<Integer>> antiNodes = new ArrayList<>();
          int k = -1;
          while(true) {
            List<Integer> antiNodeA = vectorPosOnLine(antennasInLine.get(0), dirVector(antennasInLine.get(1), antennasInLine.get(0)), k);
            if(isOutOfMap(grid, antiNodeA)) {
              break;
            }
            antiNodes.add(antiNodeA);
            k--;
          }
          k = 1;
          while(true) {
            List<Integer> antiNodeB = vectorPosOnLine(antennasInLine.get(0), dirVector(antennasInLine.get(1), antennasInLine.get(0)), k);
            if(isOutOfMap(grid, antiNodeB)) {
              break;
            }
            antiNodes.add(antiNodeB);
            k++;
          }
          for(List<Integer> n: antiNodes) {
            if(antiNodePos.containsKey(n.get(0))) {
              List<Integer> yPos = antiNodePos.get(n.get(0));
              if(!yPos.contains(n.get(1))) {
                yPos.add(n.get(1));
                antiNodePos.put(n.get(0), yPos);
                uniqueNumAntiNodes++;
              }
            } else {
              List<Integer> yPos = new ArrayList<>();
              yPos.add(n.get(1));
              antiNodePos.put(n.get(0), yPos);
              uniqueNumAntiNodes++;
            }
          }
        }
      }
      antiNodePos.forEach((k, v) -> {
        for(int i = 0; i < v.size(); i++) {
          grid.get(v.get(i)).set(k, "#");
        }
      });
      printMap(grid);
      System.out.println("Unique number of antinodes " + uniqueNumAntiNodes);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void printMap(List<List<String>> grid) {
    for (int r = 0; r < grid.size(); r++) {
      for (int c = 0; c < grid.get(0).size(); c++) {
        System.out.print(grid.get(r).get(c));
      }
      System.out.println();
    }
  }

  public static boolean isOutOfMap(List<List<String>> grid, List<Integer> antiNode) {
    if(antiNode.get(0) < 0 || antiNode.get(0) > grid.get(0).size() - 1) {
      return true;
    }
    if(antiNode.get(1) < 0 || antiNode.get(1) > grid.size() - 1) {
      return true;
    }
    return false;
  }

  public static List<Integer> dirVector(List<Integer> vectA, List<Integer> vectB) {
    return List.of(vectA.get(0) - vectB.get(0), vectA.get(1) - vectB.get(1));
  }

  public static List<Integer> vectorPosOnLine(List<Integer> posVect, List<Integer> dirVect, int scalar) {
    int newX = posVect.get(0) + scalar * dirVect.get(0);
    int newY = posVect.get(1) + scalar * dirVect.get(1);
    return List.of(newX, newY);
  }

  public static int absXDist(int x1, int x2) {
    return Math.abs(x1 - x2);
  }

  public static int absYDist(int y1, int y2) {
    return Math.abs(y1 - y2);
  }

  public static boolean isAlphaNumeric(char c) {
    return isAlphabet(c) || isDigit(c);
  }

  public static boolean isAlphabet(char c) {
    return (c >= 'a' && c <= 'z') ||
  (c >= 'A' && c <= 'Z');
  }

  public static boolean isDigit(char c) {
    return c >= '0' && c <= '9';
  }
}
