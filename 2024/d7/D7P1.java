import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class D7P1 {
  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      long sum = 0;
      while(scanner.hasNextLine()) {
        String[] eqn = scanner.nextLine().split(":");
        long rhs = Long.parseLong(eqn[0]);
        List<Long> lhs = Arrays.stream(eqn[1].trim().split(" ")).map(x -> Long.parseLong(x)).toList();
        Pair<Integer, Long> root = new Pair<>(0, lhs.get(0));
        Queue<Pair<Integer, Long>> q = new LinkedList<>();
        q.add(root);
        List<Long> rhsValues = new ArrayList<>();
        while(!q.isEmpty()) {
          Pair<Integer, Long> n = q.remove();
          int i = n.getFirst();
          long v = n.getSecond();
          Pair<Integer, Long> left = new Pair<>(i + 1, v * lhs.get(i + 1));
          Pair<Integer, Long> right = new Pair<>(i + 1, v + lhs.get(i + 1));
          if(i + 1 < lhs.size() - 1) {
            q.add(left);
            q.add(right);
          } else {
            rhsValues.add(left.getSecond());
            rhsValues.add(right.getSecond());
          }
        }
        for(Long v : rhsValues) {
          if(v == rhs) {
            sum += v;
            break;
          }
        }
      }
      System.out.println("Total calibration result " + sum);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }
}
