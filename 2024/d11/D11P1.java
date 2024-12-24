import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Stack;
import java.util.HashMap;

public class D11P1 {
  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      List<String> stones = new ArrayList<>();
      String[] line = scanner.nextLine().split(" ");
      for(String ch: line) {
        stones.add(ch);
      }
      int blinks = 25;

      for(int i = 0; i < blinks; i++) {
        for(int x = 0; x < stones.size(); x++) {
          String engraving = stones.get(x);
          if(engraving.equals("0")) {
            stones.set(x, "1");
            continue;
          }
          if(engraving.length() % 2 == 0) {
            String left = engraving.substring(0, engraving.length() / 2);
            left = removeLeadingZeros(left);
            String right = engraving.substring(engraving.length() / 2, engraving.length());
            right = removeLeadingZeros(right);
            stones.set(x, left);
            stones.add(x + 1, right);
            x++;
            continue;
          }
          stones.set(x, String.valueOf(Long.parseLong(engraving) * 2024));
        }
      }
      System.out.println(stones.size());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static String removeLeadingZeros(String s) {
    String newStr = Arrays.stream(s.split("")).dropWhile(x -> x.equals("0")).reduce("", (x, y) -> x + y);
    if(newStr.equals("")) {
      return "0";
    }
    return newStr;
  }
}
