import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Stack;
import java.util.HashMap;

public class D10P2 {
  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      List<List<Integer>> map = new ArrayList<>();
      while(scanner.hasNextLine()) {
        map.add(Arrays.stream(scanner.nextLine().split("")).map(x -> Integer.parseInt(x)).toList());
      }
      List<List<Integer>> trailHeads = new ArrayList<>();
      for(int r = 0; r < map.size(); r++) {
        for(int c = 0; c < map.get(0).size(); c++) {
          if(map.get(r).get(c) == 0) {
            trailHeads.add(List.of(c, r, 0));
          }
        }
      }
      long sum = 0;
      for(List<Integer> th : trailHeads) {
        sum += findScore(map, th);
      }
      System.out.println(sum);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static int findScore(List<List<Integer>> map, List<Integer> trailHead) {
    int rating = 0;
    Stack<List<Integer>> stack = new Stack<>();
    stack.push(trailHead);
    List<List<Integer>> directions = List.of(List.of(-1, 0), List.of(1, 0), List.of(0, -1), List.of(0, 1));
    HashMap<Integer,List<Integer>> visitedHighestPos = new HashMap<>();
    while(!stack.isEmpty()) {
      List<Integer> cell = stack.pop();
      int x = cell.get(0);
      int y = cell.get(1);
      int currHeight = cell.get(2);
      for(List<Integer> dir: directions) {
        int newX = x + dir.get(0);
        int newY = y + dir.get(1);
        if(newX < 0 || newX > map.get(0).size() - 1) {
          continue;
        }
        if(newY < 0 || newY > map.get(0).size() - 1) {
          continue;
        }
        int nextHeight = map.get(newY).get(newX);
        if(nextHeight - currHeight != 1) {
          continue;
        }
        if(nextHeight == 9) {
          if(visitedHighestPos.containsKey(newX)) {
            List<Integer> yPos = visitedHighestPos.get(newX);
            if(yPos.contains(newY)) {
              rating++;
              continue;
            } else {
              yPos.add(newY);
              visitedHighestPos.put(newX, yPos);
              rating++;
            }
          } else {
            List<Integer> yPos = new ArrayList<>();
            yPos.add(newY);
            visitedHighestPos.put(newX, yPos);
            rating++;
          }
        } else {
          stack.push(List.of(newX, newY, nextHeight));
        }
      }
    }
    return rating;
  }
}
