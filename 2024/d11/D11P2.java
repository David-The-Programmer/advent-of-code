import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

public class D11P2 {
  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      String[] line = scanner.nextLine().split(" ");
      int numBlinks = 75;
      HashMap<Pair<String, Integer>, Long> visited = new HashMap<>();
      long count = 0;
      for(String ch: line) {
        count += dfs(ch, numBlinks, visited);
      }
      System.out.println(count);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static long dfs(String engraving, int numBlinks, HashMap<Pair<String, Integer>, Long> visited) {
    if(numBlinks == 0) {
      return 1;
    }
    Pair<String, Integer> p = new Pair<>(engraving, numBlinks);
    if(visited.containsKey(p)) {
      return visited.get(p);
    }
    long count = 0;
    List<String> engravings = afterBlink(engraving);
    for(String e : engravings) {
      count += dfs(e, numBlinks - 1, visited);
    }
    visited.put(p, count);
    return count;
  }

  public static String removeLeadingZeros(String s) {
    String newStr = Arrays.stream(s.split("")).dropWhile(x -> x.equals("0")).reduce("", (x, y) -> x + y);
    if(newStr.equals("")) {
      return "0";
    }
    return newStr;
  }

  public static List<String> afterBlink(String engraving) {
    if(engraving.equals("0")) {
      return List.of("1");
    }
    if(engraving.length() % 2 == 0) {
      String left = engraving.substring(0, engraving.length() / 2);
      left = removeLeadingZeros(left);
      String right = engraving.substring(engraving.length() / 2, engraving.length());
      right = removeLeadingZeros(right);
      return List.of(left, right);
    }
    return List.of(String.valueOf(Long.parseLong(engraving) * 2024));
  }
}
