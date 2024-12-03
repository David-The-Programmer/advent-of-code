import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

public class D3P2 {

  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      int prodSum = 0;
      Pattern pattern = Pattern.compile("mul\\([0-9]+,[0-9]+\\)|do\\(\\)|don't\\(\\)");
      boolean enabled = true;
      while(scanner.hasNextLine()) {
        String s = scanner.nextLine();
        Matcher matcher = pattern.matcher(s);
        while(matcher.find()) {
          String subStr = s.substring(matcher.start(), matcher.end()).trim();
          if(subStr.equals("don't()")) {
            enabled = false;
          } else if(subStr.equals("do()")) {
            enabled = true;
          } else if(enabled) {
            subStr = subStr.substring(4, subStr.length() - 1);
            prodSum += Arrays
            .stream(subStr.split(","))
            .map(x -> Integer.parseInt(x))
            .reduce(1, (acc, x) -> acc * x);
          }
        }
      }
      System.out.println("Total sum of products is " + prodSum);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

}
