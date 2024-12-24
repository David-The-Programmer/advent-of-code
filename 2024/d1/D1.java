import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.lang.Math;

public class D1 {

  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      ArrayList<Integer> left = new ArrayList<>();
      ArrayList<Integer> right = new ArrayList<>();
      while(scanner.hasNextLine()) {
        String[] ids = scanner.nextLine().split("   ");
        left.add(Integer.parseInt(ids[0]));
        right.add(Integer.parseInt(ids[1]));
      }
      left.sort((a, b) -> a - b);
      right.sort((a, b) -> a - b);
      int totalDistance = 0;
      for(int i = 0; i < left.toArray().length; i++) {
        totalDistance += Math.abs(left.get(i) - right.get(i));
      }
      System.out.println("Total Distance: " + totalDistance);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

}
