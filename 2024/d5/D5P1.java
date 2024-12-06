import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;

public class D5P1 {

  public static void main(String[] args) {
    try {
      HashMap<String, HashSet<String>> graph = new HashMap<>();
      Scanner scanner = new Scanner(new File("./input.txt"));
      while(scanner.hasNextLine()) {
        String s = scanner.nextLine();
        String[] nums = s.split("\\|");
        if(nums.length == 1) {
          break;
        }
        if(!graph.containsKey(nums[0])) {
          HashSet<String> set = new HashSet<>();
          set.add(nums[1]);
          graph.put(nums[0], set);
        } else {
          graph.get(nums[0]).add(nums[1]);
        }
      }
      int sum = 0;
      while(scanner.hasNextLine()) {
        String[] nums = scanner.nextLine().split(",");
        boolean isCorrect = true;
        for(int i = 0; i < nums.length - 1; i++) {
          String n = nums[i];
          String adj = nums[i + 1];
          if(graph.containsKey(n)) {
            if(graph.get(n).contains(adj)) {
              continue;
            } 
            if(!graph.containsKey(adj)) {
              continue;
            }
            if(graph.get(adj).contains(n)) {
              isCorrect = false;
              break;
            } 
          } else {
            if(!graph.containsKey(adj)) {
              continue;
            }
            if(graph.get(adj).contains(n)) {
              isCorrect = false;
              break;
            } 
          }
        }
        if(isCorrect) {
          sum += Integer.parseInt(nums[nums.length / 2]);
        }
      }
      System.out.println("Sum = " + sum);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }
}
