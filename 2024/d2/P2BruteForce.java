import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.ArrayList;

public class P2BruteForce {
  public static boolean isSafe(ArrayList<Integer> arr) {
    int numConsistentLevelChanges = 0;
    boolean isOutOfDiffRange = false;
    for (int i = 0; i < arr.toArray().length - 1; i++) {
      int curr = arr.get(i);
      int adjacent = arr.get(i + 1);
      int diff = Math.abs(curr - adjacent);
      if (!(diff >= 1 && diff <= 3)) {
        isOutOfDiffRange = true;
        break;
      }
      if(curr < adjacent) {
        numConsistentLevelChanges++;
      } else if(curr > adjacent) {
        numConsistentLevelChanges--;
      }
    }
    if(isOutOfDiffRange) {
      return false;
    }
    return Math.abs(numConsistentLevelChanges) == arr.toArray().length - 1;
  }

  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(new File("./input.txt"));
      int numSafeReports = 0;
      while(scanner.hasNextLine()) {
        String[] reportStr = scanner.nextLine().split(" ");
        ArrayList<Integer> report = new ArrayList<>();
        for (int i = 0; i < reportStr.length; i++) {
          report.add(Integer.parseInt(reportStr[i]));
        }
        if(P2BruteForce.isSafe(report)) {
          numSafeReports++;
          continue;
        }
        for (int i = 0; i < report.toArray().length - 1; i++) {
          ArrayList<Integer> arr = new ArrayList<>();
          for(int l = 0; l < i; l++) {
            arr.add(report.get(l));
          }
          for(int r = i + 1; r < report.toArray().length; r++) {
            arr.add(report.get(r));
          }
          if(P2BruteForce.isSafe(arr)) {
            numSafeReports++;
            break;
          }
        }
      }
      System.out.println("Number of safe reports: " + numSafeReports);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}


