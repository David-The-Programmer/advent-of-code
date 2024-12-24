import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;

public class D5P2 {

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
          if(D5P2.isCorrectOrder(graph, n, adj)) {
            continue;
          } else {
            isCorrect = false;
            break;
          }
        }
        if(!isCorrect) {
          List<String> l = Arrays.stream(nums).sorted((a, b) -> {
            if(!D5P2.isCorrectOrder(graph, a, b)) {
              return 1;
            }
            return -1;
          }).toList();
          sum += Integer.parseInt(l.get(l.size() / 2));
        }
      }
      System.out.println("Sum = " + sum);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

  public static boolean isCorrectOrder(HashMap<String, HashSet<String>> graph, String n, String adj) {
    if(graph.containsKey(n)) {
      if(graph.get(n).contains(adj)) {
        return true;
      } 
      if(!graph.containsKey(adj)) {
        return true;
      }
      if(graph.get(adj).contains(n)) {
        return false;
      } 
    } else {
      if(!graph.containsKey(adj)) {
        return true;
      }
      if(graph.get(adj).contains(n)) {
        return false;
      } 
    }
    // should not reach here lol
    return false;
  }
}
